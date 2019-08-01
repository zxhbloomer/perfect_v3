package com.perfect.core.service.system.rabc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.vo.sys.rabc.role.SysRoleVo;
import org.apache.ibatis.annotations.Param;

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
    IPage<SRoleEntity> getListPage(SysRoleVo searchCondition) throws InstantiationException, IllegalAccessException;

    /**
     * 获取所有数据
     */
    List<SRoleEntity> getAllList(SysRoleVo searchCondition) throws InstantiationException, IllegalAccessException;
}
