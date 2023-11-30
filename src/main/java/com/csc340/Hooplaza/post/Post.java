package com.csc340.Hooplaza.post;

import com.csc340.Hooplaza.community.Community;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId")
    private Community postCommunity;

    private long communityId;
    private long userId;
    private String title;
    private String content;

    private String tag;
    private Date postDate; //set value

    public Post(long communityId, long userId, String title, String content, String tag) {
        this.communityId = communityId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    @PrePersist
    protected void onCreate() {
        this.postDate = new Date();
    }
}
