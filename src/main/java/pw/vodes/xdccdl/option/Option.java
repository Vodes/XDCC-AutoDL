package pw.vodes.xdccdl.option;

import pw.vodes.xdccdl.option.types.EnumOptionTypes;
import pw.vodes.xdccdl.option.types.OptionBoolean;
import pw.vodes.xdccdl.option.types.OptionDouble;
import pw.vodes.xdccdl.option.types.OptionString;
import pw.vodes.xdccdl.option.types.OptionStringArray;

public class Option {

    public String name;
    public EnumOptionTypes type;
    public String category = "";

    public Option(String name, EnumOptionTypes type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumOptionTypes getType() {
        return type;
    }

    public void setType(EnumOptionTypes type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean hasCategory() {
        if (this.getCategory().isEmpty() || this.getCategory().equals("")) {
            return false;
        }
        return true;
    }

    public void setValueGeneral(Object o) {
        if (this.type.equals(EnumOptionTypes.Boolean)) {
            Boolean b = (Boolean) o;
            OptionBoolean opb = (OptionBoolean) this;
            opb.setValue(b);
        } else if (this.type.equals(EnumOptionTypes.Double)) {
            Double d = (Double) o;
            OptionDouble opd = (OptionDouble) this;
            opd.setValue(d);
        } else if (this.type.equals(EnumOptionTypes.String)) {
            String s = (String) o;
            OptionString opd = (OptionString) this;
            opd.setValue(s);
        }else if(this.type.equals(EnumOptionTypes.StringArray)){
			String s = (String)o;
			OptionStringArray ops = (OptionStringArray)this;
			ops.setCurrentMode(s);
		}	

    }

    public static Object getValueGeneral(Option o) {
        if (o.getType().equals(EnumOptionTypes.Boolean)) {
            OptionBoolean ops = (OptionBoolean) o;
            return ops.isValue();
        } else if (o.getType().equals(EnumOptionTypes.Double)) {
            OptionDouble ops = (OptionDouble) o;
            return ops.getValue();
        } else if (o.getType().equals(EnumOptionTypes.String)) {
            OptionString ops = (OptionString) o;
            return ops.getValue();
        } else if(o.getType().equals(EnumOptionTypes.StringArray)) {
			OptionStringArray ops = (OptionStringArray)o;
			return ops.getCurrentMode();
		}
        return null;
    }

}