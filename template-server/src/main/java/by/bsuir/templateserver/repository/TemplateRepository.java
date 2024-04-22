package by.bsuir.templateserver.repository;


import by.bsuir.templateserver.model.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findByIdAndUserId(Long templateId, Long userId);

    Boolean existsByIdAndRecipientIds_recipientId(Long templateId, Long recipientId);

    Boolean existsTemplateByUserIdAndTitle(Long userId, String title);
}
