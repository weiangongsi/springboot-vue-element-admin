package com.dcssn.oauth2.system.config.init;


import com.dcssn.oauth2.system.entity.Menu;
import com.dcssn.oauth2.system.entity.Role;
import com.dcssn.oauth2.system.entity.User;
import com.dcssn.oauth2.system.repository.MenuRepository;
import com.dcssn.oauth2.system.repository.RoleRepository;
import com.dcssn.oauth2.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 启动时，没有admin信息则系统初始化
 *
 * @author lihaoyang
 * @since 2019/8/6
 */
@Component
public class SystemInit implements CommandLineRunner {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            menuRepository.deleteAll();

            Menu menu = new Menu();
            menu.setLevel(1);
            menu.setSequence(1);
            menu.setCode("WEB");
            menu.setTitle("网站管理");
            menuRepository.save(menu);

            Menu menu3 = new Menu();
            menu3.setLevel(1);
            menu3.setSequence(2);
            menu3.setCode("USER");
            menu3.setTitle("用户管理");
            menuRepository.save(menu3);

            Menu menu2 = new Menu();
            menu2.setLevel(1);
            menu2.setSequence(3);
            menu2.setCode("PERMISSION");
            menu2.setTitle("权限管理");
            menuRepository.save(menu2);

            Menu menu4 = new Menu();
            menu4.setLevel(1);
            menu4.setSequence(5);
            menu4.setCode("ONLINE_USERS");
            menu4.setTitle("在线用户");
            menuRepository.save(menu4);

            Role role = new Role();
            role.setName("管理员");
            role.setCode("admin");
            role.setDescription("超级管理员");
            roleRepository.save(role);


            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setAvatar("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=400062461,2874561526&fm=26&gp=0.jpg");
            user.setRole(role);
            userRepository.save(user);
        }

    }

}
