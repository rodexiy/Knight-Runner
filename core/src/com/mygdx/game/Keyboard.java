package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.Public.Constants.PPM;
public class Keyboard implements InputProcessor {
    private Character character;
    private Game game;

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
        Vector2 characterPosition = body.getPosition();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x = -1.0f;
            pressed = true;
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x = 1.0f;
            pressed = true;
        }

        if (pressed) {
            float xAmount = ((x * 5) * Gdx.graphics.getDeltaTime());
//            System.out.println(xAmount);
            body.setLinearVelocity(xAmount * 200, body.getLinearVelocity().y);
//            body.setTransform((characterPosition.x + xAmount), characterPosition.y, 0);
        }else{
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (character.getCanJump()) {
                character.jump();
            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.S)) {

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