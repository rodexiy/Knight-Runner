package com.mygdx.game;


import com.badlogic.gdx.physics.box2d.*;
import com.kotcrab.vis.ui.widget.file.internal.IconStack;
import com.mygdx.game.Public.ContactTypes;

public class Contacts implements ContactListener {
    private Game game;
    private Character character;

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

//        System.out.println(aUserdata);
//        System.out.println(bUserdata);

        if (aUserdata == ContactTypes.CHARACTER && bUserdata == ContactTypes.GROUND) {
            character.setCanJump(true);
        }else if(aUserdata == ContactTypes.CHARACTER && bUserdata == ContactTypes.OBSTACLE) {
            System.exit(0);
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
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
