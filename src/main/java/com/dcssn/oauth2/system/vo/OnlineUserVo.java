package com.dcssn.oauth2.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lihaoyang
 * @since 2019/8/5
 */
@Data
public class OnlineUserVo {
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;
}
