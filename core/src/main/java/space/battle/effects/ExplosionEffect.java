package space.battle.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

/**
 * Короткая вспышка из цветных «искр» — рисуем белый пиксель с batch.setColor().
 */
public class ExplosionEffect {

    private static Texture pixel;

    private final Particle[] particles;
    private float timeLeft;

    private static class Particle {
        float x, y, vx, vy, size, life, maxLife;
        Color color;
    }

    public static void ensurePixelLoaded() {
        if (pixel == null) {
            Pixmap pixmap = new Pixmap(4, 4, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();
            pixel = new Texture(pixmap);
            pixmap.dispose();
        }
    }

    public static void disposeShared() {
        if (pixel != null) {
            pixel.dispose();
            pixel = null;
        }
    }

    public ExplosionEffect(float centerX, float centerY, Color baseColor, int particleCount) {
        ensurePixelLoaded();
        particles = new Particle[particleCount];
        for (int i = 0; i < particleCount; i++) {
            Particle p = new Particle();
            float angle = MathUtils.random(0f, MathUtils.PI2);
            float speed = MathUtils.random(80f, 220f);
            p.x = centerX;
            p.y = centerY;
            p.vx = MathUtils.cos(angle) * speed;
            p.vy = MathUtils.sin(angle) * speed;
            p.size = MathUtils.random(6f, 16f);
            p.maxLife = MathUtils.random(0.25f, 0.55f);
            p.life = p.maxLife;
            p.color = new Color(baseColor);
            p.color.r += MathUtils.random(-0.15f, 0.15f);
            p.color.g += MathUtils.random(-0.1f, 0.1f);
            particles[i] = p;
        }
        timeLeft = 0.6f;
    }

    public void update(float delta) {
        timeLeft -= delta;
        for (Particle p : particles) {
            p.life -= delta;
            p.x += p.vx * delta;
            p.y += p.vy * delta;
            p.vy -= 120f * delta;
        }
    }

    public boolean isFinished() {
        return timeLeft <= 0f;
    }

    public void draw(SpriteBatch batch) {
        for (Particle p : particles) {
            if (p.life <= 0f) continue;
            float alpha = Math.max(0f, p.life / p.maxLife);
            batch.setColor(p.color.r, p.color.g, p.color.b, alpha);
            float half = p.size / 2f;
            batch.draw(pixel, p.x - half, p.y - half, p.size, p.size);
        }
        batch.setColor(Color.WHITE);
    }
}
