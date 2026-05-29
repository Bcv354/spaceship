package space.battle.objects;

import com.badlogic.gdx.graphics.Color;

/**
 * Параметры типа мусора: размер, скорость, HP, очки и цвет при отрисовке.
 */
public enum TrashType {

    SMALL(0.75f, 1.35f, 1, 50, new Color(0.55f, 0.85f, 1f, 1f)),
    NORMAL(1f, 1f, 1, 100, Color.WHITE),
    HEAVY(1.3f, 0.65f, 3, 300, new Color(1f, 0.45f, 0.35f, 1f));

    public final float sizeScale;
    public final float speedScale;
    public final int maxHp;
    public final int scoreValue;
    public final Color tint;

    TrashType(float sizeScale, float speedScale, int maxHp, int scoreValue, Color tint) {
        this.sizeScale = sizeScale;
        this.speedScale = speedScale;
        this.maxHp = maxHp;
        this.scoreValue = scoreValue;
        this.tint = tint;
    }
}
