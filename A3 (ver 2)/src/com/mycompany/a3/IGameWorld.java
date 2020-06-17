package com.mycompany.a3;

import com.mycompany.a3.GameObjectCollection;
import com.mycompany.a3.Ant;

public interface IGameWorld {
	public Ant getAnt();
	public int getAntLives();
	public int getClock();
	public boolean getSound();
	public int getFoodLevel();
	public int getHealthLevel();
	public void getDimension();
	public void setDimension(int width, int height);
	public GameObjectCollection getGameObjects();
	public void printMap();
	void clockTicking();
	
}
