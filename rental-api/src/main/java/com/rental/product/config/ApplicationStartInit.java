package com.rental.product.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.product.constant.DefaultRolesPermission;
import com.rental.product.enumeration.UserStatus;
import com.rental.product.enumeration.UserType;
import com.rental.product.mapper.MenuVO;
import com.rental.product.mapper.RoleVO;
import com.rental.product.mapper.UserVO;
import com.rental.product.services.IMenuService;
import com.rental.product.services.IRolePermissionService;
import com.rental.product.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApplicationStartInit {

    private final Logger logger = LoggerFactory.getLogger(ApplicationStartInit.class);

    @Value("${defaultPassword}")
    private String defaultPassword;

    private final IUserService userService;

    private final IRolePermissionService rolePermissionService;

    private final IMenuService menuService;

    @Autowired
    public ApplicationStartInit(IUserService userService, IRolePermissionService rolePermissionService, IMenuService menuService) {
        this.userService = userService;
        this.rolePermissionService = rolePermissionService;
        this.menuService = menuService;
    }

    @EventListener(classes = ApplicationStartedEvent.class)
    public void onApplicationEvent(ApplicationStartedEvent event){
        ObjectMapper mapper = new ObjectMapper();
        try{
            List<MenuVO> menuVOList = mapper.readValue(DefaultRolesPermission.MENU_DETAILS, new TypeReference<List<MenuVO>>(){});

            menuVOList.forEach(e -> {
                Optional<MenuVO> menuVODB = menuService.findByMenuDisplayName(e.getDisplayName());
                if(!menuVODB.isPresent()){
                    menuService.saveMenu(e);
                }
            });


            List<RoleVO> rolesList = mapper.readValue(DefaultRolesPermission.DEFAULT_ROLE_PERMISSION, new TypeReference<List<RoleVO>>(){});
            Map<String, Object> map = rolePermissionService.saveRolePermission(rolesList);
            if(map.containsKey("success")){
                boolean userVO = userService.isExistUser("admin@gmail.com");
                if(userVO){
                    logger.info(String.format("Default user already exists %s ", "admin@gmail.com"));
                } else {
                    UserVO userVO1 = new UserVO();
                    userVO1.setFirstName("admin");
                    userVO1.setLastName("admin");
                    userVO1.setEmail("admin@gmail.com");
                    userVO1.setStatus(UserStatus.ACTIVE);
                    userVO1.setUserType(UserType.SUPER_ADMIN);

                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    String encodedPassword = encoder.encode(defaultPassword);
                    userVO1.setPassword(encodedPassword);

                    userService.saveUser(userVO1);
                }
            } else {
                logger.error("Exception occurred in while default roles and permission save or update", map);
            }

        }catch (Exception e){
            logger.error("Exception occurred in ", e);
        }
    }
}
