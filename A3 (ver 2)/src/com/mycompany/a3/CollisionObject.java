package com.mycompany.a3;

public class CollisionObject {
	private ICollider first, second;
	
	public CollisionObject(ICollider first, ICollider second) {
		this.first = first;
		this.second = second;
	}
	
	
	@Override
	public boolean equals(Object o) {
		boolean ans = false;
		
		if (o instanceof CollisionObject) {
			CollisionObject object = (CollisionObject) o;
			
			if (first.equals(object.getFirst()) && second.equals(object.getSecond()) || 
					first.equals(object.getSecond()) && second.equals(object.getFirst())) {
				ans = true;
			}
		}
		
		return ans;
	}
	
	public ICollider getFirst() {
		return first;
	}
	
	public ICollider getSecond() {
		return second;
	}
}
