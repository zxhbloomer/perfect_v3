package com.perfect.core.utils.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.perfect.bean.entity.BaseEntity;
import com.perfect.common.utils.string.StringUtil;

/**
 * @author zxh
 * @date 2019-07-30
 */
public class PageUtil {

    /**
     * page排序
     * @param entityBeanClassName
     * @param sortCondition
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> void setSort(Page page , Class<T> entityBeanClassName, String sortCondition)
        throws IllegalAccessException, InstantiationException {
        BaseEntity<T> be = (BaseEntity<T>) entityBeanClassName.newInstance();
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
