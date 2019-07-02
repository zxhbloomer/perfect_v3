package com.zxh.base.bean.v1;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by like on 8/26/15.
 */
@Log4j2
public class BeanUtil<BeanType> {

    public Object convertMapToBean(Object object, Class<BeanType> beanClass)
    {
        if (object instanceof List)
        {
            return this.convertMapListToBeanList((List) object, beanClass);
        }
        else {
            return this.convertOneMapListToOneBean((Map) object, beanClass);
        }
    }


    public List<BeanType> convertMapListToBeanList(List<Map> mapList, Class<BeanType> beanClass)
    {
        List<BeanType> resultList = new ArrayList<>();
        for (int i = 0; i < mapList.size(); i++) {
            Map map =  mapList.get(i);
            BeanType bean = this.convertOneMapListToOneBean(map, beanClass);
            resultList.add(bean);
        }
        return resultList;
    }

    /**
     * 将map转化为bean
     * @param map
     * @return
     */
    public BeanType convertOneMapListToOneBean(Map map, Class<BeanType> beanClass){
        try
        {
            List removeKey = new ArrayList<>();
            Set keySet = map.keySet();
            keySet.forEach(key->{ if (map.get(key)== null) removeKey.add(key);});
            removeKey.forEach(key -> map.remove(key));
            BeanType bean = beanClass.newInstance();

            BeanUtils.populate(bean,map);
            return bean;
        }
        catch(Exception e) {

            log.error("com.szqd.framework.util.BeanUtils.convertOneMapListToOneBean(mapVar:util.HashMap): BeanType",e);
            throw new RuntimeException("将map转化为bean出错");

        }

    }

    /**
     * 将Page<Map>转化为Page<BeanType>
     * @param pageMap
     * @param beanClass
     * @return
     */
    public Page<BeanType> convertPageMapToPageBean(Page<Map> pageMap, Class<BeanType> beanClass){
        try {
            List<BeanType>  list = convertMapListToBeanList(pageMap.getContent(), beanClass);
            return new PageImpl<BeanType>(list, pageMap.getPageable(),pageMap.getTotalElements()) ;
        }catch(Exception e) {

            log.error("转换失败：",e);
            throw new RuntimeException("将Page<Map>转化为Page<BeanType>出错");

        }
    }


}