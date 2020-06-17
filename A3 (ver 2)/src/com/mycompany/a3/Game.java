package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;

import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.AntAccelerate;
import com.mycompany.a3.AntBrake;
import com.mycompany.a3.AntTurnLeft;
import com.mycompany.a3.AntTurnRight;
import com.mycompany.a3.ExitCmd;
import com.mycompany.a3.HelpCmd;
import com.mycompany.a3.InfoCmd;
import com.mycompany.a3.SoundCmd;
import com.mycompany.a3.GameWorld;
import com.mycompany.a3.MapView;
import com.mycompany.a3.ScoreView;
import com.mycompany.a3.GameButton;

/**
 * The game controller.
 * 
 * Manages the game world state, and interprets user input.
 */
public final class Game extends Form implements Runnable{
	private GameButton accelerateButton, leftButton, rightMargin, brakeButton, rightButton;
	private AntAccelerate accelerateCmd;
	private AntBrake brakeCmd;
	private AntTurnLeft turnLeftCmd;
	private AntTurnRight turnRightCmd;
	private ExitCmd exitCmd;
	private SoundCmd soundCmd;
	private InfoCmd aboutCmd;
	private HelpCmd	helpCmd;
	private GamePause pauseCmd;
	
	private ScoreView sv;
	private MapView mv;
	private GameWorld gw;
	private UITimer timer;
	public Game()
	{
		/**
		 * create instance of gameworld and its observer
		 */
		gw = new GameWorld();
		mv  = new MapView();
		sv = new ScoreView();
		timer = new UITimer(this);
		
		gw.addObserver(sv);
		gw.addObserver(mv);
		gw.init();
		timer.schedule(120, true, this);
		
		/**
		 * instantiate the commands for the bugz game
		 */
		accelerateCmd = new AntAccelerate(gw);
		brakeCmd = new AntBrake(gw);
		turnLeftCmd = new AntTurnLeft(gw);
		turnRightCmd = new AntTurnRight(gw);
		soundCmd = new SoundCmd(gw);
		exitCmd = new ExitCmd();
		aboutCmd = new InfoCmd();
		helpCmd = new HelpCmd();
		
		
		
		/**
		 * set a layout for the GUI function
		 */
		setLayout(new BorderLayout());
		
		
		/**
		 * layout the left margin on the WEST of the screen
		 * button to accelerate
		 * button to turn left
		 */
		//===================left--Margin============================
		Container leftMargin = new Container();
		leftMargin.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		
		accelerateButton = new GameButton("Accelerate");
		accelerateButton.getAllStyles().setMarginTop(100);
		accelerateButton.addActionListener(accelerateCmd);
		leftMargin.addComponent(accelerateButton);
		addKeyListener('a', accelerateCmd);
		
		leftButton = new GameButton("Left");
		leftButton.addActionListener(turnLeftCmd);
		leftMargin.addComponent(leftButton);
		addKeyListener('l', turnLeftCmd);
			
		addComponent(BorderLayout.WEST, leftMargin);
		leftMargin.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.rgb(211, 211, 211)));	
		
		
		/**
		 * layout the right margin in the East of the screen
		 * button to Brake
		 * button to Turn Right
		 */
		//====================right--Margin==========================
		Container rightMargin = new Container();
		rightMargin.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
			
		brakeButton = new GameButton("Brake");
		brakeButton.getAllStyles().setMarginTop(100);
		brakeButton.addActionListener(brakeCmd);
		rightMargin.addComponent(brakeButton);
		addKeyListener('b', brakeCmd);
			
			
		rightButton = new GameButton("Right");
		rightButton.addActionListener(turnRightCmd);
		rightMargin.addComponent(rightButton);
		addKeyListener('r', turnRightCmd);
			
		addComponent(BorderLayout.EAST, rightMargin);
		rightMargin.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.rgb(211, 211, 211)));	
		
		
		/**
		 * layout the bottom margin to the south of the sreeen
		 * Button to Collide with the Spider
		 * Button to Collide with the Flag
		 * Button to Collide with the Food Stations
		 * Button to for clock ticking 
		 */
	
		Container bottomMargin = new Container();	
		bottomMargin.setLayout(new FlowLayout(Component.CENTER));
		
		GameButton positionButton = new GameButton("Position");
		bottomMargin.addComponent(positionButton);
		
		GameButton pauseButton = new GameButton("Pause");
		pauseCmd = new GamePause(this, pauseButton);
		pauseButton.setCommand(pauseCmd);
		bottomMargin.addComponent(pauseButton);
		
		addComponent(BorderLayout.SOUTH, bottomMargin);
		bottomMargin.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.rgb(211, 211, 211)));
		/**
		 * create a side menu with four function on the top left corner of the screen
		 * Accelerate = to increase ant speed
		 * Quit = quit the game
		 * Sound = to turn the sound ON or OFF
		 * About = to introduce the develop
		 * Help = to tell what key command to use in the game.
		 */
		//===================================Side--Menu==========================================
		Toolbar sideMenu = new Toolbar();
		setToolbar(sideMenu);
		sideMenu.setTitle("Bugz Game");
		//side menu for exit command
			
		
		//side menu for sound command
		CheckBox soundCheck = new CheckBox();
		soundCheck.setSelected(true);
		soundCheck.setCommand(soundCmd);
		soundCmd.putClientProperty("Side Component", soundCheck);
	
		sideMenu.addCommandToRightBar(helpCmd);
		
		//add command to side menu
		sideMenu.addCommandToSideMenu(accelerateCmd);
		sideMenu.addCommandToSideMenu(soundCmd);
		sideMenu.addCommandToSideMenu(aboutCmd);
		sideMenu.addCommandToSideMenu(exitCmd);
		
		/**
		 * set the scoreviews and mapview to a specific location on the screen
		 */
		addComponent(BorderLayout.NORTH, sv);
		addComponent(BorderLayout.CENTER, mv);
		
		
		/**
		 * set gameworld width and height from the mapview observer
		 * notify the observer
		 */
		gw.setHeight(mv.getHeight());
		gw.setWidth(mv.getWidth());
		gw.notifyObservers();
		this.resume();
		show();
	}

	@Override
	public void run() {
		gw.clockTicking();
		repaint();	
	}
	
	public void pause() {
		timer.cancel();
		disableButtons();
		gw.pauseGame();
	}
	
	public void resume() {
		timer.schedule(99, true, this);
		enableButtons();
		gw.resumeGame();
	}
	
	private void disableButtons() {
		accelerateButton.setEnabled(false);
		leftButton.setEnabled(false);
		brakeButton.setEnabled(false);
		rightButton.setEnabled(false);
	}
	
	private void enableButtons() {
		accelerateButton.setEnabled(true);
		leftButton.setEnabled(true);
		brakeButton.setEnabled(true);
		rightButton.setEnabled(true);
	}
	
	public void pressed(Point pressed, Point origin) {
		if (gw.getPaused()) {
			gw.selectObject(pressed, origin);
		}
		
	}
}
