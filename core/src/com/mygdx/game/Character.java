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
        Pixmap pixmap200 = new Pixmap(Gdx.files.internal("KnightRunning.png"));
        Pixmap pixmap100 = new Pixmap(400, 140, pixmap200.getFormat());
        pixmap100.drawPixmap(pixmap200,
                0, 0, pixmap200.getWidth(), pixmap200.getHeight(),
                0, 0, pixmap100.getWidth(), pixmap100.getHeight()
        );
        Texture runTexture = new Texture(pixmap100);
        runAnimation = new Animator(game, runTexture,4, 1, 0.25f );

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(100,100);
        bodyDef.allowSleep = false;

        this.body = world.createBody(bodyDef);
        body.setUserData(ContactTypes.CHARACTER);
        PolygonShape box = new PolygonShape();
        box.setAsBox(Width,Height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 0f;


        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(ContactTypes.CHARACTER);

        BodyDef floorSensorBodyDef = new BodyDef();
        floorSensorBodyDef.type = BodyDef.BodyType.DynamicBody;
        floorSensorBodyDef.position.set(100, 80);

        this.floorSensor = world.createBody(floorSensorBodyDef);
        floorSensor.setUserData(ContactTypes.FLOORSENSOR);
        PolygonShape boxSensor = new PolygonShape();
        boxSensor.setAsBox(Width,5);

        FixtureDef floorSensorFixtureDef = new FixtureDef();
        floorSensorFixtureDef.shape = boxSensor;
        floorSensorFixtureDef.friction = 0f;
        floorSensorFixtureDef.restitution = 0f;
        floorSensorFixtureDef.density = 0f;
        floorSensorFixtureDef.isSensor = true;
        floorSensorBodyDef.allowSleep = false;

        sensorFixture = floorSensor.createFixture(floorSensorFixtureDef);
        sensorFixture.setUserData(ContactTypes.FLOORSENSOR);
        box.dispose();
        boxSensor.dispose();
    }

    public void render() {
        runAnimation.render(body.getPosition());
        floorSensor.setTransform(body.getPosition().x, body.getPosition().y - Height - 5, 0);

    }


}
