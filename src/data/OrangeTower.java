package data;

import java.util.concurrent.CopyOnWriteArrayList;

public class OrangeTower extends Tower{
	
	public OrangeTower(TowerType type, Tile startTile, CopyOnWriteArrayList<Creep> enemies) {
		super(type, startTile, enemies);
	}

	@Override
	public void shoot(Creep target) {
		super.spawnProjectile(ProjectileType.orangeTowerProjectileLVL1);
	}
}
