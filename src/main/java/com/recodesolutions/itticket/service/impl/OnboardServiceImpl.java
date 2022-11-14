package com.recodesolutions.itticket.service.impl;

import com.recodesolutions.itticket.constants.ApplicationConstants;
import com.recodesolutions.itticket.constants.ErrorCode;
import com.recodesolutions.itticket.constants.ErrorMessages;
import com.recodesolutions.itticket.dto.OnboardDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Onboard;
import com.recodesolutions.itticket.entity.OnboardSignature;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.mapper.OnboardMapper;
import com.recodesolutions.itticket.models.EmailPayload;
import com.recodesolutions.itticket.repository.OnboardRepository;
import com.recodesolutions.itticket.service.EmailService;
import com.recodesolutions.itticket.service.OnboardService;
import com.recodesolutions.itticket.service.PdfGenerateService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnboardServiceImpl implements OnboardService {

    private final OnboardRepository onboardRepository;
    private final OnboardMapper onboardMapper;

    private final EmailService emailService;
    private final PdfGenerateService pdfGenerateService;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    @Value("${html-template.email.onboard.creation.location}")
    private String emailTemplateLocation;

    @Value("${html-template.email.onboard.creation.variable}")
    private String emailTemplateReferenceVariable;

    @Value("${html-template.pdf.onboard.location}")
    private String pdfTemplateLocation;

    @Value("${html-template.pdf.onboard.variable}")
    private String pdfTemplateReferenceVariable;

    public Onboard getById(Long id) {
        Onboard onboard = onboardRepository.findById(id).get();
        return onboard;
    }


    public Page<Onboard> getOnboardList(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, ApplicationConstants.LAST_UPDATED_DATE));
        }
        return onboardRepository.findAll(pageable);
    }


    @Transactional
    public Onboard saveOnboard(OnboardDto OnboardDto) {
        Onboard Onboard = onboardMapper.OnboardDtoToOnboard(OnboardDto);
        Onboard = onboardRepository.save(Onboard);
        Long OnboardId = Onboard.getId();
        String displayId = ApplicationConstants.ONBOARD_ID + OnboardId;
        onboardRepository.setDisplayId(displayId, Onboard.getId());
        Onboard.setDisplayId(displayId);
        Onboard.setStringIssuedDate(Onboard.getIssuedDate().toLocalDate().toString());
        saveSignature(Onboard, OnboardDto);
        sendEmail(Onboard,ApplicationConstants.EMAIL_SUBJECT_ONBOARD_CREATION+displayId);

        return Onboard;
    }


    @Transactional
    public Onboard updateOnboard(OnboardDto OnboardDto) {

        Onboard Onboard = onboardRepository.findById(OnboardDto.getId()).get();
        onboardMapper.update(Onboard, OnboardDto);
        saveSignature(Onboard, OnboardDto);
        sendEmail(Onboard,ApplicationConstants.EMAIL_SUBJECT_ONBOARD_UPDATION+Onboard.getDisplayId());
        return onboardRepository.save(Onboard);
    }


    public List<Onboard> getRevisionHistoryForOnboard(Long id) {
        return onboardRepository.findRevisions(id).getContent().stream().map(revision -> {
            revision.getEntity().setOnboardItems(revision.getEntity().getOnboardItems().stream().map(a -> {
                a.setAccessory(null);
                return a;
            }).collect(Collectors.toSet()));
            return revision.getEntity();
        }).collect(Collectors.toList());
    }

    @Override
    public String downlaodPdf(Long id) {
        Onboard Onboard = onboardRepository.findById(id).orElseThrow(() -> {
            throw new ReITException("50007", "Onboard not found");
        });
        Onboard.setStringIssuedDate(Onboard.getIssuedDate().toLocalDate().toString());
        byte[] pdf = pdfGenerateService.generatePdfFile(pdfTemplateLocation, Map.of(pdfTemplateReferenceVariable, Onboard), "dummy");
        if (pdf != null) {
            return Base64.encodeBase64String(pdf);
        }
        throw new ReITException(ErrorCode.PDF_DOWNLOAD, ErrorMessages.PDF_DOWNLOAD);
    }

    private void saveSignature(Onboard onboard, OnboardDto onboardDto) {
        String[] receiverSignature = onboardDto.getReceiverSignature().split(",");
        String[] providerSignature = onboardDto.getProviderSignature().split(",");
        OnboardSignature signature = new OnboardSignature().builder().id(onboard.getId()).providerSignature(Base64.decodeBase64(providerSignature[1])).receiverSignature(Base64.decodeBase64(receiverSignature[1])).fileType(receiverSignature[0]).build();
//        signature = signatureRepository.save(signature);
        onboard.setSignature(signature);
        onboardRepository.save(onboard);
    }

    private void sendEmail(Onboard onboard,String subject){
        String signatureFileType=onboard.getSignature().getFileType()+",";
        onboard.setStringIssuedDate(onboard.getIssuedDate().toLocalDate().toString());
        onboard.setProviderSignature(signatureFileType+Base64.encodeBase64String(onboard.getSignature().getProviderSignature()));
        onboard.setReceiverSignature(signatureFileType+Base64.encodeBase64String(onboard.getSignature().getReceiverSignature()));
        String[] sendTo = {"arvinth.padmanabhan@recodesolutions.com"};
        EmailPayload emailPayload = EmailPayload.builder()
                .token(requestHeaderData.getToken())
                .sendFrom(requestHeaderData.getEmail())
                .sendTo(sendTo)
                .subject(subject).data(onboard)
                .templateLocation(emailTemplateLocation).templateReference(emailTemplateReferenceVariable).build();
        emailService.sendMail(emailPayload);
    }

}
