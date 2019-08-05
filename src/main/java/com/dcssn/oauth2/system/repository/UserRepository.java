package com.dcssn.oauth2.system.repository;

import com.dcssn.oauth2.system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author lihaoyang
 * @since 2019/7/18
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 分页查找，排除用户
     *
     * @param pageable 分页
     * @param username 用户名
     * @return page
     */
    Page<User> findAllByUsernameIsNot(String username, Pageable pageable);

    /**
     * 查找用户
     *
     * @param username 用户名
     * @return user
     */
    User findByUsername(String username);

    /**
     * 将某个角色的置空
     *
     * @param roleId 角色id
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User set role=null where role.id=:roleId")
    void updateUserRoleNull(Long roleId);
}
