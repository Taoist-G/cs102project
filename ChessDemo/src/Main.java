import controller.ClickController;
import view.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Music music = new Music();
            ClickController.play();
            ChessGameFrame mainFrame = new  ChessGameFrame(1000, 760);

            JFrame HomeFrame=new JFrame("Home");
            JPanel HomePanel = new JPanel(null);
            HomeFrame.setVisible(true);

            HomeFrame.setContentPane(HomePanel);
            HomeFrame.setSize(1000,760);
            HomeFrame.setLocationRelativeTo(null);
            HomeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JButton startGame=new JButton("");
            HomePanel.add(startGame);
            startGame.setSize(200,200);
            startGame.setBounds(543,330,402,150);
            startGame.setContentAreaFilled(false);

            ImageIcon start = new ImageIcon("./images/start.png");
            Image image = start.getImage();
            Image smallImage = image.getScaledInstance(1000, 760, Image.SCALE_FAST);
            ImageIcon starts = new ImageIcon(smallImage);
            JLabel jlabel = new JLabel(starts);
            jlabel.setBounds(0,0, 1000,760 );
            HomeFrame.add(jlabel);



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