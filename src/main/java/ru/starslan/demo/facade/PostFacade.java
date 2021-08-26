package ru.starslan.demo.facade;

import org.springframework.stereotype.Component;
import ru.starslan.demo.dto.PostDTO;
import ru.starslan.demo.entity.Post;

@Component
public class PostFacade {
    public PostDTO postToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setId(post.getId());
        postDTO.setCaption(post.getCaption());
        postDTO.setLikes(post.getLikes());
        postDTO.setUsersLiked(post.getLikedUser());
        postDTO.setLocation(post.getLocation());
        postDTO.setTitle(post.getTitle());
        return postDTO;
    }
}
