package com.wt.security.jwt;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author wtao
 * @date 2019-10-27 18:15
 */
@Data
public class JWTToken implements AuthenticationToken {


    /**
     * 秘钥
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
