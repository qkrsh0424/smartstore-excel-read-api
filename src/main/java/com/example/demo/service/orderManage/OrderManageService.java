package com.example.demo.service.orderManage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.UUID;

import com.example.demo.model.orderManage.OrderManageDto;
import com.example.demo.model.orderManage.OrdererDto;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

@Service
public class OrderManageService {

    public List<OrderManageDto> getReadExcel(Sheet worksheet) {
        List<OrderManageDto> orderManageDtos = new ArrayList<>();
        

        Set<String> prodNameSet = new TreeSet<>();
        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);

            // System.out.println(row.getCell(16).getStringCellValue()); // 상품명
            // System.out.println(row.getCell(18).getStringCellValue()); // 옵션정보
            String prodName = row.getCell(16).getStringCellValue() + "-" + (row.getCell(18) != null ? row.getCell(18).getStringCellValue():"");
            if(prodNameSet.add(prodName)){
                OrderManageDto orderManageDto = new OrderManageDto();
                orderManageDto.setUuid(UUID.randomUUID().toString());
                orderManageDto.setProdFullName(prodName);
                orderManageDto.setProdName(row.getCell(16).getStringCellValue());
                orderManageDto.setOptionInfo(row.getCell(18) != null ? row.getCell(18).getStringCellValue():"");
                orderManageDtos.add(orderManageDto);
            }
        }

        Collections.sort(orderManageDtos);
        // List prodNameList = new ArrayList<>(prodNameSet);

        for(int i = 0 ; i < orderManageDtos.size() ; i++){
            int unit = 0;
            for (int j = 2; j < worksheet.getPhysicalNumberOfRows(); j++) {
                Row row = worksheet.getRow(j);
                String prodName = row.getCell(16).getStringCellValue() + "-" + (row.getCell(18) != null ? row.getCell(18).getStringCellValue():"");
                if(prodName.equals((String) orderManageDtos.get(i).getProdFullName())){
                    unit = (int) (unit + row.getCell(20).getNumericCellValue());
                }
            }
            orderManageDtos.get(i).setUnit(unit);
        }
        // System.out.println(orderManageDtos);
        for(int i = 0 ; i < orderManageDtos.size(); i++){
            List<OrdererDto> ordererList = new ArrayList<>();
            for(int j = 2; j < worksheet.getPhysicalNumberOfRows(); j++){
                Row row = worksheet.getRow(j);
                String prodName = row.getCell(16).getStringCellValue() + "-" + (row.getCell(18) != null ? row.getCell(18).getStringCellValue():"");
                if(orderManageDtos.get(i).getProdFullName().equals(prodName)){
                    OrdererDto ordererDto = new OrdererDto();
                    ordererDto.setName(row.getCell(8).getStringCellValue());
                    ordererDto.setReceiverName(row.getCell(10).getStringCellValue());
                    ordererDto.setAddress(row.getCell(42).getStringCellValue());
                    ordererDto.setPhone(row.getCell(43).getStringCellValue());
                    ordererDto.setOrderUnit((int) row.getCell(20).getNumericCellValue());
                    ordererDto.setOrderDate(row.getCell(14).getDateCellValue());
                    ordererDto.setDeliveryLimitDate(row.getCell(28).getDateCellValue());
                    ordererList.add(ordererDto);
                }
            }
            orderManageDtos.get(i).setOrdererList(ordererList);
        }
        // System.out.println(orderManageDtos);
        return orderManageDtos;
    }
    
}
