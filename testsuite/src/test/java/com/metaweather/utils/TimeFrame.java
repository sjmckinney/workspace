package com.metaweather.utils;

public enum TimeFrame
{
    Now(0),
    PlusOne(1),
    PlusTwo(2);

    public final Integer value;
 
    private TimeFrame(Integer value)
    {
        this.value = value;
    }
}
