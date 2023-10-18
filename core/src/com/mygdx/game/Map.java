package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.World;

public class Map {
    private Game game;
    private World world = new World(new Vector2(0, -100), true);
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

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
    }

    public void render() {
        world.step(Gdx.graphics.getDeltaTime() , 3, 3);
    }

}
