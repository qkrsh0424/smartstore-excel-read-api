package com.example.demo.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.model.ExcelData;
import com.example.demo.model.ExcelData2;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ApiController {
    @GetMapping(value = "/test")
    public String ReadExcel() {
        System.out.println("test");
        return "{\"message\":\"test\"}";
    }

    @PostMapping(value = "/excel/read")
    public void ReadExcelApi(@RequestParam("file") MultipartFile file) throws IOException {
        List<ExcelData2> dataList = new ArrayList<>();

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

        dataList = getReadedExcel(worksheet);

        // Set<String> prods = new HashSet<>();
        // for (ExcelData2 data : dataList) {
        //     prods.add(data.getProdName());
        // }

        // List<String> list = new ArrayList<>();
        // list.addAll(prods);
        System.out.println(dataList);
    }

    private List<ExcelData2> getReadedExcel(Sheet worksheet) {
        List<ExcelData2> dataList = new ArrayList<>();
        for (int i = 2; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            ExcelData2 data = new ExcelData2();

            // System.out.println((int) row.getCell(20).getNumericCellValue());
            data.setBuyer(row.getCell(8).getStringCellValue());
            data.setReciever(row.getCell(10).getStringCellValue());
            data.setProdNo(row.getCell(15).getStringCellValue());
            data.setProdName(row.getCell(16).getStringCellValue());
            data.setOptionInfo(row.getCell(18).getStringCellValue());
            data.setUnit((int) row.getCell(20).getNumericCellValue());
            data.setRecieverContact1(row.getCell(40).getStringCellValue());
            data.setRecieverContact2(row.getCell(41) != null ? row.getCell(41).getStringCellValue() : "");
            data.setDestination(row.getCell(42).getStringCellValue());
            data.setBuyerContact(row.getCell(43).getStringCellValue());
            data.setZipcode(row.getCell(44).getStringCellValue());
            data.setDeliveryMessage(row.getCell(45) != null ? row.getCell(45).getStringCellValue(): "");
            data.setProdName(row.getCell(16).getStringCellValue());
            dataList.add(data);
        }
        return dataList;
    }
}
