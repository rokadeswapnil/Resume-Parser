package com.resumeextractor.extractor.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class ResumeExtract {
    public List<String> extractName(String text) {
        List<String> names = new ArrayList<>();
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String name = matcher.group().split("@")[0].split("[0-9]")[0];
            names.add(name);
        }
        return names.isEmpty() ? null : names;
    }

    public List<String> extractEmail(String text) {
        List<String> emails = new ArrayList<>();
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String email = matcher.group();
            emails.add(email);
        }
        return emails.isEmpty() ? null : emails;
    }

    public List<String> extractPhoneNumber(String text) {
        List<String> mobileNumber = new ArrayList<>();
        String phonePattern = "(?:\\+91)?\\d{10}";   // Adjust regex based on the format
        Pattern pattern = Pattern.compile(phonePattern);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String mobile = matcher.group();
            mobileNumber.add(mobile);
        }
        return mobileNumber.isEmpty() ? null : mobileNumber;
    }

    public List<String> extractEducation(String text) {
        List<String> education = new ArrayList<>();
        String[] educationKeywords = {"Bachelor", "Master", "PhD", "B.Sc", "M.Sc", "B.A", "M.A", "Diploma", "Associate", "Degree", "University", "College"};
        for (String keyword : educationKeywords) {
            Pattern pattern = Pattern.compile(".*\\b" + keyword + "\\b.*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                education.add(matcher.group());
            }
        }
        return education.isEmpty() ? null : education;
    }
    public List<String> extractExperience(String text) {
        List<String> currentCompany = new ArrayList<>();
        String[] companyKeywords = {"pvt","company"};
        for (String keyword : companyKeywords) {
            Pattern pattern = Pattern.compile(".*\\b" + keyword + "\\b.*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String modifiedCompany= matcher.group().replaceAll("EXPERIENCE","").trim();
                currentCompany.add(modifiedCompany);
            }
        }
        return currentCompany.isEmpty() ? null : currentCompany;
}


}
