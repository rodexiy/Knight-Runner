package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Representa o mapa do jogo.
 */
public class Map {

    private Game game;
    private World world;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private int backgroundTreesPosition = 0;
    private int backgroundMountainsPosition = 0;
    private int backgroundCloudsPosition = 0;
    private Texture backgroundMountains;
    private Texture backgroundTrees;
    private Texture backgroundClouds;
    private Texture groundTexture;
    private Batch batch;

    /**
     * Obtém a instância do mundo do Box2D associado ao mapa.
     *
     * @return A instância do mundo.
     */
    public World getWorld() {
        return world;
    }

    /**
     * Define o ouvinte de contatos para o mundo do Box2D associado ao mapa.
     *
     * @param contactListener O ouvinte de contatos a ser definido.
     */
    public void setContactListener(ContactListener contactListener) {
        world.setContactListener(contactListener);
    }

    /**
     * Obtém o renderizador de debug do Box2D associado ao mapa.
     *
     * @return O renderizador de debug.
     */
    public Box2DDebugRenderer getDebugRenderer() {
        return debugRenderer;
    }

    /**
     * Construtor da classe Map.
     *
     * @param game A instância do jogo.
     */
    public Map(Game game) {
        this.game = game;
        this.batch = game.getBatch();
        this.world = new World(new Vector2(0, -9), false);
        world.setContinuousPhysics(true);

        Rescaler rescaler = new Rescaler();

        groundTexture = rescaler.ReScale("ground.png", Gdx.graphics.getWidth() * 5, 32);
        backgroundMountains = rescaler.ReScale("backgroundMountains.png", Gdx.graphics.getWidth() * 3, Gdx.graphics.getHeight());
        backgroundTrees = rescaler.ReScale("backgroundTrees.png", Gdx.graphics.getWidth() * 3, Gdx.graphics.getHeight());
        backgroundClouds = rescaler.ReScale("backgroundClouds.png", Gdx.graphics.getWidth() * 3, Gdx.graphics.getHeight());
    }

    /**
     * Renderiza o mapa.
     */
    public void render() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        batch.draw(backgroundMountains, backgroundMountainsPosition, 0);
        batch.draw(backgroundTrees, backgroundTreesPosition, 32);
        batch.draw(backgroundClouds, backgroundCloudsPosition, 0);
        batch.draw(groundTexture, 0, 0);

        backgroundMountainsPosition -= 1;
        backgroundTreesPosition -= 3;
        backgroundCloudsPosition -= 2;

        if (backgroundMountainsPosition <= -(backgroundMountains.getWidth() / 2)) {
            backgroundMountainsPosition = 0;
        }
        if (backgroundCloudsPosition <= -(backgroundClouds.getWidth() / 2)) {
            backgroundCloudsPosition = 0;
        }

        if (backgroundTreesPosition <= -(backgroundTrees.getWidth() / 2)) {
            backgroundTreesPosition = 0;
        }
    }
}
