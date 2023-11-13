package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Public.ContactTypes;

/**
 * Manipula os eventos de colisão no jogo.
 */
public class Contacts implements ContactListener {

    private Game game;
    private Character character;

    /**
     * Construtor da classe Contacts.
     *
     * @param game A instância do jogo.
     */
    public Contacts(Game game) {
        this.game = game;
        this.character = game.getCharacter();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) {
            if (a.getUserData() == null || b.getUserData() == null) {
                return;
            }
        }

        Object aUserdata = a.getUserData();
        Object bUserdata = b.getUserData();

        // Lógica de colisão
        if (aUserdata == ContactTypes.CHARACTER && bUserdata == ContactTypes.GROUND) {
            character.setCanJump(true);
        } else if (aUserdata == ContactTypes.CHARACTER && bUserdata == ContactTypes.OBSTACLE) {
            game.setOldPoints(game.getPoints());
            game.setCurrentScreen("OverScreen");
        } else if (aUserdata == ContactTypes.OBSTACLE && bUserdata == ContactTypes.CHARACTER) {
            game.setOldPoints(game.getPoints());
            game.setCurrentScreen("OverScreen");
        } else if (aUserdata == ContactTypes.OBSTACLE && bUserdata == ContactTypes.WALLLEFT) {
            game.removeObstacleByFixture(a);
        } else if (aUserdata == ContactTypes.WALLLEFT && bUserdata == ContactTypes.OBSTACLE) {
            game.removeObstacleByFixture(b);
        } else {
            // Outros tipos de colisão
            System.out.println(aUserdata);
            System.out.println(bUserdata);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) {
            if (a.getUserData() == null || b.getUserData() == null) {
                return;
            }
        }

        Object aUserdata = a.getUserData();
        Object bUserdata = b.getUserData();

        // Lógica de fim de colisão
        if (aUserdata == ContactTypes.CHARACTER && bUserdata == ContactTypes.OBSTACLE) {
            game.setCurrentScreen("OverScreen");
        } else if (aUserdata == ContactTypes.OBSTACLE && bUserdata == ContactTypes.CHARACTER) {
            game.setCurrentScreen("OverScreen");
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Lógica pré-resolução de colisão
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Lógica pós-resolução de colisão
    }
}
