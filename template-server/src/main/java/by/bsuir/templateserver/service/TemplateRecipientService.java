package by.bsuir.templateserver.service;

import by.bsuir.templateserver.client.RecipientClient;
import by.bsuir.templateserver.exception.TemplateNotFoundException;
import by.bsuir.templateserver.model.dto.request.RecipientListRequest;
import by.bsuir.templateserver.model.dto.response.RecipientResponse;
import by.bsuir.templateserver.model.dto.response.TemplateResponse;
import by.bsuir.templateserver.model.entity.RecipientId;
import by.bsuir.templateserver.model.entity.Template;
import by.bsuir.templateserver.model.mapper.TemplateMapper;
import by.bsuir.templateserver.repository.RecipientIdRepository;
import by.bsuir.templateserver.repository.TemplateRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateRecipientService {

    private final TemplateRepository templateRepository;
    private final RecipientIdRepository recipientIdRepository;
    private final RecipientClient recipientClient;
    private final TemplateMapper mapper;

    public TemplateResponse addRecipients(Long userId, Long templateId, RecipientListRequest request) {
        Template template = templateRepository.findByIdAndUserId(templateId, userId)
                .orElseThrow(() -> new TemplateNotFoundException(
                        String.format("Template with id %s not found for user with id %s", templateId, userId)
                ));

        for (Long recipientId : request.recipientIds()) {
            if (recipientIdRepository.existsByTemplateIdAndRecipientId(templateId, recipientId)) {
                log.warn("Recipient {} has already been registered for Template {}", recipientId, templateId);
                continue;
            }

            try {
                Optional.of(recipientClient.receiveRecipientById(userId, recipientId))
                        .map(ResponseEntity::getBody)
                        .ifPresent(recipientResponse -> template.addRecipient(recipientResponse.id()));
                templateRepository.save(template);
            } catch (FeignException.NotFound e) {
                log.warn("Recipient {} not found for Client {}", recipientId, userId);
            }
        }
        TemplateResponse response = mapper.mapToResponse(template, recipientClient);
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return response;
    }

    public TemplateResponse removeRecipients(Long userId, Long templateId, RecipientListRequest request) {
        Template template = templateRepository.findByIdAndUserId(templateId, userId)
                .orElseThrow(() -> new TemplateNotFoundException(
                        String.format("Template with id %s not found for user with id %s", templateId, userId)
                ));

        for (Long recipientId : request.recipientIds()) {
            if (templateRepository.existsByIdAndRecipientIds_recipientId(templateId, recipientId)) {
                Optional.of(recipientClient.receiveRecipientById(userId, recipientId))
                        .map(ResponseEntity::getBody)
                        .map(RecipientResponse::id)
                        .ifPresent(template::removeRecipient);
            } else {
                log.warn("Recipient {} hasn't been registered for Template {}", recipientId, templateId);
            }
        }

        templateRepository.save(template);
        return mapper.mapToResponse(template, recipientClient);
    }

    public TemplateResponse updateRecipients(Long userId, Long templateId, RecipientListRequest request) {

        List<Long> oldList = recipientIdRepository.findAll().stream()
                .filter(recipient -> recipient.getTemplate().getId().equals(templateId))
                .map(RecipientId::getRecipientId)
                .toList();
        List<Long> newList = request.recipientIds();

        if (newList == null)
            newList = new ArrayList<>();

        HashSet<Long> oldSet = new HashSet<>(oldList);
        HashSet<Long> newSet = new HashSet<>(newList);

        oldSet.removeAll(newList); //toRemove
        RecipientListRequest delete = new RecipientListRequest(new ArrayList<>(oldSet));
        newSet.removeAll(oldList); //toAdd
        RecipientListRequest create = new RecipientListRequest(new ArrayList<>(newSet));

        removeRecipients(userId, templateId, delete);
        addRecipients(userId, templateId, create);

        Template template = templateRepository.findByIdAndUserId(templateId, userId)
                .orElseThrow(() -> new TemplateNotFoundException(
                        String.format("Template with id %s not found for user with id %s", templateId, userId)
                ));

        return mapper.mapToResponse(template, recipientClient);
    }
}
