package com.perfect.core.serviceimpl.common;

import java.util.List;

import com.perfect.bean.vo.common.component.NameAndValueVo;
import com.perfect.bean.vo.common.component.PerfectComponentVo;
import com.perfect.common.constant.PerfectConstant;
import com.perfect.common.constant.PerfectDictConstant;
import com.perfect.core.mapper.common.CommonComponentMapper;
import com.perfect.core.service.common.ICommonComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.sys.config.dict.SDictDataEntity;
import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.result.utils.v1.InsertResultUtil;
import com.perfect.bean.result.utils.v1.UpdateResultUtil;
import com.perfect.bean.vo.sys.config.dict.SDictDataVo;
import com.perfect.common.exception.BusinessException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.mapper.sys.config.dict.SDictDataMapper;
import com.perfect.core.service.sys.config.dict.ISDictDataService;
import com.perfect.core.utils.mybatis.PageUtil;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
@Service
public class CommonComponentServiceImpl extends ServiceImpl<CommonComponentMapper, NameAndValueVo> implements
    ICommonComponentService {

    @Autowired
    private CommonComponentMapper mapper;

    /**
     * 获取所有的下拉选项的数据bean
     * @return
     */
    @Override
    public PerfectComponentVo getAllSelectComponentBean(){
        PerfectComponentVo vo = new PerfectComponentVo();
        vo.setSelect_component_delete_map_normal(selectComponentDeleteMapNormal());
        vo.setSelect_component_delete_map_only_used_data(selectComponentDeleteMapOnlyUsedData());
        return vo;
    }

    /**
     * 下拉选项卡：删除类型字典
     * @return
     */
    @Override
    public List<NameAndValueVo> selectComponentDeleteMapNormal() {
        return mapper.getSelectDictDataNormal(PerfectDictConstant.SYS_DELETE_MAP);
    }

    /**
     * 下拉选项卡：删除类型字典
     * @return
     */
    @Override
    public List<NameAndValueVo> selectComponentDeleteMapOnlyUsedData() {
        return mapper.getSelectDictDataNormal(PerfectDictConstant.SYS_DELETE_MAP);
    }

}
