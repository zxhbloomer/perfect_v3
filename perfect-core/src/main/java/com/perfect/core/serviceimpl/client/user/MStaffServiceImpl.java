package com.perfect.core.serviceimpl.client.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.client.user.MStaffEntity;
import com.perfect.core.mapper.client.user.MStaffMapper;
import com.perfect.core.service.client.user.IMStaffService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-07-13
 */
@Service
public class MStaffServiceImpl extends ServiceImpl<MStaffMapper, MStaffEntity> implements IMStaffService {

}
