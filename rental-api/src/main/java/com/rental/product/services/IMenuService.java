package com.rental.product.services;

import com.rental.product.mapper.MenuVO;
import java.util.List;
import java.util.Optional;

public interface IMenuService {
    List<MenuVO> findAllMenu();

    Optional<MenuVO> findByMenuId(Long menuId);

    Optional<MenuVO> findByMenuDisplayName(String displayName);

    String validateMenuDetails(MenuVO menuVO);

    MenuVO saveMenu(MenuVO menuVO);

    String deleteMenu(Long menuId);
}
