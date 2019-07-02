package com.perfect.serviceimpl.oauth;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.perfect.bean.bo.security.OauthUserBo;
import com.perfect.bean.entity.oauth.OauthUserEntity;
import com.perfect.mapper.oauth.OauthUserMapper;
import com.perfect.service.oauth.IOauthUserService;
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
public class OauthUserServiceImpl extends ServiceImpl<OauthUserMapper, OauthUserEntity> implements IOauthUserService {
    @Autowired
    private OauthUserMapper oauthUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OauthUserEntity user = oauthUserMapper.getDataByName(username);

        if (user == null) {
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

        return new OauthUserBo(
                user.getId(),
                username,
                user.getPassword(),
                AuthorityUtils.createAuthorityList(permissions.toArray(new String[]{})))
                .setUser(user);
    }

}