package com.zxh.bean.bo.security;

import com.zxh.bean.entity.oauth.OauthUserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class OauthUserBo extends User implements Serializable {

    private static final long serialVersionUID = 1457807332111298820L;

    @Getter
    private Long id;
    @Getter
    private OauthUserEntity oauthUserEntity;

    /**
     * @param id
     * @param username
     * @param password
     * @param authorities
     */
    public OauthUserBo(Long id,
                      String username,
                      String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    /**
     * @param id
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    public OauthUserBo(Long id,
                      String username,
                      String password,
                      boolean enabled,
                      boolean accountNonExpired,
                      boolean credentialsNonExpired,
                      boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username,
                password,
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities);
        this.id = id;
    }

    public OauthUserBo setUser(OauthUserEntity user) {
        this.oauthUserEntity = user;
        return this;
    }

//    public CustomUser setClerk(Clerk clerk) {
//        this.clerk = clerk;
//        return this;
//    }

}
