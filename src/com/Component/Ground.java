package com.Component;

import com.jade.Component;
import com.jade.GameObject;
import com.jade.LevelScene;
import com.jade.Window;
import com.util.Constants;

import java.awt.*;

public class Ground extends Component {

    @Override
    public void update(double dt){

        if (!Window.getWindow().isInEditor){
            LevelScene scene = (LevelScene) Window.getWindow() .getCurrentScene();
            GameObject player = scene.player;

            if (player.transform.position.y + player.getComponent(BoxBounds.class).height >
                    gameObject.transform.position.y){
                player.transform.position.y = gameObject.transform.position.y -
                        player.getComponent(BoxBounds.class).height;

                player.getComponent(Player.class).onGround = true;
            }
            gameObject.transform.position.x = scene.camera.position.x - 10;
        }
        else {
            gameObject.transform.position.x = Window.getWindow().getCurrentScene().camera.position.x - 10;
        }

    }

    @Override
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.drawRect((int) gameObject.transform.position.x ,(int) gameObject.transform.position.y ,
                Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT); // -10, +20

    }

    @Override
    public Component copy() {
        return null;
    }

    @Override
    public String serialize(int tabsize) {
        return "";
    }
}
