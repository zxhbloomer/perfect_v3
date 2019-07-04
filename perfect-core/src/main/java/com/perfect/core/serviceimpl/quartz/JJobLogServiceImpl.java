package com.perfect.core.serviceimpl.quartz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.quartz.JJobLogEntity;
import com.perfect.core.mapper.quartz.JJobLogMapper;
import com.perfect.core.service.quartz.IJJobLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-07-04
 */
@Service
public class JJobLogServiceImpl extends ServiceImpl<JJobLogMapper, JJobLogEntity> implements
    IJJobLogService {

}
