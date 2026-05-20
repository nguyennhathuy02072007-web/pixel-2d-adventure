package main;
 

//IMPORT
import entity.Entity;
 

//ATTRIBUTES AND CONSTRUCTOR
public class CollisionChecker {

    private final GamePanel gp;
    public static final int NO_ENTITY = -1;
 
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }


    //METHODS
    public void checkTile(Entity entity) {

        int leftWorldX = entity.getWorldX() + entity.getSolidAreaDefaultX();
        int rightWorldX = entity.getWorldX() + entity.getSolidAreaDefaultX() + entity.getSolidArea().width;
    
        int topWorldY = entity.getWorldY() + entity.getSolidAreaDefaultY();
        int bottomWorldY = entity.getWorldY() + entity.getSolidAreaDefaultY() + entity.getSolidArea().height;
            
        int leftCol = leftWorldX / gp.tileSize;
        int rightCol = rightWorldX / gp.tileSize;
            
        int topRow = topWorldY / gp.tileSize;
        int bottomRow = bottomWorldY / gp.tileSize;
            
        int tileNum1, tileNum2;
    
        // UP
        if(entity.getDirection().equals("up")) {
            
            topRow = (topWorldY - entity.getSpeed()) / gp.tileSize;
                
            tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
            tileNum2 = gp.tileM.mapTileNum[rightCol][topRow];
                
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                
                entity.setCollisionOn(true);

            }
        }
    
        // DOWN
        else if(entity.getDirection().equals("down")) {
            
            bottomRow = (bottomWorldY + entity.getSpeed()) / gp.tileSize;
                
            tileNum1 = gp.tileM.mapTileNum[leftCol][bottomRow];
            tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    
                entity.setCollisionOn(true);

            }
        }
    
        // LEFT
        else if(entity.getDirection().equals("left")) {
            
            leftCol = (leftWorldX - entity.getSpeed()) / gp.tileSize;
                
            tileNum1 = gp.tileM.mapTileNum[leftCol][topRow];
            tileNum2 = gp.tileM.mapTileNum[leftCol][bottomRow];
                
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                
                entity.setCollisionOn(true);

            }
        }
        
        // RIGHT
        else if(entity.getDirection().equals("right")) {
            
            rightCol = (rightWorldX + entity.getSpeed()) / gp.tileSize;
                
            tileNum1 = gp.tileM.mapTileNum[rightCol][topRow];
            tileNum2 = gp.tileM.mapTileNum[rightCol][bottomRow];
                
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                
                entity.setCollisionOn(true);

            }
        }
    }

    public int checkEntity(Entity entity, Entity[] target) {

        int index = NO_ENTITY;

        for(int i = 0; i < target.length; i++) {

            if(target[i] != null && target[i] != entity) {

                // Entity current solid area
                entity.getSolidArea().x = entity.getWorldX() + entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getWorldY() + entity.getSolidAreaDefaultY();

                // Target current solid area
                target[i].getSolidArea().x = target[i].getWorldX() + target[i].getSolidAreaDefaultX();
                target[i].getSolidArea().y = target[i].getWorldY() + target[i].getSolidAreaDefaultY();

                // Simulate next move
                switch(entity.getDirection()) {

                    case "up":
                        entity.getSolidArea().y -= entity.getSpeed();
                        break;

                    case "down":
                        entity.getSolidArea().y += entity.getSpeed();
                        break;

                    case "left":
                        entity.getSolidArea().x -= entity.getSpeed();
                        break;

                    case "right":
                        entity.getSolidArea().x += entity.getSpeed();
                        break;
                }

                // Check collision
                if(entity.getSolidArea().intersects(target[i].getSolidArea())) {

                    index = i;

                    // Only block movement if target is solid
                    if(target[i].isCollision()) {
                        entity.setCollisionOn(true);
                    }
                }

                // Reset
                entity.getSolidArea().x = entity.getSolidAreaDefaultX();
                entity.getSolidArea().y = entity.getSolidAreaDefaultY();

                target[i].getSolidArea().x =
                        target[i].getSolidAreaDefaultX();

                target[i].getSolidArea().y =
                        target[i].getSolidAreaDefaultY();
            }
        }

        return index;
    }
    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;

        entity.getSolidArea().x =
                entity.getWorldX() + entity.getSolidAreaDefaultX();

        entity.getSolidArea().y =
                entity.getWorldY() + entity.getSolidAreaDefaultY();

        gp.getPlayer().getSolidArea().x =
                gp.getPlayer().getWorldX() + gp.getPlayer().getSolidAreaDefaultX();

        gp.getPlayer().getSolidArea().y =
                gp.getPlayer().getWorldY() + gp.getPlayer().getSolidAreaDefaultY();

        switch(entity.getDirection()) {

            case "up":
                entity.getSolidArea().y -= entity.getSpeed();
                break;

            case "down":
                entity.getSolidArea().y += entity.getSpeed();
                break;

            case "left":
                entity.getSolidArea().x -= entity.getSpeed();
                break;

            case "right":
                entity.getSolidArea().x += entity.getSpeed();
                break;
        }

        if(entity.getSolidArea().intersects(gp.getPlayer().getSolidArea())) {

            entity.setCollisionOn(true);
            contactPlayer = true;
        }

        entity.getSolidArea().x = entity.getSolidAreaDefaultX();
        entity.getSolidArea().y = entity.getSolidAreaDefaultY();

        gp.getPlayer().getSolidArea().x =
                gp.getPlayer().getSolidAreaDefaultX();

        gp.getPlayer().getSolidArea().y =
                gp.getPlayer().getSolidAreaDefaultY();

        return contactPlayer;
    }
}