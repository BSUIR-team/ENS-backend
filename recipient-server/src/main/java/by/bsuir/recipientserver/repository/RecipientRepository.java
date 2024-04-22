package by.bsuir.recipientserver.repository;

import by.bsuir.recipientserver.model.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

    Optional<Recipient> findByEmailAndUserId(String email, Long userId);

    List<Recipient> findAllByUserId(Long userId);

    Optional<Recipient> findByIdAndUserId(Long id, Long userId);
}
