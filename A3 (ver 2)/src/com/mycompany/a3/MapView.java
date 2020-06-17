package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.plaf.Border;
import com.mycompany.a3.IGameWorld;

import java.util.Observable;
import java.util.Observer;

public class MapView extends Container implements Observer{
	private Point pCmpRelPrnt;
	private IGameWorld gw;
	
	public MapView() {
		
		getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.rgb(255, 0, 0)));
	}
	
    @Override
    public void update(Observable o, Object data)
    {
    	pCmpRelPrnt = new Point(this.getX(), this.getY());
    	
        gw = (IGameWorld) data;
        gw.setDimension(this.getWidth(), this.getHeight());
        gw.printMap();
        
        repaint();
        
    }
    
    @Override 
    public void paint(Graphics g) {

    	super.paint(g);
    	
    	IIterator<GameObject> it = gw.getGameObjects().getIterator();
    
    	while (it.hasNext()) {
    		GameObject object = it.getNext();
    		
    		if (object instanceof IDrawable) {
    			((IDrawable) object).draw(g, pCmpRelPrnt);
    		}
    	}
    }
    
    @Override
    public void pointerPressed(int x, int y) {
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		Point pPtrRelPrnt = new Point(x, y);
		Point pCmpRelPrnt = new Point(getX(), getY());
		
		IIterator<GameObject> it = gw.getGameObjects().getIterator();
		
		while (it.hasNext()) {
			GameObject object = it.getNext();
			
			if (object instanceof ISelectable) {
				if (((ISelectable) object).contains(pPtrRelPrnt, pCmpRelPrnt)) {
					((ISelectable) object).setSelected(true);
				} else {
					((ISelectable) object).setSelected(false);
				}
			}
		}
		
		repaint();
    }

}
