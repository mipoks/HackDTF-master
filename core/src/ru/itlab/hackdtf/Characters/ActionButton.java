package ru.itlab.hackdtf.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import ru.itlab.hackdtf.Characters.Player;
import ru.itlab.hackdtf.Screens.GameScreen;
import ru.itlab.hackdtf.Weapons.Gun;


public class ActionButton extends Actor {
    Texture shootButton, takeButton;
    private int x = 535, y = 30, size = 75;
    public static boolean canTake = false;
    static Gun gun;

    public ActionButton(final Player player) {
        setBounds(x, y, size, size);
        shootButton = new Texture(Gdx.files.internal("actionButton.png"));
        takeButton = new Texture(Gdx.files.internal("player.png"));//TODO set image
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(ActionButton.canTake)player.pickUp(gun);
                else player.gun.shoot();
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(canTake)batch.draw(takeButton, x, y, size, size);
        else batch.draw(shootButton, x, y, size, size);
    }

    public static void setCanTake(boolean ct, Gun g){
        canTake = ct;
        gun = g;
    }

}
