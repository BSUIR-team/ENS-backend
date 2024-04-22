package by.bsuir.templateserver.model.entity;

import by.bsuir.templateserver.listener.RecipientIdListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "recipient_ids",
        uniqueConstraints = {
                @UniqueConstraint(name = "recipient_ids_unq_template-recipient", columnNames = {"template_id", "recipientId"})
        }
)
@EntityListeners(RecipientIdListener.class)
public class RecipientId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Template template;

    private Long recipientId;
}