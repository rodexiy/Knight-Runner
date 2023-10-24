package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Public.ContactTypes;

import javax.xml.soap.Text;
// https://bhopkins.net/pages/mmphysics/
public class Character {
    private Game game;
    private int health = 100;
    private Animator runAnimation;
    private Body body;
    private Body floorSensor;
    private Fixture sensorFixture;
    private Map map;
    private World world;
    private int Width = 30;
    private int Height = 62;
    private int remainingJumpSteps = 0;
    private boolean canJump = true;

    public void setRemainingJumpSteps(int remainingJumpSteps) {
        this.remainingJumpSteps = remainingJumpSteps;
    }

    public void setGravityScale(int scale) {
        this.body.setGravityScale(scale);
    }

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


        Rescaler rescaler = new Rescaler();
        Texture runTexture= rescaler.ReScale("KnightRunning.png", new Vector2(400, 140));
        runAnimation = new Animator(game, runTexture,4, 1, 0.25f );

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100, 100);
        bodyDef.allowSleep = false;
        bodyDef.gravityScale = 2;

        this.body = world.createBody(bodyDef);
        body.setUserData(ContactTypes.CHARACTER);
        PolygonShape box = new PolygonShape();
        box.setAsBox(Width,Height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 0f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(ContactTypes.CHARACTER);


        box.dispose();
    }

    public void jump() {
        setCanJump(false);
        setRemainingJumpSteps(10);
    }

    public void render() {
        runAnimation.render(body.getPosition());
//        floorSensor.setTransform(body.getPosition().x, body.getPosition().y - Height - 5, 0);

        if (remainingJumpSteps > 0) {
            remainingJumpSteps--;
            setGravityScale(0);
            body.applyLinearImpulse(new Vector2(0.01f, 2000), body.getPosition(), true);
        }else {
            setGravityScale(10);
        }

    }


}
