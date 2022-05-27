import view.ChessGameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Music music = new Music();
            ChessGameFrame mainFrame = new  ChessGameFrame(1000, 760);
            JFrame HomeFrame=new JFrame("Home");
            JPanel HomePanel = new JPanel(null);
            HomeFrame.setVisible(true);
            HomeFrame.setContentPane(HomePanel);
            HomeFrame.setSize(800,800);
            HomeFrame.setLocationRelativeTo(null);
            HomeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JButton startGame=new JButton("Start Game");
            HomePanel.add(startGame);
            startGame.setSize(200,200);
            startGame.setBounds(10,10,200,200);
            startGame.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    HomeFrame.dispose();
                    mainFrame.setVisible(true);
                    music.playMusic();
                }
            });

        });
    }
}