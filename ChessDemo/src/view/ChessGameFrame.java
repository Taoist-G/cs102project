package view;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.tools.JavaFileManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.FileStore;
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
    int backCounter = 0;
    public static int i = 30;

    Timer timer;

    public void setI(int i) {
        this.i = i;
    }

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

        addCountDown();
        addLabel();
        addChessboard(chessboard);
        addLoadButton();
        addRestartButton();
        addBackButton();
        addSaveButton();
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
        statusLabel = new JLabel(chessboard.getCurrentColor().toString());
        statusLabel.setLocation(HEIGTH + 34, (HEIGTH / 10) - 12);
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

        button.setBorderPainted(false);
        button.setText(null);
        button.setBorder(null);
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            System.out.println("Click load");

            ClickController.play();

            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("txt文件(*.txt)", "txt");
            fileChooser.setFileFilter(fileFilter);
            fileChooser.setDialogTitle("打开文件");
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println("打开文件：" + fileChooser.getSelectedFile().getAbsolutePath());
                File file = fileChooser.getSelectedFile();

                gameController.loadGameFromFile(file.getAbsolutePath());
                ClickController.play();
            }
//         String path = JOptionPane.showInputDialog(this, "Input Path here");
//            if (path == null || path.isEmpty()) {
//                return;
//            }
//            gameController.loadGameFromFile(path);

        });
    }


    //重置棋盘
    private void addRestartButton() {
        JButton button = new JButton("RESTART");
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setBorderPainted(false);
        button.setText(null);
        button.setBorder(null);
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            gameController.initialGame();
            ClickController.play();
            chessboard.chongZhi();
            chessboard.setCurrentColor(ChessColor.WHITE);
            setBackCounter(0);

        });
    }

    public void setBackCounter(int backCounter) {
        this.backCounter = backCounter;
    }


    //悔棋
    private void addBackButton() {
        JButton button = new JButton("BACK");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.setBorderPainted(false);
        button.setText(null);
        button.setBorder(null);
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            ClickController.play();
            System.out.println("Click back");
            if (chessboard.clickController.getCounter() == 0) {
                return;
            }

            backCounter++;
            if ((chessboard.clickController.getCounter() - backCounter - 1) < 0) {
                chessboard.loadGame("RNBQKBNR\nPPPPPPPP\n00000000\n00000000\n00000000\n00000000\npppppppp\nrnbqkbnr\nw");
            } else {
                chessboard.loadGame(chessboard.clickController.getGraph().get(chessboard.clickController.getCounter() - backCounter - 1));
            }

        });
    }

//    public void printLog(String msg) {
//        logLabel.setText(msg);
//    }

    //存储
    private void addSaveButton() {

        JButton button = new JButton("SAVE");
        button.setLocation(HEIGTH, HEIGTH / 10 + 430);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.setBorderPainted(false);
        button.setText(null);
        button.setBorder(null);
        button.setContentAreaFilled(false);


        button.addActionListener(e -> {
            ClickController.play();
            //        printLog("clicked Save Btn");
            String filePath = JOptionPane.showInputDialog(null, "请输入新建文本:\n", "存储功能", JOptionPane.PLAIN_MESSAGE);
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
            }, 1000);//毫秒

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
        button.setLocation(HEIGTH, HEIGTH / 10 + 530);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.setBorderPainted(false);
        button.setText(null);
        button.setBorder(null);
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            playback();
            ClickController.play();
        });
    }

    private void addCountDown() {
        JLabel countLabel = new JLabel("30");
        if (timer == null) {
            timer = new Timer();//实例化Timer类
        }
        timer.schedule(new TimerTask() {
            public void run() {
                i--;
                countLabel.setText(i + "s");
//                this.cancel();
                if (i==0){
                    i=30;
                    if (chessboard.getCurrentColor().equals(ChessColor.WHITE)) {
                        chessboard.setCurrentColor(ChessColor.BLACK);
                        statusLabel.setText("BLACK");
                    }else {
                        chessboard.setCurrentColor(ChessColor.WHITE);
                        statusLabel.setText("WHITE");
                    }
                    chessboard.getClickController().setFirst(null);
                    for (int j = 0; j < 8; j++) {
                        for (int k = 0; k < 8; k++) {
                            chessboard.getChessComponents()[j][k].setSelected(false);
                            chessboard.getChessComponents()[j][k].repaint();
                        }
                    }

                }
            }
        }, 1000, 1000);//毫秒
        countLabel.setLocation(HEIGTH - 20, (HEIGTH / 10) - 20);
        countLabel.setSize(200, 60);
        countLabel.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
        countLabel.setVisible(true);
        add(countLabel);

    }


}


