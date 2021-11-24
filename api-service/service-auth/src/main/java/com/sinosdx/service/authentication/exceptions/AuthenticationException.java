package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.common.base.exceptions.BaseException;
import com.sinosdx.service.authentication.result.enums.BaseEnum;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;

/**
 * 认证异常类
 *
 * @author wendy
 * @date 2020/12/4 12:55
 */
public class AuthenticationException extends BaseException {

    private static final long serialVersionUID = 3721036867889297081L;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(Object data) {
        super.data = data;
    }

    public AuthenticationException(BaseEnum baseEnum) {
        super.code = baseEnum.getCode().toString();
        super.message = baseEnum.getDesc();
    }

    public AuthenticationException(ResultCodeEnum resultCode, Object data) {
        super(resultCode, data);
    }

    public AuthenticationException(Integer code, String msg) {
        super.code = String.valueOf(code);
        super.message = msg;
    }

    public AuthenticationException(ResultCodeEnum resultCode, String msg) {
        super.code = String.valueOf(resultCode.getCode());
        super.message = msg + resultCode.getDesc();
    }

    public AuthenticationException(String msg) {
        super(msg);
    }


    public static AuthenticationException wrap() {
        return new AuthenticationException();
    }

    public static AuthenticationException wrap(Object data) {
        return new AuthenticationException(data);
    }

    public static AuthenticationException wrap(BaseEnum baseEnum) {
        return new AuthenticationException(baseEnum);
    }

    public static AuthenticationException wrap(ResultCodeEnum resultCode, Object data) {
        return new AuthenticationException(resultCode, data);
    }

    public static AuthenticationException wrap(Integer code, String msg) {
        return new AuthenticationException(code, msg);
    }

    public static AuthenticationException wrap(ResultCodeEnum resultCode, String msg) {
        return new AuthenticationException(resultCode, msg);
    }

    public static AuthenticationException wrap(String msg) {
        return new AuthenticationException(msg);
    }

}
