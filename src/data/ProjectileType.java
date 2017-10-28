package data;

import org.newdawn.slick.opengl.Texture;

import helpers.TextureBank;

public enum ProjectileType {
	
	greenTowerProjectileLVL1(TextureBank.greenProjectile, 10, CSEType.slowlvl1, 250),
	orangeTowerProjectileLVL1(TextureBank.orangeProjectile, 20, -1, 400),
	blueTowerProjectileLVL1(TextureBank.blueProjectile, 0.6f, CSEType.slowlvl2, 100),
	purpleTowerProjectileLVL1(TextureBank.purpleProjectile, 100, -1, 180),
	redTowerProjectileLVL1(TextureBank.redProjectile, 0.3f, CSEType.dmg_amp, 80);
	
	Texture texture;
	float damage;
	int statusEffectCode;
	float speed;
	
	private ProjectileType(Texture texture, float dmg, int statusEffectCode, float speed) {
		this.texture = texture;
		this.damage = dmg;
		this.speed = speed;
		this.statusEffectCode = statusEffectCode;
	}
	
	
	
}
