package com.recodesolutions.itticket.service;

import java.util.Map;

public interface PdfGenerateService {
    byte[] generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName);

}
