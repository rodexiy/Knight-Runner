package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import sun.management.Sensor;

import javax.xml.soap.Text;
// https://bhopkins.net/pages/mmphysics/
public class Character {
    private Game game;
    private int health = 100;
    private Animator runAnimation;
    private Body body;
    private Body floorSensor;
    private Map map;
    private World world;
    private boolean canJump = true;

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public boolean getCanJump() {
        return this.canJump;
    }

    public Body getBody() {
        return body;
    }

    public Character(Game game) {
        this.game = game;
        this.map = game.getMap();
        this.world = map.getWorld();
        runAnimation = new Animator(game, new Texture("KnightRunning.png"),4, 1, 0.25f );

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100,100);

        this.body = world.createBody(bodyDef);
        PolygonShape box = new PolygonShape();
        box.setAsBox(10,16);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f; // Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef);
        box.dispose();

    }

    public void render() {
        runAnimation.render(body.getPosition());
    }

}
