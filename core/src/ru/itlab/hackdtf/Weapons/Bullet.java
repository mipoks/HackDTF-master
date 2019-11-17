package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import ru.itlab.hackdtf.CreateFixture;
import ru.itlab.hackdtf.Screens.GameScreen;

public class Bullet extends Actor {

    Texture texture;
    Stage stage;
    Fixture body;
    boolean isSlowLast = false;
    int speed = 999999;
    float angleInRad;

    public Bullet(float angleInRad, Vector2 pos, World world, Stage stage) {
        this.stage = stage;
        this.angleInRad = angleInRad;
        body = CreateFixture.createCircle(world, pos, 2.5f, false, "pBullet", (short) 1);
        body.getBody().setTransform(pos, angleInRad);
        texture = new Texture(Gdx.files.internal("player.png"));//TODO add texture
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,
                body.getBody().getPosition().x - body.getShape().getRadius(),
                body.getBody().getPosition().y - body.getShape().getRadius(),
                body.getShape().getRadius() + 0, //center for rotation and scaling x
                body.getShape().getRadius() + 0, //center for rotation and scaling y
                body.getShape().getRadius() * 2,
                body.getShape().getRadius() * 2,
                1, 1, //scale from center
                (float) Math.toDegrees(body.getBody().getAngle()) + 0,
                0, 0, //coordinates in image file
                texture.getWidth() + 0, //size in image file
                texture.getHeight() + 0,
                false, false);
    }

    @Override
    public void act(float delta) {
        if(isSlowLast != GameScreen.isSlow)
            useBraking(GameScreen.isSlow);
        body.getBody().setTransform(body.getBody().getPosition(), angleInRad);
        body.getBody().setLinearVelocity((float) Math.cos(body.getBody().getAngle()) * speed * delta,
                (float) Math.sin(body.getBody().getAngle()) * speed * delta);
    }

    public void destroy() {
        Gdx.app.log("Bullet", "deleted");
        stage.getActors().removeValue(this, false);
    }

    public void useBraking(boolean isSlow) {
        if (isSlow) speed /= GameScreen.braker;
        else speed *= GameScreen.braker;
        isSlowLast = isSlow;
    }
}
