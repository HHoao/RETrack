package com.rare_earth_track.portal.service;

import com.rare_earth_track.mgb.model.RetMenu;

import java.util.List;

/**
 * The interface Ret role menu relation service.
 *
 * @author hhoa
 * @date 2022 /6/13
 */
public interface RetRoleMenuRelationService {
    /**
     * Gets menus.
     *
     * @param roleId the role id
     * @return the menus
     */
    List<RetMenu> getMenus(Long roleId);

    /**
     * Delete role menus.
     *
     * @param id the id
     */
    void deleteRoleMenus(Long id);
}
