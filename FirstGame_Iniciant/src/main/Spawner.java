package main;

//48-----------------------------------------
public class Spawner {
	private int interval = 0;
	private int maxInterval=80;
	
	public void tick() {
		interval++;
		if(interval >= maxInterval ) {
			Game.enemys.add(new Enemy());
			interval=0;
		}
	}
	
//---------------------------------------------

}
