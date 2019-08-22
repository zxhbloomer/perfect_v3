package com.perfect.excel.bean.importconfig.template.test;

import com.perfect.bean.entity.sys.rabc.SRoleEntity;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zxh
 * @date 2019-08-09
 */
public class testBeanUtils {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        SRoleEntity sRoleEntity = new SRoleEntity();
        BeanUtils.setProperty(sRoleEntity,"type","1");
        System.out.println("");
    }
}
