package pw.vodes.xdccdl.option;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import pw.vodes.xdccdl.XDCCDL;
import pw.vodes.xdccdl.option.types.EnumOptionTypes;
import pw.vodes.xdccdl.option.types.OptionBoolean;
import pw.vodes.xdccdl.option.types.OptionDouble;
import pw.vodes.xdccdl.option.types.OptionString;
import pw.vodes.xdccdl.option.types.OptionStringArray;
import pw.vodes.xdccdl.util.Sys;

public class OptionManager {

	public ArrayList<Option> options = new ArrayList<Option>();

	private File file;
	private FileWriter fw;
	private BufferedWriter writer;

	public void saveOptions() {
		file = new File(XDCCDL.getInstance().directory.getAbsolutePath(), "options.txt");
		if (!XDCCDL.getInstance().directory.exists()) {
			XDCCDL.getInstance().directory.mkdir();
		}
		if (file.exists()) {
			file.delete();
		}
		BufferedWriter writer1;
		try {
			writer1 = new BufferedWriter(new FileWriter(file));
			writer1.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			fw = new FileWriter(file, true);
			writer = new BufferedWriter(fw);
			for (Option op : options) {
				String optionname = op.getName();
				if (op.getType().equals(EnumOptionTypes.Boolean)) {
					OptionBoolean opb = (OptionBoolean) op;
					writer.write(opb.value + ";" + optionname);
					writer.newLine();
				} else if (op.getType().equals(EnumOptionTypes.Double)) {
					OptionDouble opd = (OptionDouble) op;
					writer.write(opd.getValue() + ";" + optionname);
					writer.newLine();
				} else if (op.getType().equals(EnumOptionTypes.String)) {
					OptionString ops = (OptionString) op;
					writer.write(ops.getValue() + ";" + optionname);
					writer.newLine();
				} else if (op.getType().equals(EnumOptionTypes.StringArray)) {
					OptionStringArray opd = (OptionStringArray) op;
					writer.write(opd.getCurrentMode() + ";" + optionname);
					writer.newLine();
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			Sys.out("What might help is deleting the options.txt in " + XDCCDL.getInstance().directory.getAbsolutePath(), "test");
		}
	}

	public void loadOptions() {
		file = new File(XDCCDL.getInstance().directory.getAbsolutePath(), "options.txt");
		if (!file.exists()) {
			return;
		}
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			for (String line; (line = br.readLine()) != null;) {
				String optionname = line.split(";")[1];
				for (Option op : options) {
					if (op.getName().equalsIgnoreCase(optionname)) {
						if (op.getType().equals(EnumOptionTypes.Boolean)) {
							op.setValueGeneral(Boolean.parseBoolean(line.split(";")[0]));
						} else if (op.getType().equals(EnumOptionTypes.Double)) {
							op.setValueGeneral(Double.parseDouble(line.split(";")[0]));
						} else if (op.getType().equals(EnumOptionTypes.String)) {
							op.setValueGeneral(line.split(";")[0]);
						} else if (op.getType().equals(EnumOptionTypes.StringArray)) {
							op.setValueGeneral(line.split(";")[0]);
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			Sys.out("What might help is deleting the options.txt in " + XDCCDL.getInstance().directory.getAbsolutePath(),
					"error");
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			Sys.out("What might help is deleting the options.txt in " + XDCCDL.getInstance().directory.getAbsolutePath(),
					"error");
		}
	}

	public void setOptionValue(String name, Object value) {
		for (Option op : options) {
			if (name.equalsIgnoreCase(op.getName())) {
				op.setValueGeneral(value);
			}
		}
	}
	
	
	public boolean getBoolean(String option) {
		for(Option op : this.options) {
			if(op.getName().equalsIgnoreCase(option) && op instanceof OptionBoolean) {
				return ((OptionBoolean)op).isValue();
			}
		}
		return false;
	}
	
	public String getString(String option) {
		for(Option op : this.options) {
			if(op.getName().equalsIgnoreCase(option) && op instanceof OptionString) {
				return ((OptionString)op).getValue();
			}
		}
		return null;
	}
	
	public String getCurrent(String option) {
		for(Option op : this.options) {
			if(op.getName().equalsIgnoreCase(option) && op instanceof OptionStringArray) {
				return ((OptionStringArray)op).getCurrentMode();
			}
		}
		return null;
	}
	
	public double getDouble(String option) {
		for(Option op : this.options) {
			if(op.getName().equalsIgnoreCase(option) && op instanceof OptionDouble) {
				return ((OptionDouble)op).getValue();
			}
		}
		return 0D;
	}
	
	public float getFloat(String option) {
		return (float)this.getDouble(option);
	}
	
	public Option getOption(String option) {
		for(Option op : this.options) {
			if(op.getName().equalsIgnoreCase(option)) {
				return op;
			}
		}
		return null;
	}
}
