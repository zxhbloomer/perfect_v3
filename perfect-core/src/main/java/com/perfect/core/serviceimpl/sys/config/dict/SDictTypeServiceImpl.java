package com.perfect.core.serviceimpl.sys.config.dict;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.result.utils.v1.InsertResultUtil;
import com.perfect.bean.result.utils.v1.UpdateResultUtil;
import com.perfect.bean.vo.sys.config.dict.SDictTypeVo;
import com.perfect.common.exception.BusinessException;
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
    private SDictTypeMapper mapper;

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
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
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
    public List<SDictTypeEntity> select(SDictTypeVo searchCondition) {
        // 查询 数据
        List<SDictTypeEntity> list = mapper.select(searchCondition);
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
        List<SDictTypeEntity> list = mapper.selectIdsIn(searchCondition);
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
        List<SDictTypeEntity> list = mapper.selectIdsIn(searchCondition);
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
    public InsertResult<Integer> insert(SDictTypeEntity entity) {
        // 插入前check
        CheckResult cr = checkLogic(entity.getCode());
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
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
    public UpdateResult<Integer> update(SDictTypeEntity entity) {
        // 更新前check
        CheckResult cr = checkLogic(entity.getCode());
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 更新逻辑保存
        return UpdateResultUtil.OK(mapper.updateById(entity));
    }

    /**
     * 获取列表，查询所有数据
     *
     * @param code
     * @return
     */
    @Override
    public List<SDictTypeEntity> selectByCode(String code) {
        // 查询 数据
        List<SDictTypeEntity> list = mapper.selectByCode(code);
        return list;
    }

    /**
     * check逻辑
     * @return
     */
    public CheckResult checkLogic(String _code){
        // code查重
        List<SDictTypeEntity> list = selectByCode(_code);
        if(list.size() > 1){
            return CheckResultUtil.NG("字典类型出现重复", list);
        }

        return CheckResultUtil.OK();
    }
}
