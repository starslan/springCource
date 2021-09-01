package ru.starslan.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.starslan.demo.dto.PostDTO;
import ru.starslan.demo.entity.Post;
import ru.starslan.demo.facade.PostFacade;
import ru.starslan.demo.payload.response.MessageResponse;
import ru.starslan.demo.service.PostService;
import ru.starslan.demo.validations.ResponseErrorValidation;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/post")
@CrossOrigin
public class PostController {
    @Autowired
    private PostFacade postFacade;
    @Autowired
    private PostService postService;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO postDTO,
                                             BindingResult bindingResult,
                                             Principal principal){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if(!ObjectUtils.isEmpty(errors)){
            return errors;
        }

        Post post = postService.createPost(postDTO, principal);
        PostDTO createdPost = postFacade.postToPostDTO(post);
        System.out.println(createdPost.toString());

        return new ResponseEntity<>(postDTO, HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> getAllPosts(){

        List<PostDTO> postDTOList = postService.getAllPosts()
            .stream()
            .map(postFacade::postToPostDTO)
            .collect(Collectors.toList());

        return  new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @GetMapping("/user/post")
    public ResponseEntity<List<PostDTO>> getAllPostForUser(Principal principal){
        List<PostDTO> postDTOList = postService.getAllPostForUser(principal)
                .stream()
                .map(postFacade::postToPostDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDTO> likePost (@PathVariable("postId") String postId,
                                             @PathVariable("username") String username){
        Post post = postService.likedPost(Long.parseLong(postId), username);
        PostDTO postDTO = postFacade.postToPostDTO(post);

        return  new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PostMapping("/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId, Principal principal){
        postService.deletePost(Long.parseLong(postId), principal);
        return  new ResponseEntity<>(new MessageResponse("Post was deleted"), HttpStatus.OK);
    }
}