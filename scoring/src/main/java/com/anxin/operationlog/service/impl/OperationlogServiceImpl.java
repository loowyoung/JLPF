package com.anxin.operationlog.service.impl;

import com.anxin.common.service.impl.BaseImpl;
import org.springframework.stereotype.Service;

import com.anxin.operationlog.dao.OperationlogDao;
import com.anxin.operationlog.domain.OperationlogDO;
import com.anxin.operationlog.service.OperationlogService;

import java.util.List;


@Service
public class OperationlogServiceImpl extends BaseImpl<OperationlogDao,OperationlogDO>implements OperationlogService {
    @Override
    public List<OperationlogDO> listUser() {
        return dao.listUser();
    }
}
