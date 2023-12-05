package com.csc340.Hooplaza.CommunityRequest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Table(name = "communityRequest")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommunityRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long requestId;
    private Date created;
    private String name;
    private String locationId;
    private String body;
    private long userId; // Id of requester
    private String userName;
    boolean requestActive = true;
    boolean requestAccepted = false;

    public CommunityRequest(String name, String locationId, String body, long userId) {
        this.name = name;
        this.locationId = locationId;
        this.body = body;
        this.userId = userId;
    }

    @PrePersist
    protected void onCreate() {
        this.created = new Date();
    }
}
