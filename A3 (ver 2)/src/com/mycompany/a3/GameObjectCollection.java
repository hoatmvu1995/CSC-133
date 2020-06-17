package com.mycompany.a3;

import com.mycompany.a3.ICollection;
import com.mycompany.a3.IIterator;
import com.mycompany.a3.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Design Patter: Iterator
 * A collection of GameObject that store the GameObjects inside an ArrayList.
 */
public class GameObjectCollection implements ICollection<GameObject>{
	private List<GameObject> gameObjects = new ArrayList<>();
	
	private class Iterator implements IIterator<GameObject> {
		private int next = 0;
		
		@Override
		public boolean hasNext() {
			return (next < gameObjects.size());
		}
		
		@Override
		public GameObject getNext() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			return gameObjects.get(next++);
		}
	}
	
	@Override
	public void add(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	@Override
	public IIterator<GameObject> getIterator() {
		return new Iterator();
	}
}
