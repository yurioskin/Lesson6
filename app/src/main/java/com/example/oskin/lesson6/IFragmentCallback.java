package com.example.oskin.lesson6;

public interface IFragmentCallback<T> {
    void setDataCallback(T data);
    T getDataCallback();
}
