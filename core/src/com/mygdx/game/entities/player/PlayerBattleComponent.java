package com.mygdx.game.entities.player;

import static com.mygdx.game.utils.StaticValues.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.BattleComponent;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;
import com.mygdx.game.weapons.RangedWeapon;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.WeaponFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayerBattleComponent extends BattleComponent {
    private int currentHealth;
    private float healthTimer = 0;
    private boolean isAttacking = false;
    private Weapon weapon;
    private World world;

    public PlayerBattleComponent(World world) {
        super();

        this.world = world;
        weapon = new RangedWeapon(world);
    }

    private void setRandomWeapon() {
        List<WeaponFactory.WeaponType> values = Collections.unmodifiableList(Arrays.asList(WeaponFactory.WeaponType.values()));
        WeaponFactory.WeaponType random = values.get((int) (Math.random() * values.size()));
        weapon = WeaponFactory.getInstance().getWeapon(random, world);
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 1) {
            if (string[0].equalsIgnoreCase(MESSAGE.RANDOM_WEAPON.toString())) {
                setRandomWeapon();
            }
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                weapon.setPosition(json.fromJson(Vector2.class, string[1]));
            }
            else if (string[0].equalsIgnoreCase(MESSAGE.ATTACK_STATUS.toString())) {
                isAttacking = json.fromJson(boolean.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.ATTACK_DIRECTION.toString())) {
                weapon.setDirection(json.fromJson(Vector2.class, string[1]));
            } else if (string[0].equalsIgnoreCase(MESSAGE.LOAD_CONFIG.toString())) {
                EntityConfig config = json.fromJson(EntityConfig.class, string[1]);

                health = config.getMaxHealthPoints();
                currentHealth = health;
            } else if (string[0].equalsIgnoreCase(MESSAGE.GET_DAMAGE.toString())) {
                if (healthTimer <= 0) {
                    currentHealth = currentHealth - 1;
                    healthTimer = 2;
                }
            }
        }
    }

    @Override
    public void update(Entity entity, Batch batch, Batch hudBatch, float delta) {
        healthTimer -= delta;

        if (isAttacking) {
            weapon.input(batch);
        }

        weapon.render(batch, delta);
        weapon.renderUI(hudBatch, delta);
    }

    @Override
    public int getHealth() {
        return currentHealth;
    }
}
