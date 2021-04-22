package com.example.demo.service.prod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.model.sell.dto.SellItemGetDto;
import com.example.demo.model.sell.dto.SellItemPostDto;
import com.example.demo.model.sell.dto.SellItemSummaryGetDto;
import com.example.demo.model.sell.dto.SellRegReadDto;
import com.example.demo.model.sell.entity.SellItemPureEntity;
import com.example.demo.model.sell.proj.SellItemGroupProj;
import com.example.demo.model.sell.repository.SellItemPureRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdService {
    @Autowired
    SellItemPureRepository prodOrderPureRepository;

    public List<SellItemGetDto> getSellItemsService(String mallName, Date startDate, Date endDate){
        List<SellItemPureEntity> entities = prodOrderPureRepository.selectAllByMallADateRange(mallName, startDate, endDate);
        List<SellItemGetDto> dtos = new ArrayList<>();
        for(SellItemPureEntity entity : entities){
            SellItemGetDto dto = new SellItemGetDto();
            dto.setAmount(entity.getAmount());
            dto.setId(entity.getId());
            dto.setMallName(entity.getMallName());
            dto.setOptionInfo(entity.getOptionInfo());
            dto.setOrderNo(entity.getOrderNo());
            dto.setProdName(entity.getProdName());
            dto.setProdNo(entity.getProdNo());
            dto.setProdOrderNo(entity.getProdOrderNo());
            dto.setRegDate(entity.getRegDate());
            dto.setShipping(entity.getShipping());
            dto.setUnit(entity.getUnit());
            dtos.add(dto);
        }
        return dtos;
    }
    public void insertSellItemAll(SellItemPostDto postDto){
        List<SellItemGetDto> dtos = postDto.getData();
        String mallName = postDto.getMallName();
        Date regDate = postDto.getRegDate();

        List<SellItemPureEntity> entities = new ArrayList<>();
        for(SellItemGetDto dto : dtos){
            SellItemPureEntity entity = new SellItemPureEntity();
            entity.setMallName(mallName);
            entity.setAmount(dto.getAmount());
            entity.setOptionInfo(dto.getOptionInfo());
            entity.setOrderNo(dto.getOrderNo());
            entity.setProdName(dto.getProdName());
            entity.setProdNo(dto.getProdNo());
            entity.setProdOrderNo(dto.getProdOrderNo());
            entity.setShipping(dto.getShipping());
            entity.setUnit(dto.getUnit());
            entity.setRegDate(regDate);
            entities.add(entity);
        }

        prodOrderPureRepository.saveAll(entities);
        // System.out.println(prodOrderPureRepository.selectGroupByProdNoAOptionInfo());
        
    }
    public List<SellItemSummaryGetDto> getSellItemsSummaryService(String mallName) {
        List<SellItemSummaryGetDto> dtos = new ArrayList<>();

        for(SellItemGroupProj proj : prodOrderPureRepository.selectGroupByProdNoAOptionInfo(mallName)){
            SellItemSummaryGetDto dto = new SellItemSummaryGetDto();
            dto.setMallName(proj.getMallName());
            dto.setProdNo(proj.getProdNo());
            dto.setProdName(proj.getProdName());
            dto.setOptionInfo(proj.getOptionInfo());
            dto.setUnitSum(proj.getUnitSum());
            dto.setShippingSum(proj.getShippingSum());
            dto.setAmountSum(proj.getAmountSum());
            dtos.add(dto);
            // System.out.println(proj.getMallName());
            // System.out.println(proj.getProdNo());
            // System.out.println(proj.getProdName());
            // System.out.println(proj.getOptionInfo());
            // System.out.println(proj.getUnitSum());
            // System.out.println(proj.getShippingSum());
            // System.out.println(proj.getAmountSum());
        }
        return dtos;
    }
}
