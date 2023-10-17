package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Map map;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera = new OrthographicCamera();
	private Character character;
	private Keyboard keyboard;

	public Character getCharacter() {
		return character;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public Map getMap() {
		return map;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map(this);
		world = map.getWorld();
		debugRenderer = map.getDebugRenderer();
		character = new Character(this);
		keyboard = new Keyboard(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.begin();
		keyboard.update();
		camera.update();
		map.render();
//		debugRenderer.render(world, camera.combined);
		character.render();
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
