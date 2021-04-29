package com.example.demo.controller.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Message;
import com.example.demo.model.orderManage.OrderManageDto;
import com.example.demo.model.sell.dto.SellCancelReadDto;
import com.example.demo.model.sell.dto.SellConfirmReadDto;
import com.example.demo.model.sell.dto.SellRegReadDto;
import com.example.demo.service.orderManage.OrderManageService;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    OrderManageService orderManageService;

    // /api/excel/sell-items/read
    @PostMapping("/sell-items/read")
    public ResponseEntity<Message> ProdExcelReadApi(@RequestParam("file") MultipartFile file) throws IOException {
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
        List<SellRegReadDto> dtos = getRegReadedExcel(worksheet);

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(dtos);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/excel/sell-cancel/read
    @PostMapping("/sell-cancel/read")
    public ResponseEntity<Message> readCanceledExcel(@RequestParam("file") MultipartFile file) throws IOException {
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
        List<SellCancelReadDto> dtos = getCancelReadedExcel(worksheet);

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(dtos);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/excel/sell-confirm/read
    @PostMapping("/sell-confirm/read")
    public ResponseEntity<Message> readConfirmedExcel(@RequestParam("file") MultipartFile file) throws IOException {
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
        List<SellConfirmReadDto> dtos = getConfirmReadedExcel(worksheet);

        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(dtos);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/excel/inflow/read
    @PostMapping("/inflow/read")
    public ResponseEntity<Message> readInflowExcel(@RequestParam("file") MultipartFile file) throws IOException {
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
        // List<SellConfirmReadDto> dtos = getConfirmReadedExcel(worksheet);
        List<Map> resultMap = getInflowReadedExcel(worksheet);
        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(resultMap);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    // /api/excel/order-manage/read
    @PostMapping("/order-manage/read")
    public ResponseEntity<Message> readOrderManageExcel(@RequestParam("file") MultipartFile file) throws IOException {
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
        List<OrderManageDto> orderManageDtos = orderManageService.getReadExcel(worksheet);
        message.setMessage("success");
        message.setStatus(HttpStatus.OK);
        message.setData(orderManageDtos);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    private List<Map> getInflowReadedExcel(Sheet worksheet) throws MalformedURLException {
        List<Map> resultMaps = new ArrayList<>();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);
            Map<String, Object> resultMap = new HashMap();

            URL aUrl = new URL(row.getCell(0).getStringCellValue());
            if (aUrl.getHost().equals("m.smartstore.naver.com")) {
                resultMap.put("deviceType", "모바일");
            } else {
                resultMap.put("deviceType", "PC");
            }
            String[] pathArr = aUrl.getPath().split("/");
            if (pathArr.length == 4 && pathArr[2].equals("products")) {
                resultMap.put("prodNo", pathArr[3]);
                resultMap.put("pageUrl", row.getCell(0).getStringCellValue());
                resultMap.put("pageView", row.getCell(1).getNumericCellValue());
                resultMap.put("avgRegidenceTime", row.getCell(2).getStringCellValue());
                resultMaps.add(resultMap);
            }
            
        }
        return resultMaps;
    }

    private List<SellConfirmReadDto> getConfirmReadedExcel(Sheet worksheet) {
        // private String mallName;
        // private String prodOrderNo; // 1(0)
        // private String orderNo; // 2(1)
        // private String prodNo; // 14(13)
        // private String prodName; // 15(14)
        // private String optionInfo; // 17(16)
        // private int unit; // 18(17)
        // private int shipping; // 30(29)
        // private int amount; // 41(40)
        // private Date regDate; // 33(32)
        // private Date confirmDate; // 3(2)
        List<SellConfirmReadDto> dataList = new ArrayList<>();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            SellConfirmReadDto data = new SellConfirmReadDto();

            // System.out.println((int) row.getCell(20).getNumericCellValue());
            data.setMallName("");
            data.setProdOrderNo(row.getCell(0).getStringCellValue()); // 1(0)
            data.setOrderNo(row.getCell(1).getStringCellValue()); // 2(1)
            data.setProdNo(row.getCell(13).getStringCellValue()); // 14(13)
            data.setProdName(row.getCell(14).getStringCellValue()); // 18(17)
            data.setOptionInfo(row.getCell(16).getStringCellValue()); // 20(19)
            data.setUnit((int) row.getCell(17).getNumericCellValue()); // 21(20)
            data.setShipping((int) row.getCell(29).getNumericCellValue()); // 32(31)
            data.setAmount((int) row.getCell(40).getNumericCellValue()); // 25(24)
            data.setRegDate(row.getCell(32).getDateCellValue()); // 5(4)
            data.setConfirmDate(row.getCell(2).getDateCellValue()); // 11(10)
            dataList.add(data);
        }
        return dataList;
    }

    private List<SellCancelReadDto> getCancelReadedExcel(Sheet worksheet) {
        List<SellCancelReadDto> dataList = new ArrayList<>();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            SellCancelReadDto data = new SellCancelReadDto();

            // System.out.println((int) row.getCell(20).getNumericCellValue());
            data.setMallName("");
            data.setProdOrderNo(row.getCell(0).getStringCellValue()); // 1(0)
            data.setOrderNo(row.getCell(1).getStringCellValue()); // 2(1)
            data.setProdNo(row.getCell(13).getStringCellValue()); // 14(13)
            data.setProdName(row.getCell(17).getStringCellValue()); // 18(17)
            data.setOptionInfo(row.getCell(19) != null ? row.getCell(19).getStringCellValue() : ""); // 20(19)
            data.setUnit((int) row.getCell(20).getNumericCellValue()); // 21(20)
            data.setShipping((int) row.getCell(31).getNumericCellValue()); // 32(31)
            data.setAmount((int) row.getCell(24).getNumericCellValue()); // 25(24)
            data.setRegDate(row.getCell(4).getDateCellValue()); // 5(4)
            data.setCancelDate(row.getCell(10).getDateCellValue()); // 11(10)
            dataList.add(data);
        }
        return dataList;
    }

    private List<SellRegReadDto> getRegReadedExcel(Sheet worksheet) {
        // private String mallName;
        // private String prodOrderNo; // 상품주문번호 1(0)
        // private String orderNo; // 주문번호 2(1)
        // private String prodNo; // 상품번호 6(5)
        // private String prodName; // 상품명 7(6)
        // private String optionInfo; // 옵션정보 8(7)
        // private int unit; // 수량 9(8)
        // private Date regDate; // 주문일시 3(2)
        List<SellRegReadDto> dataList = new ArrayList<>();
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            SellRegReadDto data = new SellRegReadDto();

            // System.out.println((int) row.getCell(20).getNumericCellValue());
            data.setMallName("");
            data.setProdOrderNo(row.getCell(0).getStringCellValue());
            data.setOrderNo(row.getCell(1).getStringCellValue());
            data.setProdNo(row.getCell(5).getStringCellValue());
            data.setProdName(row.getCell(6).getStringCellValue());
            data.setOptionInfo(row.getCell(7) != null ? row.getCell(7).getStringCellValue() : "");
            data.setUnit((int) row.getCell(8).getNumericCellValue());
            data.setOrderStatus(row.getCell(3).getStringCellValue());
            // data.setShipping((int) row.getCell(34).getNumericCellValue());
            // data.setAmount((int) row.getCell(53).getNumericCellValue());
            data.setRegDate(row.getCell(2).getDateCellValue());
            if (!data.getOrderStatus().equals("미결제취소")) {
                dataList.add(data);
            }
        }
        return dataList;
    }
}
