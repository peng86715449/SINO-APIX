package com.sinosdx.service.management.controller;

import com.sinosdx.common.base.result.R;
import com.sinosdx.service.management.consumer.SupportLogServiceFeign;
import com.sinosdx.service.management.controller.dto.ApplicationInnerNumDTO;
import com.sinosdx.service.management.controller.dto.ApplicationNumDTO;
import com.sinosdx.service.management.controller.vo.ApplicationNumVo;
import com.sinosdx.service.management.controller.vo.StatisticsVo;
import com.sinosdx.service.management.service.ApplicationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Api(tags = "API模块")
@RestController
@RequestMapping("/app/data")
public class ApiHomeController {

    @Autowired
    private SupportLogServiceFeign supportLogService;

    @Autowired
    private ApplicationService applicationService;

    /**
     * 查询首页柱状图数据
     *
     * @param statisticsVo
     * @return
     */
    @PostMapping("/statistics")
    public R<Object> statistics(@RequestBody StatisticsVo statisticsVo) {
        // 时间范围目前写死为 24h前 到 当前时间
        LocalDateTime endLocalDateTime = LocalDateTime.now();
        LocalDateTime startLocalDateTime = endLocalDateTime.minusHours(24);
        Long endTime = endLocalDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long startTime = startLocalDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return supportLogService.queryListByAppCode(statisticsVo.getAppCodes(), startTime, endTime);
    }

    /**
     * 查询首页应用相关数据
     *
     * @param applicationNumVo
     * @return
     */
    @PostMapping("/num")
    public R<Object> queryApplicationNum(@RequestBody ApplicationNumVo applicationNumVo) {
        ApplicationNumDTO applicationNumDTO = applicationService.queryApplicationNum(applicationNumVo);
        return R.success(applicationNumDTO);
    }


    /**
     * 查询应用内部请求数、请求失败数、已订阅数
     *
     * @param appCode
     * @return
     */
    @PostMapping("/{appCode}/appNum")
    public R<Object> applicationInnerNum(@PathVariable String appCode) {
        // 时间范围目前写死为 前3个月 到 当前时间
        LocalDateTime endLocalDateTime = LocalDateTime.now();
        LocalDateTime startLocalDateTime = endLocalDateTime.minusMonths(3);
        Long endTime = endLocalDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long startTime = startLocalDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        Long subscribeNum =  applicationService.applicationSubscribeNum(appCode,startTime,endTime);
        R<Object> objectR = supportLogService.queryGatewayLogByStatus(appCode, startTime, endTime);
        ApplicationInnerNumDTO applicationInnerNumDTO = (ApplicationInnerNumDTO)objectR.getData();
        applicationInnerNumDTO.setSubscribedNum(subscribeNum);
        return R.success(applicationInnerNumDTO);
    }
}