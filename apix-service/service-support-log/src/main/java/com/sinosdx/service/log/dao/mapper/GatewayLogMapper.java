/*
 * Copyright © 2022 SinoSDX (biz@sinosdx.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinosdx.service.log.dao.mapper;

import com.sinosdx.common.base.base.mapper.SuperMapper;
import com.sinosdx.service.log.dao.entity.GatewayLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pengjiahu
 * @date 2020-08-29
 * @description
 */
@Repository
public interface GatewayLogMapper extends SuperMapper<GatewayLog> {

    List<Object> queryGatewayLogList(@Param("httpMethod") String httpMethod,
                                     @Param("domain") String domain,
                                     @Param("requestPath") String requestPath,
                                     @Param("startTime") Long startTime,
                                     @Param("endTime") Long endTime,
                                     @Param("appCode") String appCode,
                                     @Param("responseStatus") String responseStatus,
                                     @Param("limit") Integer limit,
                                      @Param("offset") Integer offset);

}
