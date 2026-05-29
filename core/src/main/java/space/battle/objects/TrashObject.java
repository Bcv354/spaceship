package space.battle.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import space.battle.base.GameResources;
import space.battle.base.GameSettings;

public class TrashObject extends GameObject {

    private static final int paddingHorizontal = 30;
    private static final Random random = new Random();

    private final TrashType type;
    private int livesLeft;

    public TrashObject(int width, int height, String texturePath, World world, TrashType type) {
        super(
            texturePath,
            width / 2 + paddingHorizontal + random.nextInt(
                Math.max(1, GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)
            ),
            GameSettings.SCREEN_HEIGHT + height / 2,
            width, height,
            GameSettings.TRASH_BIT,
            GameSettings.TRASH_MASK,
            false,
            world
        );
        this.type = type;
        maintainFallSpeed();
        livesLeft = type.maxHp;
    }

    /** Мусор всегда падает с одной скоростью — столкновения не должны его останавливать. */
    public void maintainFallSpeed() {
        body.setLinearVelocity(0, -GameSettings.TRASH_VELOCITY * type.speedScale);
    }

    /** Случайный тип: чаще обычный, реже тяжёлый. */
    public static TrashObject spawnRandom(World world) {
        TrashType type = pickRandomType();
        int width = Math.round(GameSettings.TRASH_WIDTH * type.sizeScale);
        int height = Math.round(GameSettings.TRASH_HEIGHT * type.sizeScale);
        return new TrashObject(width, height, GameResources.TRASH_IMG_PATH, world, type);
    }

    private static TrashType pickRandomType() {
        int roll = random.nextInt(100);
        if (roll < 25) return TrashType.SMALL;
        if (roll < 80) return TrashType.NORMAL;
        return TrashType.HEAVY;
    }

    public TrashType getType() {
        return type;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.setColor(type.tint);
        super.draw(batch);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }
}
