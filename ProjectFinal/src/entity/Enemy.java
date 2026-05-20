package entity;

//IMPORT
import main.GamePanel;
import java.util.Random;

//ATTRIBUTES AND CONSTRUCTOR
public abstract class Enemy extends Entity {

    private int range = 50;
    private int startX;
    private int startY;

    public Enemy(GamePanel gp, int x, int y, int speed, String direction) {
        super(gp, x, y, speed, direction);
        this.startX = x;
        this.startY = y;
        collision = true;
        loadImage();
    }

    public abstract void loadImage();

    public void movement() {

        actionLockCounter++;

        if(actionLockCounter == 60) {

            Random rad = new Random();
            int i = rad.nextInt(4);

            //East
            if(i == 0) {
                direction = "right";
            }

            //West
            if(i == 1) {
                direction = "left";
            }

            //South
            if(i == 2) {
                direction = "down";
            }

            //North
            if(i == 3) {
                direction = "up";
            }

            actionLockCounter = 0;
        }
    }

    public void checkBoundary() {

        if(worldX > startX + range) {
            direction = "left";
        }

        if(worldX < startX - range) {
            direction = "right";
        }

        if(worldY > startY + range) {
            direction = "up";
        }

        if(worldY < startY - range) {
            direction = "down";
        }
    }

    public void setAction() {
        movement();
        checkBoundary();
    }

    public void update() {

        setAction();

        collisionOn = false;

        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);

        if(collisionOn == false) {

            switch(direction) {

                case "up":
                    worldY -= speed;
                    break;

                case "down":
                    worldY += speed;
                    break;

                case "left":
                    worldX -= speed;
                    break;

                case "right":
                    worldX += speed;
                    break;
            }
        }    
    }
}
