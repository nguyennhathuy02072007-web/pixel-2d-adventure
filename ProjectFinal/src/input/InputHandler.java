package input;


//IMPORT
import java.awt.event.KeyListener;
import main.GamePanel;
import java.awt.event.KeyEvent;


//ATTRIBUTES AND CONSTRUCTOR
public class InputHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed; 
    public GamePanel gp;

    public InputHandler(GamePanel gp) {
        this.gp = gp;
    }


    //METHODS
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
                    gp.gameState = gp.playState; //Start game
            // gp.playMusic(0); 
                }
                if (gp.ui.commandNum == 1) {
                    System.exit(0); // Out game
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
                    gp.gameState = gp.playState; // play again
                }
                if (gp.ui.commandNum == 1) {
                    gp.stopMusic();
                    gp.gameState = gp.titleState; 
                    gp.playMusic(0);
                }
            }
        }


        if (gp.gameState == gp.gameOverState) {
            
            if (code == KeyEvent.VK_ENTER) {
                
                gp.stopMusic();             // stop current music
                
                gp.resetGame(); 
                
                gp.gameState = gp.titleState; // return menu
                gp.playMusic(0);            // play music at menu
            } 
        }
        else if (gp.gameState == gp.winState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.stopMusic(); 
                gp.resetGame();               // heal, reset player location
                gp.gameState = gp.titleState; // return menu
                gp.playMusic(0);              // play music at menu
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

    

