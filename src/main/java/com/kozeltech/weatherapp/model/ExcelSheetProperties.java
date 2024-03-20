package com.kozeltech.weatherapp.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExcelSheetProperties {
    private ExcelEntry headerRow;
    private List<ExcelEntry> dataRows;
    private String sheetName;
}
