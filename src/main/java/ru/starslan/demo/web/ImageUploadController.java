package ru.starslan.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.starslan.demo.entity.ImageModel;
import ru.starslan.demo.payload.response.MessageResponse;
import ru.starslan.demo.service.ImageService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("api/image")
@CrossOrigin
public class ImageUploadController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadItemToUser(@RequestParam("file") MultipartFile file, Principal principal)
    throws IOException {

        imageService.uploadImageToUser(file, principal);
        return  new ResponseEntity.ok(new MessageResponse("Image Upload Succefuly"));

    }

    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") String postId,
                                                             @RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException{
        imageService.uploadImageToPost(file, principal, Long.parseLong(postId));
        return new ResponseEntity.ok(new MessageResponse("Image Upload Succefuly"));
    }

    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageForUser(Principal principal){
        ImageModel userImage = imageService.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }
}
