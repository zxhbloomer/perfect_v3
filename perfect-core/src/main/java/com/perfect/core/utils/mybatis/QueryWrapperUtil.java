package com.perfect.core.utils.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.perfect.bean.entity.BaseEntity;
import com.perfect.bean.entity.system.rabc.SRoleEntity;
import org.apache.poi.ss.formula.functions.T;

/**
 * @author zxh
 * @date 2019-07-29
 */
public class QueryWrapperUtil<T> {

    /**
     * 获取排序wrapper
     * 
     * @param entityBeanClassName
     * @param sortCondition
     * @return
     */
    public static <T> QueryWrapper<T> getSortWrapper(Class<T> entityBeanClassName, String sortCondition)
        throws IllegalAccessException, InstantiationException {
        BaseEntity<T> be = (BaseEntity<T>) entityBeanClassName.newInstance();
        QueryWrapper<T> wrapper = new QueryWrapper<T>();
        if (sortCondition != null) {
            if (sortCondition.startsWith("-")) {
                // 此为降序
                wrapper.orderByDesc(be.getProperty2Columnn(sortCondition.substring(1)));
            } else {
                wrapper.orderByAsc(be.getProperty2Columnn(sortCondition));
            }
        }
        return wrapper;
    }
}
