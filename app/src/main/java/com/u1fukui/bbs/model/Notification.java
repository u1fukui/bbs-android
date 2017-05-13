package com.u1fukui.bbs.model;


import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Notification implements Serializable {

    private static final long serialVersionUID = -6826333503595117337L;

    public long id;

    public String url;

    public String message;

    public Date createdAt;
}
