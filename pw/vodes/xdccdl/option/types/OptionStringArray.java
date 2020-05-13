package pw.vodes.xdccdl.option.types;

import pw.vodes.xdccdl.option.Option;

public class OptionStringArray extends Option {
	
	private String currentmode;
	private String[] availablemodes;
	private String name;

	public OptionStringArray(String name, String current, String[] availablemodes) {
		super(name, EnumOptionTypes.StringArray);
		this.currentmode = current;
		this.availablemodes = availablemodes;
	}
	
	public void setCurrentMode(String s) {
		this.currentmode = s;
	}
	
	public String getCurrentMode() {
		return currentmode;
	}
	
	public String[] getAvailableModes() {
		return this.availablemodes;
	}
	
	public int getNumberForCurrent() {
		boolean stopcounting = false;
		int i = 0;
		for(String s : availablemodes) {
			if(currentmode.equalsIgnoreCase(s)) {
				stopcounting = true;
			}
			if(!stopcounting) {
				i++;
			}
		}
		return i;
	}
	
	public void setCurrentWithInt(int i) {
		this.currentmode = this.availablemodes[i];
	}
}