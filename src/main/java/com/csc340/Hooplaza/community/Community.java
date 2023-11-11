package com.csc340.Hooplaza.community;

import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Entity
@Table(name = "community")
@NoArgsConstructor
@Getter
@Setter
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cID;

    private String name;
    private String locationID;
    private String description;
    @Transient
    private List<User> mods; //may need to create Moderator class
    @Transient
    private List<User> members;
    @Transient
    private List<Post> posts;



    public Community(String name, String locationID, String description, List<User> mods, List<User> members) {
        this.name = name;
    }
}
