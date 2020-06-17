package com.mycompany.a3;
import com.codename1.charts.models.Point;


/**
 * Represents a game object that can be move like ant
 */
public abstract class MovableGameObject extends GameObject{
	protected int heading;
	protected int speed;
	
	/**
	 * get a direction where the object turn into
	 */
	public int getHeading() {
		return heading;
	}
	
	/**
	 * get the speed of the object
	 */
	public int getSpeed() {
		return speed;
	}
	
	/**
	 * utilize the speed and the direction. the movable game object can be change to specific
	 * location. 
	 */
	public void move() {
		double angle = Math.toRadians(90 - heading);
		
		float deltaX = (float)Math.cos(angle) * speed;
		float deltaY = (float)Math.sin(angle) * speed;
		
		setLocation(new Point(location.getX() + deltaX, location.getY() + deltaY));
	}
	
	@Override
	public String toString()
	{
		return super.toString() + ", speed=" + speed + ", heading=" + heading;
	}
}
