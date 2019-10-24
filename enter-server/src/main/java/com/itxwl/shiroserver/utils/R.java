package com.itxwl.shiroserver.utils;

import lombok.Data;

import java.util.List;

@Data
public class R<T> {
    private Boolean isStatus;
    private List<T> Lists;
    private T data;
    private String message;

    public R(Boolean isStatus, List<T> lists, String message) {
        this.isStatus = isStatus;
        this.Lists = lists;
        this.message = message;
    }

    public R(Boolean isStatus, String message) {
        this.isStatus = isStatus;
        this.message = message;
    }

    public R(Boolean isStatus, List<T> lists) {
        this.isStatus = isStatus;
        this.Lists = lists;
    }

    public R(Boolean code, T data, String message) {
        this.isStatus = code;
        this.data = data;
        this.message = message;
    }

    public static <T> R<T> data(Boolean code, T data, String message) {
        return new R(code, data, data == null ? "暂无承载数据" : message);
    }

    public static <T> R<T> data(T data, String message) {
        return data(true, data, message);
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

}

