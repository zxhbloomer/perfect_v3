package com.perfect.core.serviceimpl.sys.config.tentant;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.entity.sys.config.tenant.STentantEntity;
import com.perfect.bean.pojo.result.CheckResult;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.result.utils.v1.CheckResultUtil;
import com.perfect.bean.result.utils.v1.InsertResultUtil;
import com.perfect.bean.result.utils.v1.UpdateResultUtil;
import com.perfect.bean.vo.sys.config.tenant.STentantTreeVo;
import com.perfect.bean.vo.sys.config.tenant.STentantVo;
import com.perfect.common.exception.BusinessException;
import com.perfect.core.mapper.sys.config.tentant.STentantMapper;
import com.perfect.core.service.sys.config.tentant.ITentantService;
import com.perfect.core.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
@Service
public class STentantServiceImpl extends ServiceImpl<STentantMapper, STentantEntity> implements ITentantService {

    @Autowired
    private STentantMapper mapper;

    /**
     * 获取数据，树结构
     * 
     * @param id
     * @return
     */
    @Override
    public List<STentantTreeVo> getTreeList(Long id, String name) {
        List<STentantTreeVo> listVo = mapper.getTreeList(id, name);
        return listVo;
    }

    /**
     * 获取数据，级联结构
     *
     * @param id
     * @return
     */
    @Override
    public List<STentantTreeVo> getCascaderList(Long id, String name) {
        List<STentantTreeVo> listVo = mapper.getCascaderList(id, name);
        return listVo;
    }

    /**
     * 获取列表，页面查询
     * 
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public IPage<STentantVo> selectPage(STentantVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        // 分页条件
        Page<STentantVo> pageCondition =
            new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, searchCondition.getPageCondition().getSort());
        return mapper.selectPage(pageCondition, searchCondition);
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
    public List<STentantEntity> selectIdsIn(List<STentantVo> searchCondition) {
        // 查询 数据
        List<STentantEntity> list = mapper.selectIdsIn(searchCondition);
        return list;
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsertResult<Integer> insert(STentantEntity entity) {
        // 插入前check
        CheckResult cr = checkLogic(entity, CheckResult.INSERT_CHECK_TYPE);

        // 初始化值
        entity.setIsenable(false);
        entity.setIsfreeze(false);
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
    public UpdateResult<Integer> update(STentantEntity entity) {
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
    public STentantVo selectByid(Long id) {
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
    public List<STentantEntity> selectByCode(String code) {
        // 查询 数据
        List<STentantEntity> list = mapper.selectByCode(code);
        return list;
    }

    /**
     * 查询by 名称，返回结果
     *
     * @param name
     * @return
     */
    @Override
    public List<STentantEntity> selectByName(String name) {
        // 查询 数据
        List<STentantEntity> list = mapper.selectByName(name);
        return list;
    }

    /**
     * check逻辑，模块编号 or 模块名称 不能重复
     * 
     * @return
     */
    public CheckResult checkLogic(STentantEntity entity, String moduleType) {
        List<STentantEntity> listCode = selectByCode(entity.getCode());
        List<STentantEntity> listName = selectByName(entity.getName());

        switch (moduleType) {
            case CheckResult.INSERT_CHECK_TYPE:
                // 新增场合，不能重复
                if (listCode.size() >= 1) {
                    // 模块编号不能重复
                    return CheckResultUtil.NG("新增保存出错：租户编码出现重复", listCode);
                }
                if (listName.size() >= 1) {
                    // 模块名称不能重复
                    return CheckResultUtil.NG("新增保存出错：租户名称出现重复", listName);
                }
                break;
            case CheckResult.UPDATE_CHECK_TYPE:
                // 更新场合，不能重复设置
                if (listCode.size() >= 2) {
                    // 模块编号不能重复
                    return CheckResultUtil.NG("更新保存出错：租户编码出现重复", listCode);
                }
                if (listName.size() >= 2) {
                    // 模块名称不能重复
                    return CheckResultUtil.NG("更新保存出错：租户名称出现重复", listName);
                }
                break;
            default:

        }

        return CheckResultUtil.OK();
    }

    /**
     * 根据ID获取子节点数组
     *
     * @param id
     * @return
     */
    @Override
    public List<STentantTreeVo> getChildren(Long id, String name) {
        List<STentantTreeVo> list = mapper.getTreeList(id, name);
        return null;
    }
}
