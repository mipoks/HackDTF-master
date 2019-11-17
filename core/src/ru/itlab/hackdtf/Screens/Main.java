package ru.itlab.hackdtf.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Main extends Game {

	GameScreen gs;
	
	@Override
	public void create () {
		gs = new GameScreen();
		setScreen(gs);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
