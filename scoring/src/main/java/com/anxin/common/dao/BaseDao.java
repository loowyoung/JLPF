package com.anxin.common.dao;


import java.util.List;
import java.util.Map;

/**
 * Created by sun on 2018/10/16.
 */
public interface BaseDao<T> {

    T get(String id);

    List<T> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(T entity);

    int update(T entity);

    int remove(String id);

    int batchRemove(String[] ids);

}
