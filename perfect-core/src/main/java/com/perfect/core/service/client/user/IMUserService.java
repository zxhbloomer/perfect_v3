package com.perfect.core.service.client.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.perfect.bean.entity.client.user.MUserEntity;
import com.perfect.bean.pojo.redis.user.UserInSessionPojo;
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

    /**
     * 获取use的基本信息
     * @param userName
     * @return
     */
    UserInfoVo getUserInfo(String userName);


    /**
     * 获取保存到session中的userbean
     * @param userid
     * @return
     */
    UserInSessionPojo getUserInSessionBean(long userid);

}
