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

    // GET PLAYER
    public Player getPlayer() {
        return player;
    }


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
        
// CHECK TILE KHÔNG COLLISION
if(!tileM.tile[tileM.mapTileNum[col][row]].collision) {

    int x = col * tileSize;
    int y = row * tileSize;

    // SAFE ZONE
    int safeZone = tileSize * 5;

    int distanceX = Math.abs(x - player.getWorldX());
    int distanceY = Math.abs(y - player.getWorldY());

    // KHÔNG SPAWN GẦN PLAYER
    if(distanceX > safeZone || distanceY > safeZone) {

        monster[spawned] = new Slime(this, x, y);
        System.out.println("Slime " + spawned + " spawned");
        spawned++;
    }
}
        }
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
            // Vòng lặp cập nhật và tính va chạm với tất cả quái vật
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();

                    // Tính khoảng cách giữa player và monster[i]
                    int diffX = Math.abs(player.getWorldX() - monster[i].getWorldX()); 
                    int diffY = Math.abs(player.getWorldY() - monster[i].getWorldY()); 

                    // Nếu va chạm xảy ra VÀ đã hết thời gian bất tử (damageCooldown đã về bằng 0)
                    if (diffX < tileSize - 10 && diffY < tileSize - 10) {
                        
                        player.life -= 1;     // Giảm 1 máu 
                        isHurt = true;        // Kích hoạt chớp đỏ màn hình bên UI
                        hurtCounter = 0;      // Đặt lại bộ đếm chớp đỏ để bắt đầu chu kỳ mới
                        
                        playSE(1);            // Phát âm thanh punch.wav (Không gõ chữ i:)
                        damageCooldown = 60;  // Khóa nhận sát thương trong 60 khung hình tiếp theo (1 giây)

                        System.out.println("Quái cắn! Máu còn: " + player.life);
                        break;
                    }
                }
            }
        }else {
            // Nếu đang bất tử (damageCooldown > 0), quái vẫn di chuyển nhưng không thể gây sát thương
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    monster[i].update();
                }
            }
        }

            if (player.life <= 0) {
                    gameState = gameOverState; // Đổi biến gameState (Dùng dấu = chuẩn)
                    ui.commandNum = 0;         // Đặt lại con trỏ Menu Game Over về dòng đầu tiên
            }
            int gateTileX = 48; 
            int gateTileY = 48;

            // Tính xem Player hiện tại đang đứng ở ô gạch số mấy
            int playerTileX = player.getWorldX() / tileSize;
            int playerTileY = player.getWorldY() / tileSize;

            if (playerTileX == gateTileX && playerTileY == gateTileY) {
                
                stopMusic();            // Dừng nhạc nền map chơi
                playSE(2);           // Phát âm thanh chiến thắng (nếu bạn có file win.wav ở index 2)
                
                gameState = winState; // Chuyển trạng thái game sang THẮNG!
                ui.commandNum = 0;    // Đặt lại con trỏ menu trên màn hình Win
            }
        
        if(gameState == pauseState){
            //nothing
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
            ui.draw(g2); // Vẽ menu chính (chữ Adventure Game, nút chọn...)
        } else {
            // Nếu đang trong game thì vẽ Bản đồ, Người chơi, Quái vật trước...
            // tileM.draw(g2);
            // player.draw(g2);
            
            // Cuối cùng vẽ UI (Thanh máu HUD, Màn hình Pause, Màn hình GameOver)
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
        player.life = player.maxLife; // Hồi đầy lại máu cho người chơi
        isHurt = false;
        damageCooldown = 0;
        // (Thay số 1 bằng số Cột/Hàng ô gạch bạn muốn nhân vật xuất hiện)

        // Đặt lại tọa độ hồi sinh mặc định cho Player tránh bị kẹt ở bầy quái cũ
        player.setWorldX(tileSize * 13);
        player.setWorldY(tileSize * 12);
    }
}