package com.kooozel.weatherapp.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExcelEntry {
    private List<String> columns;
}
