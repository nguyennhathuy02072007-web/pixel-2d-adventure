package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public int commandNum = 0;
    
    // Màu sắc cho thanh máu HUD
    Color hpBarColor = new Color(255, 0, 30);
    Color hpBarBgColor = new Color(35, 35, 35);

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    // --- HÀM DRAW TỔNG QUẢN LÝ CÁC MÀN HÌNH MÀU SẮC ---
    public void draw(Graphics2D g2) {
        this.g2 = g2;

        // 1. Trạng thái Title Screen (Menu chính cũ của bạn)
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        }
        // 2. Trạng thái chơi game bình thường (Vẽ thanh máu HUD)
        else if (gp.gameState == gp.playState) {
            drawPlayerHUD();
        }
        // 3. Trạng thái tạm dừng game (Màn hình Pause cũ của bạn)
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

    // =========================================================================
    // 1. HÀM VẼ THANH MÁU NHÂN VẬT (HUD) & HIỆU ỨNG CHỚP ĐỎ
    // =========================================================================
    public void drawPlayerHUD() {
        // Dùng hàm getPlayer() của GamePanel để tránh hoàn toàn lỗi private/public
        int maxLife = gp.getPlayer().maxLife;
        int currentLife = gp.getPlayer().life;
        
        int barWidth = 200; 
        int barHeight = 15;  
        int x = gp.tileSize; 
        int y = gp.tileSize; 

        // Vẽ nền đen xám sau thanh máu
        g2.setColor(hpBarBgColor);
        g2.fillRect(x, y, barWidth, barHeight);

        // Tính toán tỷ lệ phần trăm máu còn lại để co giãn thanh màu đỏ
        g2.setColor(hpBarColor);
        int currentBarWidth = (int) (barWidth * ((double) currentLife / maxLife));
        if (currentBarWidth < 0) currentBarWidth = 0; 
        g2.fillRect(x, y, currentBarWidth, barHeight);

        // Vẽ viền khung trắng cho đẹp
        g2.setColor(Color.white);
        g2.drawRect(x, y, barWidth, barHeight);
        
        // HIỆU ỨNG CHỚP ĐỎ TOÀN MÀN HÌNH KHI BỊ QUÁI VẬT CẮN
        if (gp.isHurt) {
            g2.setColor(new Color(255, 0, 0, 75)); // Màu đỏ trong suốt
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            
            gp.hurtCounter++;
            
            if (gp.hurtCounter > 10) { // Hiển thị chớp đỏ trong 10 khung hình rồi tắt
                gp.isHurt = false;
                gp.hurtCounter = 0;
            }
            
        }
    }


    
public void drawTitleScreen(Graphics2D g2) { // 1. Thêm Graphics2D g2 vào đây
        // NỀN ĐEN
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // TÊN GAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "THE LOST MAZE"; // 2. Sửa thành 'text' đồng bộ
        int x = getXforCenteredText(text, g2);
        int y = gp.tileSize * 3;

        // ĐỔ BÓNG CHO CHỮ
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);
        // CHỮ CHÍNH
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // ==========================================
        // MENU LỰA CHỌN (PLAY / QUIT) - BỔ SUNG TẠI ĐÂY
        // ==========================================
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        // 1. NÚT PLAY
        text = "PLAY";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 4; // Đẩy tọa độ Y xuống dưới tên Game
        g2.drawString(text, x, y);
        // Nếu đang chọn dòng 0, vẽ dấu mũi tên phía trước chữ Play
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // 2. NÚT QUIT
        text = "QUIT";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 1.5; // Đẩy xuống dưới chữ Play một chút
        g2.drawString(text, x, y);
        // Nếu đang chọn dòng 1, vẽ dấu mũi tên phía trước chữ Quit
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    


        // MENU LỰA CHỌN (Ví dụ code cũ của bạn chạy tiếp ở đây...)
        // ... Hãy để nguyên phần code vẽ nút bấm Start, Quit... của bạn tại đây ...
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

        // MENU LỰA CHỌN PAUSE CỦA BẠN
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
        // Làm tối màn hình nền phía sau
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        String text;
        int x;
        int y;

        // VẼ CHỮ GAME OVER LỚN
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
        text = "GAME OVER";
            
        // Đổ bóng màu đen
        g2.setColor(Color.black);
        x = getXforCenteredText(text, g2);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);
            
        // Chữ chính màu đỏ
        g2.setColor(Color.red);
        g2.drawString(text, x - 4, y - 4);

        // MENU LỰA CHỌN
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        // CHỈ VẼ DUY NHẤT NÚT QUIT TO TITLE
        text = "QUIT TO TITLE";
        x = getXforCenteredText(text, g2);
        y += gp.tileSize * 4; // Đẩy khoảng cách xuống dưới chữ Game Over
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
            
        // Vì chỉ có 1 lựa chọn nên luôn luôn vẽ dấu mũi tên ">" trước chữ này
        g2.drawString(">", x - gp.tileSize, y);
    }
    public void drawWinScreen() {
        
        // Vẽ một lớp nền màu đen mờ phủ lên màn hình game hiện tại
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        String text;
        int x;
        int y;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));

        // 1. VẼ CHỮ "YOU WIN" MÀU VÀNG RỰC RỠ
        text = "YOU WIN!";
        g2.setColor(Color.black); // Vẽ bóng chữ màu đen trước
        x = getXforCenteredText(text, g2);
        y = gp.tileSize * 4;
        g2.drawString(text, x + 4, y + 4);
        
        g2.setColor(Color.yellow); // Vẽ chữ chính màu vàng đè lên
        g2.drawString(text, x, y);

        // 2. VẼ LỰA CHỌN QUAY LẠI MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        text = "QUIT TO TITLE";
        x = getXforCenteredText(text, g2);
        y = gp.tileSize * 8;
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Vẽ dấu mũi tên '>' chỉ vào dòng chữ lựa chọn
        g2.drawString(">", x - gp.tileSize, y);
    }

    public int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }
}