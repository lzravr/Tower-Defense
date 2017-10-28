package data;

import java.util.concurrent.CopyOnWriteArrayList;

// Ovako od sad gradimo towere

public class GreenTower extends Tower {
	
	public GreenTower(TowerType type, Tile startTile, CopyOnWriteArrayList<Creep> enemies) {
		super(type, startTile, enemies);
	}

	@Override
	
	/**
	 * samo skracena verzija
	 */
	public void shoot(Creep target) {
		super.spawnProjectile(ProjectileType.greenTowerProjectileLVL1);
	}
}
