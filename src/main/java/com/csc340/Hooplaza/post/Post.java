package com.csc340.Hooplaza.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Entity
@Table(name = "post")
@NoArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long pID;

    private long cID;
    private long uID;
    private String title;
    private String content;
    @Transient
    private List<String> tags;
    private Date postDate; //set value

    public Post(long cID, long uID, String title, String content, List<String> tags) {
        this.cID = cID;
        this.uID = uID;
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    @PrePersist
    protected void onCreate() {
        this.postDate = new Date();
    }
}
