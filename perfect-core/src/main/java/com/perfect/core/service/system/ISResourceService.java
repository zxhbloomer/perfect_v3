package com.perfect.core.service.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.system.SResourceEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.vo.sys.resource.SResourceVo;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
public interface ISResourceService extends IService<SResourceEntity> {
    /**
     * 获取列表，页面查询
     */
    IPage<SResourceEntity> selectPage(SResourceVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所有数据
     */
    List<SResourceEntity> select(SResourceVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    List<SResourceEntity> selectIdsIn(List<SResourceVo> searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    boolean saveBatches(List<SResourceEntity> entityList);
}
