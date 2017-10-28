package data;


import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;

public class Wave {
	private float timeSinceLastSpawn, spawnTime;
	private CreepType creepType;
	private CopyOnWriteArrayList<Creep> enemyList;
	private int enemiesPerWave, enemiesSpawned;
	private boolean waveComplete;

	public Wave(CreepType creepType, float spawnTime, int enemiesPerWave) {
		this.spawnTime = spawnTime;
		this.creepType = creepType;
		this.enemiesPerWave = enemiesPerWave;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Creep>();
		this.waveComplete = false;
		this.enemiesSpawned = 0;

		spawn(creepType);
	}

	public void update() {
		boolean allEnemiesDead = true;
		if (enemiesSpawned < enemiesPerWave) {
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				spawn(creepType);
				timeSinceLastSpawn = 0;
			}
		}

		for (Creep e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			}
			else{
				enemyList.remove(e);
			}
		}
		
		if (allEnemiesDead && enemiesSpawned == enemiesPerWave)
			waveComplete = true;
	}

	private void spawn(CreepType creepType) {
		enemyList.add(new Creep(creepType, TileGrid.startTile , Game.grid));
		enemiesSpawned++;
	}

	public boolean isCompleted() {
		return waveComplete;
	}

	public CopyOnWriteArrayList<Creep> getEnemyList() {
		return enemyList;
	}
	
	
}
