package pw.vodes.xdccdl.option.types;

import pw.vodes.xdccdl.option.Option;

public class OptionBoolean extends Option {

    public boolean value;

    public OptionBoolean(String name, boolean boolea) {
        super(name, EnumOptionTypes.Boolean);
        this.value = boolea;
    }

    public OptionBoolean(String name, boolean boolea, String category) {
        super(name, EnumOptionTypes.Boolean);
        this.value = boolea;
        this.setCategory(category);
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
