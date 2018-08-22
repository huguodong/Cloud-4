package com.ssit.entity;

public class EquipmentEntity extends BaseEntity {
    private long total;     // 设备数量

    public EquipmentEntity() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
