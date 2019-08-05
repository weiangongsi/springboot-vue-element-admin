package com.dcssn.oauth2.system.repository;

import com.dcssn.oauth2.system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lihaoyang
 * @since 2019/7/18
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
