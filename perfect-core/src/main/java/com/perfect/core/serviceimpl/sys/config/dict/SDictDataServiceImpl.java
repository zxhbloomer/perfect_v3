package com.perfect.core.serviceimpl.sys.config.dict;

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
    private SDictDataMapper mapper;

    /**
     * 获取列表，页面查询
     *
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public IPage<SDictDataVo> selectPage(SDictDataVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        // 分页条件
        Page<SDictTypeEntity> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, SDictTypeEntity.class, searchCondition.getPageCondition().getSort());
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
    public List<SDictDataVo> select(SDictDataVo searchCondition) {
        // 查询 数据
        List<SDictDataVo> list = mapper.select(searchCondition);
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
    public List<SDictDataVo> selectIdsIn(List<SDictDataVo> searchCondition) {
        // 查询 数据
        List<SDictDataVo> list = mapper.selectIdsIn(searchCondition);
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
        List<SDictDataVo> list = mapper.selectIdsIn(searchCondition);
        list.forEach(
            bean -> {
                bean.setIsdel(!bean.getIsdel());
            }
        );
        List<SDictDataEntity> entityList = BeanUtilsSupport.copyProperties(list, SDictDataEntity.class);
        super.saveOrUpdateBatch(entityList, 500);
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsertResult<Integer> insert(SDictDataEntity entity) {
        // 插入前check
        CheckResult cr = checkLogic(entity.getDict_value(),entity.getLabel());
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 设置：字典键值和字典排序
        SDictDataEntity data = mapper.getSortNum(entity.getDict_type_id());
        entity.setSort(data.getSort());
        // 插入逻辑保存
        return InsertResultUtil.OK(mapper.insert(entity));
    }

    /**
     * 更新一条记录（选择字段，策略更新）
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateResult<Integer> update(SDictDataEntity entity) {
        // 更新前check
        CheckResult cr = checkLogic(entity.getDict_value(),entity.getLabel());
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 更新逻辑保存
        return UpdateResultUtil.OK(mapper.updateById(entity));
    }

    /**
     * 获取列表，查询所有数据
     *
     * @param dict_value
     * @return
     */
    @Override
    public List<SDictDataEntity> selectByDictValue(String dict_value) {
        // 查询 数据
        List<SDictDataEntity> list = mapper.selectByDictValue(dict_value);
        return list;
    }

    /**
     * 获取列表，查询所有数据
     *
     * @param lable
     * @return
     */
    @Override
    public List<SDictDataEntity> selectByLabel(String lable) {
        // 查询 数据
        List<SDictDataEntity> list = mapper.selectByLabel(lable);
        return list;
    }

    /**
     * check逻辑
     * @return
     */
    public CheckResult checkLogic(String dict_value,String label){

        List<SDictDataEntity> list;
        // dict_value查重
        list = selectByDictValue(dict_value);
        if(list.size() > 1){
            return CheckResultUtil.NG("字典键值出现重复", list);
        }
        // label查重
        list = selectByLabel(label);
        if(list.size() > 1){
            return CheckResultUtil.NG("字典标签出现重复", list);
        }

        return CheckResultUtil.OK();
    }
}
