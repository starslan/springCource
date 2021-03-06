package ru.starslan.demo.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    private String title;
    private String caption;
    private String username;
    private String location;
    private Integer likes;
    private Set<String> usersLiked;

}
