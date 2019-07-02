package com.zxh.serviceimpl.client.user;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxh.bean.bo.login.MUserBo;
import com.zxh.bean.bo.security.OauthUserBo;
import com.zxh.bean.entity.client.login.MUserEntity;
import com.zxh.bean.entity.oauth.OauthUserEntity;
import com.zxh.mapper.client.user.MUserMapper;
import com.zxh.mapper.oauth.OauthUserMapper;
import com.zxh.service.client.user.IMUserService;
import com.zxh.service.oauth.IOauthUserService;
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

}