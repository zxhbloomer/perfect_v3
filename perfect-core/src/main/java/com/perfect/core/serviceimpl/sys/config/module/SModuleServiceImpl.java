package com.perfect.core.serviceimpl.sys.config.module;

import java.util.List;

import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.result.utils.v1.InsertResultUtil;
import com.perfect.bean.result.utils.v1.UpdateResultUtil;
import com.perfect.bean.vo.sys.config.module.SModuleVo;
import com.perfect.common.exception.BusinessException;
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
public class SModuleServiceImpl extends ServiceImpl<SModuleMapper, SModuleEntity> implements IModuleService {

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
    public IPage<SModuleVo> selectPage(SModuleVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        // 分页条件
        Page<SModuleVo> pageCondition =
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
     * 
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIdsIn(List<SModuleVo> searchCondition) {
        List<SModuleEntity> list = mapper.selectIdsIn(searchCondition);
        list.forEach(bean -> {
            bean.setIsdel(!bean.getIsdel());
        });
        saveOrUpdateBatch(list, 500);
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsertResult<Integer> insert(SModuleEntity entity) {
        // 插入前check
        CheckResult cr = checkLogic(entity, CheckResult.INSERT_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 插入逻辑保存
        return InsertResultUtil.OK(mapper.insert(entity));
    }

    /**
     * 更新一条记录（选择字段，策略更新）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public UpdateResult<Integer> update(SModuleEntity entity) {
        // 更新前check
        CheckResult cr = checkLogic(entity, CheckResult.UPDATE_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 更新逻辑保存
        return UpdateResultUtil.OK(mapper.updateById(entity));
    }

    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    @Override
    public SModuleVo selectByid(Long id) {
        // 查询 数据
        return mapper.selectId(id);
    }

    /**
     * 查询by code，返回结果
     *
     * @param code
     * @return
     */
    @Override
    public List<SModuleEntity> selectByCode(String code) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectByCode(code);
        return list;
    }

    /**
     * 查询by 名称，返回结果
     *
     * @param name
     * @return
     */
    @Override
    public List<SModuleEntity> selectByName(String name) {
        // 查询 数据
        List<SModuleEntity> list = mapper.selectByName(name);
        return list;
    }

    /**
     * check逻辑，模块编号 or 模块名称 不能重复
     * 
     * @return
     */
    public CheckResult checkLogic(SModuleEntity entity, String moduleType) {
        List<SModuleEntity> listCode = selectByCode(entity.getCode());
        List<SModuleEntity> listName = selectByName(entity.getName());

        switch(moduleType){
            case CheckResult.INSERT_CHECK_TYPE :
                // 新增场合，不能重复
                if (listCode.size() >= 1) {
                    // 模块编号不能重复
                    return CheckResultUtil.NG("新增保存出错：模块编号出现重复", listCode);
                }
                if (listName.size() >= 1) {
                    // 模块名称不能重复
                    return CheckResultUtil.NG("新增保存出错：模块名称出现重复", listName);
                }
                break;
            case CheckResult.UPDATE_CHECK_TYPE :
                // 更新场合，不能重复设置
                if (listCode.size() >= 2) {
                    // 模块编号不能重复
                    return CheckResultUtil.NG("更新保存出错：模块编号出现重复", listCode);
                }
                if (listName.size() >= 2) {
                    // 模块名称不能重复
                    return CheckResultUtil.NG("更新保存出错：模块名称出现重复", listName);
                }
                break;
            default :

        }

        return CheckResultUtil.OK();
    }

    /**
     * 根据模块名称查询资源文件找到json进行转换成excel导出
     * @param code
     */
    @Override
    public SModuleVo getTemplateBeanByModuleName(String code){
        // 查询 数据
        SModuleVo vo = mapper.selectTemplateName(code);
        return vo;
    }
}
