package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.charts.util.ColorUtil;
import com.mycompany.a3.Tool;

public final class Ant extends MovableGameObject implements ISteerable, IDrawable, ICollider{ 
	private static final int DAMAGE = 5;
	private static final int ANT_SIZE = 20;
	private static final int CONSUMPTION_RATE = 2;
	private static final int MAX_SPEED = 10;
	private static final int INITIAL_FOOD = 1000;
	private static final int INITIAL_DIRECTION = 0;
	private static final int INITIAL_HEALTH = 10;
	private static final int INITIAL_SPEED = 5;
	private static final int INITIAL_ANT_COLOR = ColorUtil.rgb(255, 0, 0);
	
	private int foodLevel = INITIAL_FOOD;
	private int healthLevel = INITIAL_HEALTH;
	private int lastFlagReached;
	private int speedLimit;
	
	private static Ant instance = new Ant(Tool.CENTER_SCREEN);
	
	private Ant(Point location) {
		setLocation(location);
		calculateSpeedLimit();
		size = ANT_SIZE;
		color = INITIAL_ANT_COLOR;
		heading = INITIAL_DIRECTION;
		speed = INITIAL_SPEED;
		lastFlagReached = 1;
	}
	
	/*
	 * Calculate the ant's color based on the health level.
	*/
	private void calculateColor() {
		color = ColorUtil.rgb(healthLevel * 20, 0, 0);
	}
	
	/*
	 * Calculate the ant's speed, base on current speed of the ant;
	*/
	private void calculateSpeed() {
		setSpeed(speed);
	}
	
	
	/*
	 * Calculate the speed of the ant base on its health
	*/
	private void calculateSpeedLimit() {
		float speedLimitChanging = (float) (1f / INITIAL_HEALTH) * healthLevel;
		
		speedLimit = Math.round(speedLimitChanging * MAX_SPEED);
		speedLimit = Math.min(speedLimit, MAX_SPEED);
	}
	
	
	/*
	 * reset the health level of the ant whenever the lives reduce by 1. 
	*/
	public void resetHealthLevel() {
		
		if (healthLevel == 0) {
			this.healthLevel = INITIAL_HEALTH;
		}
		
	}
	
	public void resetFoodLevel() {
		if (foodLevel == 0) {
			this.foodLevel = INITIAL_FOOD;
		}
	}
	
	public int getConsumeRate() {
		return CONSUMPTION_RATE;
	}
	/*
	 * Design Patter: Singleton
	 * @return only one instance of Ant.
	*/
	public static Ant getInstance() {
		return instance;
	}

	
	/*
	 * Reduce the food level by its consumpsion rate
	 * The food level can't never be negative
	*/
	public void foodConsumeRate() {
		foodLevel = foodLevel - CONSUMPTION_RATE;
		foodLevel = Math.max(foodLevel, 0);
	}
	
	public int getFood() {
		return foodLevel;
	}
	
	public int getHealthLevel() {
		return healthLevel;
	}
	
	/*
	 *@return the flag number where the ant has reached
	*/
	public int getLastFlagReached() {
		return lastFlagReached;
	}
	
	/*
	 * Everytime, the ant collide to the next flag, this assume that the next flag will 
	 * be the next consecutive order. For example, the ant was at Flag 1, when the ant, 
	 * collide to a next flag, it assume the next flag is Flag 2. 
	*/
	public void collideToNextFlag(int flag) {
		if (flag == getLastFlagReached() + 1) {
			this.lastFlagReached = flag;
		}
	}
	
	
	/*
	 * the ant's health level will be reduced by certain amount of damage.
	*/
	public void colldieToSpider() {
		setHealth(healthLevel - DAMAGE);
	}
	
	/*
	 * Mutator with a validation that the food level can't be lower than 0
	*/
	public void setFoodLevel(int food) {
		this.foodLevel = Math.max(food, 0);
	}
	
	/*
	 * Mutator with a validation that the health level can't be lower than 0
	 * Calculate the speed, speedLimit, and the color of the Ant base on the health
	*/
	public void setHealth(int health) {
		this.healthLevel = Math.max(health, 0);
		
		calculateSpeedLimit();
		calculateSpeed();
		calculateColor();
	}
	
	
	@Override
	public void setHeading(int heading) {
		this.heading = heading;
	}
		

	/*
	 * set the Ant's speed
	 * validation that make sure the speed can't be lower than 0
	 * lock the speed into a specific range, where the an't speed can't be larger than speed limit.
	*/
	@Override
	public void setSpeed(int speed) {
		speed = Math.max(speed, 0);
		
		this.speed = Math.min(speed,  speedLimit);
	}
	
	@Override
	public String toString()
	{
		return "Ant: "
				+ super.toString()
				+ ", speedLimit=" + speedLimit
				+ ", maxSpeed=" + MAX_SPEED
				+ ", health=" + healthLevel
				+ ", foodConsumptionRate=" + CONSUMPTION_RATE;
	}
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		
		int xLoc = (int)  (this.getLocation().getX() + pCmpRelPrnt.getX() - this.getSize()/2);
		int yLoc = (int)  (this.getLocation().getY() + pCmpRelPrnt.getY() - this.getSize()/2);
		
		g.fillArc(xLoc, yLoc, this.getSize()*5, this.getSize()*5, 0, 360);
		
	}

	@Override
	public boolean collidesWith(ICollider otherObjects) {
		boolean result = false;
		
		GameObject temp = (GameObject)otherObjects;
		int thisCenterX = (int) this.getLocation().getX() + this.getSize()/2;
		int thisCenterY = (int) this.getLocation().getY() + this.getSize()/2;
		int otherCenterX = (int) temp.getLocation().getX() + temp.getSize()/2;
		int otherCenterY = (int) temp.getLocation().getX() + temp.getSize()/2;
		int dx = thisCenterX - otherCenterX;
		int dy = thisCenterY - otherCenterY;
		int distance = (dx*dx + dy*dy);
		int thisRadius = this.getSize()/2;
		int otherRadius = temp.getSize()/2;
		int radiSquare = thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius;
		if (distance <= radiSquare) {
			result = true;
		}
		
		return result;
	}

	@Override
	public void handleCollision(ICollider otherObjects, GameWorld gw) {
		if (otherObjects instanceof Spider) {
			 gw.collidingWithSpider();
		} else if (otherObjects instanceof Flag) {
			gw.collideWithFlag(((Flag) otherObjects).getSequenceNumber());
		} else if (otherObjects instanceof FoodStation) {
			gw.collidingWithFoodStation();
		}
		
	}
}


