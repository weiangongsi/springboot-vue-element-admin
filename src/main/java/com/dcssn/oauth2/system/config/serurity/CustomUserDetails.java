package com.dcssn.oauth2.system.config.serurity;

import com.dcssn.oauth2.system.entity.Menu;
import com.dcssn.oauth2.system.entity.Role;
import com.dcssn.oauth2.system.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 登陆用户的信息
 *
 * @author lihy
 */
@Data
public class CustomUserDetails implements UserDetails {

    private User user;

    private Role role;

    private Set<GrantedAuthority> authorities = new HashSet<>();

    private LocalDateTime loginTime;

    CustomUserDetails(final User user, final Role role) {
        this.loginTime = LocalDateTime.now();
        this.user = user;
        this.role = role;
        if (role != null) {
            if (!StringUtils.isEmpty(role.getCode())) {
                Assert.isTrue(!role.getCode().startsWith("ROLE_"), () -> role
                        + " cannot start with ROLE_ (it is automatically added)");
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode().toUpperCase()));
            }
            List<Menu> menus = role.getMenus();
            // 每个菜单作为一个角色
            menus.forEach(menu ->
            {
                if (!StringUtils.isEmpty(menu.getCode())) {
                    Assert.isTrue(!menu.getCode().startsWith("ROLE_"), () -> menu
                            + "'s code cannot start with ROLE_ (it is automatically added)");
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + menu.getCode().toUpperCase()));
                }
            });
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }
}
