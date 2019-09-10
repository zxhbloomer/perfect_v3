package com.perfect.core.serviceimpl.sys.config.module;

import java.util.List;

import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.vo.sys.module.SModuleVo;
import com.perfect.core.mapper.sys.config.module.SModuleMapper;
import com.perfect.core.service.sys.config.module.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.core.utils.mybatis.PageUtil;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Service
public class SModuleServiceImpl extends ServiceImpl<SModuleMapper, SModuleEntity>
    implements IModuleService {

    @Autowired
    private SModuleMapper mapper;

    /**
     * 获取列表，页面查询
     * 
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public IPage<SModuleEntity> selectPage(SModuleVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        // 分页条件
        Page<SModuleEntity> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, SModuleEntity.class, searchCondition.getPageCondition().getSort());
        return mapper.selectPage(pageCondition, searchCondition);
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
    public List<SModuleEntity> select(SModuleVo searchCondition) {
        // 查询 数据
        List<SModuleEntity> list = mapper.select(searchCondition);
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
    public List<SModuleEntity> selectIdsIn(List<SModuleVo> searchCondition) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectIdsIn(searchCondition);
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
    public boolean saveBatches(List<SModuleEntity> entityList) {
        return super.saveBatch(entityList, 500);
    }

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIdsIn(List<SModuleVo> searchCondition) {
        List<SModuleEntity> list = mapper.selectIdsIn(searchCondition);
        list.forEach(
            bean -> {
                bean.setIsdel(!bean.getIsdel());
            }
        );
        saveOrUpdateBatch(list, 500);
    }
}
