package ru.starslan.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.starslan.demo.dto.PostDTO;
import ru.starslan.demo.entity.ImageModel;
import ru.starslan.demo.entity.Post;
import ru.starslan.demo.entity.User;
import ru.starslan.demo.exceptions.PostNotFondException;
import ru.starslan.demo.repository.ImageRepository;
import ru.starslan.demo.repository.PostRepository;
import ru.starslan.demo.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public static final Logger LOG = LoggerFactory.getLogger(PostService.class);
    private final PostRepository  postRepository;
    private final UserRepository  userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);

        LOG.info("Save user post {}" + user.getMail());
        return postRepository.save(post);

    }

    public List<Post> getAllPosts(){
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public Post getPostBuId(Long id, Principal principal){
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(id, user).orElseThrow(()->new PostNotFondException("Post cannot found for username"+user.getMail()));
    }

    public List<Post> getAllPostForUser(Principal principal){
        User user = getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public Post likedPost(Long postId, String username){
        Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFondException("Post cannot be found"));
        Optional<String> userLiked = post.getLikedUser().stream().filter(u -> u.equals(username)).findAny();

        if(userLiked.isPresent()){
            post.setLikes(post.getLikes() - 1);
            post.getLikedUser().remove(username);
        }else{
            post.setLikes(post.getLikes() + 1);
            post.getLikedUser().add(username);
        }

        return postRepository.save(post);
    }

    public void deletePost(Long postId, Principal principal){
        Post post = getPostBuId(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(post.getId());
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);
    }

    private User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUserName(username).orElseThrow(()-> new UsernameNotFoundException("Username not found " + username));
    }
}
