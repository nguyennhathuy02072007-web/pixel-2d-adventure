package input;

import java.awt.event.KeyListener;

import main.GamePanel;

import java.awt.event.KeyEvent;


public class InputHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed; 
    GamePanel gp;

    public InputHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE) {
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }else if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }

        }


            
        
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = 1;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState; // Bắt đầu chơi
            // gp.playMusic(0); // Có thể bật nhạc tại đây
                }
                if (gp.ui.commandNum == 1) {
                    System.exit(0); // Thoát game
                }
            }
        }

        if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) gp.ui.commandNum = 1;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState; // Quay lại chơi tiếp
                }
                if (gp.ui.commandNum == 1) {
                    gp.stopMusic();
                    gp.gameState = gp.titleState; 
                    gp.playMusic(0);
                }
            }
        }

            // Người chơi chỉ có thể bấm ENTER để xác nhận thoát
            // Đổi toàn bộ chữ Slate thành State
        if (gp.gameState == gp.gameOverState) {
            
            // Người chơi chỉ có thể bấm ENTER để xác nhận thoát
            if (code == KeyEvent.VK_ENTER) {
                
                gp.stopMusic();             // Dừng nhạc nền hiện tại
                
                // GỌI HÀM RESET GAME (Nếu bạn đã có hàm đặt lại vị trí quái và hồi máu)
                gp.resetGame(); 
                
                gp.gameState = gp.titleState; // Đưa người chơi về thẳng Menu chính
                gp.playMusic(0);            // Bật lại nhạc nền của Menu chính
            } 
        }
        else if (gp.gameState == gp.winState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.stopMusic(); 
                gp.resetGame();               // Hồi máu, reset vị trí player
                gp.gameState = gp.titleState; // Đưa về Menu chính
                gp.playMusic(0);              // Bật lại nhạc nền Menu
            }
        }
    }
    

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }    
    }
}

    

