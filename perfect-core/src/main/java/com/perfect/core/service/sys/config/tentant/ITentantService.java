package com.perfect.core.service.sys.config.tentant;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.sys.config.tenant.STentantEntity;
import com.perfect.bean.pojo.result.InsertResult;
import com.perfect.bean.pojo.result.UpdateResult;
import com.perfect.bean.vo.sys.config.tenant.STentantTreeVo;
import com.perfect.bean.vo.sys.config.tenant.STentantVo;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-08-16
 */
public interface ITentantService extends IService<STentantEntity> {

    /**
     * 获取数据，树结构
     * 
     * @param id
     * @return
     */
    List<STentantTreeVo> getTreeList(Long id, String nam);

    /**
     * 获取数据，级联结构
     *
     * @param id
     * @return
     */
    List<STentantTreeVo> getCascaderList(Long id, String nam);

    /**
     * 获取列表，页面查询
     */
    IPage<STentantVo> selectPage(STentantVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    List<STentantEntity> selectIdsIn(List<STentantVo> searchCondition)
        throws InstantiationException, IllegalAccessException;

    /**
     * 插入一条记录（选择字段，策略插入）
     * 
     * @param entity 实体对象
     * @return
     */
    InsertResult<Integer> insert(STentantEntity entity);

    /**
     * 更新一条记录（选择字段，策略更新）
     * 
     * @param entity 实体对象
     * @return
     */
    UpdateResult<Integer> update(STentantEntity entity);

    /**
     * 查询by id，返回结果
     *
     * @param id
     * @return
     */
    STentantVo selectByid(Long id);

    /**
     * 通过code查询
     *
     */
    List<STentantEntity> selectByCode(String code);

    /**
     * 通过名称查询
     *
     */
    List<STentantEntity> selectByName(String name);

    /**
     * 根据ID获取子节点数组
     * 
     * @param id
     * @return
     */
     List<STentantTreeVo> getChildren(Long id, String name);
}
