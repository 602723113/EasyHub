package top.zoyn.easyhub;

/**
 * 时间枚举
 *
 * @author Zoyn
 */
public enum TimeType {

    SUN_RISE(23000),
    DAY(1000),
    NOON(6000),
    SUN_SET(11616),
    NIGHT(13000),
    MID_NIGHT(18000);

    private final int tick;

    TimeType(int tick) {
        this.tick = tick;
    }

    public int toTick() {
        return tick;
    }
}
