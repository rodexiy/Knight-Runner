package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.file.internal.IconStack;
import com.sun.org.apache.xpath.internal.objects.XObject;

import javax.print.attribute.standard.PagesPerMinute;
import java.util.ArrayList;
import static com.mygdx.game.Public.Constants.PPM;

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
		camera = new OrthographicCamera();
		camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));

		batch = new SpriteBatch();
		map = new Map(this);
		world = map.getWorld();

		debugRenderer = map.getDebugRenderer();
		character = new Character(this);
		keyboard = new Keyboard(this);
		ground = new Ground(this, 50, 50);

		contacts = new Contacts(this);
		world.setContactListener(contacts);

		Rescaler rescaler = new Rescaler();
		Texture rescaledMonster = rescaler.ReScale("monster.png", 300, 100);
		Animator animation = new Animator(this, rescaledMonster, 2, 1, 0.5f);

		for (int i = 0; i < 1; i++) {
			Obstacle obstacle = new Obstacle(this, animation, new Vector2(1, 2), new Vector2(Gdx.graphics.getWidth() / PPM, 5), new Vector2(-15, -5));
			obstacles.add(obstacle);
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		batch.begin();
		map.render();

		for (int i = 0; i < obstacles.size(); i++) {
			Obstacle obstacle = obstacles.get(i);

			obstacle.render();
			if (obstacle.getPosition().x < 0) {
				obstacles.remove(i);
				obstacle = null;
				System.out.println(obstacle);
			}
		}

		keyboard.update();
		camera.update();
		character.render();
		batch.end();

		debugRenderer.render(world, camera.combined.scl(PPM));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, width, height);
	}
}
