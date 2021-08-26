package ru.starslan.demo.facade;

import org.springframework.stereotype.Component;
import ru.starslan.demo.dto.CommentDTO;
import ru.starslan.demo.entity.Comment;

@Component
public class CommentFacade {

    public CommentDTO commentToCommentDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setUsername(comment.getUsername());
        commentDTO.setMassage(comment.getMessage());

        return  commentDTO;
    }
}
