package com.perfect.core.service.sys.config.module;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import com.perfect.bean.vo.sys.config.resource.SResourceVo;
import com.perfect.bean.vo.sys.module.SModuleVo;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
public interface IModuleService extends IService<SModuleEntity> {
    /**
     * 获取列表，页面查询
     */
    IPage<SModuleEntity> selectPage(SModuleVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所有数据
     */
    List<SModuleEntity> select(SModuleVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    List<SModuleEntity> selectIdsIn(List<SModuleVo> searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    boolean saveBatches(List<SModuleEntity> entityList);

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    void deleteByIdsIn(List<SModuleVo> searchCondition);

}
