package com.dcssn.oauth2.system.controller;

import com.dcssn.oauth2.common.constants.Security;
import com.dcssn.oauth2.system.config.serurity.CustomUserDetails;
import com.dcssn.oauth2.system.entity.User;
import com.dcssn.oauth2.system.utils.HttpResultUtils;
import com.dcssn.oauth2.system.utils.HttpResultUtils.HttpResult;
import com.dcssn.oauth2.system.utils.SecurityUtil;
import com.dcssn.oauth2.system.vo.OnlineUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 在线用户列表
 *
 * @author lhy
 * @since 2019-05-17
 */
@Secured(Security.ONLINE_USERS)
@RestController
@RequestMapping("/online-users")
public class OnlineUsersController {

    private static final String ADMIN_LOGIN_NAME = "admin";

    @Autowired
    private InMemoryTokenStore memoryTokenStore;

    /**
     * 所有在线用户
     *
     * @return users
     */
    @GetMapping("/all")
    public HttpResult all() {
        try {
            // 当前用户登录名
            String username = SecurityUtil.currentUser().getUsername();
            List<OnlineUserVo> userList = new ArrayList<>();
            // 取出oauth2的token列表
            Collection<OAuth2AccessToken> tokens = memoryTokenStore.findTokensByClientId("web-auto-update");
            tokens.forEach(oAuth2AccessToken ->
            {
                // 没有过期的用户
                if (!oAuth2AccessToken.isExpired()) {
                    // 根据token获取用户信息
                    OAuth2Authentication oAuth2Authentication = memoryTokenStore.readAuthentication(oAuth2AccessToken.getValue());
                    CustomUserDetails userDetails = (CustomUserDetails) oAuth2Authentication.getUserAuthentication().getPrincipal();
                    User user = userDetails.getUser();
                    // 不包含超级管理员和当前用户
                    if (!ADMIN_LOGIN_NAME.equals(user.getUsername()) && !username.equals(user.getUsername())) {
                        // 封装视图层数据
                        OnlineUserVo onlineUser = new OnlineUserVo();
                        onlineUser.setUsername(user.getUsername());
                        onlineUser.setNickname(user.getNickname());
                        onlineUser.setLoginTime(userDetails.getLoginTime());
                        userList.add(onlineUser);
                    }
                }
            });
            List<OnlineUserVo> userListSorted = userList.stream().sorted(Comparator.comparing(OnlineUserVo::getLoginTime).reversed()).collect(Collectors.toList());
            return HttpResultUtils.success(userListSorted);
        } catch (Exception e) {
            return HttpResultUtils.fail("服务器错误" + e.getMessage());
        }
    }

    /**
     * 强制退出
     *
     * @param loginName 用户名
     * @return result
     */
    @DeleteMapping("/force-logout/{loginName}")
    public HttpResult forceLogout(@PathVariable String loginName) {
        try {
            // 移除access_token
            Collection<OAuth2AccessToken> tokens = memoryTokenStore.findTokensByClientIdAndUserName("web-auto-update", loginName);
            List<OAuth2AccessToken> tokenList = new ArrayList<>(tokens);
            for (int i = 0; i < tokenList.size(); i++) {
                OAuth2AccessToken oAuth2AccessToken = tokenList.get(i);
                if (i == 0) {
                    OAuth2Authentication authentication = memoryTokenStore.readAuthentication(oAuth2AccessToken);
                    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                }
                memoryTokenStore.removeAccessToken(oAuth2AccessToken);
            }
            return HttpResultUtils.success("强制退出失败");
        } catch (Exception e) {
            return HttpResultUtils.fail("强制退出失败" + e.getMessage());
        }
    }

}
