package com.u1fukui.bbs.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BbsThread implements Serializable {

    private static final long serialVersionUID = -4762213969235749270L;

    public long id;

    public String title;

    public User author;

    public int commentCount;

    public Date createdAt;

    public Date updatedAt;
}
