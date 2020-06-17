package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameButton;

public class GamePause extends Command{
	private Game g;
	private GameButton pauseButton;
	private boolean paused;
	
	public GamePause(Game g, GameButton pButton) {
		super("Pause");
		this.pauseButton = pButton;
		this.g = g;
		paused = false;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getKeyEvent() != -1) {
			if (paused) {
				pauseButton.setText("Pause");
				g.resume();
			} else {
				pauseButton.setText("Play");
				g.pause();
			}
		}
		paused = !paused;
		
	}
}
