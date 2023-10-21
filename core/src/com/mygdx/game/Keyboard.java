package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;


public class Keyboard implements InputProcessor {
    Character character;
    Game game;
    public Keyboard(Game game) {
        this.game = game;
        this.character = game.getCharacter();

    }


    public void update() {
        boolean pressed = false;
        boolean upDownPressed = false;
        boolean leftRightPressed = false;

        float x = 0;
        float y = 0;
        Body body = character.getBody();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (character.getCanJump()) {
                character.setCanJump(false);
                Vector2 position = body.getPosition();
                body.applyLinearImpulse(new Vector2(0, 10000000), position, true);
            }
            pressed = true;
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)) {

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x = -1.0f;
            pressed = true;
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x = 1.0f;
            pressed = true;
        }

        if (pressed) {
            body.setLinearVelocity(x * 100, body.getLinearVelocity().y);
        }else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }



    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return  false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}