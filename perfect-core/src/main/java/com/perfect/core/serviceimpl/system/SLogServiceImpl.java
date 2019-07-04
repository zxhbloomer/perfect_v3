package com.perfect.core.serviceimpl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.system.SLogEntity;
import com.perfect.core.mapper.system.SLogMapper;
import com.perfect.core.service.system.ISLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-07-04
 */
@Service
public class SLogServiceImpl extends ServiceImpl<SLogMapper, SLogEntity> implements ISLogService {

}
