package net.codejava.store.embbed_system;

import net.codejava.store.notification.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/gas")
@CrossOrigin(origins = "*")
public class NotificationController {
    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private NotificationRepository notificationRepository;

//    @PostMapping("/fcmtoken")
//    public ResponseEntity updateFCMToken(@RequestBody String fcmToken) {
//        if(!fcmToken.isEmpty()){
//            Notification notification = new Notification();
//            notification.setFcmToken(fcmToken);
//            if (!fcmToken.isEmpty())
//                notificationRepository.save(notification);
//            return new ResponseEntity(HttpStatus.OK);
//        }else {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//
//    }

    @GetMapping("/info/{tempurature}/{gas}")
    public ResponseEntity getNoti(@PathVariable("tempurature") double tempurature,
                                  @PathVariable("gas") double gas) {

        Gas g = new Gas(tempurature, (gas * 100) / 1024);
        String fcmToken = "feYd_7RDmhI:APA91bGHic5mn355u2qI1n3xqmia5tyrPk2Vs1mCeWuC9u5jWrJfUk" +
                "_vzRmdENmqFKSlINFNO_fJra0au-C72h7dujn9nE8Fgvhf9pSpuhOWY3yTOzTDp0Sxa6ez3KlS4ZgVUGcLcF33";
//        List<Notification> ls = notificationRepository.findAll();
//        for (Notification notification : ls) {
        FCMService.sendNotification(restTemplate, fcmToken, g);
//        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
