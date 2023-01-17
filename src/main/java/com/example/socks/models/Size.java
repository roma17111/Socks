package com.example.socks.models;

public enum Size {
    M(25),
    S(27),
    L(29),
    XL(31);

    Integer size;

    Size(Integer size) {
        this.size = size;
    }
}
