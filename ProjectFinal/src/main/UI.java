package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public int commandNum = 0;
    
    Color hpBarColor = new Color(255, 0, 30);
    Color hpBarBgColor = new Color(35, 35, 35);

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        }
        else if (gp.gameState == gp.playState) {
            drawPlayerHUD();
        }
        else if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
        else if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen(g2);
        }
        else if (gp.gameState == gp.winState) {
            drawWinScreen();
        }
    }

    public void drawPlayerHUD() {
        int maxLife = gp.getPlayer().maxLife;
        int currentLife = gp.getPlayer().life;
        
        int barWidth = 200; 
        int barHeight = 15;  
        int x = gp.tileSize; 
        int y = gp.tileSize; 

        g2.setColor(hpBarBgColor);
        g2.fillRect(x, y, barWidth, barHeight);

        g2.setColor(hpBarColor);
        int currentBarWidth = (int) (barWidth * ((double) currentLife / maxLife));
        if (currentBarWidth < 0) currentBarWidth = 0; 
        g2.fillRect(x, y, currentBarWidth, barHeight);

        g2.setColor(Color.white);
        g2.drawRect(x, y, barWidth, barHeight);
        
        if (gp.isHurt) {
            g2.setColor(new Color(255, 0, 0, 75)); 
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            
            gp.hurtCounter++;
            
            if (gp.hurtCounter > 10) { 
                gp.isHurt = false;
                gp.hurtCounter = 0;
            }
            
        }
    }


    
public void drawTitleScreen(Graphics2D g2) { 
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "THE LOST MAZE"; 
        int x = getXforCenteredText(text, g2);
        int y = gp.tileSize * 3;

        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        text = "PLAY";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 4; 
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 1.5; 
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    
    }


    public void drawPauseScreen() {
        g2.setColor(new Color(0, 0, 0, 150)); 
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text, g2);
        int y = gp.screenHeight / 2 - gp.tileSize * 2;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        text = "RESUME";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "QUIT TO TITLE";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 1.5;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        String text;
        int x;
        int y;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
        text = "GAME OVER";
            
        g2.setColor(Color.black);
        x = getXforCenteredText(text, g2);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
            
        g2.setColor(Color.red);
        g2.drawString(text, x - 4, y - 4);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        text = "QUIT TO TITLE";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 4; 
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
            
        g2.drawString(">", x - gp.tileSize, y);
    }
    public void drawWinScreen() {
        
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        String text;
        int x;
        int y;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));

        text = "YOU WIN!";
        g2.setColor(Color.black); 
        x = getXforCenteredText(text, g2);
        y = gp.tileSize * 4;
        g2.drawString(text, x + 4, y + 4);
        
        g2.setColor(Color.yellow); 
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        text = "QUIT TO TITLE";
        x = getXforCenteredText(text, g2);
        y = gp.tileSize * 8;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.drawString(">", x - gp.tileSize, y);
    }

    public int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}