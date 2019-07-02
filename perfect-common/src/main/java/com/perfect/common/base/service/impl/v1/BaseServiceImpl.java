package com.perfect.common.base.service.impl.v1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.common.base.service.v1.IBaseService;

/**
 * 扩展Mybatis-Plus接口
 *
 * @author ruxuanwo
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M,T> implements IBaseService<T> {

}