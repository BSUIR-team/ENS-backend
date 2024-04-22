package by.bsuir.apiserver.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("${servers.recipient}")
public interface RecipientClient {


}
