package com.perfect.core.serviceimpl.system.rabc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.vo.sys.rabc.role.SysRoleVo;
import com.perfect.core.mapper.client.user.MUserMapper;
import com.perfect.core.mapper.system.rabc.SRoleMapper;
import com.perfect.core.service.system.rabc.ISRoleService;
import com.perfect.core.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-07-11
 */
@Service
public class SRoleServiceImpl extends ServiceImpl<SRoleMapper, SRoleEntity> implements
    ISRoleService {

    @Autowired
    private SRoleMapper sRoleMapper;

    /**
     * 获取列表，页面查询
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public IPage<SRoleEntity> getListPage(SysRoleVo searchCondition) throws InstantiationException, IllegalAccessException {
        // 分页条件
        Page<SRoleEntity> pageCondition = new Page(searchCondition.getPageCondition().getCurrent(), searchCondition.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, SRoleEntity.class, searchCondition.getPageCondition().getSort());
        return sRoleMapper.getListPage(pageCondition, searchCondition);
    }

    /**
     * 获取列表，查询所有数据
     * @param searchCondition
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override public List<SRoleEntity> getAllList(SysRoleVo searchCondition)
        throws InstantiationException, IllegalAccessException {
        // 查询 数据
        List<SRoleEntity> list =sRoleMapper.getAllList(searchCondition);
        return list;
    }

}
