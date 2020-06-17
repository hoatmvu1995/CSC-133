package com.mycompany.a3;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class AntTurnRight extends Command{
	GameWorld gw;
	
	public AntTurnRight(GameWorld gw) {
		super("AntTurnRight");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		gw.turnRight();
	}
}
