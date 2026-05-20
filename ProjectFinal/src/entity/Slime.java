package entity;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Slime extends Enemy {

    public Slime(GamePanel gp, int worldX, int worldY) {

        super(gp, worldX, worldY, 2, "down");

        solidArea = new Rectangle(3,18,42,30);
        solidAreaDefaultX = solidArea.x; 
        solidAreaDefaultY = solidArea.y;
    }

    @Override
    public void loadImage() {

        try {

            // DOWN
            front_idle = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_down_idle.png"
                )
            );

            front_walk1 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_down_1.png"
                )
            );

            front_walk2 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_down_2.png"
                )
            );

            // UP
            behind_idle = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_up_idle.png"
                )
            );

            behind_walk1 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_up_1.png"
                )
            );

            behind_walk2 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_up_2.png"
                )
            );

            // LEFT
            left_idle = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_left_idle.png"
                )
            );

            left_walk1 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_left_1.png"
                )
            );

            left_walk2 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_left_2.png"
                )
            );

            // RIGHT
            right_idle = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_right_idle.png"
                )
            );

            right_walk1 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_right_1.png"
                )
            );

            right_walk2 = ImageIO.read(
                getClass().getResourceAsStream(
                    "/assets/monster/slime_right_2.png"
                )
            );

        }
        catch(IOException e) {

            e.printStackTrace();
        }
    }
}