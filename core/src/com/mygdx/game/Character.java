package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Public.ContactTypes;

import static com.mygdx.game.Public.Constants.PPM;

import javax.xml.soap.Text;

/**
 * Representa o personagem jogável no jogo.
 */
public class Character {

    private Game game;
    private int health = 100;
    private Animator runAnimation;
    private Body body;
    private Body floorSensor;
    private Fixture sensorFixture;
    private Map map;
    private World world;
    private float Width = 0.5f;
    private int Height = 2;
    private int remainingJumpSteps = 0;
    private boolean canJump = true;

    /**
     * Define o número de etapas restantes do pulo.
     *
     * @param remainingJumpSteps O número de etapas restantes do pulo.
     */
    public void setRemainingJumpSteps(int remainingJumpSteps) {
        this.remainingJumpSteps = remainingJumpSteps;
    }

    /**
     * Define a escala da gravidade para o personagem.
     *
     * @param scale A escala da gravidade.
     */
    public void setGravityScale(int scale) {
        this.body.setGravityScale(scale);
    }

    /**
     * Define se o personagem pode pular.
     *
     * @param canJump True se o personagem pode pular, False caso contrário.
     */
    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    /**
     * Verifica se o personagem pode pular.
     *
     * @return True se o personagem pode pular, False caso contrário.
     */
    public boolean getCanJump() {
        return this.canJump;
    }

    /**
     * Obtém o corpo físico do personagem.
     *
     * @return O corpo físico do personagem.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Construtor da classe Character.
     *
     * @param game A instância do jogo.
     */
    public Character(Game game) {
        this.game = game;
        this.map = game.getMap();
        this.world = map.getWorld();

        Rescaler rescaler = new Rescaler();
        Texture runTexture = rescaler.ReScale("KnightRunning.png", new Vector2(400, 140));
        runAnimation = new Animator(game, runTexture, 4, 1, 0.25f);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(5, 0);
        bodyDef.allowSleep = false;
        bodyDef.gravityScale = 2;

        this.body = world.createBody(bodyDef);
        body.setUserData(ContactTypes.CHARACTER);
        PolygonShape box = new PolygonShape();
        box.setAsBox(Width, Height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 0f;

        Fixture fixture = body.createFixture(fixtureDef);
        body.setUserData(ContactTypes.CHARACTER);
        fixture.setUserData(ContactTypes.CHARACTER);

        box.dispose();
    }

    /**
     * Inicia o pulo do personagem.
     */
    public void jump() {
        setCanJump(false);
        setRemainingJumpSteps(10);
    }

    /**
     * Renderiza o personagem na tela, atualizando a animação e aplicando a lógica do pulo.
     */
    public void render() {
        runAnimation.render(body.getPosition().x * PPM, body.getPosition().y * PPM);

        if (remainingJumpSteps > 0) {
            remainingJumpSteps--;
            setGravityScale(0);
            body.applyLinearImpulse(new Vector2(0f, 3), body.getPosition(), true);
        } else {
            setGravityScale(15);
        }
    }
}
