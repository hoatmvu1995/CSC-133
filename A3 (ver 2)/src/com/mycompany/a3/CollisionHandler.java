package com.mycompany.a3;

import java.util.Vector;

import com.mycompany.a3.GameWorld;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.GameObjectCollection;

import com.mycompany.a3.ICollider;
import com.mycompany.a3.IIterator;

public class CollisionHandler {
	private Vector<CollisionObject> list;
	//private GameSound sound;
	private GameObjectCollection goc;
	private GameWorld gw;
	
	public CollisionHandler(GameObjectCollection goc, GameWorld gw) {
		this.goc = goc;
		//this.sound = sound;
		this.gw = gw;
		
		list = new Vector<CollisionObject>();
	}
	
	public void removeCollisionList() {
		int temp = list.size();
		
		for(int i = 0; i < temp; i++) {
			list.remove(0);
		}
	}
	
	public int getSize() {
		return list.size();
	}
	
	public void checkCollisions() {
		IIterator<GameObject> it = goc.getIterator();
		
		while (it.hasNext()) {
			GameObject temp = it.getNext();
			
			if (temp instanceof ICollider) {
				ICollider first = (ICollider) temp;
				IIterator<GameObject> collide = goc.getIterator();
				
				while(collide.hasNext()) {
					GameObject temp2 = collide.getNext();
					
					if (temp2 instanceof ICollider) {
						ICollider second = (ICollider) temp2;
						
						if (first.collidesWith(second) && first != second) {
							if (first instanceof Spider && second instanceof Spider) {
								continue;
							} else {
								CollisionObject collision = new CollisionObject(first, second);
								
								boolean repeat = false;
								
								for (int j = 0; j < list.size(); j++) {
									if (collision.equals(list.get(j))) {
										repeat = true;
									}
								}
								
								if (!repeat) {
									list.add(collision);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void handleCollision() {
		boolean x;
		boolean y;
		for (int i = 0; i < list.size(); i++) {
			x = list.get(i).getFirst().collidesWith(list.get(i).getSecond());
			y = !list.get(i).getFirst().equals(list.get(i).getSecond());
			
			if (x && y) {
				list.get(i).getFirst().handleCollision(list.get(i).getSecond(), gw);
				
				if (list.size() > 0) {
					list.remove(i);
				}
			}
		}
	}
}
