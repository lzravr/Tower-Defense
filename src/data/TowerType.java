package data;

import org.newdawn.slick.opengl.Texture;

import helpers.TextureBank;

public enum TowerType {
	
	greenTower(TextureBank.greenTowerBase, TextureBank.greenTowerCannon, ProjectileType.greenTowerProjectileLVL1, 100, 5, 1.2f),
	orangeTower(TextureBank.orangeTowerBase, TextureBank.orangeTowerCannon,ProjectileType.orangeTowerProjectileLVL1, 100, 5, 1),
	purpleTower(TextureBank.purpleTowerBase, TextureBank.purpleTowerCannon, ProjectileType.purpleTowerProjectileLVL1, 200, 25, 2),
	blueTower(TextureBank.blueTowerBase, TextureBank.blueTowerCannon, ProjectileType.blueTowerProjectileLVL1, 80, 35, 1.5f),
	redTower(TextureBank.redTowerBase, TextureBank.redTowerCannon, ProjectileType.redTowerProjectileLVL1, 100, 75, 1.5f);
	
	Texture towerBase;
	Texture towerCannon;
	ProjectileType projectileType;
	int range, cost;
	float attackSpeed;
	
	TowerType(Texture tb, Texture tc,ProjectileType projectileType, int range, int cost, float as) {
		towerBase = tb;
		towerCannon = tc;
		this.projectileType = projectileType;
		this.range = range;
		this.attackSpeed = as;
		this.cost = cost;
	}

	public Texture getTowerBase() {
		return towerBase;
	}

	public Texture getTowerCannon() {
		return towerCannon;
	}

	public ProjectileType getProjectileType() {
		return projectileType;
	}

	public int getRange() {
		return range;
	}

	public int getCost() {
		return cost;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}
	
	
	
	
}
