package com.mygdx.game.weapons;

import static com.mygdx.game.utils.StaticValues.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.audio.AudioManager;
import com.mygdx.game.audio.AudioObserver;
import com.mygdx.game.manager.ResourceManager;
import com.mygdx.game.map.RoomExit;
import com.mygdx.game.physics.PhysicsBodyData;

import java.util.ArrayList;
import java.util.Iterator;

public class RangedWeapon extends Weapon {
    private World world;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Texture bulletTexture;

    public RangedWeapon(World world) {
        this.world = world;
        ResourceManager.loadTextureAsset("textures/weapons/bullet1.png");
        bulletTexture = ResourceManager.getTextureAsset("textures/weapons/bullet1.png");
    }

    @Override
    public void input(Batch batch) {
        if (interval >= maxInterval) {
            attack();
            interval = 0;
        }

        int directionX = direction.x > 0 ? -1 : 1;
        batch.begin();
        batch.draw(texture,position.x * PPM - (8 * directionX),
                position.y * PPM - 12, 16 * directionX, 16);
        batch.end();
    }

    protected void update(float delta) {
        interval += delta;

        for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext(); ) {
            Bullet bullet = iterator.next();
            if (!bullet.isActive) {
                bullet.destroy();
                iterator.remove();
            }
        }
    }

    @Override
    public void render(Batch batch, float delta) {
        update(delta);

        for (Bullet bullet : bullets) {
            bullet.render(batch, delta);
        }
    }

    protected void attack() {
        Bullet bullet = new Bullet(position, direction, world, damage, bulletTexture);
        bullets.add(bullet);

        AudioManager.getInstance().onNotify(AudioObserver.AudioCommand.MUSIC_STOP, weaponConfig.getSound());
        AudioManager.getInstance().onNotify(AudioObserver.AudioCommand.MUSIC_PLAY_ONCE, weaponConfig.getSound());
    }

    @Override
    public void loadConfig() {
        super.loadConfig();
    }

    public class Bullet {
        public boolean isActive = true;
        private Body body;
        private Vector2 direction;
        private TextureRegion texture;
        private int damage;

        public Bullet(Vector2 position, Vector2 direction, World world, int damage, Texture texture) {
            this.direction = direction;
            this.damage = damage;
            this.texture = new TextureRegion(texture);
            body = createBody(world);
            body.setTransform(position.add(direction), 0);
            body.setUserData(new PhysicsBodyData(PhysicsBodyData.DataType.BULLET, this));
        }

        public int getDamage() { return damage; }

        public Body getBody() {
            return body;
        }

        public void render(Batch batch, float delta) {
            batch.begin();
            batch.draw(texture, body.getPosition().x * PPM, body.getPosition().y * PPM,
            0, 0, 8, 8, 1, 1, direction.angleDeg());
            batch.end();
            update(delta);
        }

        private void update(float delta) {
            body.setLinearVelocity(new Vector2(direction).scl(5000 * delta));
        }

        private Body createBody(World world) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.bullet = true;
            Body body = world.createBody(bodyDef);
            Shape shape = new CircleShape();
            shape.setRadius(0.2f);
            body.createFixture(shape, 10000);
            return body;
        }

        public void destroy() {
            body.getWorld().destroyBody(body);
        }
    }
}
