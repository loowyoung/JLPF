package com.anxin.operationlog.service;

import com.anxin.common.service.BaseService;
import com.anxin.operationlog.domain.OperationlogDO;

import java.util.List;

/**
 * 临时账户操作日志表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-17 10:39:48
 */
public interface OperationlogService extends BaseService<OperationlogDO>{
    //查询所有用户，group by tempuserid
    List<OperationlogDO> listUser();
}
