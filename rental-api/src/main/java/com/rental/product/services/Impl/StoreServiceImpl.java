package com.rental.product.services.Impl;

import com.rental.product.exception.RentalException;
import com.rental.product.mapper.StoreVO;
import com.rental.product.model.StoreEntity;
import com.rental.product.repository.StoreRepository;
import com.rental.product.services.IStoreService;
import com.rental.product.util.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StoreServiceImpl implements IStoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<StoreVO> findAllStore() {
        List<StoreVO> storeVOList = new ArrayList<>();
        try{
            List<StoreEntity> storeEntityList = storeRepository.findAll();
            BeanUtils.copyProperties(storeEntityList, storeVOList);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return storeVOList;
    }

    @Override
    public Optional<StoreVO> findByStoreId(Long storeId) {
        StoreVO storeVO = new StoreVO();
        try{
            Optional<StoreEntity> storeEntity = storeRepository.findById(storeId);
            if(storeEntity.isPresent()){
                BeanUtils.copyProperties(storeEntity.get(), storeVO);
                return Optional.of(storeVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public Optional<StoreVO> findByStoreName(String name) {
        StoreVO storeVO = new StoreVO();
        try{
            Optional<StoreEntity> storeEntity = storeRepository.findByName(name);
            if(storeEntity.isPresent()){
                BeanUtils.copyProperties(storeEntity.get(), storeVO);
                return Optional.of(storeVO);
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return Optional.empty();
    }

    @Override
    public String validateStoreDetails(StoreVO storeVO) {
        AtomicReference<String> error = new AtomicReference<>("");
        try{
            if(StringUtils.isBlank(storeVO.getName())){
                error.set(error + " Store Name");
            }
            if(storeVO.getStoreAddress().isEmpty()){
                error.set(error + " Store Address");
            }else {
                storeVO.getStoreAddress().forEach(e -> {
                    error.set(AppUtil.validateAddress(e, "Address "));
                });
            }
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return error.get();
    }

    @Override
    public StoreVO saveStore(StoreVO storeVO) {
        try{
            StoreEntity storeEntity = new StoreEntity();
            BeanUtils.copyProperties(storeVO, storeEntity);
            storeEntity = storeRepository.save(storeEntity);
            BeanUtils.copyProperties(storeEntity, storeVO);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return storeVO;
    }

    @Override
    public String deleteStore(Long storeId) {
        try{
            storeRepository.deleteById(storeId);
        }catch (Exception e){
            throw new RentalException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage(),e.getStackTrace());
        }
        return "Supplier deleted successfully";
    }
}
