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
package com.sinosdx.service.authentication.exceptions;

import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wendy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotRegisteredException extends RuntimeException {

    private static final long serialVersionUID = -5645133786666659153L;

    private Integer code;
    private String msg;
    private Object data;

    public UserNotRegisteredException(String msg) {
        super(msg);
        this.code = ResultCodeEnum.USER_NOT_REGISTERED.getCode();
    }

}