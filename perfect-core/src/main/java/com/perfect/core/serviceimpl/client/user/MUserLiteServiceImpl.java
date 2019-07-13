package com.perfect.core.serviceimpl.client.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.client.user.MUserLiteEntity;
import com.perfect.core.mapper.client.user.MUserLiteMapper;
import com.perfect.core.service.client.user.IMUserLiteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 简单 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Service
public class MUserLiteServiceImpl extends ServiceImpl<MUserLiteMapper, MUserLiteEntity> implements IMUserLiteService {

}
