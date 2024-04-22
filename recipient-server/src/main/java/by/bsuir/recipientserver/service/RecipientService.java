package by.bsuir.recipientserver.service;

import by.bsuir.recipientserver.exception.RecipientNotFoundException;
import by.bsuir.recipientserver.exception.RecipientRegistrationException;
import by.bsuir.recipientserver.model.dto.request.RecipientRequest;
import by.bsuir.recipientserver.model.dto.response.RecipientResponse;
import by.bsuir.recipientserver.model.entity.Recipient;
import by.bsuir.recipientserver.model.entity.TemplateId;
import by.bsuir.recipientserver.model.mapper.RecipientMapper;
import by.bsuir.recipientserver.repository.RecipientRepository;
import by.bsuir.recipientserver.repository.TemplateIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final TemplateIdRepository templateIdRepository;
    private final RecipientMapper mapper;

    public RecipientResponse register(Long userId, RecipientRequest request) {
        Optional<Recipient> existing = recipientRepository.findByEmailAndUserId(request.email(), userId);
        if (existing.isPresent()) {
            return update(userId, existing.get().getId(), request);
        }

        try {
            return Optional.of(request)
                    .map(mapper::toEntity)
                    .map(recipient -> recipient.addUser(userId))
                    .map(recipientRepository::save)
                    .map(mapper::toDTO)
                    .orElseThrow(() -> new RecipientRegistrationException(
                            String.format("Could not register recipient %s", request.email())
                    ));
        } catch (DataIntegrityViolationException e) {
            throw new RecipientRegistrationException(e.getMessage());
        }
    }

    public RecipientResponse receive(Long userId, Long recipientId) {
        return recipientRepository.findByIdAndUserId(recipientId, userId)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RecipientNotFoundException(
                        String.format("Could not find recipient with id = %s", recipientId)
                ));
    }

    public Boolean delete(Long userId, Long recipientId) {
        return recipientRepository.findByIdAndUserId(recipientId, userId)
                .map(recipient -> {
                    recipientRepository.delete(recipient);
                    return recipient;
                })
                .isPresent();
    }

    public RecipientResponse update(Long userId, Long recipientId, RecipientRequest request) {
        try {
            return recipientRepository.findByIdAndUserId(recipientId, userId)
                    .map(recipient -> mapper.updateEntity(request, recipient))
                    .map(recipientRepository::saveAndFlush)
                    .map(mapper::toDTO)
                    .orElseThrow(() -> new RecipientNotFoundException(
                            String.format("Could not find recipient with id = %s", recipientId)
                    ));
        } catch (DataIntegrityViolationException e) {
            throw new RecipientRegistrationException(e.getMessage());
        }
    }

    public List<RecipientResponse> receiveByTemplate(Long userId, Long templateId) {
        return templateIdRepository.findAllByRecipient_userIdAndTemplateId(userId, templateId)
                .stream()
                .map(TemplateId::getRecipient)
                .map(mapper::toDTO)
                .toList();
    }

    public List<RecipientResponse> receiveByUser(Long userId) {
        return recipientRepository.findAllByUserId(userId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
