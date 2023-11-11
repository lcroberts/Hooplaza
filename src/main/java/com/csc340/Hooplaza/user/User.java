package com.csc340.Hooplaza.user;

import com.csc340.Hooplaza.community.Community;
import com.csc340.Hooplaza.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uID;

    private String name;
    private String email;
    @Transient
    private List<Community> communities;
    @Transient
    private List<Post> bookmarks;

    public User(String name, String email, List<Community> communities) {
        this.name = name;
        this.email = email;
        this.communities = communities;
    }



}
