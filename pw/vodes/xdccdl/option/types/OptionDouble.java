package pw.vodes.xdccdl.option.types;

import pw.vodes.xdccdl.option.Option;

public class OptionDouble extends Option {

    private double value;
    public double increment;
    private double max;
    private double min;

    public OptionDouble(String name, Double value) {
        super(name, EnumOptionTypes.Double);
        this.value = value;
        this.increment = 1;
        this.max = 99999999;
        this.min = 0;
    }

    public OptionDouble(String name, Double value, Double increment, Double max, Double min) {
        super(name, EnumOptionTypes.Double);
        this.value = value;
        this.increment = increment;
        this.max = max;
        this.min = min;
    }

    public OptionDouble(String name,  Double value, Double increment, Double max, Double min, String category) {
        super(name, EnumOptionTypes.Double);
        this.value = value;
        this.increment = increment;
        this.max = max;
        this.min = min;
        this.setCategory(category);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void addIncrement(boolean positive) {
        if(positive) {
            value += increment;
        } else {
            value -= increment;
        }
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

}