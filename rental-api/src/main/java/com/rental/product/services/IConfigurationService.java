package com.rental.product.services;


import com.rental.product.mapper.ConfigurationVO;
import java.util.List;
import java.util.Optional;

public interface IConfigurationService {
    List<ConfigurationVO> findAllConfiguration();

    Optional<ConfigurationVO> findByConfigurationId(Long configurationId);

    Optional<ConfigurationVO> findByConfigurationName(String name);

    String validateConfigurationDetails(ConfigurationVO configurationVO);

    Object saveConfiguration(ConfigurationVO configurationVO);

    String deleteConfiguration(Long configurationId);
}
