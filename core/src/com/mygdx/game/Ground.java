package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Ground {
    private Game game;
    private BodyDef groundBodyDef;
    private Body groundBody;
    private Map map;
    private World world;
    private PolygonShape groundBox;
    private OrthographicCamera camera;

    public Ground(Game game, int x, int y) {
        this.game = game;
        this.map = game.getMap();
        this.world = map.getWorld();
        this.camera = game.getCamera();

        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(0, 10));
        groundBody = world.createBody(groundBodyDef);
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth * 2, 10.0f);

        groundBody.createFixture(groundBox, 10.0f);
    }
}
