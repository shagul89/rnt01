package com.rental.product.services.Impl;

import com.rental.product.exception.RentalException;
import com.rental.product.mapper.ConfigurationVO;
import com.rental.product.model.ConfigurationEntity;
import com.rental.product.repository.ConfigurationRepository;
import com.rental.product.services.IConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ConfigurationServiceImpl implements IConfigurationService {

    private final ConfigurationRepository configurationRepository;

    @Autowired
    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public List<ConfigurationVO> findAllConfiguration() {
        List<ConfigurationVO> configurationVOList = new ArrayList<>();
        try{
            List<ConfigurationEntity> configurationEntityList = configurationRepository.findAll();
            configurationEntityList.forEach(e -> {
                ConfigurationVO configurationVO = new ConfigurationVO();
                BeanUtils.copyProperties(e, configurationVO);
                configurationVOList.add(configurationVO);
            });
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return configurationVOList;
    }

    @Override
    public Optional<ConfigurationVO> findByConfigurationId(Long configurationId) {
        ConfigurationVO configurationVO = new ConfigurationVO();
        try{
            Optional<ConfigurationEntity> configurationEntity = configurationRepository.findById(configurationId);
            if(configurationEntity.isPresent()){
                BeanUtils.copyProperties(configurationEntity.get(), configurationVO);
                return Optional.of(configurationVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ConfigurationVO> findByConfigurationName(String name) {
        ConfigurationVO configurationVO = new ConfigurationVO();
        try{
            Optional<ConfigurationEntity> configurationEntity = configurationRepository.findByName(name);
            if(configurationEntity.isPresent()){
                BeanUtils.copyProperties(configurationEntity.get(), configurationVO);
                return Optional.of(configurationVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public String validateConfigurationDetails(ConfigurationVO configurationVO) {
        AtomicReference<String> error = new AtomicReference<>("");
        try{
            if(StringUtils.isBlank(configurationVO.getName())){
                error.set(error + " Configuration Name");
            }
            if(StringUtils.isBlank(configurationVO.getValue())){
                error.set(error + " Configuration Value");
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error.get();
    }

    @Override
    public Object saveConfiguration(ConfigurationVO configurationVO) {
        try{
            ConfigurationEntity configurationEntity = new ConfigurationEntity();
            BeanUtils.copyProperties(configurationVO, configurationEntity);
            configurationEntity = configurationRepository.save(configurationEntity);
            BeanUtils.copyProperties(configurationEntity, configurationVO);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return configurationVO;
    }

    @Override
    public String deleteConfiguration(Long configurationId) {
        try{
            configurationRepository.deleteById(configurationId);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return "Configuration deleted successfully";
    }
}
