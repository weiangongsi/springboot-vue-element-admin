package com.dcssn.oauth2.system.repository;

import com.dcssn.oauth2.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author lihaoyang
 * @since 2019/7/18
 */
public interface MenuRepository extends JpaRepository<Menu, Long>
{

    /**
     * 找出一级菜单
     *
     * @return menus
     */
    @Query("select m from Menu m where m.level=1 order by m.sequence")
    List<Menu> findFirstLevelMenus();
}
