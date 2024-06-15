package com.resumeextractor.extractor.controller;

import com.resumeextractor.extractor.parser.ResumeExtract;
import com.resumeextractor.extractor.util.ExelSaver;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeExtractController {
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        ResumeExtract parser = new ResumeExtract();
        ExelSaver exelSaver = new ExelSaver();

        for (MultipartFile file : files) {
            try (InputStream inputStream = file.getInputStream()) {
                String text = "";
                if (file.getOriginalFilename().endsWith(".docx")) {
                    text = extractTextFromWord(inputStream);
                } else if (file.getOriginalFilename().endsWith(".pdf")) {
                    text = extractTextFromPDF(inputStream);
                }

                if (!text.isEmpty()) {
                    List<String> names = parser.extractName(text);
                    List<String> emails = parser.extractEmail(text);
                    List<String> phones = parser.extractPhoneNumber(text);
                    List<String> educations = parser.extractEducation(text);
                    List<String>experience = parser.extractExperience(text);

                    if (names != null && emails != null && phones != null && educations != null && experience !=null) {
                        int size = Math.min(Math.min(names.size(), emails.size()), Math.min(phones.size(), educations.size()));
                        for (int i = 0; i < size; i++) {
                            exelSaver.addRow(names.get(i), emails.get(i), phones.get(i), educations.get(i),experience.get(i));
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            exelSaver.saveToFile("resumes.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving Excel file");
        }

        return ResponseEntity.ok("File Data Saved to Excel successfully!");
    }

    private String extractTextFromWord(InputStream inputStream) {
        try (XWPFDocument doc = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
            return extractor.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractTextFromPDF(InputStream inputStream) {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
