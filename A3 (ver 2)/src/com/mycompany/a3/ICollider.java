package com.mycompany.a3;

public interface ICollider {
	public boolean collidesWith(ICollider second);
	public void handleCollision(ICollider otherObjects, GameWorld gw);
}
