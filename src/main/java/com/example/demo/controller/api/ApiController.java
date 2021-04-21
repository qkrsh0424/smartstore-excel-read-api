package com.example.demo.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import com.example.demo.handler.UuidGenerator;
import com.example.demo.model.ExcelData2;
import com.example.demo.model.ExcelData3;
import com.example.demo.model.Message;
import com.example.demo.model.OptionInfo;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    UuidGenerator uuidGenerator;


    @PostMapping(value = "/excel/read")
    public ResponseEntity<?> ReadExcelApi(@RequestParam("file") MultipartFile file) throws IOException {
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

        List<ExcelData2> dataList = getReadedExcel(worksheet);

        // Set<String> prods = new HashSet<>();
        // for (ExcelData2 data : dataList) {
        // prods.add(data.getProdName());
        // }

        // List<String> list = new ArrayList<>();
        // list.addAll(prods);
        // System.out.println(dataList);
        message.setStatus(HttpStatus.OK);
        message.setMessage("success");
        message.setData(dataList);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/excel/assemble")
    public List<ExcelData3> AssembleExcelApi(@RequestBody List<ExcelData2> dataList) {
        System.out.println(dataList.size());
        Collections.sort(dataList);

        Set<String> fSet = new HashSet<>();
        Stack<ExcelData2> fStack = new Stack<>();

        // 상품명 + 수취인명 + 주소 + 옵션정보 : 중복이 된다면 수량을 +1 해서 저장
        for (int i = 0; i < dataList.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(dataList.get(i).getProdName());
            sb.append(dataList.get(i).getReciever());
            sb.append(dataList.get(i).getDestination());
            sb.append(dataList.get(i).getOptionInfo());

            String resultString = sb.toString();
            boolean setResult = fSet.add(resultString);

            if (!setResult) {
                ExcelData2 prevData = fStack.pop();
                prevData.setUnit(prevData.getUnit() + dataList.get(i).getUnit());
                fStack.add(prevData);
            } else {
                fStack.add(dataList.get(i));
            }
        }
        System.out.println(fStack.size());
        List<ExcelData2> fResultList = new ArrayList<>(fStack);

        Set<String> sSet = new HashSet<>();
        Stack<ExcelData3> sStack = new Stack<>();

        // 상품명 + 수취인명 + 주소 : 중복이 된다면 옵션정보를 추가
        for (int i = 0; i < fResultList.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(fResultList.get(i).getProdName());
            sb.append(fResultList.get(i).getReciever());
            sb.append(fResultList.get(i).getDestination());

            String resultString = sb.toString();
            boolean setResult = sSet.add(resultString);
            if (!setResult) {
                ExcelData3 prevData = sStack.pop();

                List<OptionInfo> ois = prevData.getOptionInfos();
                OptionInfo oi = new OptionInfo();
                oi.setOptionInfo(fResultList.get(i).getOptionInfo());
                oi.setUnit(fResultList.get(i).getUnit());
                ois.add(oi);
                sStack.add(prevData);
            } else {
                ExcelData3 exd3 = new ExcelData3();
                exd3.setBuyer(fResultList.get(i).getBuyer());
                exd3.setBuyerContact(fResultList.get(i).getBuyerContact());
                exd3.setDeliveryMessage(fResultList.get(i).getDeliveryMessage());
                exd3.setDestination(fResultList.get(i).getDestination());
                exd3.setProdName(fResultList.get(i).getProdName());
                exd3.setProdNo(fResultList.get(i).getProdNo());
                exd3.setReciever(fResultList.get(i).getReciever());
                exd3.setRecieverContact1(fResultList.get(i).getRecieverContact1());
                exd3.setRecieverContact2(fResultList.get(i).getRecieverContact2());
                exd3.setZipcode(fResultList.get(i).getZipcode());

                List<OptionInfo> ois = new ArrayList<>();
                OptionInfo oi = new OptionInfo();
                oi.setOptionInfo(fResultList.get(i).getOptionInfo());
                oi.setUnit(fResultList.get(i).getUnit());
                ois.add(oi);

                exd3.setOptionInfos(ois);
                sStack.add(exd3);
            }
        }

        System.out.println(sStack.size());
        return new ArrayList<>(sStack);
    }

    private List<ExcelData3> convExcelData2ToExcelData3(List<ExcelData2> excelData2) {
        List<ExcelData3> exd3s = new ArrayList<>();
        for (ExcelData2 exd2 : excelData2) {
            ExcelData3 exd3 = new ExcelData3();
            exd3.setBuyer(exd2.getBuyer());
            exd3.setBuyerContact(exd2.getBuyerContact());
            exd3.setDeliveryMessage(exd2.getDeliveryMessage());
            exd3.setDestination(exd2.getDestination());
            exd3.setProdName(exd2.getProdName());
            exd3.setProdNo(exd2.getProdNo());
            exd3.setReciever(exd2.getReciever());
            exd3.setRecieverContact1(exd2.getRecieverContact1());
            exd3.setRecieverContact2(exd2.getRecieverContact2());
            exd3.setZipcode(exd2.getZipcode());

            List<OptionInfo> ois = new ArrayList<>();
            OptionInfo oi = new OptionInfo();
            oi.setOptionInfo(exd2.getOptionInfo());
            oi.setUnit(exd2.getUnit());
            ois.add(oi);

            exd3.setOptionInfos(ois);
            exd3s.add(exd3);
        }
        return exd3s;
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
            data.setDeliveryMessage(row.getCell(45) != null ? row.getCell(45).getStringCellValue() : "");
            data.setProdName(row.getCell(16).getStringCellValue());
            dataList.add(data);
        }
        return dataList;
    }
}
