package com.mycompany.a3;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.Tool;

/*
 * The abstract class Game Objects that pass down its properties to 
 * all game objects in the bugz games. 
 * 
*/
public abstract class GameObject {
	private static final int MAXIMUM_SIZE = 50;
	private static final int MINIMUM_SIZE = 10;
	private static final float MIN_X = Tool.MN_X;
	private static final float MIN_Y = Tool.MN_Y;
	private static final float MAX_X = Tool.MX_X;
	private static final float MAX_Y = Tool.MX_Y;
	
	//protected properties that have on every main component game objects
	protected int color;
	protected Point location;
	protected int size;
	
	protected void setRandomSize() {
		size = Tool.rangeIntGenerating(MINIMUM_SIZE, MAXIMUM_SIZE);
	}
	
	//accessor and mutator
	public int getColor() {
		return color;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setColor(int color) {
		this.color = color;
	}
	
	/**
	 * set location to the game object
	 * 
	 * validation to make sure that the location can't be generated outside of the screen resolution.
	 */
	public void setLocation(Point location) {
		this.location = location;
		
		if (location.getX() < MIN_X) {
			location.setX(MIN_X);
		} 
		
		if (location.getY() < MIN_Y) {
			location.setY(MIN_Y);
		}
		
		if (location.getX() > MAX_X) {
			location.setX(MAX_X);
		}
		
		if (location.getY() > MAX_Y) {
			location.setY(MAX_Y);
		}
	}
	
	/**
	 * to generate a string color
	 */
	private String getColorString() {
		return "[" + ColorUtil.red(color) + "," + ColorUtil.green(color) + "," + ColorUtil.blue(color) + "]";
	}
	
	@Override
	public String toString()
	{
		return "size=" + size
				+ ", color=" + getColorString()
				+ ", location=" + Tool.pointToString(location);
	}
}
