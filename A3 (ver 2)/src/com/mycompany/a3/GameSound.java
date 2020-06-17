package com.mycompany.a3;

public class GameSound {
	private Sound eating, cheering, ending;	
	private BGSound music;
	private boolean play;
	
	public GameSound(){
		eating = new Sound("eating.wav");
		cheering = new Sound("cheering.wav");
		ending = new Sound("ending.mp3");
		music = new BGSound("background.mp3");
		play = true;
	}

	public void soundToggle() {
		play = !play;
		if (play == false){
			pauseMusic();
		}else {
			playMusic();
		}
	}
	
	public void setSound() {
		play = !play;
	}
	
	public boolean getSound() {
		return play;
	}
	
	public void playMusic() {
		if(play){
			music.play();
		}		
	}

	public void pauseMusic() {
		music.pause();	
	}
	
	public void eatSound() {
		if (play) {
			eating.play();
		}
	}
	
	public void cheeringSound() {
		if (play) {
			cheering.play();
		}
	}
	
	public void endingSound() {
		if (play) {
			ending.play();
		}
	}
}
