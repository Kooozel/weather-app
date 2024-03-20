package com.kozeltech.weatherapp.service.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.kozeltech.weatherapp.model.ExcelSheetProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExcelExporter {

    public byte[] create(ExcelSheetProperties excelSheetProperties) {
        try (var workbook = new XSSFWorkbook()) {
            var outputStream = new ByteArrayOutputStream();

            createSheet(workbook, excelSheetProperties);

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void createSheet(XSSFWorkbook workbook, ExcelSheetProperties excelSheetProperties) {
        var sheet = workbook.createSheet(excelSheetProperties.getSheetName());

        var headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        int rowCount = 0;

        if (excelSheetProperties.getHeaderRow() != null) {
            createRow(excelSheetProperties.getHeaderRow().getColumns(), sheet, rowCount++, headerStyle);
        }

        if (excelSheetProperties.getDataRows() != null) {
            for (var dataRow : excelSheetProperties.getDataRows()) {
                createRow(dataRow.getColumns(), sheet, rowCount++, null);
            }
        }
    }

    private void createRow(List<String> columns, XSSFSheet sheet, int rowCount, XSSFCellStyle style) {
        var row = sheet.createRow(rowCount);
        int cellCount = 0;
        for (var column : columns) {
            var cell = row.createCell(cellCount++);
            cell.setCellValue(column);
            if (style != null) {
                cell.setCellStyle(style);
            }
        }
    }
}
