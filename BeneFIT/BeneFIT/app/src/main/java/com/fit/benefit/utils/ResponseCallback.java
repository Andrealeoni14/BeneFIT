package com.fit.benefit.utils;

import com.fit.benefit.models.Exercise;

import java.util.List;

public interface ResponseCallback {
    void onResponse(List<Exercise> exerciseList, long lastUpdate);
    void onFailure(String message);
}
