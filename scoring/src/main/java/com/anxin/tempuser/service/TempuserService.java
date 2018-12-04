package com.anxin.tempuser.service;

import com.anxin.common.service.BaseService;
import com.anxin.post.domain.PostDO;
import com.anxin.tempuser.domain.TempuserDO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 面试官临时账户表
 * 
 * @author sun
 * @email safeinfo@163.com
 * @date 2018-10-16 19:53:36
 */
public interface TempuserService extends BaseService<TempuserDO> {
    List<PostDO> list(String tempuserid);
    //批量修改临时用户的密码和岗位
    int batchUpdate(TempuserDO tempuserDO);
    //查询用户自增数的最大值
    List<Integer> maxUserNum();

    List<TempuserDO> getByname(Map<String, Object> map);

}
