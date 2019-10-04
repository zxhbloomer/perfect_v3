package com.perfect.bean.vo.common.tree;

import lombok.Data;

import java.util.List;

/**
 * 树形数据实体接口
 * 
 * T：子节点
 * M：数据bean
 *
 * @author zxh
 * @date 2019年 10月03日 11:29:30
 */
@Data
public class IDataTree<T, M> {

    public Long id;
    public long parentid;
    public String lable;
    public int level;
    public List<T> children;
    // 其他数据
    public M data;

}