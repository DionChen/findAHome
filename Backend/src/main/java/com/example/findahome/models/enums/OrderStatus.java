package com.example.findahome.models.enums;


/**
 * Order states
 */
public enum OrderStatus {
    DELETE(0, "Delete"),
    CANCELLATION(1, "cancellation"),
    UNPAID(2, "unpaid"),
    PAID(3, "paid"),
    FINISH(4,"finish");

    private Integer statusNum;

    private String desc;

    OrderStatus(Integer statusNum, String desc) {
        this.statusNum = statusNum;
        this.desc = desc;
    }

    public Integer getStatusNum(){
        return statusNum;
    }

    public String getDesc() {
        return desc;
    }
}
