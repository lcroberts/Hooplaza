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
@Entity(name = "User")
@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    private String name;
    private String email;
    private String role;
    private String password;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_communities",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "communityId")}
    )
    private List<Community> communities = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "user_moderated_communtities",
            joinColumns = {@JoinColumn(name = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "communityId")}
    )
    private List<Community> moderatorOf = new ArrayList<>();

    @Transient
    private List<Post> bookmarks = new ArrayList<>();

    public User(String name, String email, List<Community> communities, String role, String password) {
        this.name = name;
        this.email = email;
        this.communities = communities;
        this.role = role;
        this.password = password;
    }


}
