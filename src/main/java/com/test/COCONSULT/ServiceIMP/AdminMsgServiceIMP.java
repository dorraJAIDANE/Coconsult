package com.test.COCONSULT.ServiceIMP;

import com.test.COCONSULT.Entity.AdminMsg;
import com.test.COCONSULT.Entity.Notification;
import com.test.COCONSULT.Interfaces.AdminMsgInterface;
import com.test.COCONSULT.Reposotories.AdminMsgRepository;
import com.test.COCONSULT.Reposotories.NotificationRepository;
import com.test.COCONSULT.Reposotories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminMsgServiceIMP implements AdminMsgInterface {
    @Autowired
    private AdminMsgRepository adminMsgRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    public void createmsg(AdminMsg adminMsg) {
        // You might want to add some validation or processing logic here before saving
        adminMsgRepository.save(adminMsg);
    }

    public void deletemsg(Long id) {
        adminMsgRepository.deleteById(id);
    }

    public AdminMsg getmsg(Long id) {
        Optional<AdminMsg> adminMsgOptional = adminMsgRepository.findById(id);
        return adminMsgOptional.orElse(null);
    }

    public List<AdminMsg> getallmsg() {
        return adminMsgRepository.findAll();
    }

    public void sendmsg(Long idadminMsg, List<Long> idUsers) {
        AdminMsg adminMsg = getmsg(idadminMsg);
        if (adminMsg == null) {
            // Handle the case where the admin message with the given ID does not exist
            return;
        }
        for(Long idUser:idUsers){

        }
    }
    public void sendNotification(Long adminMsgId, String message, List<Long>  recipients) {
        // Create a new notification
        Notification notification = Notification.builder()
                .date(new Date())
                .title(message)
                .adminMsgId(adminMsgRepository.findById(adminMsgId).orElse(null))
                .recipients(userRepository.findAllById(recipients))
                .build();

        // Save the notification
        notificationRepository.save(notification);

        // Update the flag in AdminMsg indicating that notification has been sent
        Optional<AdminMsg> adminMsgOptional = adminMsgRepository.findById(adminMsgId);
        adminMsgOptional.ifPresent(adminMsg -> {
            adminMsg.setNotificationSent(true);
            adminMsgRepository.save(adminMsg);
        });
    }
}
