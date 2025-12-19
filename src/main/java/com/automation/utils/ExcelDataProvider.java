package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for reading and writing Excel files for data-driven testing
 */
public class ExcelDataProvider {
    
    private static LoggerUtil logger = LoggerUtil.getInstance();
    private Workbook workbook;
    private Sheet sheet;
    private String filePath;

    public ExcelDataProvider(String filePath, String sheetName) {
        this.filePath = filePath;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
            logger.info("Excel file loaded successfully: " + filePath);
        } catch (IOException e) {
            logger.error("Failed to load Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to load Excel file", e);
        }
    }

    /**
     * Get data from Excel as 2D Object array for TestNG DataProvider
     */
    public Object[][] getData() {
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        
        Object[][] data = new Object[rowCount - 1][colCount];
        
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                Cell cell = row.getCell(j);
                data[i - 1][j] = getCellValue(cell);
            }
        }
        return data;
    }

    /**
     * Get data as List of Maps for more flexible access
     */
    public List<Map<String, String>> getDataAsMapList() {
        List<Map<String, String>> dataList = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            Map<String, String> rowData = new HashMap<>();
            
            for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
                String key = headerRow.getCell(j).getStringCellValue();
                String value = getCellValue(row.getCell(j)).toString();
                rowData.put(key, value);
            }
            dataList.add(rowData);
        }
        return dataList;
    }

    /**
     * Get specific cell value
     */
    public String getCellData(int rowNum, int colNum) {
        return getCellValue(sheet.getRow(rowNum).getCell(colNum)).toString();
    }

    /**
     * Set cell value
     */
    public void setCellData(int rowNum, int colNum, String value) {
        try {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }
            Cell cell = row.getCell(colNum);
            if (cell == null) {
                cell = row.createCell(colNum);
            }
            cell.setCellValue(value);
            
            FileOutputStream fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
            logger.info("Data written to Excel successfully");
        } catch (IOException e) {
            logger.error("Failed to write to Excel: " + e.getMessage());
        }
    }

    /**
     * Get cell value based on cell type
     */
    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                }
                return String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * Close workbook
     */
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            logger.error("Failed to close workbook: " + e.getMessage());
        }
    }
}
