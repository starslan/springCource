package ru.starslan.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.starslan.demo.entity.Post;
import ru.starslan.demo.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreatedDateDesc(User user);
    List<Post> findAllByOrderByCreatedDateDesc();
    List<Post> findAllByIdAndUser(Long id, User user);
    Optional<Post> findPostByIdAndUser(Long id, User user);
}
