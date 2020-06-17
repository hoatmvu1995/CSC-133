package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.mycompany.a3.Tool;

/*
 * Flag class to represent the flag objects with certain default properties such as color and size
 * Also, it has a sequenceNumber to represent itself 
*/
public final class Flag extends FixedGameObject implements IDrawable, ICollider, ISelectable{
	private static final int SIZE = 50;
	private static final int FLAG_COLOR = ColorUtil.rgb(0, 0, 255);
	
	private int sequenceNumber;
	private boolean selected;
	private GameWorld gw;
	/*
	 * the instance flags are created with a given sequence Order and location
	 * the location is specific by the developer, and it will be validate 
	 * however, it is to be fit within the screen resolution
	*/
	public Flag(int sequenceNumber, Point location, GameWorld gw) {
		this.location = Tool.lockToResolution(location);
		this.color = FLAG_COLOR;
		this.sequenceNumber = sequenceNumber;
		this.size = SIZE;
		selected = false;
		this.gw = gw;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}
	
	/*
	 * the color of the flag can't be changed by anyone.
	*/
	@Override
	public final void setColor(int color) {}
	
	@Override
	public String toString() {
		return "Flag: " + super.toString() + ", seqNumber=" + sequenceNumber;
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		String flagNumString = "" + sequenceNumber;
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
		
		if (selected && gw.getPaused()) {
			g.drawPolygon(x, y, 3);
		} else {
			g.fillPolygon(x, y, 3);
		}
		

		
		g.setColor(0xffffff);
		g.drawString(flagNumString, xLoc, yLoc);
		
		
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
