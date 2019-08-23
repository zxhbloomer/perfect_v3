package com.perfect.core.service.sys.config.dict;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.vo.sys.config.dict.SDictDataVo;
import com.perfect.bean.vo.sys.config.dict.SDictTypeVo;
import com.perfect.bean.vo.sys.config.resource.SResourceVo;

import java.util.List;

/**
 * <p>
 * 字典类型表、字典主表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
public interface ISDictTypeService extends IService<SDictTypeEntity> {
    /**
     * 获取列表，页面查询
     */
    IPage<SDictTypeEntity> selectPage(SDictTypeVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所有数据
     */
    List<SDictTypeEntity> select(SDictTypeVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    List<SDictTypeEntity> selectIdsIn(List<SDictTypeVo> searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    boolean saveBatches(List<SDictTypeEntity> entityList);

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    void deleteByIdsIn(List<SDictTypeVo> searchCondition);
}