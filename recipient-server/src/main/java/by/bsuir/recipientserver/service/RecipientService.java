package by.bsuir.recipientserver.service;

import by.bsuir.recipientserver.model.mapper.RecipientMapper;
import by.bsuir.recipientserver.repository.RecipientRepository;
import by.bsuir.recipientserver.repository.TemplateIdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final TemplateIdRepository templateIdRepository;
    private final RecipientMapper recipientMapper;

}
