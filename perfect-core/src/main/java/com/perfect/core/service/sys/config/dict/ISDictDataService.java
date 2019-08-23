package com.perfect.core.service.sys.config.dict;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.sys.config.dict.SDictDataEntity;
import com.perfect.bean.vo.sys.config.dict.SDictDataVo;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
public interface ISDictDataService extends IService<SDictDataEntity> {
    /**
     * 获取列表，页面查询
     */
    IPage<SDictDataEntity> selectPage(SDictDataVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所有数据
     */
    List<SDictDataEntity> select(SDictDataVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    List<SDictDataEntity> selectIdsIn(List<SDictDataVo> searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    boolean saveBatches(List<SDictDataEntity> entityList);

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    void deleteByIdsIn(List<SDictDataVo> searchCondition);
}
