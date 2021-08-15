package ru.starslan.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import ru.starslan.demo.entity.enums.ERole;

import javax.persistence.Entity;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, updatable = false)
    private String userName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String mail;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(length = 3000)
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))
    private Set<ERole> role = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();


    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(){

    }

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
