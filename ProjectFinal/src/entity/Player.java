package entity;

import main.CollisionChecker;
//import
import main.GamePanel;
import input.InputHandler;

import java.awt.Rectangle;

import javax.imageio.ImageIO;

//methods
public class Player extends Entity {

    public final InputHandler inputhandler;

    public int maxLife = 6; 
    public int life = maxLife;
    
    public boolean invincible = false; 
    public int invincibleCounter = 0;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, InputHandler inputhandler, int speed, String direction) {
        super(gp, 0, 0, speed, direction);
        this.inputhandler = inputhandler;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        this.worldX = gp.tileSize * 1; 
        this.worldY = gp.tileSize * 1;
        solidArea = new Rectangle(12, 28, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        loadImage();
    }

    public void loadImage() {

        try{

            front_idle = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-front-idle.png"));
            front_walk1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-front-walk-right.png"));
            front_walk2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-front-walk-left.png"));

            behind_idle = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-behind-idle.png"));
            behind_walk1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-behind-walk-right.png"));
            behind_walk2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-behind-walk-left.png"));

            right_idle = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-right-idle.png"));
            right_walk1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-right-walk-right.png"));
            right_walk2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-right-walk-left.png"));

            left_idle = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-left-idle.png"));
            left_walk1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-left-walk-right.png"));
            left_walk2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/Main-left-walk-left.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        isMoving = false;

        if(inputhandler.upPressed) {
            direction = "up";
            isMoving = true;
        }

        else if(inputhandler.downPressed) {
            direction = "down";
            isMoving = true;
        }

        else if(inputhandler.leftPressed) {
            direction = "left";
            isMoving = true;
        }

        else if(inputhandler.rightPressed) {
            direction = "right";
            isMoving = true;
        }

        collisionOn = false;

        gp.cChecker.checkTile(this);

        int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
        interactMonster(monsterIndex);

        if(isMoving && collisionOn == false) {

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

        if(isMoving) {

            spriteCounter++;

            if(spriteCounter > 12) {
                if(spriteNum == 0) {
                    spriteNum = 1;
                } else {
                    spriteNum = 0;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 0;
        }

        if(invincible == true) {
            invincibleCounter++;
            
            if(invincibleCounter > 60) { 
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void damagePlayer() {
        if(invincible == false) {
            life -= 1; 
            invincible = true; 
            
            System.out.println("Player took a hit! Current health: " + life);
        }
    }

    public void interactMonster(int i) {
        if(i != CollisionChecker.NO_ENTITY) {
            damagePlayer();
        }
    }
}