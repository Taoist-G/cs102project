import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("x");
        System.out.println("y");
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
            mainFrame.setVisible(true);
        });
    }
}
