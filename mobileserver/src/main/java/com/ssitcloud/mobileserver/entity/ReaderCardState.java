package com.ssitcloud.mobileserver.entity;

/**
 * Created by LXP on 2017/7/21.
 */

public class ReaderCardState {
    public enum CardState {
        NORMAL(0,"正常"),INVAILD(1,"卡无效"),PASSWORD_ERROR(2,"密码错误"),NOT_EXISTS(3,"不存在");

        private final int value;
        private final String message;

        CardState(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public int getValue() {
            return value;
        }

        public String getMessage() {
            return message;
        }
    }
    private CardState state;

    public CardState getState() {
        return state;
    }

    public void setState(CardState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ReaderCardState{" +
                "state=" + state +"("+ state.getMessage()+")"+
                '}';
    }
}
