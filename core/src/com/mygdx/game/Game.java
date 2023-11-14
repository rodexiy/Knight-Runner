package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kotcrab.vis.ui.widget.file.internal.IconStack;
import com.mygdx.game.Public.Constants;
import com.mygdx.game.Public.ContactTypes;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.Public.Constants.PPM;

/**
 * Classe principal que controla o jogo.
 */
public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private Map map;
	private World world;
	private OrthographicCamera camera;
	private Character character;
	private Keyboard keyboard;
	private Contacts contacts;
	private ArrayList<Obstacle> obstacles = new ArrayList<>();
	private Random random = new Random();
	private Rescaler rescaler;
	private Animator animation;
	private BitmapFont font;
	private float pointsWait = 0;
	private int oldPoints = 0;
	private int points = 0;
	private Double timeUntilNextObstacle;
	private String currentScreen = "StartMenu";

	/**
	 * Obtém a instância do personagem.
	 *
	 * @return A instância do personagem.
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * Obtém a câmera do jogo.
	 *
	 * @return A câmera do jogo.
	 */
	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * Obtém o lote de sprites do jogo.
	 *
	 * @return O lote de sprites do jogo.
	 */
	public SpriteBatch getBatch() {
		return batch;
	}

	/**
	 * Obtém o mapa do jogo.
	 *
	 * @return O mapa do jogo.
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Define a tela atual do jogo.
	 *
	 * @param currentScreen A tela atual do jogo.
	 */
	public void setCurrentScreen(String currentScreen) {
		this.currentScreen = currentScreen;
	}

	/**
	 * Obtém a tela atual do jogo.
	 *
	 * @return A tela atual do jogo.
	 */
	public String getCurrentScreen() {
		return currentScreen;
	}

	/**
	 * Define a pontuação anterior do jogo.
	 *
	 * @param oldPoints A pontuação anterior do jogo.
	 */
	public void setOldPoints(int oldPoints) {
		this.oldPoints = oldPoints;
	}

	/**
	 * Obtém a pontuação atual do jogo.
	 *
	 * @return A pontuação atual do jogo.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Obtém um obstáculo com base em sua fixture.
	 *
	 * @param fixture A fixture do obstáculo.
	 * @return O obstáculo correspondente à fixture.
	 */
	public Obstacle getObstacleByFixture(Fixture fixture) {
		for (int i = 0; i < obstacles.size(); i++) {
			if (fixture == obstacles.get(i).getFixture()) {
				return obstacles.get(i);
			}
		}
		return obstacles.get(0);
	}

	/**
	 * Remove um obstáculo com base em sua fixture.
	 *
	 * @param fixture A fixture do obstáculo a ser removido.
	 */
	public void removeObstacleByFixture(Fixture fixture) {
		Obstacle obstacle = getObstacleByFixture(fixture);

		for (int i = 0; i < obstacles.size(); i++) {
			if (obstacles.get(i) != obstacle) {
				continue;
			}
			obstacles.remove(i);
			obstacle.remove();
			break;
		}
	}

	@Override
	public void create() {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Montserrat.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 25;
		parameter.borderWidth = 3;
		font = generator.generateFont(parameter);
		rescaler = new Rescaler();
		camera = new OrthographicCamera();
		camera.position.set(new Vector3(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0));

		timeUntilNextObstacle = random.nextDouble();

		batch = new SpriteBatch();
		map = new Map(this);
		world = map.getWorld();

		character = new Character(this);
		keyboard = new Keyboard(this);
		new Ground(this, Gdx.graphics.getWidth() * 5, 1, Gdx.graphics.getWidth() * 2, 0, ContactTypes.GROUND);
		new Ground(this, 1, Gdx.graphics.getHeight() * 2, -1, Gdx.graphics.getHeight() / 2, ContactTypes.WALLLEFT);
		new Ground(this, 1, Gdx.graphics.getHeight() * 2, -1, Gdx.graphics.getWidth(), ContactTypes.WALLRIGHT);

		contacts = new Contacts(this);
		world.setContactListener(contacts);

		Texture rescaledMonster = rescaler.ReScale("monster.png", 300, 100);
		animation = new Animator(this, rescaledMonster, 2, 1, 0.5f);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		keyboard.update();
		batch.begin();
		if (currentScreen.equals("Game")) {
			map.render();
			if (pointsWait >= 0.05) {
				pointsWait = 0;
				points += 1;
			} else {
				pointsWait += Gdx.graphics.getDeltaTime();
			}

			font.draw(batch, "Points: " + Integer.toString(points), Gdx.graphics.getWidth() - 250, Gdx.graphics.getHeight() - 50);

			if (timeUntilNextObstacle <= 0) {
				timeUntilNextObstacle = 0.5f + random.nextDouble() * (1.5f - 0.5f);

				Obstacle obstacle = new Obstacle(this, animation, new Vector2(1, 1.5f), new Vector2(Gdx.graphics.getWidth() / PPM, 3), new Vector2(-30, -5));
				obstacles.add(obstacle);
			} else {
				timeUntilNextObstacle -= Gdx.graphics.getDeltaTime();
			}

			for (Obstacle obstacle : obstacles) {
				obstacle.render();
			}

			camera.update();
			character.render();
		} else if (currentScreen.equals("StartMenu")) {
			font.draw(batch, "Press enter to start", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
		} else if (currentScreen.equals("OverScreen")) {
			points = 0;
			font.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 + 150);
			font.draw(batch, "Press enter to start", Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
			font.draw(batch, "Points: " + oldPoints, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 100);

			character.getBody().setTransform(5, 5, 0);
			for (int i = 0; i < obstacles.size(); i++) {
				Obstacle obstacle = obstacles.get(i);
				obstacles.remove(i);
				obstacle.remove();
			}
		}

		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		camera.setToOrtho(false, width, height);
	}
}
