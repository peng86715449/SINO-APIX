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
package com.sinosdx.service.authentication.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wendy
 * @date 2020/11/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String account;
    private String mobile;
    private String email;
    private String clientId;
    private List<String> roles = new ArrayList<>();
}
