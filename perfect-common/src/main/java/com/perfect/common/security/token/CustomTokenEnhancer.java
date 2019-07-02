package com.perfect.common.security.token;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 可用于自定义令牌策略，在令牌被 AuthorizationServerTokenServices 的实现存储之前增强令牌的策略，它有两个实现类：
 *
 * JwtAccessTokenConverter：用于令牌 JWT 编码与解码
 * TokenEnhancerChain：一个令牌链，可以存放多个令牌，并循环的遍历令牌并将结果传递给下一个令牌
 *
 * 链接：https://www.jianshu.com/p/c2395772bc86
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	/**
	 * jwt中添加额外信息
	 * @param accessToken
	 * @param authentication
	 * @return
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		final Object principal = authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();

        if (principal instanceof User) {
        	User user = (User) principal;
        	additionalInfo.put("uid", user.getUsername());
        	
        	Map<String, Map<String, String>> _links = new HashMap<>();
        	Map<String, String> userInfoLink = new HashMap<>();
			// todo:这里还没想好怎么搞
			// todo：userInfoLink.put("href", ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(com.gnet.authentication.user.UserController.class).user(authentication)).withSelfRel().getHref());
        	_links.put("self", userInfoLink);
        	additionalInfo.put("_links", _links);
        }

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
	}

}
