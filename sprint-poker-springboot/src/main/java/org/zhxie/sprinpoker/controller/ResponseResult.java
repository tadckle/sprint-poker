package org.zhxie.sprinpoker.controller;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by jianyang on 2019/1/9.
 */
public class ResponseResult {
    private boolean isSuccess;
    private String message;
    private Map<String, Object> data = Maps.newHashMap();

    public ResponseResult(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public ResponseResult(boolean isSuccess, String message, Map<String, Object> data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
