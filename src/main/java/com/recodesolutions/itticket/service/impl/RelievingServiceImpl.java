package com.recodesolutions.itticket.service.impl;

import com.recodesolutions.itticket.constants.ApplicationConstants;
import com.recodesolutions.itticket.constants.ErrorCode;
import com.recodesolutions.itticket.constants.ErrorMessages;
import com.recodesolutions.itticket.dto.RelievingDto;
import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Relieving;
import com.recodesolutions.itticket.entity.RelievingSignature;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.mapper.RelievingMapper;
import com.recodesolutions.itticket.models.EmailPayload;
import com.recodesolutions.itticket.repository.RelievingRepository;
import com.recodesolutions.itticket.service.EmailService;
import com.recodesolutions.itticket.service.RelievingService;
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
public class RelievingServiceImpl implements RelievingService {

    private final RelievingRepository relievingRepository;
    private final RelievingMapper relievingMapper;

    private final EmailService emailService;
    private final PdfGenerateService pdfGenerateService;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    @Value("${html-template.email.relieving.creation.location}")
    private String emailTemplateLocation;

    @Value("${html-template.email.relieving.creation.variable}")
    private String emailTemplateReferenceVariable;

    @Value("${html-template.pdf.relieving.location}")
    private String pdfTemplateLocation;

    @Value("${html-template.pdf.relieving.variable}")
    private String pdfTemplateReferenceVariable;

    public Relieving getById(Long id) {
        Relieving Relieving = relievingRepository.findById(id).get();
        return Relieving;
    }


    public Page<Relieving> getRelievingList(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, ApplicationConstants.LAST_UPDATED_DATE));
        }
        return relievingRepository.findAll(pageable);
    }


    @Transactional
    public Relieving saveRelieving(RelievingDto RelievingDto) {
        Relieving Relieving = relievingMapper.RelievingDtoToRelieving(RelievingDto);
        Relieving = relievingRepository.save(Relieving);
        Long RelievingId = Relieving.getId();
        String displayId = ApplicationConstants.RELIEVING_ID + RelievingId;
        relievingRepository.setDisplayId(displayId, Relieving.getId());
        Relieving.setDisplayId(displayId);
        Relieving.setStringIssuedDate(Relieving.getIssuedDate().toLocalDate().toString());
        saveSignature(Relieving, RelievingDto);
        sendEmail(Relieving,ApplicationConstants.EMAIL_SUBJECT_RELIEVING_CREATION+displayId);

        return Relieving;
    }


    @Transactional
    public Relieving updateRelieving(RelievingDto RelievingDto) {

        Relieving Relieving = relievingRepository.findById(RelievingDto.getId()).get();
        relievingMapper.update(Relieving, RelievingDto);
        saveSignature(Relieving, RelievingDto);
        sendEmail(Relieving,ApplicationConstants.EMAIL_SUBJECT_RELIEVING_UPDATION+Relieving.getDisplayId());
        return relievingRepository.save(Relieving);
    }


    public List<Relieving> getRevisionHistoryForRelieving(Long id) {
        return relievingRepository.findRevisions(id).getContent().stream().map(revision -> {
            revision.getEntity().setRelievingItems(revision.getEntity().getRelievingItems().stream().map(a -> {
                a.setAccessory(null);
                return a;
            }).collect(Collectors.toSet()));
            return revision.getEntity();
        }).collect(Collectors.toList());
    }

    @Override
    public String downlaodPdf(Long id) {
        Relieving Relieving = relievingRepository.findById(id).orElseThrow(() -> {
            throw new ReITException("50007", "Relieving not found");
        });
        Relieving.setStringIssuedDate(Relieving.getIssuedDate().toLocalDate().toString());
        byte[] pdf = pdfGenerateService.generatePdfFile(pdfTemplateLocation, Map.of(pdfTemplateReferenceVariable, Relieving), "dummy");
        if (pdf != null) {
            return Base64.encodeBase64String(pdf);
        }
        throw new ReITException(ErrorCode.PDF_DOWNLOAD, ErrorMessages.PDF_DOWNLOAD);
    }

    private void saveSignature(Relieving relieving, RelievingDto relievingDto) {
        String[] receiverSignature = relievingDto.getReceiverSignature().split(",");
        String[] providerSignature = relievingDto.getProviderSignature().split(",");
        RelievingSignature signature = new RelievingSignature().builder().id(relieving.getId()).providerSignature(Base64.decodeBase64(providerSignature[1])).receiverSignature(Base64.decodeBase64(receiverSignature[1])).fileType(receiverSignature[0]).build();
        relieving.setSignature(signature);
        relievingRepository.save(relieving);
    }

    private void sendEmail(Relieving relieving,String subject){
        String signatureFileType=relieving.getSignature().getFileType()+",";
        relieving.setStringIssuedDate(relieving.getIssuedDate().toLocalDate().toString());
        relieving.setProviderSignature(signatureFileType+Base64.encodeBase64String(relieving.getSignature().getProviderSignature()));
        relieving.setReceiverSignature(signatureFileType+Base64.encodeBase64String(relieving.getSignature().getReceiverSignature()));
        String[] sendTo = {"arvinth.padmanabhan@recodesolutions.com"};
        EmailPayload emailPayload = EmailPayload.builder()
                .token(requestHeaderData.getToken())
                .sendFrom(requestHeaderData.getEmail())
                .sendTo(sendTo)
                .subject(subject).data(relieving)
                .templateLocation(emailTemplateLocation).templateReference(emailTemplateReferenceVariable).build();
        emailService.sendMail(emailPayload);
    }

}
