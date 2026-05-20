package entity;


//IMPORT
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import main.GamePanel;
import java.awt.Rectangle;


//ATTRIBUTE AND CONSTRUCTOR
public class Entity {
    
    protected GamePanel gp;
    protected int worldX, worldY;
    protected int speed;
    protected String direction;
    protected Rectangle solidArea;
    protected boolean collisionOn = false;
    protected int spriteCounter = 0;
    protected int spriteNum = 0;
    protected int actionLockCounter = 0;
    protected boolean isMoving = false;
    protected boolean collision = false;
    protected int solidAreaDefaultX;
    protected int solidAreaDefaultY;
    protected BufferedImage front_idle, front_walk1, front_walk2;
    protected BufferedImage behind_idle, behind_walk1, behind_walk2;
    protected BufferedImage right_idle, right_walk1, right_walk2;
    protected BufferedImage left_idle, left_walk1, left_walk2;

    
    public Entity(GamePanel gp, int worldX, int worldY, int speed, String direction) {
        this.gp = gp;
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.direction = direction; 
    }


    //GETTER AND SETTER
    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) { 
        this.worldX = worldX; 
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) { 
        this.worldY = worldY; 
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getCollisionOn() {
        return collisionOn;
    }

    public void setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }


    //METHODS
    public boolean isCollision() {
        return collision;
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().screenX;
        int screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().screenY;

        switch(direction) {

            case "up":
                if(isMoving) {
                    if(spriteNum == 0) {
                        image = behind_walk1;
                    } else {
                        image = behind_walk2;
                    }
                } else {
                    image = behind_idle;
                }
                break;

            case "down":
                if(isMoving) {
                    if(spriteNum == 0) {
                        image = front_walk1;
                    } else {
                        image = front_walk2;
                    }
                } else {
                    image = front_idle;
                }
                break;

            case "right":
                if(isMoving) {
                    if(spriteNum == 0) {
                        image = right_walk1;
                    } else {
                        image = right_walk2;
                    }
                } else {
                    image = right_idle;
                }
                break;

            case "left":
                if(isMoving) {
                    if(spriteNum == 0) {
                        image = left_walk1;
                    } else {
                        image = left_walk2;
                    }
                } else {
                    image = left_idle;
                }
                break;

        }

        if (worldX + gp.tileSize > gp.getPlayer().worldX - gp.getPlayer().screenX &&
            worldX - gp.tileSize < gp.getPlayer().worldX + gp.getPlayer().screenX &&
            worldY + gp.tileSize > gp.getPlayer().worldY - gp.getPlayer().screenY &&
            worldY - gp.tileSize < gp.getPlayer().worldY + gp.getPlayer().screenY) {
            
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }

}
