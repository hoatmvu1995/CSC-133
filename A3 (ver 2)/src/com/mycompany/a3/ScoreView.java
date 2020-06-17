package com.mycompany.a3;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.IGameWorld;

import java.util.Observable;
import java.util.Observer;

public class ScoreView extends Container implements Observer
{
    private Label time = new Label("Time: 0");
    private Label lives = new Label("Lives left: 0");
    private Label food = new Label("Food left: 0");
    private Label health = new Label("Health level: 0");
    private Label sound = new Label("Sound: OFF");

    public ScoreView()
    {
        setLayout(new FlowLayout(Component.CENTER));
        addComponent(time);
        addComponent(lives);
        addComponent(food);
        addComponent(health);
        addComponent(sound);
    }

    @Override
    public void update(Observable o, Object data)
    {
        IGameWorld gwObserver = (IGameWorld) data;
        time.setText("Time: " + gwObserver.getClock());
        lives.setText("Lives left: " + gwObserver.getAntLives());
        food.setText("Food level: " + gwObserver.getFoodLevel());
        health.setText("Health level: " + gwObserver.getHealthLevel());
        sound.setText("Sound: " + (gwObserver.getSound() ? "ON" : "OFF"));
        
        repaint();
    }
}

