package space.battle.effects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Случайное смещение камеры; trauma затухает со временем.
 */
public class ScreenShake {

    private float trauma;
    private final Vector2 offset = new Vector2();

    public void addTrauma(float amount) {
        trauma = Math.min(1f, trauma + amount);
    }

    public void update(float delta) {
        trauma = Math.max(0f, trauma - delta * 1.8f);
        if (trauma > 0.01f) {
            float shake = trauma * trauma * 18f;
            offset.set(
                MathUtils.random(-shake, shake),
                MathUtils.random(-shake, shake)
            );
        } else {
            offset.setZero();
        }
    }

    public Vector2 getOffset() {
        return offset;
    }
}
