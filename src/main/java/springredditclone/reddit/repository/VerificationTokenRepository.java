package springredditclone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springredditclone.reddit.model.VerificationToken;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends
        JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
}
