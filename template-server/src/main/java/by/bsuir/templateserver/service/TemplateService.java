package by.bsuir.templateserver.service;

import by.bsuir.templateserver.client.RecipientClient;
import by.bsuir.templateserver.exception.TemplateAlreadyExistsException;
import by.bsuir.templateserver.exception.TemplateCreationException;
import by.bsuir.templateserver.exception.TemplateNotFoundException;
import by.bsuir.templateserver.model.dto.request.TemplateRequest;
import by.bsuir.templateserver.model.dto.response.TemplateResponse;
import by.bsuir.templateserver.model.mapper.TemplateMapper;
import by.bsuir.templateserver.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final RecipientClient recipientClient;
    private final TemplateMapper mapper;

    public TemplateResponse create(Long userId, TemplateRequest request) {
        if (templateRepository.existsTemplateByUserIdAndTitle(userId, request.title())) {
            throw new TemplateAlreadyExistsException(
                    String.format("Template with title %s already exists", request.title())
            );
        }

        return Optional.of(request)
                .map(mapper::toEntity)
                .map(template -> template.addUser(userId))
                .map(templateRepository::save)
                .map(template -> mapper.mapToResponse(template, recipientClient))
                .orElseThrow(() -> new TemplateCreationException(
                        String.format("Could not create template %s", request.title())
                ));
    }

    public TemplateResponse get(Long userId, Long templateId) {
        return templateRepository.findByIdAndUserId(templateId, userId)
                .map(template -> mapper.mapToResponse(template, recipientClient))
                .orElseThrow(() -> new TemplateNotFoundException(
                        String.format("Template with id %s not found", templateId)
                ));
    }

    public Boolean delete(Long userId, Long templateId) {
        return templateRepository.findByIdAndUserId(templateId, userId)
                .map(template -> {
                    templateRepository.delete(template);
                    return template;
                })
                .isPresent();
    }

    public List<TemplateResponse> getAll(Long userId) {
        return templateRepository.findByUserId(userId).stream()
                .map(template -> mapper.mapToResponse(template, recipientClient)).toList();
    }
}
