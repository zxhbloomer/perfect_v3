package com.perfect.core.service.sys.rabc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.sys.rabc.SRoleEntity;
import com.perfect.bean.vo.sys.rabc.role.SRoleVo;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author zxh
 * @since 2019-07-11
 */
public interface ISRoleService extends IService<SRoleEntity> {

    /**
     * 获取列表，页面查询
     */
    IPage<SRoleEntity> selectPage(SRoleVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所有数据
     */
    List<SRoleEntity> select(SRoleVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所选id的数据
     */
    List<SRoleEntity> selectIdsIn(List<SRoleVo> searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 批量导入
     */
    boolean saveBatches(List<SRoleEntity> entityList);

    /**
     * 批量删除复原
     * @param searchCondition
     * @return
     */
    void deleteByIdsIn(List<SRoleVo> searchCondition);

    /**
     * 批量启用禁用
     * @param searchCondition
     * @return
     */
    void enableByIdsIn(List<SRoleVo> searchCondition);
}
