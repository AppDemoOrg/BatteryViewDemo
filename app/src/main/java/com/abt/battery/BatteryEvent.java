package com.abt.battery;

/**
 * @描述：     @电池监听事件
 * @作者：     @黄卫旗
 * @创建时间： @2018-04-14
 */
public class BatteryEvent {

    /**
     * 电池刻度
     */
    public int level;

    /**
     * 是否充电状态
     */
    public boolean isCharging;

    /**
     * 是否是满格电
     */
    public boolean isFull;

    public BatteryEvent(int level, boolean isCharging, boolean full) {
        this.level = level;
        this.isCharging = isCharging;
        this.isFull= full;
    }
}
