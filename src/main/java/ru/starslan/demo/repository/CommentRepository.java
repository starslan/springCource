package ru.starslan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.starslan.demo.entity.Comment;
import ru.starslan.demo.entity.Post;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    Comment findAllByIdAndUserId(Long commentId, Long userId);
}
