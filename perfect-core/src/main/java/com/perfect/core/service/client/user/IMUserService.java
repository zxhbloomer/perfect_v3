package com.perfect.core.service.client.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.client.login.MUserEntity;
import com.perfect.bean.vo.user.info.UserInfoVo;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-06-24
 */
public interface IMUserService extends IService<MUserEntity> , UserDetailsService {
    public UserInfoVo getUserInfo(String userName);
}
