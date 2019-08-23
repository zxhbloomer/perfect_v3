package com.perfect.core.serviceimpl.sys.config.dict;

import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.core.mapper.sys.config.dict.SDictTypeMapper;
import com.perfect.core.service.sys.config.dict.ISDictTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典类型表、字典主表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
@Service
public class SDictTypeServiceImpl extends ServiceImpl<SDictTypeMapper, SDictTypeEntity> implements ISDictTypeService {

}
