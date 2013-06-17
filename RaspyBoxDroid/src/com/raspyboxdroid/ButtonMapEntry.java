package com.raspyboxdroid;

public class ButtonMapEntry {
	
	private int onButtonId;
	private int offButtonId;
	
	public ButtonMapEntry() {
	}
	
	public ButtonMapEntry(int onButtonId, int offButtonId) {
		this.onButtonId = onButtonId;
		this.offButtonId = offButtonId;
	}
	
	public int getOnButtonId() {
		return onButtonId;
	}

	public void setOnButtonId(int onButtonId) {
		this.onButtonId = onButtonId;
	}

	public int getOffButtonId() {
		return offButtonId;
	}

	public void setOffButtonId(int offButtonId) {
		this.offButtonId = offButtonId;
	}
			
}
