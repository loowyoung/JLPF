package com.anxin.common.service.impl;

import com.anxin.common.dao.BaseDao;
import com.anxin.common.domain.BaseEntity;
import com.anxin.common.service.BaseService;
import com.anxin.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by sun on 2018/10/16.
 */
public class BaseImpl<D extends BaseDao<T>, T extends BaseEntity> {

    /**
     * 持久层对象
     */
    @Autowired
    protected D dao;

    /**
     * 获取单条数据
     * @param id
     * @return
     */
    public T get(String id) {
        return dao.get(id);
    }

    /**
     * 获取list列表
     * @param map
     * @return
     */
    public List<T> list(Map<String, Object> map){
        return  dao.list(map);
    }

    /**
     * 获取数量
     * @param map
     * @return
     */
    public int count(Map<String, Object> map){
        return dao.count(map);
    }

    /**
     * 保存
     * @param entity
     * @return
     */
    public int save(T entity){
        int r=-1;
        if (StringUtils.isBlank(entity.getId())) {
            entity.preInsert();
            r = dao.save(entity);
            return r;
        }
        return r;
    }

    /**
     * 更新
     * @param entity
     * @return
     */
    public int update(T entity){
        entity.preUpdate();
        return dao.update(entity);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public int remove(String id){
        return dao.remove(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public int batchRemove(String[] ids){
        return dao.batchRemove(ids);
    }
}
