package com.perfect.core.serviceimpl.sys.config.dict;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.vo.sys.config.dict.SDictTypeVo;
import com.perfect.core.mapper.sys.config.dict.SDictTypeMapper;
import com.perfect.core.service.sys.config.dict.ISDictTypeService;
import com.perfect.core.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    private SDictTypeMapper sDictTypeMapper;

    /**
     * 获取列表，页面查询
     *
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public IPage<SDictTypeEntity> selectPage(SDictTypeVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        // 分页条件
        Page<SDictTypeEntity> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, SDictTypeEntity.class, searchCondition.getPageCondition().getSort());
        return sDictTypeMapper.selectPage(pageCondition, searchCondition);
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
    public List<SDictTypeEntity> select(SDictTypeVo searchCondition) {
        // 查询 数据
        List<SDictTypeEntity> list = sDictTypeMapper.select(searchCondition);
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
    public List<SDictTypeEntity> selectIdsIn(List<SDictTypeVo> searchCondition) {
        // 查询 数据
        List<SDictTypeEntity> list = sDictTypeMapper.selectIdsIn(searchCondition);
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
    public boolean saveBatches(List<SDictTypeEntity> entityList) {
        return super.saveBatch(entityList, 500);
    }

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIdsIn(List<SDictTypeVo> searchCondition) {
        List<SDictTypeEntity> list = sDictTypeMapper.selectIdsIn(searchCondition);
        list.forEach(
            bean -> {
                bean.setIsdel(!bean.getIsdel());
            }
        );
        saveOrUpdateBatch(list, 500);
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(SDictTypeEntity entity) {
        // ch
        return super.save(entity);
    }

    public boolean checkLogic(){

    }
}
