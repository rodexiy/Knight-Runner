package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.ScreenUtils;

import javax.print.attribute.standard.PagesPerMinute;
import java.util.ArrayList;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Map map;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private Character character;
	private Keyboard keyboard;
	private Ground ground;
	private Contacts contacts;
	private ArrayList<Obstacle> obstacles = new ArrayList<>();

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
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		map = new Map(this);
		world = map.getWorld();

		debugRenderer = map.getDebugRenderer();
		character = new Character(this);
		keyboard = new Keyboard(this);
		ground = new Ground(this, 50, 50);

		contacts = new Contacts(this);
		world.setContactListener(contacts);

		Animator animation = new Animator(this, new Texture("monster.png"), 2, 1, 0.25f);

		for (int i = 0; i < 10; i++) {
			Obstacle obstacle = new Obstacle(this, animation, new Vector2(10, 20), new Vector2(Gdx.graphics.getWidth(), 50), new Vector2(-100, -100));
			obstacles.add(obstacle);
		}


	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.begin();

		for (Obstacle obstacle: obstacles) {
			obstacle.render();
		}

		keyboard.update();
		camera.update();
		map.render();
		character.render();
		batch.end();

		debugRenderer.render(world, camera.combined);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.position.set(width / 2, height / 2, 0);
	}
}
