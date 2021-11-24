package com.sinosdx.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sinosdx.common.base.context.ThreadContext;
import com.sinosdx.common.base.result.R;
import com.sinosdx.service.user.constants.Constants;
import com.sinosdx.service.user.dao.entity.SysOrg;
import com.sinosdx.service.user.dao.entity.SysUser;
import com.sinosdx.service.user.dao.mapper.SysOrgMapper;
import com.sinosdx.service.user.result.ResultCodeEnum;
import com.sinosdx.service.user.service.SysOrgService;
import com.sinosdx.service.user.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author wendy
 * @date 2021/9/24
 */
@Service
@Slf4j
public class SysOrgServiceImpl implements SysOrgService {

    @Resource
    private SysOrgMapper sysOrgMapper;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 更新组织
     *
     * @param sysOrg
     * @return
     */
    @Override
    public R<Object> updateOrg(SysOrg sysOrg) {
        SysOrg oldOrg = sysOrgMapper.selectById(sysOrg.getId());
        if (null == oldOrg) {
            return R.fail(ResultCodeEnum.ORG_NOT_EXISTED);
        }

        if (StringUtils.isNotEmpty(sysOrg.getName()) && !sysOrg.getName().equals(oldOrg.getName())) {
            Integer count = sysOrgMapper.selectCount(new LambdaQueryWrapper<SysOrg>().eq(SysOrg::getName, sysOrg.getName()));
            if (count > 0) {
                return R.fail(ResultCodeEnum.ORG_NAME_IS_EXISTED);
            }
        }

        sysOrg.setLastUpdatedBy(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        sysOrg.setLastUpdateDate(LocalDateTime.now(TimeZone.getTimeZone("Asia/Shanghai").toZoneId()));
        sysOrgMapper.updateById(sysOrg);

        return R.success(sysOrg);
    }

    /**
     * 查询组织列表
     *
     * @param name
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public R<Object> queryOrgList(String name, Integer limit, Integer offset) {
        if ((null != limit && limit < 0) || (null != offset && offset < 1)) {
            limit = null;
            offset = null;
        }

        List<Integer> userIdList = sysRoleService.queryAllUserIdListByRole(ThreadContext.get(Constants.THREAD_CONTEXT_USER_ID));
        Map<String, Object> resultMap = new HashMap<>();
        if (!userIdList.isEmpty()) {
            List<Object> list = sysOrgMapper.queryOrgList(name, userIdList, limit, offset);
            // 数据集合
            List<SysOrg> orgList = (List<SysOrg>) list.get(0);
            // 数据总量
            Integer total = ((List<Integer>) list.get(1)).get(0);
            resultMap.put("orgList", orgList);
            resultMap.put("total", total);
        } else {
            resultMap.put("orgList", null);
            resultMap.put("total", 0);
        }

        return R.success(resultMap);
    }
}
