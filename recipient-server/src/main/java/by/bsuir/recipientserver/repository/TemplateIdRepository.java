package by.bsuir.recipientserver.repository;

import by.bsuir.recipientserver.model.entity.TemplateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateIdRepository extends JpaRepository<TemplateId, Long> {

    Boolean existsByTemplateIdAndRecipientId(Long templateId, Long recipientId);

    List<TemplateId> findAllByRecipient_userIdAndTemplateId(Long userId, Long templateId);
}
