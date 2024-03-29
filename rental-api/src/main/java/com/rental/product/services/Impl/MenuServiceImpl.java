package com.rental.product.services.Impl;

import com.rental.product.exception.RentalException;
import com.rental.product.mapper.MenuVO;
import com.rental.product.model.MenuEntity;
import com.rental.product.repository.MenuRepository;
import com.rental.product.services.IMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MenuServiceImpl implements IMenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    @Override
    public List<MenuVO> findAllMenu() {
        List<MenuVO> menuVOList = new ArrayList<>();
        try{
            List<MenuEntity> menuEntityList = menuRepository.findAll();
            menuEntityList.forEach(e -> {
                MenuVO menuVO = new MenuVO();
                BeanUtils.copyProperties(e, menuVO);
                menuVOList.add(menuVO);
            });
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return menuVOList.stream().sorted(Comparator.comparing(MenuVO::getSortOrder)).toList();
    }

    @Override
    public Optional<MenuVO> findByMenuId(Long menuId) {
        MenuVO menuVO = new MenuVO();
        try{
            Optional<MenuEntity> menuEntity = menuRepository.findById(menuId);
            if(menuEntity.isPresent()){
                BeanUtils.copyProperties(menuEntity.get(), menuVO);
                return Optional.of(menuVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<MenuVO> findByMenuDisplayName(String displayName) {
        MenuVO menuVO = new MenuVO();
        try{
            Optional<MenuEntity> menuEntity = menuRepository.findByDisplayName(displayName);
            if(menuEntity.isPresent()){
                BeanUtils.copyProperties(menuEntity.get(), menuVO);
                return Optional.of(menuVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public String validateMenuDetails(MenuVO menuVO) {
        AtomicReference<String> error = new AtomicReference<>("");
        try{
            if(StringUtils.isBlank(menuVO.getDisplayName())){
                error.set(error + " Menu Display Name");
            }
            if(StringUtils.isBlank(menuVO.getIcon())){
                error.set(error + " Menu Icon");
            }
            if(StringUtils.isBlank(menuVO.getRoute())){
                error.set(error + " Menu Route URL");
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error.get();
    }

    @Override
    public MenuVO saveMenu(MenuVO menuVO) {
        try{
            MenuEntity menuEntity = new MenuEntity();
            BeanUtils.copyProperties(menuVO, menuEntity);
            menuEntity = menuRepository.save(menuEntity);
            BeanUtils.copyProperties(menuEntity, menuVO);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return menuVO;
    }

    @Override
    public String deleteMenu(Long menuId) {
        try{
            menuRepository.deleteById(menuId);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return "Menu deleted successfully";
    }
}
