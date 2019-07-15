package com.perfect.core.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.perfect.common.utils.security.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

@Slf4j
public class MyBatisAutoFillHandel implements MetaObjectHandler {

    // 新增的时候自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        Object cTime = this.getFieldValByName("cTime", metaObject);
        Object cId = this.getFieldValByName("cId", metaObject);
        this.setFieldValByName("cTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("uTime", LocalDateTime.now(), metaObject);

        this.setFieldValByName("cId", SecurityUtil.getLoginUserId(), metaObject);
        this.setFieldValByName("uId", SecurityUtil.getLoginUserId(), metaObject);

    }

    // 更新的税后自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        Object uTime = this.getFieldValByName("uTime", metaObject);
        Object uId = this.getFieldValByName("uId", metaObject);

        this.setFieldValByName("uTime", LocalDateTime.now(), metaObject);

        this.setFieldValByName("uId", SecurityUtil.getLoginUserId(), metaObject);


        // todo: 这里找到session后，加入uId
    }
}
