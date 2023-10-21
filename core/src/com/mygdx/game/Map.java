package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

public class Map {
    private Game game;
    private World world;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    private int backgroundPosition = 0;
    private Texture backgroundImage;
    private Batch batch;

    public World getWorld() {
        return world;
    }
    public void setContactListener(ContactListener contactListener) {
        world.setContactListener(contactListener);
    }

    public Box2DDebugRenderer getDebugRenderer() {
        return debugRenderer;
    }

    public Map(Game game) {
        this.game = game;
        this.batch = game.getBatch();
        this.world = new World(new Vector2(0, -1), false);

        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("backgroundImage.png"));
        Pixmap pixmap100 = new Pixmap(Gdx.graphics.getWidth() * 3, Gdx.graphics.getHeight(), pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        backgroundImage = new Texture(pixmap100);
        pixmap200.dispose();
        pixmap100.dispose();

    }

    public void render() {
        batch.draw(backgroundImage, backgroundPosition, 0);
        backgroundPosition -= 3;
        if (backgroundPosition <= -(backgroundImage.getWidth() / 2)) {
            backgroundPosition = 0;
        }
        world.step(Gdx.graphics.getDeltaTime() + 1 , 10, 10);
    }

}
