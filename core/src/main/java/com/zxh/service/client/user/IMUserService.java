package com.zxh.service.client.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zxh.bean.entity.client.login.MUserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-06-24
 */
public interface IMUserService extends IService<MUserEntity> , UserDetailsService {

}
