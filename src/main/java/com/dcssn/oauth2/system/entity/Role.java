package com.dcssn.oauth2.system.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色
 *
 * @author lihy
 */
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色名
     */
    @NotEmpty
    @Column(unique = true)
    private String name;

    /**
     * 角色key
     */
    @NotEmpty
    @Column(unique = true)
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 菜单
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private List<Menu> menus = new ArrayList<>();

    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createDate;

    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updateDate;
}
