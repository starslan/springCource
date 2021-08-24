package ru.starslan.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.starslan.demo.dto.CommentDTO;
import ru.starslan.demo.entity.Comment;
import ru.starslan.demo.entity.Post;
import ru.starslan.demo.entity.User;
import ru.starslan.demo.exceptions.PostNotFondException;
import ru.starslan.demo.repository.CommentRepository;
import ru.starslan.demo.repository.PostRepository;
import ru.starslan.demo.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment saveComment(Long postId, CommentDTO commentDTO, Principal principal){

        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFondException("Post cannot found for username"+user.getMail()));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMassage());

        LOG.info("Saving comment for Post {}", post.getId());

        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommentsForPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFondException("Post cannot found"));
        List<Comment> comments = commentRepository.findAllByPost(post);

        return  comments;
    }

    public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }


    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUserName(username).orElseThrow(()-> new UsernameNotFoundException("Username not found " + username));
    }
}
