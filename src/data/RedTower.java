package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class RedTower extends AoeTower {

	public RedTower(TowerType type, Tile startTile, CopyOnWriteArrayList<Creep> enemies) {
		super(type, startTile, enemies);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void shoot(Creep target) {
		spawnProjectile(ProjectileType.redTowerProjectileLVL1);

	}

}
