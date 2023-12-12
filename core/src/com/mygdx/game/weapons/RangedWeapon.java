package com.mygdx.game.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.map.RoomExit;
import com.mygdx.game.physics.PhysicsBodyData;

public class RangedWeapon extends Weapon {
    private int magazineSize;

    @Override
    public void attack() {

    }

    public class Bullet {
        private Body body;
        private Vector2 direction;

        public Bullet(Vector2 position, Vector2 direction, World world) {
            this.direction = direction;
            body = createBody(world);
            body.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.BULLET, this));
        }

        public Body getBody() {
            return body;
        }

        public void render(Batch batch, float delta) {
            update(delta);
        }

        private void update(float delta) {
            body.setLinearVelocity(new Vector2(direction).scl(5 * delta));
        }

        private Body createBody(World world) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.KinematicBody;
            bodyDef.bullet = true;
            Body body = world.createBody(bodyDef);
            Shape shape = new CircleShape();
            shape.setRadius(0.5f);
            body.createFixture(shape, 1000);
            return body;
        }
    }
}
