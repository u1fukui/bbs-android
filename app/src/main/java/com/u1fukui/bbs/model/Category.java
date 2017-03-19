package com.u1fukui.bbs.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 4312405835923148571L;

    private int id;

    private String name;
}