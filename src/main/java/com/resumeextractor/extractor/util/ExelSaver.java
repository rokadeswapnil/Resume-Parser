package com.resumeextractor.extractor.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExelSaver {
    private Workbook workbook;
    private Sheet sheet;
    private int rowCount;

    public ExelSaver() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Resumes");
        rowCount = 0;
        Row header = sheet.createRow(rowCount++);
        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("Phone");
        header.createCell(3).setCellValue("Education");
        header.createCell(4).setCellValue("Experience");
    }

    public void addRow(String name, String email, String phone, String education,String experience) {
        Row row = sheet.createRow(rowCount++);
        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(email);
        row.createCell(2).setCellValue(phone);
        row.createCell(3).setCellValue(education);
        row.createCell(4).setCellValue(experience);
    }

    public void saveToFile(String fileName) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        workbook.close();
    }
}
