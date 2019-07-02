package com.zxh.security.bean;

import com.zxh.bean.entity.oauth.OauthClientDetailsEntity;
import com.zxh.utils.CommonUtil;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 *
 **/
@Data
public final class BootClientDetails implements ClientDetails {

    private Set<String> scope;

    private static final long serialVersionUID = -8704644100479999283L;

    private OauthClientDetailsEntity clientDetails;

    public BootClientDetails(OauthClientDetailsEntity clientDetails) {
        this.clientDetails = clientDetails;
    }

    public BootClientDetails() {
    }

    @Override
    public String getClientId() {
        return clientDetails.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return clientDetails.getResourceIds()!=null?
                CommonUtil.convertStringToSet(clientDetails.getResourceIds(),String.class):null;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientDetails.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {

        this.scope = clientDetails.getScope()!=null?
                CommonUtil.convertStringToSet(clientDetails.getScope(),String.class):null;

        return clientDetails.getScope()!=null?
                CommonUtil.convertStringToSet(clientDetails.getScope(),String.class):null;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return clientDetails.getAuthorizedGrantTypes()!=null?
                CommonUtil.convertStringToSet(clientDetails.getAuthorizedGrantTypes(),String.class):null;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return clientDetails.getWebServerRedirectUri()!=null?
                CommonUtil.convertStringToSet(clientDetails.getWebServerRedirectUri(),String.class):null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return (clientDetails.getAuthorities()!=null&&clientDetails.getAuthorities().trim().length()>0)?
                AuthorityUtils.commaSeparatedStringToAuthorityList(clientDetails.getAuthorities()):null;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return clientDetails.getAccessTokenValidity();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return clientDetails.getRefreshTokenValidity();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return  this.clientDetails.getAutoapprove()==null ? false: true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
