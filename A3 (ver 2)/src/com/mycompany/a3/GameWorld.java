package com.mycompany.a3;

import com.codename1.charts.models.Point;
import java.util.Observable;

/**
 * GameWorld is the most important Class of the Bugz Games
 * It's maintain the all states of the GameWork, and also
 * generate many functionality to the game.
 */
public class GameWorld extends Observable implements IGameWorld{
	private static final int SPEED_INCREASE = 1;
	private static final int HEADING_INCREASE = 5;
	private static final int INITIAL_LIVES = 3;
	private static final int SCREEN_WIDTH = 1024;
	private static final int SCREEN_HEIGHT = 768;
	
	private Clock clock = new Clock();
	private int antLives = INITIAL_LIVES;
	private int width = SCREEN_WIDTH;
	private int height = SCREEN_HEIGHT;
	
	private GameObjectCollection gameObjects;
	private Ant ant;
	private boolean paused;
	private GameSound gameSound;
	private CollisionHandler collisionHandler;
	
	
	/**
	 * Call for initial state of the game
	 */
	public GameWorld() {
		init();
	}
	
	/**
	 * create only instance of ant, and add the necessary components to the bugz game collection
	 * and notify observer that these components are currently exist within the game.
	 */
	public void init() {
		collisionHandler = new CollisionHandler(gameObjects, this);
		
		ant = Ant.getInstance();
		ant.resetHealthLevel();
		
		paused = false;
		
		gameObjects = new GameObjectCollection();
		gameObjects.add(ant);
		gameObjects.add(new Flag(1, new Point(120, 550), this));
		gameObjects.add(new Flag(2, new Point(450, 25), this));
		gameObjects.add(new Flag(3, new Point(950, 220), this));
		gameObjects.add(new Flag(4, new Point(800, 700), this));
		gameObjects.add(new Spider());
		gameObjects.add(new Spider());
		gameObjects.add(new FoodStation(this));
		gameObjects.add(new FoodStation(this));
		gameSound = new GameSound();
		
		notifyObservers();
	}
	

	//===============================================================================================================================
	//==================================NewLy Implemented for A3=====================================================================
	//===============================================================================================================================
	/**
	 * every time the time tick, the food will be reduce by a certain amount
	 * the ant also move by certain distant base on the speed and the heading
	 */
	public void clockTicking() {
		clock.tick();
		
		ant.foodConsumeRate();
		
		if (ant.getFood() == 0) {
			System.out.println("The ant starved to death.");
			
			dying();
		}
		
		IIterator<GameObject> it = gameObjects.getIterator();
		
		while (it.hasNext()) {
			GameObject object = it.getNext();
			
			if (object instanceof MovableGameObject) {
				((MovableGameObject) object).move();
			}
			
		}
		
		collisionHandler.checkCollisions();
		collisionHandler.handleCollision();
		
		
		
		notifyObservers();
	}
	//================================Sound && GamePause=================================
	/**
	 * Game pause
	 */
	public void pauseGame() {
		paused = true;
		gameSound.pauseMusic();
		
		IIterator<GameObject> it = gameObjects.getIterator();
		
		while (it.hasNext()) {
			GameObject object = it.getNext();
			
			if (object instanceof ISelectable) {
				((ISelectable) object).setSelected(false);
			}
		}
		
		notifyObservers();
	}
	
	/**
	 * Game resume
	 */
	public void resumeGame() {
		paused = false;
		gameSound.playMusic();
		
		IIterator<GameObject> it = gameObjects.getIterator();
		
		while (it.hasNext()) {
			GameObject object = it.getNext();
			
			if (object instanceof ISelectable) {
				((ISelectable) object).setSelected(false);
			}
		}
		
		notifyObservers();
	}
	
	
	public boolean getPaused() {
		return paused;
	}
	
	public void setSound() {
		if (getPaused()) {
			gameSound.setSound();
		} else {
			gameSound.soundToggle();
		}
		
		notifyObservers();
	}
	
	public boolean getSound() {
		return gameSound.getSound();
	}
	//===============================Paused Game Mode with Selected Option==============================
	public void selectObject(Point pressed, Point origin) {
		IIterator<GameObject> it = gameObjects.getIterator();
		
		while(it.hasNext()) {
			GameObject object = it.getNext();
			
			boolean selected = ((ISelectable) object).contains(pressed,  origin);
			
			if (selected) {
				((ISelectable) object).setSelected(true);
			} else {
				((ISelectable) object).setSelected(false);
			}
		}
		
		notifyObservers();
	}
	
