package com.u1fukui.bbs.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = -2300031079350686170L;

    public long id;

    public long threadId;

    public int displayNumber;

    public String description;

    public User author;

    public int likeCount;

    public boolean isLiked;

    public Date createdAt;
}
