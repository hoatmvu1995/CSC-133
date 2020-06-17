package com.mycompany.a3;

import com.codename1.charts.models.Point;

public interface ISelectable {
	public boolean isSelected();
	public void setSelected(boolean selected);
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
}
