package springredditclone.reddit.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import springredditclone.reddit.exceptions.SpringRedditException;
import springredditclone.reddit.model.NotificationEmail;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    void sendMail(NotificationEmail notificationEmail){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("vinhpl041195@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };

        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new SpringRedditException("Exception occurred when sending mail to "
                    + notificationEmail.getRecipient(), e);
        }
    }
}

// Phương thức sendMail lấy NotificationEmail làm tham số đầu vào trong phương thức này.
// Tạo MimeMessage bằng cách chuyển vào các trường người gửi, người nhận, title and content
// Nội dung thư mà chúng ta đang nhận từ phương thức build() của lớp MailContentBuilder.
// Cuối cùng  gọi phương thức sendMail từ phương thức đăng ký của bên trong lớp AuthService.