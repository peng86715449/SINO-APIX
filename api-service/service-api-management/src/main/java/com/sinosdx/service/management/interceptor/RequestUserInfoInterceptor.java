package com.sinosdx.service.management.interceptor;

import com.sinosdx.service.management.constants.Constants;
import com.sinosdx.service.management.utils.ThreadContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * @author wendy
 * @date 2020/11/30
 */
@Slf4j
public class RequestUserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(String.format("requestUrl: %s", request.getRequestURL().toString()));

        // 中台用户系统
        if (StringUtils.isNotEmpty(request.getHeader(Constants.AUTH_HEADER))) {
            String token = request.getHeader(Constants.AUTH_HEADER).substring(Constants.AUTH_HEADER_PREFIX.length());
            Claims claims = Jwts.parser().setSigningKey("sinosdx".getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();

//            ThreadContext.put(Constants.THREAD_CONTEXT_CLIENT_ID, claims.get("client_id"));
            ThreadContext.put(Constants.THREAD_CONTEXT_TOKEN, token);

            if (!claims.containsKey(Constants.THREAD_CONTEXT_USER_ID)) {
                ThreadContext.put(Constants.THREAD_CONTEXT_USER_ID, 1);
                ThreadContext.put(Constants.THREAD_CONTEXT_USERNAME, "user_1");
                ThreadContext.put(Constants.THREAD_CONTEXT_PHONE, null);
            } else {
                ThreadContext.put(Constants.THREAD_CONTEXT_USER_ID, claims.get("userId"));
                ThreadContext.put(Constants.THREAD_CONTEXT_USERNAME, claims.get("username"));
                ThreadContext.put(Constants.THREAD_CONTEXT_PHONE, claims.get("phone"));
            }
        }

        // csp2.0 jwt
//        if (StringUtils.isNotEmpty(request.getHeader(Constants.AUTH_JWT))) {
//            String jwt = request.getHeader(Constants.AUTH_JWT);
//            ThreadContext.put(Constants.THREAD_CONTEXT_TOKEN, jwt);
//            try {
//                Map<String, Claim> claimMap = JwtUtil.verifyJwt(null, jwt);
//                if (null == claimMap) {
//                    log.error("jwt解析错误");
//                    throw new AuthException(ResultCodeEnum.TOKEN_ERROR);
//                }
//                String mobile = claimMap.get("phone_number").asString();
//
//                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//                OmpServiceFeign ompService = factory.getBean(OmpServiceFeign.class);
//                JSONObject ompUser = ompService.queryOmpUser(mobile, null, null).getData();
//                if (null == ompUser) {
//                    throw new AuthException(ResultCodeEnum.JWT_EXPIRED);
//                }
//                ThreadContext.put(Constants.THREAD_CONTEXT_USER_ID, ompUser.getInteger("id"));
//                ThreadContext.put(Constants.THREAD_CONTEXT_USERNAME, ompUser.getString("username"));
//                ThreadContext.put(Constants.THREAD_CONTEXT_PHONE, mobile);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("验证token错误", e);
//                throw new AuthException(ResultCodeEnum.JWT_EXPIRED);
//            }
//        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        ThreadContext.remove(Constants.THREAD_CONTEXT_CLIENT_ID);
        ThreadContext.remove(Constants.THREAD_CONTEXT_USER_ID);
        ThreadContext.remove(Constants.THREAD_CONTEXT_USERNAME);
        ThreadContext.remove(Constants.THREAD_CONTEXT_PHONE);
        ThreadContext.remove(Constants.THREAD_CONTEXT_TOKEN);
    }
}
