package com.csc340.Hooplaza.community;

import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;


@AllArgsConstructor
@Entity
@Table(name = "community")
@NoArgsConstructor
@Getter
@Setter
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long communityId;

    private String name;
    private String locationId;
    private String description;
    @Transient
    private ArrayList<User> mods;
    @Transient
    private ArrayList<User> members;
    @Transient
    private ArrayList<Post> posts;


    public Community(String name, String locationId, String description, ArrayList<User> mods, ArrayList<User> members) {
        this.name = name;
        this.locationId = locationId;
        this.description = description;
        this.mods = mods;
        this.members = members;
    }
}
