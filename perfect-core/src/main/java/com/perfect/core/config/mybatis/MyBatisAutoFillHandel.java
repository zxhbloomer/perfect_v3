package com.perfect.core.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.perfect.common.utils.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Slf4j
public class MyBatisAutoFillHandel implements MetaObjectHandler {

    /**
     * 新增的时候自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info(" ....新增的时候自动填充 ....");
//        Object cTime = this.getFieldValByName("cTime", metaObject);
//        Object cId = this.getFieldValByName("cId", metaObject);
        this.setFieldValByName("cTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("uTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("dbversion", 0, metaObject);

        this.setFieldValByName("cId", SecurityUtil.getLoginUserId(), metaObject);
        this.setFieldValByName("uId", SecurityUtil.getLoginUserId(), metaObject);

    }

    /**
     * 更新的时候自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info(" ....更新的时候自动填充 ....");
//        Object uTime = this.getFieldValByName("uTime", metaObject);
//        Object uId = this.getFieldValByName("uId", metaObject);
//        Object dbversion = this.getFieldValByName("dbversion", metaObject);

        this.setFieldValByName("uTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("uId", SecurityUtil.getLoginUserId(), metaObject);
//        this.setFieldValByName("dbversion", Integer.valueOf(dbversion.toString()) + 1, metaObject);


        // todo: 这里找到session后，加入uId
    }
}
