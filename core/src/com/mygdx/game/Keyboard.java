package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static com.mygdx.game.Public.Constants.PPM;

/**
 * Classe responsável pelo processamento de entrada do teclado.
 */
public class Keyboard implements InputProcessor {

    private Character character;
    private Game game;

    /**
     * Construtor da classe Keyboard.
     *
     * @param game A instância do jogo.
     */
    public Keyboard(Game game) {
        this.game = game;
        this.character = game.getCharacter();
    }

    /**
     * Atualiza o estado do teclado.
     */
    public void update() {
        boolean pressed = false;

        if ((Gdx.input.isKeyPressed(Input.Keys.ENTER) && (game.getCurrentScreen().equals("StartMenu") || game.getCurrentScreen().equals("OverScreen")))) {
            game.setCurrentScreen("Game");
            return;
        }

        float x = 0;
        Body body = character.getBody();

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x = -1.0f;
            pressed = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x = 1.0f;
            pressed = true;
        }

        if (pressed) {
            float xAmount = ((x * 5) * Gdx.graphics.getDeltaTime());
            body.setLinearVelocity(xAmount * 200, body.getLinearVelocity().y);
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.SPACE))
                && (game.getCurrentScreen().equals("Game"))
        ) {
            if (character.getCanJump()) {
                character.jump();
            }
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
        return false;
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
