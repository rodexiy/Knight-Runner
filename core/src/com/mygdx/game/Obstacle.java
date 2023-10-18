package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Obstacle {
    private Game game;
    private Map map;
    private World world;

    private Animator animation;
    private Vector2 size;
    private Vector2 position;
    private Vector2 velocity;
    private BodyDef obstacleBodyDef;
    private Body obstacleBody;
    private PolygonShape obstacleBox;

    //Animation, size, position, velocity
    public Obstacle(Game game, Animator animation, Vector2 size, Vector2 position, Vector2 velocity) {
        this.game = game;
        this.map = game.getMap();
        this.world = map.getWorld();
        this.animation = animation;
        this.size = size;
        this.position = position;
        this.velocity = velocity;

        obstacleBodyDef = new BodyDef();
        obstacleBodyDef.position.set(position);
        obstacleBodyDef.type = BodyDef.BodyType.DynamicBody;
        obstacleBody = world.createBody(obstacleBodyDef);


        obstacleBox = new PolygonShape();
        obstacleBox.setAsBox(size.x, size.y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = obstacleBox;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f; // Make it bounce a little bit

        Fixture fixture = obstacleBody.createFixture(fixtureDef);

        obstacleBody.createFixture(fixtureDef);
    }

    public void render() {
        animation.render(obstacleBody.getPosition());
        obstacleBody.setLinearVelocity(velocity);
    }
}
