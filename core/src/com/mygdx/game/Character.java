package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.xml.soap.Text;

public class Character {
    private Game game;
    private int health = 100;
    private Animator runAnimation;
    private Body body;
    private Map map;
    private World world;

    public Body getBody() {
        return body;
    }

    public Character(Game game) {
        this.game = game;
        this.map = game.getMap();
        this.world = map.getWorld();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(50,50);

        runAnimation = new Animator(game, new Texture("KnightRunning.png"),4, 1, 0.25f );

        this.body = world.createBody(bodyDef);

        PolygonShape box = new PolygonShape();
        box.setAsBox(1,1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
//        fixtureDef.density = 5f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        Fixture fixture = body.createFixture(fixtureDef);
        box.dispose();

    }

    public void render() {
        System.out.println(body.getPosition());
        runAnimation.render(body.getPosition());
    }

}
