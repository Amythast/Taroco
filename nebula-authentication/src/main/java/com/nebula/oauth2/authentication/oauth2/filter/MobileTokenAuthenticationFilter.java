package com.nebula.oauth2.authentication.oauth2.filter;

import com.nebula.oauth2.authentication.consts.SecurityConstants;
import com.nebula.oauth2.authentication.oauth2.token.MobileTokenAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 手机号获取token登录认证filter
 * 通过手机号直接获取 token
 *
 * @author feifeixia
 * 2019/5/13 15:37
 * @see UsernamePasswordAuthenticationFilter
 */
public class MobileTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SPRING_SECURITY_RESTFUL_PHONE_KEY = "mobile";
    private static final String SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY = "code";

    private boolean postOnly = true;

    public MobileTokenAuthenticationFilter() {
        // 定义一个指定路径的手机号登录前缀
        super(new AntPathRequestMatcher(SecurityConstants.MOBILE_TOKEN_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        AbstractAuthenticationToken authRequest;
        String principal;
        String credentials;

        // 手机验证码登陆
        principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_PHONE_KEY);
        credentials = obtainParameter(request, SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY);

        principal = principal.trim();
        authRequest = new MobileTokenAuthenticationToken(principal, credentials);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        String result =  request.getParameter(parameter);
        return result == null ? "" : result;
    }

    protected void setDetails(HttpServletRequest request,
                              AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public boolean isPostOnly() {
        return postOnly;
    }
}
