package com.wt.security.shiro.realm;

import com.wt.blog.base.service.IUserService;
import com.wt.blog.entity.User;
import com.wt.commons.exception.enums.AuthExceptionEnum;
import com.wt.commons.exception.enums.ExceptionEnum;
import com.wt.commons.exception.utils.ExceptionEnumUtils;
import com.wt.security.jwt.JWTToken;
import com.wt.security.jwt.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Shiro 自定义 认证 Realm
 *
 * @author wtao
 * @date 2019-10-27 18:32
 */
@Slf4j
public class AuthRealm extends AuthorizingRealm {


    /**
     * 使JWTToken 能用于shiro的token认证
     *
     * @author wtao
     * @date 2019-10-27 21:36
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Resource
    private IUserService iUserService;

    /**
     * 授权方法
     *
     * @author wtao
     * @date 2019-10-27 18:51
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


    /**
     * 认证方法
     *
     * @author wtao
     * @date 2019-10-27 18:51
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        log.info("【AuthRealm doGetAuthenticationInfo() 】TOKEN认证");

        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtils.getUsername(token);

        // TOKEN 已过期 / 失效
        if (username == null || StringUtils.isEmpty(username)) {
            throw new AuthenticationException(ExceptionEnumUtils.getMsg(AuthExceptionEnum.TOKEN_INVALID));
        }

        /*User user = iUserService.getOne(new QueryWrapper<User>().eq("account", username));*/
        User user = new User();
        user.setUsername("123132");
        user.setPassword("1321320");
        user.setToken("aaaa");
        // 账号不存在
        if (ObjectUtils.isEmpty(user)) {
            throw new UnknownAccountException(ExceptionEnumUtils.getMsg(AuthExceptionEnum.ACCOUNT_NOT_FOUND));
        }
        // 密码错误
        if (!JWTUtils.verify(token, username, user.getPassword())) {
            throw new IncorrectCredentialsException(ExceptionEnumUtils.getMsg(AuthExceptionEnum.PASSWORD_ERROR));
        }
        // 账号被冻结
        if (!JWTUtils.verify(token, username, user.getPassword())) {
            throw new LockedAccountException(ExceptionEnumUtils.getMsg(AuthExceptionEnum.ACCOUNT_NOT_AVAILABLE));
        }
        return new SimpleAuthenticationInfo(token, token, user.getUsername());
    }


    /**
     * TOKEN 异常
     *
     * @author wtao
     * @date 2019/10/30 11:51
     */
    class TokenException extends AuthenticationException {
        private int status;
        private String msg;

        public TokenException(ExceptionEnum em) {
            this.status = em.getStatus();
            this.msg = em.getMsg();
        }
    }
}
