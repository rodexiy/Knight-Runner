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
    private int backgroundTreesPosition = 0;
    private int backgroundMountainsPosition = 0;
    private Texture backgroundMountains;
    private Texture backgroundTrees;
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

        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("backgroundMountains.png"));
        Pixmap pixmap100 = new Pixmap(Gdx.graphics.getWidth() * 3, Gdx.graphics.getHeight(), pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        backgroundMountains = new Texture(pixmap100);

        pixmap200.dispose();
        pixmap100.dispose();

        Pixmap pixmap2002 = new Pixmap(Gdx.files.internal("backgroundTrees.png"));
        Pixmap pixmap1002 = new Pixmap(Gdx.graphics.getWidth() * 3, Gdx.graphics.getHeight(), pixmap2002.getFormat());
        pixmap1002.drawPixmap(pixmap2002,
                0, 0, pixmap2002.getWidth(), pixmap2002.getHeight(),
                0, 0, pixmap1002.getWidth(), pixmap1002.getHeight()
        );
        backgroundTrees = new Texture(pixmap1002);
        pixmap2002.dispose();
        pixmap1002.dispose();
    }

    public void render() {
        batch.draw(backgroundMountains, backgroundMountainsPosition, 0);
        batch.draw(backgroundTrees, backgroundTreesPosition, 0);

        backgroundMountainsPosition -= 1;
        backgroundTreesPosition -= 3;
        if (backgroundMountainsPosition <= -(backgroundMountains.getWidth() / 2)) {
            backgroundMountainsPosition = 0;
        }
        if (backgroundTreesPosition <= -(backgroundTrees.getWidth() / 2)) {
            backgroundTreesPosition = 0;
        }
        world.step(Gdx.graphics.getDeltaTime() + 1 , 10, 10);
    }

}
