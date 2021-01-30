package lv.sit.todo;

import java.util.Date;

/**
 * Calculate time to pospone item preview
 */
public class Time {
    /**
     * @return current time
     */
    public long currentTime ()
    {
        Date d = new Date();
        return d.getTime() / 1000;
    }

    /**
     * Postponed time
     * one hour
     * @return
     */
    public long nextTime ()
    {
        return currentTime() + (60 * 60);
    }
}
