package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Representa um objeto de chão no jogo.
 */
public class Ground {

    private Game game;
    private BodyDef groundBodyDef;
    private Body groundBody;
    private Map map;
    private World world;
    private PolygonShape groundBox;
    private OrthographicCamera camera;

    /**
     * Construtor da classe Ground.
     *
     * @param game       A instância do jogo.
     * @param sizeX      O tamanho do chão no eixo X.
     * @param sizeY      O tamanho do chão no eixo Y.
     * @param PosX       A posição do chão no eixo X.
     * @param PosY       A posição do chão no eixo Y.
     * @param ContactType O tipo de contato associado ao chão.
     */
    public Ground(Game game, int sizeX, int sizeY, int PosX, int PosY, Object ContactType) {
        this.game = game;
        this.map = game.getMap();
        this.world = map.getWorld();
        this.camera = game.getCamera();

        groundBodyDef = new BodyDef();
        groundBodyDef.position.set(new Vector2(PosX, PosY));
        groundBody = world.createBody(groundBodyDef);
        groundBody.setUserData(ContactType);
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        groundBox = new PolygonShape();
        groundBox.setAsBox(sizeX, sizeY);

        Fixture groundFixture = groundBody.createFixture(groundBox, 10.0f);
        groundFixture.setUserData(ContactType);
    }
}
