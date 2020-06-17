package com.mycompany.a3;

/*
 * It's an imagination of simulation of time.
 * It's not base on the real concept of time, but it a way to simulate the seconds 
 * that passing by. Therefore, the game certainly changes in some aspects.
*/
public final class Clock {
	private int time = 0;
	
	/*
	 * @return the click time
	*/
	public int getTime() {
		return time;
	}
	
	/*
	 * time increased by every click
	*/
	public void tick() {
		time+=1;
	}
}
