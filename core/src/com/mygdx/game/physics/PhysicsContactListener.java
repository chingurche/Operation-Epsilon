package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class PhysicsContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Object body1 = contact.getFixtureA().getBody().getUserData();
        Object body2 = contact.getFixtureB().getBody().getUserData();
        PhysicsBodyData data1 = null;
        PhysicsBodyData data2 = null;

        if (body1 != null) {
            data1 = (PhysicsBodyData) body1;
        }

        if (body2 != null) {
            data2 = (PhysicsBodyData) body2;
        }

        if (body1 != null) {
            data1.interact(data2);
        }

        if (body2 != null) {
            data2.interact(data1);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
