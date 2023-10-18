package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Animator {
    private Game game;
    private float stateTime = 0;
    private Animation<TextureRegion> animationTrack;
    private Texture animationSheet;
    private int FRAME_COLS;
    private int FRAME_ROWS;
    private float frameDuration;
    private Batch batch;

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

    public Animator(Game game, Texture animationSheet, int FRAME_COLS, int FRAME_ROWS, float frameDuration) {
        this.game = game;
        this.animationSheet = animationSheet;
        this.FRAME_COLS = FRAME_COLS;
        this.FRAME_ROWS = FRAME_ROWS;
        this.frameDuration = frameDuration;
        this.batch = game.getBatch();

        this.updateAnimation();
    }

    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
        updateAnimation();
    }


    public void render(float x, float y) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animationTrack.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, x - ((animationSheet.getWidth() / this.FRAME_COLS) / 2), y - ((animationSheet.getHeight() / this.FRAME_ROWS) / 2));
    }
    public void render(Vector2 position) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animationTrack.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x - ((animationSheet.getWidth() / this.FRAME_COLS) / 2), position.y - ((animationSheet.getHeight() / this.FRAME_ROWS) / 2));
    }

}