package com.mozio.toddssyndrome.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mutha on 18/09/16.
 */

//Unused part of the remote api code (don't have an end point)
public class SuccessBaseResponse{

    @SerializedName(value = "success")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

