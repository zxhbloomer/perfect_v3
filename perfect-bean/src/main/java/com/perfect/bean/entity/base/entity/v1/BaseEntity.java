package com.perfect.bean.entity.base.entity.v1;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxh
 * @date 2019-07-29
 */
public class BaseEntity<T> {

    /**
     * 获取属性名和表列名的map
     */
    @TableField(exist = false)
    private Map<String, String> mapPropertyColumn;

    /**
     * 获取表列名和属性的map
     */
    @TableField(exist = false)
    private Map<String, String> mapColumnProperty;

    /**
     * entity名字
     */
    @TableField(exist = false)
    private Class<T> entity;

    public void setMapPropertyColumnn() {
        mapPropertyColumn = getMapPropertyColumnn();
    }

    /** 数据权限_租户 */
    @Getter
    @Setter
    @TableField(exist = false)
    private Long authorityTenant;

    /** 数据权限_角色 */
    @Getter
    @Setter
    @TableField(exist = false)
    private Long authorityRole;

    /** 数据权限_个人 */
    @Getter
    @Setter
    @TableField(fill = FieldFill.INSERT,exist = false)
    private Long authorityUser;

    /** 数据权限_组织 */
    @Getter
    @Setter
    @TableField(fill = FieldFill.INSERT,exist = false)
    private Long authorityOrg;

    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限） */
    @Getter
    @Setter
//    @Excel(name = "数据范围", readConverterExp = "1=所有数据权限,2=自定义数据权限,3=本部门数据权限,4=本部门及以下数据权限")
    @TableField(exist = false)
    private String dataScope;

    /**
     * 获取实体bean的属性和字段的数据
     * 
     * @return
     */
    public Map<String, String> getMapPropertyColumnn() {
        int num = 500;
        Map<String, String> map = new HashMap<>(num);

        entity = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity);
        List<TableFieldInfo> TableFieldInfos = tableInfo.getFieldList();
        for (TableFieldInfo tableFieldInfo : TableFieldInfos) {
            map.put(tableFieldInfo.getProperty(), tableFieldInfo.getColumn());
        }
        return map;
    }

    public void setMapColumnProperty() {
        mapColumnProperty = getMapColumnProperty();
    }

    /**
     * 获取实体bean的属性和字段的数据
     * 
     * @return
     */
    public Map<String, String> getMapColumnProperty() {
        int num = 500;
        Map<String, String> map = new HashMap<>(num);

        entity = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity);
        List<TableFieldInfo> TableFieldInfos = tableInfo.getFieldList();
        for (TableFieldInfo tableFieldInfo : TableFieldInfos) {
            map.put(tableFieldInfo.getColumn(), tableFieldInfo.getProperty());
        }
        return map;
    }

    /**
     *
     * @param propertyName
     * @return
     */
    public String getProperty2Columnn(String propertyName) {
        setMapPropertyColumnn();
        setMapColumnProperty();

        String rtn = null;
        // 先从entity属性中查找是否存在有数据
        rtn = mapPropertyColumn.get(propertyName);
        if ((rtn == null) && (mapColumnProperty.get(propertyName) == null)) {
            rtn = propertyName;
        }
        return rtn;
    }
}
