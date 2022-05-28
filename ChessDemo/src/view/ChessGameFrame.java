package view;

import controller.ClickController;
import controller.GameController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.text.StyleConstants.Background;



/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    JLabel statusLabel;
    int backCounter=0;


    public int getBackCounter() {
        return backCounter;
    }

    private GameController gameController;
    Chessboard chessboard = new Chessboard(600, 600);
    public ChessGameFrame(int width, int height) {
        setTitle("Chess Game!"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;


        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
//        setLayout(null);


        addLabel();

        addChessboard(chessboard);

        addLoadButton();
        addRestartButton();
        addBackButton();
        addSaveButton();
        addThemeButton();
        addPlaybackButton();


        //设置背景图
        ImageIcon background = new ImageIcon("./images/bg.png");
        //将背景图进行压缩，一般如果你想显示一整张图片，就得把大小设置跟窗口一样
        Image image = background.getImage();
        Image smallImage = image.getScaledInstance(1000, 760, Image.SCALE_FAST);
        ImageIcon backgrounds = new ImageIcon(smallImage);

        //将图片添加到JLable标签
        JLabel jlabel = new JLabel(backgrounds);
        //设置标签的大小
        jlabel.setBounds(0, 0, getWidth(), getHeight());
        //将图片添加到窗口
        add(jlabel);

    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard(Chessboard chessboard) {
//        Chessboard chessboard = new Chessboard(600, 600);
        chessboard.setStatusLabel(statusLabel);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }




    /**
     * 在游戏面板中添加标签
     */

    //当前行棋方
    private void addLabel() {
        statusLabel = new JLabel("WHITE");
        statusLabel.setLocation(HEIGTH + 40, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
        add(statusLabel);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    //载入游戏
    private void addLoadButton() {
        JButton button = new JButton("LOAD");
        button.setLocation(HEIGTH, HEIGTH / 10 + 100);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");

            ClickController.play();

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("txt文件(*.txt)","txt");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setDialogTitle("打开文件");
            int result = fileChooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
                System.out.println("打开文件："+fileChooser.getSelectedFile().getAbsolutePath());
                File file = fileChooser.getSelectedFile();

                gameController.loadGameFromFile(file.getAbsolutePath());
                ClickController.play();
            }
         String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (path == null || path.isEmpty()) {
                return;
            }
            gameController.loadGameFromFile(path);

        });
    }


    //重置棋盘
    private void addRestartButton() {
        JButton button = new JButton("RESTART");
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            gameController.initialGame();
        });
    }


    //悔棋
    private void addBackButton() {
        JButton button = new JButton("BACK");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click back");
            if (chessboard.clickController.getCounter()==0){
                return;
            }

            backCounter++;
            if ((chessboard.clickController.getCounter()-backCounter-1)<0){
                chessboard.loadGame("RNBQKBNR\nPPPPPPPP\n00000000\n00000000\n00000000\n00000000\npppppppp\nrnbqkbnr\nw");
            }else {
                chessboard.loadGame(chessboard.clickController.getGraph().get(chessboard.clickController.getCounter()-backCounter-1));
            }

        });
    }

//    public void printLog(String msg) {
//        logLabel.setText(msg);
//    }

    //存储
    private void addSaveButton() {

        JButton button = new JButton("SAVE");
        button.setLocation(HEIGTH, HEIGTH / 10 + 500);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);


        button.addActionListener(e -> {
            //        printLog("clicked Save Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            if (filePath == null || filePath.isEmpty()) {
                return;
            }
            if (!filePath.endsWith(".txt")) {
                filePath += ".txt";
            }
            gameController.writeDataToFile(filePath);


        });
    }
    public void playback() {

        int i = 0;
        java.util.Timer timer = new Timer();//实例化Timer类
        while (i < chessboard.clickController.getGraph().size()) {
            int finalI = i;
            timer.schedule(new TimerTask() {
                public void run() {
            chessboard.loadGame("RNBQKBNR\nPPPPPPPP\n00000000\n00000000\n00000000\n00000000\npppppppp\nrnbqkbnr\nw");
                    this.cancel();
                }
            },  1000);//毫秒

            timer.schedule(new TimerTask() {
                public void run() {

                    chessboard.loadGame(chessboard.clickController.getGraph().get(finalI));
                    this.cancel();

                }

            }, (finalI + 2) * 1000);//毫秒
            i++;

        }


    }

    private void addPlaybackButton() {
        JButton button = new JButton("PLAYBACK");
        button.setLocation(HEIGTH, HEIGTH / 10 + 570);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            playback();
        });
    }

    //主题更换
    private void addThemeButton() {
        JButton button = new JButton("THEME");
        button.setLocation(HEIGTH, HEIGTH / 10 + 400);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {


        });
    }


}
