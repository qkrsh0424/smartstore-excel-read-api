package com.example.demo.controller.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.Message;
import com.example.demo.model.sell.dto.SellRegReadDto;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/excel")
public class ExcelApiController {
    // /api/excel/sell-items/read
    @PostMapping("/sell-items/read")
    public ResponseEntity<Message> ProdExcelReadApi(@RequestParam("file") MultipartFile file) throws IOException{
        Message message = new Message();

        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);
        List<SellRegReadDto> dtos = getReadedExcel(worksheet);

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(dtos);
        return new ResponseEntity<>(message, HttpStatus.OK);
        
    }

    private List<SellRegReadDto> getReadedExcel(Sheet worksheet) {
        List<SellRegReadDto> dataList = new ArrayList<>();
        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            SellRegReadDto data = new SellRegReadDto();

            // System.out.println((int) row.getCell(20).getNumericCellValue());
            data.setMallName("");
            data.setProdOrderNo(row.getCell(0).getStringCellValue());
            data.setOrderNo(row.getCell(1).getStringCellValue());
            data.setProdNo(row.getCell(15).getStringCellValue());
            data.setProdName(row.getCell(16).getStringCellValue());
            data.setOptionInfo(row.getCell(18).getStringCellValue());
            data.setUnit((int) row.getCell(20).getNumericCellValue());
            data.setShipping((int) row.getCell(34).getNumericCellValue());
            data.setAmount((int) row.getCell(53).getNumericCellValue());
            dataList.add(data);
        }
        return dataList;
    }
}
