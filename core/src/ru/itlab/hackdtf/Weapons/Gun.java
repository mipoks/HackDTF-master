package ru.itlab.hackdtf.Weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import ru.itlab.hackdtf.Characters.ActionButton;
import ru.itlab.hackdtf.Characters.Player;

public class Gun extends Actor {

    Texture texture;
    Vector2 size;
    int type;
    Stage stage;
    World world;
    float reload, reloadTime = 5;
    public int bulletCount;

    public boolean isDropped = false;
    Rectangle rectangle;
    Player player;

    int sumOfBullets = 0, maxSumOfBullets;
    float timeForNextBullet;

    public Vector2 pos = new Vector2(0, 0);
    public float angleInDeg = 0;

    public Gun(Stage stage, World world, int type, boolean isEnemy, Player player) {
        this.player = player;
        this.type = type;
        chooseGun();
        if (isEnemy) bulletCount = 99999;
        this.stage = stage;
        this.world = world;
        rectangle = new Rectangle(0, 0, 0, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,
                pos.x,
                pos.y,
                0, //center for rotation and scaling x
                0, //center for rotation and scaling y
                size.x, size.y,
                1, -1, //scale from center
                angleInDeg,
                0, 0, //coordinates in image file
                texture.getWidth(), //size in image file
                texture.getHeight(),
                false, false);
    }

    @Override
    public void act(float delta) {
        reloadTime += delta;
        if (reloadTime > timeForNextBullet && sumOfBullets < maxSumOfBullets - 1 && sumOfBullets >= 0) {
            reloadTime = 0;
            sumOfBullets++;
            stage.addActor(new Bullet((float) Math.toRadians(angleInDeg), pos, world, stage));
        }
        rectangle.set(pos.x, pos.y, size.y, size.x);
        if(rectangle.overlaps(new Rectangle(player.body.getBody().getTransform().getPosition().x - player.body.getShape().getRadius(),
                player.body.getBody().getTransform().getPosition().y - player.body.getShape().getRadius(),
                player.body.getShape().getRadius()*2, player.body.getShape().getRadius()*2))
        && !player.gun.equals(this) && isDropped){
            Gdx.app.log("Can take", "Yes");
            ActionButton.setCanTake(true, this);
        } else ActionButton.setCanTake(false, this);
    }

    public void shoot() {
        if (reloadTime > reload && sumOfBullets == maxSumOfBullets - 1) {
            reloadTime = 0;
            sumOfBullets = 0;
            stage.addActor(new Bullet((float) Math.toRadians(angleInDeg), pos, world, stage));
        }
    }

    public void destroy() {
        stage.getActors().removeValue(this, false);
    }

    public void chooseGun() {
        switch (type) {
            case 1:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(30, 10);
                reload = 0.6f;
                maxSumOfBullets = 1;
                timeForNextBullet = 0;
                bulletCount = (int) (2 + TimeUtils.millis() % 4);
                break;
            case 2:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(20, 20);
                reload = 1;
                maxSumOfBullets = 3;
                timeForNextBullet = 0.2f;
                bulletCount = (int) (10 + TimeUtils.millis() % 6);
                break;
            case 3:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(10, 30);
                reload = 1;
                maxSumOfBullets = 4;
                timeForNextBullet = 0;
                bulletCount = (int) (16 + TimeUtils.millis() % 9);
                break;
            case 4:
                texture = new Texture(Gdx.files.internal("enemy.png"));
                size = new Vector2(50, 20);
                reload = 0.2f;
                maxSumOfBullets = 1;
                timeForNextBullet = 0;
                bulletCount = (int) (10 + TimeUtils.millis() % 11);
                break;

                //TODO set texture, bullet count, size, reload
        }
        sumOfBullets = maxSumOfBullets - 1;
    }

    public void updatePos(Vector2 pos, float angleInDeg, float size) {
        this.pos.x = (float) (pos.x + (size / 2) * Math.cos(Math.toRadians(angleInDeg)));
        this.pos.y = (float) (pos.y + (size / 2) * Math.sin(Math.toRadians(angleInDeg)));
        this.angleInDeg = angleInDeg;
    }
}
