package com.perfect.core.serviceimpl.sys.config.config;

import java.util.List;
import com.perfect.bean.entity.sys.config.config.SConfigEntity;
import com.perfect.bean.vo.sys.config.config.SConfigVo;
import com.perfect.core.mapper.sys.config.config.SConfigMapper;
import com.perfect.core.service.sys.config.config.ISConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.perfect.common.exception.BusinessException;
import com.perfect.common.utils.bean.BeanUtilsSupport;
import com.perfect.core.utils.mybatis.PageUtil;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-08-23
 */
@Service
public class SConfigServiceImpl extends ServiceImpl<SConfigMapper, SConfigEntity> implements ISConfigService {

    @Autowired
    private SConfigMapper mapper;

    /**
     * 获取列表，页面查询
     *
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public IPage<SConfigVo> selectPage(SConfigVo searchCondition)
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
    public List<SConfigVo> select(SConfigVo searchCondition) {
        // 查询 数据
        List<SConfigVo> list = mapper.select(searchCondition);
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
    public List<SConfigVo> selectIdsIn(List<SConfigVo> searchCondition) {
        // 查询 数据
        List<SConfigVo> list = mapper.selectIdsIn(searchCondition);
        return list;
    }

    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    @Override
    public SConfigVo selectByid(Long id) {
        // 查询 数据
        return mapper.selectId(id);
    }

    /**
     * 批量导入逻辑
     *
     * @param entityList
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatches(List<SConfigEntity> entityList) {
        return super.saveBatch(entityList, 500);
    }

    /**
     * 批量启用复原
     * 
     * @param searchCondition
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void enableByIdsIn(List<SConfigVo> searchCondition) {
        List<SConfigVo> list = mapper.selectIdsIn(searchCondition);
        list.forEach(bean -> {
            bean.setIsenable(!bean.getIsenable());
        });
        List<SConfigEntity> entityList = BeanUtilsSupport.copyProperties(list, SConfigEntity.class);
        super.saveOrUpdateBatch(entityList, 500);
    }

    /**
     * 插入一条记录（选择字段，策略插入）
     * 
     * @param entity 实体对象
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public InsertResult<Integer> insert(SConfigEntity entity) {
        // 插入前check
        CheckResult cr = checkLogic(entity.getName(), entity.getConfig_key(), CheckResult.INSERT_CHECK_TYPE);
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
    public UpdateResult<Integer> update(SConfigEntity entity) {
        // 更新前check
        CheckResult cr = checkLogic(entity.getName(), entity.getConfig_key(), CheckResult.UPDATE_CHECK_TYPE);
        if (cr.isSuccess() == false) {
            throw new BusinessException(cr.getMessage());
        }
        // 更新逻辑保存
        return UpdateResultUtil.OK(mapper.updateById(entity));
    }

    /**
     * 获取列表，查询所有数据
     *
     * @param name
     * @return
     */
    @Override
    public List<SConfigEntity> selectByName(String name) {
        // 查询 数据
        List<SConfigEntity> list = mapper.selectByName(name);
        return list;
    }

    /**
     * 获取列表，查询所有数据
     *
     * @param key
     * @return
     */
    @Override
    public List<SConfigEntity> selectByKey(String key) {
        // 查询 数据
        List<SConfigEntity> list = mapper.selectByKey(key);
        return list;
    }

    /**
     * 获取列表，查询所有数据
     *
     * @param value
     * @return
     */
    @Override
    public List<SConfigEntity> selectByValue(String value) {
        // 查询 数据
        List<SConfigEntity> list = mapper.selectByValue(value);
        return list;
    }

    /**
     * check逻辑
     * 
     * @return
     */
    public CheckResult checkLogic(String name, String key, String moduleType) {
        List<SConfigEntity> selectByName = selectByName(name);
        List<SConfigEntity> selectByKey = selectByKey(key);

        switch (moduleType) {
            case CheckResult.INSERT_CHECK_TYPE:
                // 新增场合，不能重复
                if (selectByName.size() >= 1) {
                    return CheckResultUtil.NG("新增保存出错：参数名称出现重复", name);
                }
                if (selectByKey.size() >= 1) {
                    return CheckResultUtil.NG("新增保存出错：参数键名出现重复", key);
                }
                break;
            case CheckResult.UPDATE_CHECK_TYPE:
                // 更新场合，不能重复设置
                if (selectByName.size() >= 2) {
                    return CheckResultUtil.NG("新增保存出错：参数名称出现重复", name);
                }
                if (selectByKey.size() >= 2) {
                    return CheckResultUtil.NG("新增保存出错：参数键名出现重复", key);
                }
                break;
            default:
        }
        return CheckResultUtil.OK();
    }

}
