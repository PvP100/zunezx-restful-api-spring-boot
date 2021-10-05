package net.codejava.store.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.codejava.store.response_model.HeaderConstant;
import org.springframework.web.client.RestTemplate;

public class FCMService {
    public static final String HEADER = HeaderConstant.KEY_VALUE_PREFIX + "AAAA_GyqTnE:" +
            "APA91bGQtbR4eq6gLvaS9BhM1edi78VbzvCGeh_ETOGDsPlCNlkiBjW" +
            "-lFrz1SfGPgsGBPIKgVj2wFZ7nPfxrFtzLP3oQMXoOfAkJwbxf0plOJrLHgOdNgQFthrRy6Lg8o4" +
            "-fhOumn8u";

    public static final String NOTIFICATION_URL = "fcm.googleapis.com/fcm/send";

    public static NotificationResponse sendNotification(RestTemplate restTemplate, String cloudToken, Object notification) {
        if (notification == null || cloudToken == null || restTemplate == null) {
            return null;
        }

        Payload payload = new Payload();
        payload.setData(notification);
        payload.setTo(cloudToken);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return new HttpPostRequestBuilder(restTemplate)
                    .withProtocol(HttpPostRequestBuilder.HTTPS)
                    .withUrl(NOTIFICATION_URL)
                    .addToHeader(HeaderConstant.AUTHORIZATION, HEADER)
                    .setJsonBody(objectMapper.writeValueAsString(payload))
                    .execute(NotificationResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
