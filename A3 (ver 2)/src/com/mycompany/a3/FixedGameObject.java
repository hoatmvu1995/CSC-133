package com.mycompany.a3;
import com.codename1.charts.models.Point;

public abstract class FixedGameObject extends GameObject
{
	/**
	 * The fixed game objects can't be moved. Therefore, the setLocation()
	 * won't do anything if someone want to change the location of fixed
	 * game objects.
	 */
	@Override
	public final void setLocation(Point location) {}
}
