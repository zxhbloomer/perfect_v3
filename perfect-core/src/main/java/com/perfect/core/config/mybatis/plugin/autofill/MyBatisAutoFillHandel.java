package com.perfect.core.config.mybatis.plugin.autofill;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.perfect.core.utils.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author zxh
 */
@Slf4j
public class MyBatisAutoFillHandel implements MetaObjectHandler {

    /**
     * 新增的时候自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info(" ....新增的时候自动填充 ....");
//        Object c_time = this.getFieldValByName("c_time", metaObject);
//        Object c_id = this.getFieldValByName("c_id", metaObject);
        this.setFieldValByName("c_time", LocalDateTime.now(), metaObject);
        this.setFieldValByName("u_time", LocalDateTime.now(), metaObject);
        this.setFieldValByName("dbversion", 0, metaObject);

        this.setFieldValByName("c_id", SecurityUtil.getLoginUserId(), metaObject);
        this.setFieldValByName("u_id", SecurityUtil.getLoginUserId(), metaObject);
        // 默认未删除
        this.setFieldValByName("isdel", false, metaObject);
        // 默认未启用
        this.setFieldValByName("isenable", false, metaObject);

    }

    /**
     * 更新的时候自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info(" ....更新的时候自动填充 ....");
//        Object u_time = this.getFieldValByName("u_time", metaObject);
//        Object u_id = this.getFieldValByName("u_id", metaObject);
//        Object dbversion = this.getFieldValByName("dbversion", metaObject);

        this.setFieldValByName("u_time", LocalDateTime.now(), metaObject);
        this.setFieldValByName("u_id", SecurityUtil.getLoginUserId(), metaObject);
//        this.setFieldValByName("dbversion", Integer.valueOf(dbversion.toString()) + 1, metaObject);


        // todo: 这里找到session后，加入uId
    }
}
