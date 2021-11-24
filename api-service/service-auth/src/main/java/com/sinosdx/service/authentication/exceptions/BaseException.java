package com.sinosdx.service.authentication.exceptions;


import com.sinosdx.service.authentication.result.enums.BaseExceptionEnum;
import com.sinosdx.service.authentication.result.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常基类
 * <br>
 * 可自定义继承BaseException异常
 *
 * @author pengjiahu
 * @date 2020/8/4 12:55
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 194906846739586856L;

    protected String code;

    protected String message;

    protected ResultCodeEnum resultCode;

    protected Object data;

    public BaseException() {
        BaseExceptionEnum exceptionEnum = BaseExceptionEnum.getByEnumClass(this.getClass());
        if (exceptionEnum != null) {
            resultCode = exceptionEnum.getResultCode();
            code = exceptionEnum.getResultCode().getCode().toString();
            message = exceptionEnum.getResultCode().getDesc();
        }
    }

    public BaseException(String message) {
        this();
        this.message = message;
    }

    public BaseException(ResultCodeEnum resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }

    public BaseException(ResultCodeEnum resultCode) {
        this.resultCode = resultCode;
        this.code = resultCode.getCode().toString();
        this.message = resultCode.getDesc();
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
