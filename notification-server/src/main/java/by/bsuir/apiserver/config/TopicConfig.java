package by.bsuir.apiserver.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Value("${spring.kafka.topics.partitions}")
    private Integer partitions;

    @Value("${spring.kafka.topics.notifications.phone}")
    private String phoneTopic;

    @Value("${spring.kafka.topics.notifications.email}")
    private String emailTopic;

    @Value("${spring.kafka.topics.splitter}")
    private String splitterTopic;

    @Bean
    public NewTopic phoneTopic() {
        return TopicBuilder.name(phoneTopic)
                .partitions(partitions)
                .build();
    }

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name(emailTopic)
                .partitions(partitions)
                .build();
    }

    @Bean
    public NewTopic splitterTopic() {
        return TopicBuilder.name(splitterTopic)
                .partitions(partitions)
                .build();
    }
}
