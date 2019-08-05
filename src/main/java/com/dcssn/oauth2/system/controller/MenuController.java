package com.dcssn.oauth2.system.controller;

import com.dcssn.oauth2.system.repository.MenuRepository;
import com.dcssn.oauth2.system.utils.HttpResultUtils;
import com.dcssn.oauth2.system.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单
 *
 * @author lihaoyang
 * @since 2019/7/29
 */
@RestController
@RequestMapping("menu")
public class MenuController
{

    @Autowired
    private MenuRepository menuRepository;

    /**
     * 所有role
     *
     * @return list
     */
    @GetMapping("")
    public HttpResultUtils.HttpResult all()
    {
        // 一级菜单
        List<Menu> menus = menuRepository.findFirstLevelMenus();
        return HttpResultUtils.success(menus);
    }


}
