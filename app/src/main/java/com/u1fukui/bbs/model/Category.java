package com.u1fukui.bbs.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 4312405835923148571L;

    public int id;

    public String name;
}