package com.anxin.common.service;

import org.springframework.stereotype.Service;

import com.anxin.common.domain.LogDO;
import com.anxin.common.domain.PageDO;
import com.anxin.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
