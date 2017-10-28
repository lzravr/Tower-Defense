package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class PurpleTower extends Tower {

	public PurpleTower(TowerType type, Tile startTile, CopyOnWriteArrayList<Creep> enemies) {
		super(type, startTile, enemies);
	}	

	@Override
	public void shoot(Creep target) {
		super.spawnProjectile(ProjectileType.purpleTowerProjectileLVL1);
	}

}
