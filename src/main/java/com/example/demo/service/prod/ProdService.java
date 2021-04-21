package com.example.demo.service.prod;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.sell.dto.SellRegReadDto;
import com.example.demo.model.sell.entity.SellItemPureEntity;
import com.example.demo.model.sell.repository.SellItemPureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdService {
    @Autowired
    SellItemPureRepository prodOrderPureRepository;

    public void insertProdOrderAll(List<SellRegReadDto> dtos){
        List<SellItemPureEntity> entities = new ArrayList<>();
        for(SellRegReadDto dto : dtos){
            SellItemPureEntity entity = new SellItemPureEntity();
            // entity.setBuyer(dto.getBuyer());
            // entity.setOrderNo(dto.getOrderNo());
            entities.add(entity);
        }

        prodOrderPureRepository.saveAll(entities);
    }
}
