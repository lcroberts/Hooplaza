package com.csc340.Hooplaza.community;

import com.csc340.Hooplaza.post.Post;
import com.csc340.Hooplaza.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Entity(name = "Community")
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
    private boolean communityActive = true;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "moderatorOf"
    )
    private List<User> mods = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "communities"
    )
    private List<User> members = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();


    public Community(String name, String locationId, String description, List<User> mods, List<User> members) {
        this.name = name;
        this.locationId = locationId;
        this.description = description;
        this.mods = mods;
        this.members = members;
    }
}