	//========================================================================================================================

	/**
	 * inrease the speed of the ant and notify the observerr
	 */
	public void accelerate() {
		ant.setSpeed(ant.getSpeed() + SPEED_INCREASE);
		notifyObservers();
	}
	
	/**
	 * Reduce the speed of the ant and notify the observer
	 */
	public void brake() {
		ant.setSpeed(ant.getSpeed() - SPEED_INCREASE);
		notifyObservers();
	}
	
	/**
	 * reduce lives by 1 every time the foodLevel or the healthLevel reached 0
	 * once the lives reached 0, the game is over.
	 * notify the observer whenever there is a change in the game
	 */
	public void dying() {
		antLives--;
		
		if (antLives < 0) {
			System.out.println("Game over, you failed!");
			System.exit(0);
		}
		
		ant.resetFoodLevel();
		notifyObservers();
	}
	
	
	/**
	 * colliding to the next flag
	 * if reached the last flag, then the game is end
	 * notify the observer
	 * @param flag 
	 */
	public void collideWithFlag(int flag) {
		ant.collideToNextFlag(flag);
		
		if (ant.getLastFlagReached() == 5) {
			System.out.println("Game over, you win! Total time: " + clock.getTime());
			System.exit(0);
		}
		
		notifyObservers();
	}
	
	
	/**
	 * Ant will collide to the food station
	 * The food level would increase by certain amount
	 * notify the observer and breakout of the loop
	 */
	public void collidingWithFoodStation() {
		IIterator<GameObject> it = gameObjects.getIterator();
		
		while (it.hasNext()) {
			int eatingRate;
			GameObject object = it.getNext();
			
			if (object instanceof FoodStation && ((FoodStation) object).getCapacity() > 0) {
				ant.setFoodLevel(ant.getFood() + ((FoodStation) object).getCapacity());
				
				eatingRate = ((FoodStation) object).getCapacity() - ant.getConsumeRate();
				((FoodStation) object).setCapacity(eatingRate);
				
				if (((FoodStation) object).getCapacity() == 0) {
					gameObjects.add(new FoodStation(this));
				}
				
				
				notifyObservers();
				return;
			}
		}
	}
	
	
	/**
	 * collide to the spider
	 * the ant's health level will be impacted by cetain amount of damage
	 * notify the observer about these damages.
	 */
	public void collidingWithSpider() {
		ant.colldieToSpider();
		
		if (ant.getHealthLevel() == 0) {
			System.out.println("The ant was beaten to death by the spider.");
			dying();
		}
		
		notifyObservers();
	}
	
	
	/**
	 * ant turn left, change the heading however it suppose to be
	 */
	public void turnLeft() {
		ant.setHeading(ant.getHeading() - HEADING_INCREASE);
		notifyObservers();
	}
	
	
	/**
	 * ant turn right, change the heading however it suppose to be
	 */
	public void turnRight() {
		ant.setHeading(ant.getHeading() + HEADING_INCREASE);
		notifyObservers();
	}
	//===========================================================================================================================================
	
	/**
	 * Iterator through the whole collection of game object
	 * Print the map info.
	 */
	public void printMap()
	{
		IIterator<GameObject> it = gameObjects.getIterator();

		while (it.hasNext())
		{
			GameObject obj = it.getNext();

			System.out.println(obj);
		}
		//notifyObservers();
	}
	
	
	//================================Implement for IGameWorld and GameWorldProxy================================================
	public Ant getAnt() {
		return ant;
	}

	
	public int getAntLives() {
		return antLives;
	}

	
	public int getClock() {
		return clock.getTime();
	}
	
	public int getFoodLevel() {
		return ant.getFood();
	}
	
	
	public int getHealthLevel() {
		return ant.getHealthLevel();
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public void setDimension(int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	
	@Override
	public void getDimension() {
		System.out.println("--- Updated Map Width: " + getWidth() + "---Updated Map Height: " + getHeight() + " ---");
	}
	
	@Override
	public GameObjectCollection getGameObjects() {
		return gameObjects;
	}
	
	/**
	 * implement for notifyObserver.
	 */
	@Override 
	public void notifyObservers() {
		GameWorldProxy proxy = new GameWorldProxy(this);
		setChanged();
		super.notifyObservers(proxy);
	}
	
}
