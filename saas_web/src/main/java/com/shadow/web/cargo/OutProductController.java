package com.shadow.web.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shadow.bean.cargo.ContractProductVo;
import com.shadow.service.cargo.ContractProductService;
import com.shadow.web.BaseController;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class OutProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;


    @RequestMapping("/print")
    String print() {
    return "cargo/print/contract-print";
    }


    /**
     * 出货表导出（1）XSSF普通导出
     */
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws IOException {//2019-09  2019-11
        // 第一步：导出第一行
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        // 合并单元格  开始行0，结束0，开始列1，结束列8
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
        // 设置宽度
        sheet.setColumnWidth(0,256*5);
        sheet.setColumnWidth(1,256*26);
        sheet.setColumnWidth(2,256*11);
        sheet.setColumnWidth(3,256*29);
        sheet.setColumnWidth(4,256*15);
        sheet.setColumnWidth(5,256*11);
        sheet.setColumnWidth(6,256*10);
        sheet.setColumnWidth(7,256*10);
        sheet.setColumnWidth(8,256*10);

        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(36);
        Cell cell = row.createCell(1);
        String header = inputDate.replace("-0","-").replace("-","年") + "月份出货表";
        cell.setCellValue(header);
        // 设置单元格样式
        cell.setCellStyle(this.bigTitle(workbook));

        // 第二步：导出第二行
        row = sheet.createRow(1);
        row.setHeightInPoints(26);
        String[] title = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i+1);
            cell.setCellValue(title[i]);
            cell.setCellStyle(this.title(workbook));
        }

        // 第三步：导出数据行
        List<ContractProductVo> list =
                contractProductService.findByShipTime(getCompanyId(), inputDate);
        if (list != null && list.size() > 0) {
            int index = 2;
            for (ContractProductVo cp : list) {
                row = sheet.createRow(index++);
                row.setHeightInPoints(24);

                cell = row.createCell(1);
                cell.setCellValue(cp.getCustomName());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(2);
                cell.setCellValue(cp.getContractNo());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(3);
                cell.setCellValue(cp.getProductNo());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(4);
                cell.setCellValue(cp.getCnumber());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(5);
                cell.setCellValue(cp.getFactoryName());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(6);
                cell.setCellValue(cp.getDeliveryPeriod());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(7);
                cell.setCellValue(cp.getShipTime());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(8);
                cell.setCellValue(cp.getTradeTerms());
                cell.setCellStyle(this.text(workbook));
            }
        }


        // 第四步：导出下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition","attachment;fileName=export.xlsx");
        OutputStream out = response.getOutputStream();
        workbook.write(out);

        out.close();
        workbook.close();
    }



    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }


    /**
     * 出货表导出（2）模板导出
     */
    @RequestMapping("/printExcel2")
    public void printExcel2(String inputDate) throws IOException {
        // 加载excel模板文件
        InputStream in =
                session.getServletContext()
                        .getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        // 第一步：导出第一行
        Workbook workbook = new XSSFWorkbook(in);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        String header = inputDate.replace("-0","-").replace("-","年") + "月份出货表";
        cell.setCellValue(header);

        // 第二步：获取第三行样式
        row = sheet.getRow(2);
        CellStyle[] cellStyles = new CellStyle[8];
        for (int i = 0; i < cellStyles.length; i++) {
            cellStyles[i] = row.getCell(i+1).getCellStyle();
        }

        // 第三步：导出数据行
        List<ContractProductVo> list =
                contractProductService.findByShipTime(getCompanyId(), inputDate);
        if (list != null && list.size() > 0) {
            int index = 2;
            for (ContractProductVo cp : list) {
                row = sheet.createRow(index++);
                row.setHeightInPoints(24);

                cell = row.createCell(1);
                cell.setCellValue(cp.getCustomName());
                cell.setCellStyle(cellStyles[0]);

                cell = row.createCell(2);
                cell.setCellValue(cp.getContractNo());
                cell.setCellStyle(cellStyles[1]);

                cell = row.createCell(3);
                cell.setCellValue(cp.getProductNo());
                cell.setCellStyle(cellStyles[2]);

                cell = row.createCell(4);
                cell.setCellValue(cp.getCnumber());
                cell.setCellStyle(cellStyles[3]);

                cell = row.createCell(5);
                cell.setCellValue(cp.getFactoryName());
                cell.setCellStyle(cellStyles[4]);

                cell = row.createCell(6);
                cell.setCellValue(cp.getDeliveryPeriod());
                cell.setCellStyle(cellStyles[5]);

                cell = row.createCell(7);
                cell.setCellValue(cp.getShipTime());
                cell.setCellStyle(cellStyles[6]);

                cell = row.createCell(8);
                cell.setCellValue(cp.getTradeTerms());
                cell.setCellStyle(cellStyles[7]);
            }
        }


        // 第四步：导出下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition","attachment;fileName=export.xlsx");
        OutputStream out = response.getOutputStream();
        workbook.write(out);

        out.close();
        workbook.close();
    }

    /**
     * 出货表导出（3）SXSSF 导出百万数据，避免内存溢出
     */
    @RequestMapping("/printExcel3")
    public void printExcel3(String inputDate) throws IOException {
        // 第一步：导出第一行  [...............SXSSFWorkbook................]
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        // 合并单元格  开始行0，结束0，开始列1，结束列8
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
        // 设置宽度
        sheet.setColumnWidth(0,256*5);
        sheet.setColumnWidth(1,256*26);
        sheet.setColumnWidth(2,256*11);
        sheet.setColumnWidth(3,256*29);
        sheet.setColumnWidth(4,256*15);
        sheet.setColumnWidth(5,256*11);
        sheet.setColumnWidth(6,256*10);
        sheet.setColumnWidth(7,256*10);
        sheet.setColumnWidth(8,256*10);

        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(36);
        Cell cell = row.createCell(1);
        String header = inputDate.replace("-0","-").replace("-","年") + "月份出货表";
        cell.setCellValue(header);
        // 设置单元格样式
        cell.setCellStyle(this.bigTitle(workbook));

        // 第二步：导出第二行
        row = sheet.createRow(1);
        row.setHeightInPoints(26);
        String[] title = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i+1);
            cell.setCellValue(title[i]);
            cell.setCellStyle(this.title(workbook));
        }

        // 第三步：导出数据行
        List<ContractProductVo> list =
                contractProductService.findByShipTime(getCompanyId(), inputDate);
        if (list != null && list.size() > 0) {
            int index = 2;
            for (ContractProductVo cp : list) {
                for (int i=1; i<=50000; i++) {
                    row = sheet.createRow(index++);
                    row.setHeightInPoints(24);

                    cell = row.createCell(1);
                    cell.setCellValue(cp.getCustomName());

                    cell = row.createCell(2);
                    cell.setCellValue(cp.getContractNo());

                    cell = row.createCell(3);
                    cell.setCellValue(cp.getProductNo());

                    cell = row.createCell(4);
                    cell.setCellValue(cp.getCnumber());

                    cell = row.createCell(5);
                    cell.setCellValue(cp.getFactoryName());

                    cell = row.createCell(6);
                    cell.setCellValue(cp.getDeliveryPeriod());

                    cell = row.createCell(7);
                    cell.setCellValue(cp.getShipTime());

                    cell = row.createCell(8);
                    cell.setCellValue(cp.getTradeTerms());
                }
            }
        }


        // 第四步：导出下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition","attachment;fileName=export.xlsx");
        OutputStream out = response.getOutputStream();
        workbook.write(out);

        out.close();
        workbook.close();
    }



}
