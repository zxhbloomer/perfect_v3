package com.perfect.core.utils.mybatis;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.config.base.v1.BaseBean;
import com.perfect.bean.entity.base.entity.v1.BaseEntity;
import com.perfect.common.utils.string.StringUtil;

/**
 * @author zxh
 * @date 2019-07-30
 */
public class PageUtil {

    /**
     * page排序
     * @param sortCondition
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> void setSort(Page page , String sortCondition) {
        if (StringUtil.isNotEmpty(sortCondition)) {
            if (sortCondition.startsWith("-")) {
                // 此为降序
                page.addOrder(OrderItem.desc(sortCondition.substring(1)));
            } else {
                // 此为升序
                page.addOrder(OrderItem.asc(sortCondition));
            }
        }
    }

    /**
     * page排序
     * @param entityBeanClassName
     * @param sortCondition
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    @Deprecated
    public static <T> void setSort(Page page , Class<T> entityBeanClassName, String sortCondition)
        throws IllegalAccessException, InstantiationException {
        BaseBean<T> be = (BaseBean<T>) entityBeanClassName.newInstance();
        if (StringUtil.isNotEmpty(sortCondition)) {
            if (sortCondition.startsWith("-")) {
                // 此为降序
                page.addOrder(OrderItem.desc(be.getProperty2Columnn(sortCondition.substring(1))));
            } else {
                // 此为升序
                page.addOrder(OrderItem.asc(be.getProperty2Columnn(sortCondition)));
            }
        }
    }
}
