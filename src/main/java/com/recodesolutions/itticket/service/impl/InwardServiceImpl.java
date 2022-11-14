package com.recodesolutions.itticket.service.impl;

import com.recodesolutions.itticket.constants.ApplicationConstants;
import com.recodesolutions.itticket.constants.ErrorCode;
import com.recodesolutions.itticket.constants.ErrorMessages;
import com.recodesolutions.itticket.dto.InwardDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Inward;
import com.recodesolutions.itticket.entity.InwardSignature;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.mapper.InwardMapper;
import com.recodesolutions.itticket.models.EmailPayload;
import com.recodesolutions.itticket.repository.InwardRepository;
import com.recodesolutions.itticket.service.EmailService;
import com.recodesolutions.itticket.service.InwardService;
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
public class InwardServiceImpl implements InwardService {

    private final InwardRepository inwardRepository;
    private final InwardMapper inwardMapper;
    private final EmailService emailService;
    private final PdfGenerateService pdfGenerateService;
    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    @Value("${html-template.email.inward.creation.location}")
    private String emailTemplateLocation;

    @Value("${html-template.email.inward.creation.variable}")
    private String emailTemplateReferenceVariable;

    @Value("${html-template.pdf.inward.location}")
    private String pdfTemplateLocation;

    @Value("${html-template.pdf.inward.variable}")
    private String pdfTemplateReferenceVariable;

    public Inward getById(Long id) {
        Inward inward = inwardRepository.findById(id).get();
        return inward;
    }


    public Page<Inward> getInwardList(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, ApplicationConstants.LAST_UPDATED_DATE));
        }
        return inwardRepository.findAll(pageable);
    }


    @Transactional
    public Inward saveInward(InwardDto inwardDto) {
        Inward inward = inwardMapper.inwardDtoToInward(inwardDto);
        inward = inwardRepository.save(inward);
        Long inwardId = inward.getId();
        String displayId = ApplicationConstants.INWARD_ID + inwardId;
        inwardRepository.setDisplayId(displayId, inward.getId());
        inward.setDisplayId(displayId);
        inward.setStringIssuedDate(inward.getIssuedDate().toLocalDate().toString());
        saveSignature(inward, inwardDto);
        sendEmail(inward,ApplicationConstants.EMAIL_SUBJECT_INWARD_CREATION+displayId);

        return inward;
    }


    @Transactional
    public Inward updateInward(InwardDto inwardDto) {

        Inward inward = inwardRepository.findById(inwardDto.getId()).get();
        inwardMapper.update(inward, inwardDto);
        saveSignature(inward, inwardDto);
        sendEmail(inward,ApplicationConstants.EMAIL_SUBJECT_INWARD_UPDATION+inward.getDisplayId());
        return inwardRepository.save(inward);
    }


    public List<Inward> getRevisionHistoryForInward(Long id) {
        return inwardRepository.findRevisions(id).getContent().stream().map(revision -> {
            revision.getEntity().setInwardItems(revision.getEntity().getInwardItems().stream().map(a -> {
                a.setAccessory(null);
                return a;
            }).collect(Collectors.toSet()));
            return revision.getEntity();
        }).collect(Collectors.toList());
    }

    @Override
    public String downlaodPdf(Long id) {
        Inward inward = inwardRepository.findById(id).orElseThrow(() -> {
            throw new ReITException("50007", "Inward not found");
        });
        inward.setStringIssuedDate(inward.getIssuedDate().toLocalDate().toString());
        byte[] pdf = pdfGenerateService.generatePdfFile(pdfTemplateLocation, Map.of(pdfTemplateReferenceVariable, inward), "dummy");
        if (pdf != null) {
            return Base64.encodeBase64String(pdf);
        }
        throw new ReITException(ErrorCode.PDF_DOWNLOAD, ErrorMessages.PDF_DOWNLOAD);
    }

    private void saveSignature(Inward inward, InwardDto inwardDto) {
        String[] receiverSignature = inwardDto.getReceiverSignature().split(",");
        String[] providerSignature = inwardDto.getProviderSignature().split(",");
        InwardSignature signature = new InwardSignature().builder().id(inward.getId()).providerSignature(Base64.decodeBase64(providerSignature[1])).receiverSignature(Base64.decodeBase64(receiverSignature[1])).fileType(receiverSignature[0]).build();
        inward.setSignature(signature);
        inwardRepository.save(inward);
    }

    private void sendEmail(Inward inward,String subject){
        String signatureFileType=inward.getSignature().getFileType()+",";
        inward.setStringIssuedDate(inward.getIssuedDate().toLocalDate().toString());
        inward.setProviderSignature(signatureFileType+Base64.encodeBase64String(inward.getSignature().getProviderSignature()));
        inward.setReceiverSignature(signatureFileType+Base64.encodeBase64String(inward.getSignature().getReceiverSignature()));
        String[] sendTo = {"arvinth.padmanabhan@recodesolutions.com"};
        EmailPayload emailPayload = EmailPayload.builder()
                .token(requestHeaderData.getToken())
                .sendFrom(requestHeaderData.getEmail())
                .sendTo(sendTo)
                .subject(subject).data(inward)
                .templateLocation(emailTemplateLocation).templateReference(emailTemplateReferenceVariable).build();
        emailService.sendMail(emailPayload);
    }

}
