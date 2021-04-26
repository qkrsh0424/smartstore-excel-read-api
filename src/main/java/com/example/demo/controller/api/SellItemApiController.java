package com.example.demo.controller.api;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Message;
import com.example.demo.model.sell.dto.SellItemGetDto;
import com.example.demo.model.sell.dto.SellItemPostDto;
import com.example.demo.model.sell.dto.SellRegReadDto;
import com.example.demo.service.prod.ProdService;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/sell-item")
public class SellItemApiController {
    @Autowired
    ProdService prodService;

    // /api/sell-item/list : GET
    @GetMapping("/list")
    public ResponseEntity<Message> showList(@RequestParam("mallName") String mallName,
            @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) throws IOException {
        Message message = new Message();

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(prodService.getSellItemsService(mallName, startDate, endDate));
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/sell-item/list : POST
    @PostMapping("/list")
    public ResponseEntity<Message> createList(@RequestBody SellItemPostDto postDto) throws IOException {
        Message message = new Message();

        prodService.insertSellItemAll(postDto);

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        // message.setData(dtos);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/sell-item/list : DELETE
    @DeleteMapping("/list")
    public ResponseEntity<Message> deleteList(@RequestBody SellItemPostDto postDto) throws IOException {
        Message message = new Message();

        System.out.println(postDto);
        prodService.deleteSellItems(postDto);

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        // message.setData(dtos);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/sell-item/list : PATCH
    @PatchMapping("/list")
    public ResponseEntity<Message> updateList(@RequestBody SellItemPostDto postDto) throws IOException {
        Message message = new Message();

        System.out.println(postDto);
        prodService.updateSellItems(postDto);

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        // message.setData(dtos);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/sell-item/summary : GET
    @GetMapping("/summary")
    public ResponseEntity<Message> showSummary(@RequestParam("mallName") String mallName, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) throws IOException {
        Message message = new Message();

        // URL aUrl = new URL("https://m.smartstore.naver.com/1heart/products/5430150125");
        URL aUrl = new URL("https://m.smartstore.naver.com/1heart");
        System.out.println("protocol = " + aUrl.getProtocol());
        System.out.println("authority = " + aUrl.getAuthority());
        System.out.println("host = " + aUrl.getHost());
        System.out.println("port = " + aUrl.getPort());
        System.out.println("path = " + aUrl.getPath());
        System.out.println("query = " + aUrl.getQuery());
        System.out.println("filename = " + aUrl.getFile());
        System.out.println("ref = " + aUrl.getRef());
        Map<String, String> inflow = new HashMap<>();

        if(aUrl.getHost().equals("m.smartstore.naver.com")){
            
            inflow.put("deviceType", "모바일");
        }else{
            inflow.put("deviceType", "PC");
        }

        String[] pathArr = aUrl.getPath().split("/");
        for(int i = 0 ; i < pathArr.length; i++){
            System.out.println(pathArr[i]);
        }
        if(pathArr.length > 4 && pathArr[2].equals("products")){
            System.out.println("helo");
        }
        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(prodService.getSellItemsSummaryService(mallName, startDate, endDate));
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
}
