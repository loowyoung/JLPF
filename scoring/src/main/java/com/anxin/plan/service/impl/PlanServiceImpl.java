package com.anxin.plan.service.impl;

import com.anxin.common.service.impl.BaseImpl;
import com.anxin.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import com.anxin.plan.dao.PlanDao;
import com.anxin.plan.domain.PlanDO;
import com.anxin.plan.service.PlanService;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PlanServiceImpl extends BaseImpl<PlanDao,PlanDO> implements PlanService {

    @Override
    public List<PlanDO> listJob() {
        return dao.listJob();
    }

    @Transactional
    @Override
    public int batchSetTime(String[] appids, String starttime, String endtime) {
        PlanDO plan = new PlanDO();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isBlank(plan.getId())) {
            plan.preInsert();
        }
        try {
            plan.setStarttime(format.parse(starttime));
            plan.setEndtime(format.parse(endtime));
            dao.save(plan);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Map params = new HashMap();
        params.put("appids",appids);
        params.put("planid",plan.getId());
        return dao.batchSetTime(params);
    }
}
