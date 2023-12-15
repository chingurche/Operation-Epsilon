package com.mygdx.game.entities.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.BattleComponent;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityConfig;

public class EnemyBattleComponent extends BattleComponent {
    private Vector2 currentPosition = new Vector2();
    private Vector2 target = new Vector2();

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(MESSAGE_TOKEN);

        if (string.length == 0) {
            return;
        }

        if (string.length == 2) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                currentPosition.set(json.fromJson(Vector2.class, string[1]));
            } else if (string[0].equalsIgnoreCase(MESSAGE.BATTLE_TARGET.toString())) {
                target.set(json.fromJson(Vector2.class, string[1]));
            } else if (string[0].equalsIgnoreCase(MESSAGE.GET_DAMAGE.toString())) {
                getDamage(json.fromJson(int.class, string[1]));
            } else if (string[0].equalsIgnoreCase(MESSAGE.LOAD_CONFIG.toString())) {
                EntityConfig config = json.fromJson(EntityConfig.class, string[1]);

                health = config.getMaxHealthPoints();
            }
        }
    }

    @Override
    public void update(Entity entity, Batch batch, Batch hudBatch, float delta) {
        Vector2 direction = new Vector2(target).sub(currentPosition).nor();
        entity.sendMessage(MESSAGE.ENTITY_DIRECTION, json.toJson(direction));
    }
}
