package utils;

public enum TimeFrame
{
    Now(0),
    PlusOne(1),
    PlusTwo(2)
 
    public final Integer value;
 
    private Value(Integer value) {
        this.value = value;
    }
}
}