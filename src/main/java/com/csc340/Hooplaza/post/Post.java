package com.csc340.Hooplaza.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long postId;

    private long communityId;
    private long userId;
    private String title;
    private String content;
    private String tag;
    private String imageURL;
    private Date postDate; //set value

    public Post(long communityId, long userId, String title, String content, String tag, String imageURL) {
        this.communityId = communityId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.imageURL = imageURL;
    }

    @PrePersist
    protected void onCreate() {
        this.postDate = new Date();
    }
}
