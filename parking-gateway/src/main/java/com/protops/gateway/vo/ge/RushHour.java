package com.protops.gateway.vo.ge;

/**
 * Created by damen on 2016/4/17.
 */
public class RushHour {

    public static Integer[] weekDay = {1, 2, 3, 4, 5, 6, 7};

    private String percent;
    private String unit;

    public RushHour(Double percent, String unit) {

        this.percent = String.format("%.0f", Math.rint(percent));
        this.unit = unit;

    }

    public static void main(String[] args) {

        Double d = 33.58181818181818;

        System.out.println();

    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
