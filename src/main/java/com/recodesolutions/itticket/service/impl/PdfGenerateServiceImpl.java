package com.recodesolutions.itticket.service.impl;

import com.lowagie.text.DocumentException;
import com.recodesolutions.itticket.service.PdfGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PdfGenerateServiceImpl implements PdfGenerateService {

    private final TemplateEngine templateEngine;

    @Override
    public byte[] generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {



        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process(templateName, context);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(byteArrayOutputStream, false);
            renderer.finishPDF();
            return byteArrayOutputStream.toByteArray();
        }  catch (DocumentException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
