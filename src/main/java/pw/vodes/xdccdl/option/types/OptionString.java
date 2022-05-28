package pw.vodes.xdccdl.option.types;

import pw.vodes.xdccdl.option.Option;

public class OptionString extends Option {

    public String value;

    public OptionString(String name, String option) {
        super(name, EnumOptionTypes.String);
        this.value = option;
    }

    public OptionString(String name, String option, String category) {
        super(name, EnumOptionTypes.String);
        this.value = option;
        this.setCategory(category);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}