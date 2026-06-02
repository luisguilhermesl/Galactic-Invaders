package main;

public class Spawner {
	
	private int interval = 0;
	private int maxInterval = 80;
	
	public void logic() {
		interval++;
		if(interval >= maxInterval) {
			Game.enemys.add(new Enemy());
			interval = 0;
		}
	}
}