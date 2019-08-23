package com.perfect.core.serviceimpl.sys.config.dict;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.sys.config.dict.SDictDataEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.vo.sys.config.dict.SDictDataVo;
import com.perfect.bean.vo.sys.config.dict.SDictTypeVo;
import com.perfect.bean.vo.sys.config.resource.SResourceVo;
import com.perfect.core.mapper.sys.config.dict.SDictDataMapper;
import com.perfect.core.mapper.sys.config.dict.SDictTypeMapper;
import com.perfect.core.service.sys.config.dict.ISDictDataService;
import com.perfect.core.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
@Service
public class SDictDataServiceImpl extends ServiceImpl<SDictDataMapper, SDictDataEntity> implements ISDictDataService {

    @Autowired
    private SDictDataMapper sDictDataMapper;

    /**
     * 获取列表，页面查询
     *
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public IPage<SDictDataEntity> selectPage(SDictDataVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        // 分页条件
        Page<SDictTypeEntity> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, SDictTypeEntity.class, searchCondition.getPageCondition().getSort());
        return sDictDataMapper.selectPage(pageCondition, searchCondition);
    }

    /**
     * 获取列表，查询所有数据
     *
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<SDictDataEntity> select(SDictDataVo searchCondition) {
        // 查询 数据
        List<SDictDataEntity> list = sDictDataMapper.select(searchCondition);
        return list;
    }

    /**
     * 获取列表，根据id查询所有数据
     *
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<SDictDataEntity> selectIdsIn(List<SDictDataVo> searchCondition) {
        // 查询 数据
        List<SDictDataEntity> list = sDictDataMapper.selectIdsIn(searchCondition);
        return list;
    }

    /**
     * 批量导入逻辑
     *
     * @param entityList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatches(List<SDictDataEntity> entityList) {
        return super.saveBatch(entityList, 500);
    }

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIdsIn(List<SDictDataVo> searchCondition) {
        List<SDictDataEntity> list = sDictDataMapper.selectIdsIn(searchCondition);
        list.forEach(
            bean -> {
                bean.setIsdel(!bean.getIsdel());
            }
        );
        saveOrUpdateBatch(list, 500);
    }
}
