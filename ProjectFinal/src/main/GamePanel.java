package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import input.InputHandler;

import entity.Player;
import entity.Enemy;
import entity.Slime;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale = 4;

    public final int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 20;
    public final int maxScreenRow = 15;

    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // GAME
    private Thread gameThread;

    private final int TARGET_FPS = 60;

    private boolean running = false;

    // INPUT
    public InputHandler inputhandler = new InputHandler(this);

    // PLAYER
    private Player player = new Player(this, inputhandler, 4, "down");

    // MONSTERS
    public Enemy[] monster = new Enemy[50];

    // FPS COUNTER
    private int frames = 0;
    private int CurrentFPS = 0;

    private long lastTime = System.currentTimeMillis();

    // COLLISION
    public CollisionChecker cChecker = new CollisionChecker(this);

    // TILE MANAGER
    public tile.TileManager tileM = new tile.TileManager(this);




    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    public final int winState = 4;


    public int diffX;
    public int diffY;



    public UI ui = new UI(this);
    public Sound music = new Sound();
    public Sound se = new Sound(); 

    public void setupGame() {
        gameState = titleState;
        playMusic(0);
    }

    public boolean isHurt = false;
    public int hurtCounter = 0;
    public int damageCooldown = 0;

    public void playMusic(int i) {
    music.setFile(i);
    music.play();
    music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }




    // CONSTRUCTOR
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        this.setBackground(Color.black);

        this.setDoubleBuffered(true);

        this.setFocusable(true);

        this.addKeyListener(inputhandler);

        // SPAWN RANDOM SLIMES
        spawnSlimes(50);
    }

    // SPAWN RANDOM SLIMES
    public void spawnSlimes(int amount) {

        int spawned = 0;

        while(spawned < amount) {

          int col = (int)(Math.random() * maxWorldCol);
        int row = (int)(Math.random() * maxWorldRow);
        
// CHECK TILE
if(!tileM.tile[tileM.mapTileNum[col][row]].collision) {

    int x = col * tileSize;
    int y = row * tileSize;

    // SAFE ZONE
    int safeZone = tileSize * 5;

    int distanceX = Math.abs(x - player.getWorldX());
    int distanceY = Math.abs(y - player.getWorldY());

    //NOT SPAWN NEAR PLAYER
    if(distanceX > safeZone || distanceY > safeZone) {

        monster[spawned] = new Slime(this, x, y);
        System.out.println("Slime " + spawned + " spawned");
        spawned++;
    }
}
        }
    }

        // GETTER
    public Player getPlayer() {
        return player;
    }

    // START GAME THREAD
    public void startGameThread() {

        running = true;

        gameThread = new Thread(this);

        gameThread.start();

        this.requestFocusInWindow();
    }

    // UPDATE
    public void update() {


        if(gameState == playState){
            player.update();
        }
        if (damageCooldown > 0) {
            damageCooldown--;
        }
        if (damageCooldown == 0) {
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();

                    int diffX = Math.abs(player.getWorldX() - monster[i].getWorldX()); 
                    int diffY = Math.abs(player.getWorldY() - monster[i].getWorldY()); 

                    if (diffX < tileSize - 10 && diffY < tileSize - 10) {
                        
                        player.life -= 1;    
                        isHurt = true;       
                        hurtCounter = 0;      
                        
                        playSE(1);          
                        damageCooldown = 60;  

                        System.out.println("Enemy attack! Current Health: " + player.life);
                        break;
                    }
                }
            }
        }else {
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();
                }
            }
        }

            if (player.life <= 0) {
                    gameState = gameOverState; 
                    ui.commandNum = 0;        
            }
            int gateTileX = 48; 
            int gateTileY = 48;

            int playerTileX = player.getWorldX() / tileSize;
            int playerTileY = player.getWorldY() / tileSize;

            if (playerTileX == gateTileX && playerTileY == gateTileY) {
                
                stopMusic();            
                playSE(2);           
                
                gameState = winState; 
                ui.commandNum = 0;    
            }
        
        if(gameState == pauseState){
        }
        
    }

 
    // GAME LOOP
    @Override
    public void run() {

        double drawInterval = 1000000000 / TARGET_FPS;

        double nextDrawTime = System.nanoTime() + drawInterval;

        while(running) {

            update();

            repaint();

            try {

                double remainingTime = nextDrawTime - System.nanoTime();

                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            }
            catch(InterruptedException e) {

                e.printStackTrace();
            }
        }
    }

    // STOP GAME
    public void stopGame() {

        running = false;
    }

    // DRAW
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g;

        // DRAW MAP
        tileM.draw(g2);

        // DRAW MONSTERS
        for(int i = 0; i < monster.length; i++) {

            if(monster[i] != null) {

                monster[i].draw(g2);
            }
        }


        // DRAW PLAYER
        player.draw(g2);
        if (gameState == titleState) {
            ui.draw(g2); 
        } else {

            ui.draw(g2);
        }
        // FPS COUNTER
        frames++;

        long now = System.currentTimeMillis();

        if(now - lastTime >= 1000) {

            CurrentFPS = frames;

            frames = 0;

            lastTime += 1000;

            System.out.println("FPS: " + CurrentFPS);
        }

        // DRAW FPS
        g2.setColor(Color.green);

        g2.drawString("FPS: " + CurrentFPS, 10, 20);

        g2.dispose();
    }
    public void resetGame() {
        player.life = player.maxLife; 
        isHurt = false;
        damageCooldown = 0;

        player.setWorldX(tileSize * 13);
        player.setWorldY(tileSize * 12);
    }
}