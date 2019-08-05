package com.dcssn.oauth2.system.controller;

import com.dcssn.oauth2.common.constants.Security;
import com.dcssn.oauth2.system.entity.Role;
import com.dcssn.oauth2.system.repository.RoleRepository;
import com.dcssn.oauth2.system.utils.HttpResultUtils;
import com.dcssn.oauth2.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * 角色
 *
 * @author lihaoyang
 * @since 2019/7/29
 */
@Secured(Security.PERMISSION)
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 所有role
     *
     * @return list
     */
    @GetMapping("")
    public HttpResultUtils.HttpResult all() {
        List<Role> all = roleRepository.findAll();
        return HttpResultUtils.success(all);
    }

    /**
     * 新增
     *
     * @return 是否成功
     */
    @PostMapping("")
    public HttpResultUtils.HttpResult create(@Valid @RequestBody Role role) {
        roleRepository.save(role);
        return HttpResultUtils.success();
    }

    /**
     * 删除
     *
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    public HttpResultUtils.HttpResult delete(@PathVariable Long id) {
        userRepository.updateUserRoleNull(id);
        roleRepository.deleteById(id);
        return HttpResultUtils.success();
    }

    /**
     * 修改
     *
     * @return 是否成功
     */
    @PutMapping("")
    public HttpResultUtils.HttpResult update(@Valid @RequestBody Role role) {
        Optional<Role> roleOptional = roleRepository.findById(role.getId());
        if (roleOptional.isPresent()) {
            Role currentRule = roleOptional.get();
            currentRule.setName(role.getName());
            currentRule.setCode(role.getCode());
            currentRule.setDescription(role.getDescription());
            currentRule.setMenus(role.getMenus());
            roleRepository.save(currentRule);
            return HttpResultUtils.success();
        }
        return HttpResultUtils.fail();
    }

}
