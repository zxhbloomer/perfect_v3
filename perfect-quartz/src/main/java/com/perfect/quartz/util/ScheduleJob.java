package com.perfect.quartz.util;

import com.perfect.bean.entity.quartz.JJobLogEntity;
import com.perfect.bean.entity.quartz.JJobMasterEntity;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.service.quartz.IJJobLogService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务
 *
 *
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Autowired
    IJJobLogService scheduleJobLogService;
    @Autowired
    JJobLogEntity jJobLogEntity;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        JJobMasterEntity scheduleJob = (JJobMasterEntity) context.getMergedJobDataMap().get(
            PerfectConstant.JOB_PARAM_KEY);

        BeanUtilsSupport.copyProperties(scheduleJob,jJobLogEntity);

        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            log.debug("任务准备执行，任务ID：{}", scheduleJob.getId());
            log.debug("任务准备执行，任务名称：{}", scheduleJob.getJobName());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(),
                    scheduleJob.getParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            jJobLogEntity.setCost(times);
            // 任务状态 true：成功 false：失败
            jJobLogEntity.setStatus(true);

            log.info("任务执行完毕，任务ID：{} 总共耗时：{} 毫秒", scheduleJob.getId(), times);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getId(), e);
            long times = System.currentTimeMillis() - startTime;
            jJobLogEntity.setCost(times);
            // 任务状态 0：成功 1：失败
            jJobLogEntity.setStatus(false);
            jJobLogEntity.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(jJobLogEntity);
        }
    }
}
