package com.u1fukui.bbs.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 8679411212262196713L;

    public long id;

    public String name;
}
