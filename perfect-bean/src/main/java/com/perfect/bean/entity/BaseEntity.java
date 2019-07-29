package com.perfect.bean.entity;/**
                                 * @author zxh
                                 * @date 2019-07-29
                                 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.perfect.bean.entity.system.rabc.SRoleEntity;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxh
 * @date 2019-07-29
 */
public class BaseEntity<T> {

    @TableField(exist=false)
    private Map<String,String> mapPropertyColumn;

    @TableField(exist=false)
    private Map<String,String> mapColumnProperty;

    @TableField(exist=false)
    private Class<T> entity;

    public void setMapPropertyColumnn(){
        mapPropertyColumn = getMapPropertyColumnn();
    }

    /**
     * 获取实体bean的属性和字段的数据
     * @return
     */
    public Map<String,String> getMapPropertyColumnn(){
        int num = 500;
        Map<String,String> map = new HashMap<>(num);

        entity = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity);
        List<TableFieldInfo> TableFieldInfos =tableInfo.getFieldList();
        for (TableFieldInfo tableFieldInfo : TableFieldInfos) {
            map.put(tableFieldInfo.getProperty(),tableFieldInfo.getColumn());
        }
        return map;
    }

    public void setMapColumnProperty(){
        mapColumnProperty = getMapColumnProperty();
    }
    /**
     * 获取实体bean的属性和字段的数据
     * @return
     */
    public Map<String,String> getMapColumnProperty(){
        int num = 500;
        Map<String,String> map = new HashMap<>(num);

        entity = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entity);
        List<TableFieldInfo> TableFieldInfos =tableInfo.getFieldList();
        for (TableFieldInfo tableFieldInfo : TableFieldInfos) {
            map.put(tableFieldInfo.getColumn(),tableFieldInfo.getProperty());
        }
        return map;
    }

    /**
     *
     * @param propertyName
     * @return
     */
    public String getProperty2Columnn(String propertyName){
        setMapPropertyColumnn();
        setMapColumnProperty();

        String rtn = null;
        // 先从entity属性中查找是否存在有数据
        rtn = mapPropertyColumn.get(propertyName);
        if((rtn == null) && ( mapColumnProperty.get(propertyName) == null) ) {
            rtn = propertyName;
        }
        return rtn;
    }
}
