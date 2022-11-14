package com.recodesolutions.itticket.service.impl;

import com.recodesolutions.itticket.constants.ApplicationConstants;
import com.recodesolutions.itticket.constants.ErrorCode;
import com.recodesolutions.itticket.constants.ErrorMessages;
import com.recodesolutions.itticket.dto.OutwardDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Outward;
import com.recodesolutions.itticket.entity.OutwardSignature;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.mapper.OutwardMapper;
import com.recodesolutions.itticket.models.EmailPayload;
import com.recodesolutions.itticket.repository.OutwardRepository;
import com.recodesolutions.itticket.service.EmailService;
import com.recodesolutions.itticket.service.OutwardService;
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
public class OutwardServiceImpl implements OutwardService {

    private final OutwardRepository outwardRepository;
    private final OutwardMapper OutwardMapper;

    private final EmailService emailService;
    private final PdfGenerateService pdfGenerateService;


    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    @Value("${html-template.email.outward.creation.location}")
    private String emailTemplateLocation;

    @Value("${html-template.email.outward.creation.variable}")
    private String emailTemplateReferenceVariable;

    @Value("${html-template.pdf.outward.location}")
    private String pdfTemplateLocation;

    @Value("${html-template.pdf.outward.variable}")
    private String pdfTemplateReferenceVariable;

    public Outward getById(Long id) {
        Outward Outward = outwardRepository.findById(id).get();
        return Outward;
    }


    public Page<Outward> getOutwardList(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, ApplicationConstants.LAST_UPDATED_DATE));
        }
        return outwardRepository.findAll(pageable);
    }


    @Transactional
    public Outward saveOutward(OutwardDto OutwardDto) {
        Outward Outward = OutwardMapper.OutwardDtoToOutward(OutwardDto);
        Outward = outwardRepository.save(Outward);
        Long OutwardId = Outward.getId();
        String displayId = ApplicationConstants.OUTWARD_ID + OutwardId;
        outwardRepository.setDisplayId(displayId, Outward.getId());
        Outward.setDisplayId(displayId);
        Outward.setStringIssuedDate(Outward.getIssuedDate().toLocalDate().toString());
        saveSignature(Outward, OutwardDto);
        sendEmail(Outward,ApplicationConstants.EMAIL_SUBJECT_OUTWARD_CREATION+displayId);

        return Outward;
    }


    @Transactional
    public Outward updateOutward(OutwardDto OutwardDto) {

        Outward Outward = outwardRepository.findById(OutwardDto.getId()).get();
        OutwardMapper.update(Outward, OutwardDto);
        saveSignature(Outward, OutwardDto);
        sendEmail(Outward,ApplicationConstants.EMAIL_SUBJECT_OUTWARD_UPDATION+Outward.getDisplayId());
        return outwardRepository.save(Outward);
    }


    public List<Outward> getRevisionHistoryForOutward(Long id) {
        return outwardRepository.findRevisions(id).getContent().stream().map(revision -> {
            revision.getEntity().setOutwardItems(revision.getEntity().getOutwardItems().stream().map(a -> {
                a.setAccessory(null);
                return a;
            }).collect(Collectors.toSet()));
            return revision.getEntity();
        }).collect(Collectors.toList());
    }

    @Override
    public String downlaodPdf(Long id) {
        Outward Outward = outwardRepository.findById(id).orElseThrow(() -> {
            throw new ReITException("50007", "Outward not found");
        });
        Outward.setStringIssuedDate(Outward.getIssuedDate().toLocalDate().toString());
        byte[] pdf = pdfGenerateService.generatePdfFile(pdfTemplateLocation, Map.of(pdfTemplateReferenceVariable, Outward), "dummy");
        if (pdf != null) {
            return Base64.encodeBase64String(pdf);
        }
        throw new ReITException(ErrorCode.PDF_DOWNLOAD, ErrorMessages.PDF_DOWNLOAD);
    }

    private void saveSignature(Outward outward, OutwardDto outwardDto) {
        String[] receiverSignature = outwardDto.getReceiverSignature().split(",");
        String[] providerSignature = outwardDto.getProviderSignature().split(",");
        OutwardSignature signature = new OutwardSignature().builder().id(outward.getId()).providerSignature(Base64.decodeBase64(providerSignature[1])).receiverSignature(Base64.decodeBase64(receiverSignature[1])).fileType(receiverSignature[0]).build();
        outward.setSignature(signature);
        outwardRepository.save(outward);
    }

    private void sendEmail(Outward outward,String subject){
        String signatureFileType=outward.getSignature().getFileType()+",";
        outward.setStringIssuedDate(outward.getIssuedDate().toLocalDate().toString());
        outward.setProviderSignature(signatureFileType+Base64.encodeBase64String(outward.getSignature().getProviderSignature()));
        outward.setReceiverSignature(signatureFileType+Base64.encodeBase64String(outward.getSignature().getReceiverSignature()));
        String[] sendTo = {"arvinth.padmanabhan@recodesolutions.com"};
        EmailPayload emailPayload = EmailPayload.builder()
                .token(requestHeaderData.getToken())
                .sendFrom(requestHeaderData.getEmail())
                .sendTo(sendTo)
                .subject(subject).data(outward)
                .templateLocation(emailTemplateLocation).templateReference(emailTemplateReferenceVariable).build();
        emailService.sendMail(emailPayload);
    }

}
