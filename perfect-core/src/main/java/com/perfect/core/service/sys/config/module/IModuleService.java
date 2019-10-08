package com.perfect.core.service.sys.config.module;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.sys.config.dict.SDictTypeEntity;
import com.perfect.bean.entity.sys.config.module.SModuleEntity;
import com.perfect.bean.entity.sys.config.resource.SResourceEntity;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.vo.sys.config.module.SModuleVo;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
public interface IModuleService extends IService<SModuleEntity> {
    /**
     * 获取列表，页面查询
     */
    IPage<SModuleVo> selectPage(SModuleVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所有数据
     */
    List<SModuleEntity> select(SModuleVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    List<SModuleEntity> selectIdsIn(List<SModuleVo> searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 批量导入
     */
    boolean saveBatches(List<SModuleEntity> entityList);

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    void deleteByIdsIn(List<SModuleVo> searchCondition);

    /**
     * 插入一条记录（选择字段，策略插入）
     * @param entity 实体对象
     * @return
     */
    InsertResult<Integer> insert(SModuleEntity entity);

    /**
     * 更新一条记录（选择字段，策略更新）
     * @param entity 实体对象
     * @return
     */
    UpdateResult<Integer> update(SModuleEntity entity);

    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    SModuleVo selectByid(Long id);

    /**
     * 通过code查询
     *
     */
    List<SModuleEntity> selectByCode(String code);

    /**
     * 通过名称查询
     *
     */
    List<SModuleEntity> selectByName(String name);

    /**
     * 根据模块名称查询资源文件找到json进行转换成excel导出
     * @param code
     * @return
     */
    SModuleVo getTemplateBeanByModuleName(String code);
}
