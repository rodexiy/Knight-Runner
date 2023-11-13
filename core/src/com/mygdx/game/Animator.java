package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.Public.Constants.PPM;

/**
 * Uma classe utilitária para lidar com animações de sprites em um jogo LibGDX.
 */
public class Animator {

    private Game game;
    private float stateTime = 0;
    private Animation<TextureRegion> animationTrack;
    private Texture animationSheet;
    private int FRAME_COLS;
    private int FRAME_ROWS;
    private float frameDuration;
    private Batch batch;

    /**
     * Inicializa o Animator com os parâmetros especificados e atualiza os quadros de animação.
     *
     * @param game           A instância Game do LibGDX.
     * @param animationSheet A textura contendo os quadros de animação.
     * @param FRAME_COLS     O número de colunas na folha de animação.
     * @param FRAME_ROWS     O número de linhas na folha de animação.
     * @param frameDuration  A duração (em segundos) de cada quadro da animação.
     */
    public Animator(Game game, Texture animationSheet, int FRAME_COLS, int FRAME_ROWS, float frameDuration) {
        this.game = game;
        this.animationSheet = animationSheet;
        this.FRAME_COLS = FRAME_COLS;
        this.FRAME_ROWS = FRAME_ROWS;
        this.frameDuration = frameDuration;
        this.batch = game.getBatch();

        this.updateAnimation();
    }

    /**
     * Atualiza os quadros de animação com base na folha de animação atual.
     */
    public void updateAnimation() {
        TextureRegion[][] tmp = TextureRegion.split(animationSheet,
                animationSheet.getWidth() / FRAME_COLS,
                animationSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }

        animationTrack = new Animation<TextureRegion>(frameDuration, animationFrames);
        stateTime = 0;
    }

    /**
     * Define a duração do quadro para a animação.
     *
     * @param frameDuration A duração (em segundos) de cada quadro da animação.
     */
    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
        updateAnimation();
    }

    /**
     * Renderiza o quadro atual da animação na posição especificada.
     *
     * @param x A coordenada x da posição.
     * @param y A coordenada y da posição.
     */
    public void render(float x, float y) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animationTrack.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x - ((animationSheet.getWidth() / this.FRAME_COLS) / 2),
                y - ((animationSheet.getHeight() / this.FRAME_ROWS) / 2));
    }

    /**
     * Renderiza o quadro atual da animação na posição especificada.
     *
     * @param position A posição como Vector2.
     */
    public void render(Vector2 position) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animationTrack.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x - ((animationSheet.getWidth() / this.FRAME_COLS) / 2),
                position.y - ((animationSheet.getHeight() / this.FRAME_ROWS) / 2));
    }

}
