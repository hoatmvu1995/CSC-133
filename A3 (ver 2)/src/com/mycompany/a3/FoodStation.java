package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.FixedGameObject;
import com.mycompany.a3.Tool;


/*
 * The class is use to represent the food station instance object
 * The food station is extends from the FixGameObject, so it's can't be move
 * There will be some certain attributes, the has been defaultly provided
*/
public final class FoodStation extends FixedGameObject implements IDrawable, ICollider, ISelectable{
	private static final int FOODSTATION_COLOR = ColorUtil.rgb(0, 255, 0);
	private static final int MIN_CAPACITY = 10;
	private static final int MAX_CAPACITY = 50;
	
	private int capacity;
	private GameWorld gw;
	private boolean selected;
	
	/*
	 * the location of the food station will be generated randomly, BUT 
	 * it will be lock to the screen resolution.
	 * The size and the capacity of Food Station will be generated randomly 
	 * based on default values.
	*/
	public FoodStation(GameWorld gw) {
		location = Tool.randomLocation();
		size = this.capacity = Tool.rangeIntGenerating(MIN_CAPACITY, MAX_CAPACITY);
		color = FOODSTATION_COLOR;
		this.gw = gw;
		selected = false;
	}
	
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = Math.max(capacity, 0);
	}
	
	@Override
	public String toString() {
		return "Food Station: " + super.toString() + ", capacity= " + capacity;
	}


	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		String capacityString = "" + this.capacity;
		int xLoc = (int)(pCmpRelPrnt.getX() + this.getLocation().getX() - getSize()/2);
		int yLoc = (int)(pCmpRelPrnt.getY() + this.getLocation().getY() - getSize()/2);
		
		g.setColor(this.getColor());
		
		if (selected && gw.getPaused()) {
			g.drawRect(xLoc, yLoc, getSize(), getSize());
		} else {
			g.fillRect(xLoc, yLoc, getSize(), getSize());
		}
		
		
		g.setColor(0xffffff);
		g.drawString(capacityString, xLoc, yLoc);
		
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


	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return selected;
	}


	@Override
	public void setSelected(boolean selected) {
		if (gw.getPaused()) {
			this.selected = selected;
		}
		
	}


	@Override
	public boolean contains(Point pPtrRePrnt, Point pCmpRelPrnt) {
		boolean ans = false;
		
		int x = (int) pPtrRePrnt.getX();
		int y = (int) pPtrRePrnt.getY();
		int xLoc = (int) pCmpRelPrnt.getX() + (int)getLocation().getX();
		int yLoc = (int) pCmpRelPrnt.getY() + (int)getLocation().getY();
		if( (x >= xLoc - getSize()/2) && (x <= xLoc + getSize()/2) && (y >= yLoc - getSize()/2) && (y <= yLoc + getSize()/2)) {
			ans = true;
		}
		return ans;
	}
}
