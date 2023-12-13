package com.mygdx.game.entities.player;

import static com.mygdx.game.utils.StaticValues.PPM;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.components.BattleComponent;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.WeaponFactory;

public class PlayerBattleComponent extends BattleComponent {
    private boolean isAttacking = false;
    private Weapon weapon;

    public PlayerBattleComponent(World world) {
        super();

        weapon = WeaponFactory.getInstance().getWeapon(WeaponFactory.WeaponType.AK47, world);
    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                weapon.setPosition(json.fromJson(Vector2.class, string[1]));
            }
            else if (string[0].equalsIgnoreCase(MESSAGE.ATTACK_STATUS.toString())) {
                isAttacking = json.fromJson(boolean.class, string[1]);
            }
            else if (string[0].equalsIgnoreCase(MESSAGE.ATTACK_DIRECTION.toString())) {
                weapon.setDirection(json.fromJson(Vector2.class, string[1]));
            }
        }
    }

    @Override
    public void update(Entity entity, Batch batch, float delta) {
        if (isAttacking) {
            weapon.input(batch);
        }

        weapon.render(batch, delta);
    }
}
