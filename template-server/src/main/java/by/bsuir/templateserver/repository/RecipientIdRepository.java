package by.bsuir.templateserver.repository;

import by.bsuir.templateserver.model.entity.RecipientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipientIdRepository extends JpaRepository<RecipientId, Long> {

    Boolean existsByTemplateIdAndRecipientId(Long templateId, Long recipientId);

    RecipientId deleteByRecipientIdAndTemplateId(Long recipientId, Long templateId);

}
