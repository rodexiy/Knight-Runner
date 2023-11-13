package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.kotcrab.vis.ui.widget.file.internal.IconStack;
import com.mygdx.game.Public.ContactTypes;

import static com.mygdx.game.Public.Constants.PPM;

/**
 * Representa um obstáculo no jogo.
 */
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
    private Fixture fixture;
    private boolean removing;

    /**
     * Construtor da classe Obstacle.
     *
     * @param game      A instância do jogo.
     * @param animation A animação do obstáculo.
     * @param size      O tamanho do obstáculo.
     * @param position  A posição inicial do obstáculo.
     * @param velocity  A velocidade do obstáculo.
     */
    public Obstacle(Game game, Animator animation, Vector2 size, Vector2 position, Vector2 velocity) {
        this.game = game;
        this.map = game.getMap();
        this.world = map.getWorld();
        this.animation = animation;
        this.size = size;
        this.velocity = velocity;

        obstacleBodyDef = new BodyDef();
        obstacleBodyDef.position.set(position);
        obstacleBodyDef.type = BodyDef.BodyType.DynamicBody;
        obstacleBody = world.createBody(obstacleBodyDef);
        obstacleBody.setUserData(ContactTypes.OBSTACLE);

        obstacleBox = new PolygonShape();
        obstacleBox.setAsBox(size.x, size.y);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = obstacleBox;
        fixtureDef.density = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        fixture = obstacleBody.createFixture(fixtureDef);
        obstacleBody.setUserData(ContactTypes.OBSTACLE);
        fixture.setUserData(ContactTypes.OBSTACLE);
    }

    /**
     * Obtém a posição do obstáculo.
     *
     * @return A posição do obstáculo.
     */
    public Vector2 getPosition() {
        return this.obstacleBody.getPosition();
    }

    /**
     * Obtém o fixture associado ao obstáculo.
     *
     * @return O fixture do obstáculo.
     */
    public Fixture getFixture() {
        return this.fixture;
    }

    /**
     * Remove o obstáculo do mundo.
     */
    public void remove() {
        if (removing) {
            return;
        }
        removing = true;
        new Thread(new Runnable() {
            public void run() {
                while (world.isLocked()) {

                }

                world.destroyBody(obstacleBody);
                animation = null;
                fixture = null;
                obstacleBodyDef = null;
                obstacleBox = null;
                velocity = null;
                size = null;
                position = null;
            }
        }).start();
    }

    /**
     * Renderiza o obstáculo.
     */
    public void render() {
        animation.render(obstacleBody.getPosition().x * PPM, obstacleBody.getPosition().y * PPM);
        obstacleBody.setLinearVelocity(velocity);
    }
}
