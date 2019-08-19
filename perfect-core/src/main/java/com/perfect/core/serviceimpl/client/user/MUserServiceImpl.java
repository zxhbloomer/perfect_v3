package com.perfect.core.serviceimpl.client.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.bo.user.login.MUserBo;
import com.perfect.bean.entity.client.user.MStaffEntity;
import com.perfect.bean.entity.client.user.MUserEntity;
import com.perfect.bean.pojo.redis.user.UserInSessionPojo;
import com.perfect.bean.vo.user.info.UserInfoVo;
import com.perfect.core.mapper.client.user.MStaffMapper;
import com.perfect.core.mapper.client.user.MUserMapper;
import com.perfect.core.service.client.user.IMUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-17
 */
@Service
@Slf4j
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUserEntity> implements IMUserService {
    @Autowired
    private MUserMapper mUserMapper;
    @Autowired
    private MStaffMapper mStaffMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MUserEntity user = mUserMapper.getDataByName(username);

        if (user == null) {
            log.error("您输入的用户名不存在！");
            throw new UsernameNotFoundException("您输入的用户名不存在！");
        }

        //        Clerk clerk = clerkMapper.selectByPrimaryKey(user.getId());
        //        if (clerk == null) {
        //            throw new ClerkNotFoundException("Couldn't found clerk in system");
        //        }
        //
        //        List<Role> roles = userMapper.selectRoles(user.getId());
        //
        List<String> permissions = new ArrayList<>();
        permissions.addAll(CollectionUtils.arrayToList(new String[]{"ROLE_USER"}));
        //        for (Role role : roles) {
        //            permissions.addAll(CollectionUtils.arrayToList(role.getPermissions()));
        //        }

        return new MUserBo(
                user.getId(),
                username,
                user.getPwd(),
                AuthorityUtils.createAuthorityList(permissions.toArray(new String[]{})))
                .setUser(user);
    }

    /**
     * 获取use的基本信息
     * @param userName
     * @return
     */
    @Override
    public UserInfoVo getUserInfo(String userName){

        // TODO 测试bean
        UserInfoVo ui = new UserInfoVo();
        ui.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ui.setIntroduction("我是超级管理员");
        ui.setName("超级管理员");
        ui.setRoles(new String[]{"admin"});
        return ui;
    }

    /**
     * 获取保存到session中的userbean
     * @param userid
     * @return
     */
    @Override
    public UserInSessionPojo getUserInSessionBean(long userid){
        MUserEntity mUserEntity = mUserMapper.selectById(userid);
        MStaffEntity mStaffEntity = mStaffMapper.getDataByUserId(mUserEntity.getId());
        UserInSessionPojo userInSessionPojo = new UserInSessionPojo();
        userInSessionPojo.setUser_info(mUserEntity);
        userInSessionPojo.setStaff_info(mStaffEntity);
        return userInSessionPojo;
    }
}