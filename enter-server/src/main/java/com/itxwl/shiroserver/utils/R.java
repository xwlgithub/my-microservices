package com.itxwl.shiroserver.utils;

import lombok.Data;

import java.util.List;

@Data
public class R<T> {
    private Boolean isStatus;
    private List<T> Lists;
    private String message;

    public R(Boolean isStatus,List<T> lists,String message) {
        this.isStatus=isStatus;
        this.Lists=lists;
        this.message=message;
    }
    public R(Boolean isStatus,String message) {
        this.isStatus=isStatus;
        this.message=message;
    }
    public R(Boolean isStatus,List<T> lists) {
        this.isStatus=isStatus;
        this.Lists=lists;
    }
}
