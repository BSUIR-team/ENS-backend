package by.bsuir.recipientserver.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "recipients",
        uniqueConstraints = {
                @UniqueConstraint(name = "recipients_unq_userId-email", columnNames = {"userId", "email"}),
                @UniqueConstraint(name = "recipients_unq_userId-phoneNumber", columnNames = {"userId", "phoneNumber"})
        },
        indexes = {
                @Index(name = "recipients_idx_email", columnList = "email")
        }
)
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    @Column(nullable = false)
    private String email;
    private String phoneNumber;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(
            mappedBy = "recipient",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TemplateId> templateIds = new ArrayList<>();

    public Recipient addUser(Long userId) {
        setUserId(userId);
        return this;
    }

    public void addTemplate(Long templateId) {
        templateIds.add(
                TemplateId.builder()
                        .templateId(templateId)
                        .recipient(this)
                        .build()
        );
    }

    public Recipient removeTemplate(Long templateId) {
        templateIds.removeIf(
                template -> Objects.equals(template.getTemplateId(), templateId)
        );
        return this;
    }
}
