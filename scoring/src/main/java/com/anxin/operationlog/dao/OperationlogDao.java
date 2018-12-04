package com.anxin.operationlog.dao;

import com.anxin.common.dao.BaseDao;
import com.anxin.operationlog.domain.OperationlogDO;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 临时账户操作日志表
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 10:39:48
 */
@Mapper
public interface OperationlogDao extends BaseDao<OperationlogDO>{
    //查询所有用户，group by tempuserid
    List<OperationlogDO> listUser();
}
