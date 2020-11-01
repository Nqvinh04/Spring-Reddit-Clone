package springredditclone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springredditclone.reddit.model.Comment;
import springredditclone.reddit.model.Post;
import springredditclone.reddit.model.User;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost (Post post);
    List<Comment> findAllByUser (User user);
}
