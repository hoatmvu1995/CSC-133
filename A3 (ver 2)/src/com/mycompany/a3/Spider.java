package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.Tool;


/**
 * class represent a spider instance object with black color
 * random location, random heading, random speed, and random size
 */
public final class Spider extends MovableGameObject implements IDrawable, ICollider{
	
	public Spider() {
		color = ColorUtil.rgb(0,0,0);
		setLocation(Tool.randomLocation());
		heading = Tool.rangeIntGenerating(0,  359);
		speed = Tool.rangeIntGenerating(5, 10);
		setRandomSize();
	}
	
	/**
	 * spider is also a movable object, so it also inherited the ability to move
	 * spider has their own way of moving around. This move method contraint 
	 * how the spider move within the screeen resolution
	 */
	@Override
	public void move() {
		heading = heading + Tool.rangeIntGenerating(-5, 5);
		super.move();
		
		if (!Tool.isWithinDefaultResolution(location)) {
			location = Tool.lockToResolution(location);
			
			heading -= 180;
		}
	}
	
	/**
	 * has only one color
	 */
	@Override
	public final void setColor(int color) {}
	
	@Override
	public String toString() {
		return "Spider: " + super.toString();
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		//top part
		int xLoc = (int) (pCmpRelPrnt.getX() + this.getLocation().getX());
		int yLoc = (int) (pCmpRelPrnt.getY() + (int) this.getLocation().getY() + getSize()/2);
		
		//left part
		int xLeftLoc = (int) (this.getLocation().getX() + pCmpRelPrnt.getX() - getSize()/2);
		int yLeftLoc = (int) (this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()/2);
		
		//right part
		int xRightLoc = (int) (this.getLocation().getX() + pCmpRelPrnt.getX() + getSize()/2);
		int yRightLoc = (int) (this.getLocation().getY() + pCmpRelPrnt.getY() - getSize()/2);
		
		Point top = new Point(xLoc, yLoc);
		Point bottomLeft = new Point(xLeftLoc, yLeftLoc);
		Point bottomRight = new Point(xRightLoc, yRightLoc);
		
		int[] x = new int[] {(int) top.getX(), (int) bottomLeft.getX(), (int) bottomRight.getX()};
		int[] y = new int[] {(int) top.getY(), (int) bottomLeft.getY(), (int) bottomRight.getY()};
		g.setColor(this.getColor());
		
		g.drawPolygon(x, y, 3);
		
		
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
		// TODO Auto-generated method stub
		
	}
	
}
