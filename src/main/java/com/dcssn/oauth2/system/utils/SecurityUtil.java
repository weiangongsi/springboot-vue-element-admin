package com.dcssn.oauth2.system.utils;

import com.dcssn.oauth2.system.config.serurity.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具
 *
 * @author lihaoyang
 * @since 2019/7/22
 */
public class SecurityUtil
{

    /**
     * 获取当前登录的用户
     *
     * @return 用户
     */
    public static CustomUserDetails currentUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
        {
            return (CustomUserDetails) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 强制用户退出
     *
     * @param username 用户名
     */
    public static void logout(String username)
    {
        InMemoryTokenStore memoryTokenStore = SpringUtils.getBean(InMemoryTokenStore.class);
        Collection<OAuth2AccessToken> tokens = memoryTokenStore.findTokensByClientIdAndUserName("web-auto-update", username);
        List<OAuth2AccessToken> tokenList = new ArrayList<>(tokens);
        for (OAuth2AccessToken oAuth2AccessToken : tokenList)
        {
            memoryTokenStore.removeAccessToken(oAuth2AccessToken);
        }
    }

}
