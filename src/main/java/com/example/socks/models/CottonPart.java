package com.example.socks.models;

public enum CottonPart {
    C1(20),
    C2(40),
    C3(60),
    C4(80),
    C5(100);
   private Integer percent;

    CottonPart(Integer percent) {
        this.percent = percent;
    }

    public Integer getPercent() {
        return percent;
    }
}
