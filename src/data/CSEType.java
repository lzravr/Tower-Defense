package data;

public enum CSEType {
	
	SLOWLVL1(2, -0.2f, 1, true),
	SLOWLVL2(2, -0.5f, 1, true),
	SLOWLVL3(2, -0.5f, 1, true),
	STUN(0.15f, -1, 1, true),
	DMG_AMP(1.5f, 1, 0.5f, false);
	
	public static final int slowlvl1 = 0, stun = 1, dmg_amp = 2, slowlvl2 = 3;
	public static final CSEType[] EFFECTS = {SLOWLVL1, STUN, DMG_AMP, SLOWLVL2, SLOWLVL3};
	public static final int numberOfTypes = EFFECTS.length;
	
	private float duration, speedModifier, damageModifier;
	private boolean resistable;
	
	


	private CSEType(float duration, float speedModifier, float damageModifier, boolean resistable) {
		this.duration = duration;
		this.speedModifier = speedModifier;
		this.damageModifier = damageModifier;
		this.resistable = resistable;
	}

	

	public float getDuration() {
		return duration;
	}



	public void setDuration(float duration) {
		this.duration = duration;
	}



	public float getSpeedModifier() {
		return speedModifier;
	}



	public void setSpeedModifier(float speedModifier) {
		this.speedModifier = speedModifier;
	}



	public float getDamageModifier() {
		return damageModifier;
	}



	public void setDamageModifier(float damageModifier) {
		this.damageModifier = damageModifier;
	}



	public void setResistable(boolean resistable) {
		this.resistable = resistable;
	}



	public boolean isResistable() {
		return resistable;
	}
	
	
	
	
	
}
