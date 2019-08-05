package com.dcssn.oauth2.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author lihy
 */
@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    private String title;

    /**
     * 编码
     */
    private String code;

    /**
     * 层级
     */
    private int level;

    /**
     * 排序
     */
    @OrderBy("sequence asc")
    private Integer sequence;

    /**
     * 父类
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Menu parent;

    /**
     * 子类
     */
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @OrderBy("sequence asc")
    private List<Menu> children = new ArrayList<>();

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
