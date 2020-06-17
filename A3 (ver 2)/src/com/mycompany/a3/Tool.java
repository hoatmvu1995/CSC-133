package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.models.Point;


/**
 * The main purpose of this class to generate some general function that is not
 * important inside the Game. However, it will make the code in other class look 
 * cleaner and not misleading the purpose of the other classes in the Bugz Game.
 * This is like a short hand tools to generate some values that has a contraint 
 * specify by the bugz game
 */
public final class Tool {
	public static final float MN_X = 0;
	public static final float MN_Y = 0;
	public static final float MX_X = 1024;
	public static final float MX_Y = 768;
	public static final Point CENTER_SCREEN = new Point((MN_X + MX_X) / 2, (MN_Y + MX_Y) / 2); 

	/**
	 * lock the number to specific range
	 */
	public static final float lockToRange(float num, float min, float max) {
		num = Math.max(num,  min);
		
		return Math.min(num,  max);
	}
	
	/**
	 * lock the coordinate to the screeen resolution
	 */
	public static Point lockToResolution(Point point) {
		float x = lockToRange(point.getX(), MN_X, MX_X);
		float y = lockToRange(point.getY(), MN_Y, MX_Y);
		
		return new Point(x, y);	
	}
	
	/**
	 * to answer if true or false that the coordinate is within the screen resolution
	 */
	public static boolean isWithinDefaultResolution(Point point)
	{
		return point.getX() >= MN_X && point.getX() <= MX_X &&
			   point.getY() >= MN_Y && point.getY() <= MX_Y;
	}
	

	public static String pointToString(Point point)
	{
		return "(" + (Math.round(point.getX() * 10.00) / 10.0) + ", " + (Math.round(point.getY() * 10.0) / 10.0) + ")";
	}
	
	/**
	 * generate float number in range
	 */
	public static float rangeFloatGenerating(float lowerBound, float upperBound)
	{
		return (float) new Random().nextDouble() * (upperBound - lowerBound) + lowerBound;
	}
	

	/**
	 * generate int number in range
	 */
	public static int rangeIntGenerating(int lowerBound, int upperBound)
	{
		return new Random().nextInt(upperBound - lowerBound) + lowerBound;
	}
	
	/**
	 * generate random location base on the range
	 */
	public static Point randomLocation()
	{
		return new Point(rangeFloatGenerating(MN_X, MX_X), rangeFloatGenerating(MN_Y, MX_Y));
	}
}


