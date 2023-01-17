package com.example.socks.models;

public enum CottonPart {
    C1(20),
    C2(40),
    C3(60),
    C4(80),
    C5(100);
    Integer percent;

    CottonPart(Integer percent) {
        this.percent = percent;
    }
}
