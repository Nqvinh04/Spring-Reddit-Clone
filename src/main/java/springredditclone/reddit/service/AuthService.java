package springredditclone.reddit.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springredditclone.reddit.dto.RegisterRequest;
import springredditclone.reddit.exceptions.SpringRedditException;
import springredditclone.reddit.model.NotificationEmail;
import springredditclone.reddit.model.User;
import springredditclone.reddit.model.VerificationToken;
import springredditclone.reddit.repository.UserRepository;
import springredditclone.reddit.repository.VerificationTokenRepository;

import javax.transaction.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        user.setCreated(Instant.now());

        // vô hiệu hóa người dùng sau khi đăng ký
        // kích hoạt khi người dùng xác minh đia chi email của người dùng
        user.setEnabled(false);
        userRepository.save(user);

//        // Kích hoạt tài khoản qua email
        String token = generateVerificationToken(user);

            mailService.sendMail(new NotificationEmail("Please Activate your Account",
                    user.getEmail(),"Thank you for signing up to Spring Reddit, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:8080/api/auth/accountVerification/" + token));
    }

//    Tạo một UUID ngẫu nhiên làm mã thông báo của tài khoản,
//    tạo một đối tượng cho VerificationToken,
//    điền dữ liệu cho đối tượng đó và lưu nó vào
//    VerificationTokenRepository .

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }



    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }
}
