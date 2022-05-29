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
    int i = 30;

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


        addLabel();
        addChessboard(chessboard);
        addLoadButton();
        addRestartButton();
        addBackButton();
        addSaveButton();
        addPlaybackButton();
//        addCountDown();


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
//        countLabel = new JLabel("30");
//        int i = 30;
//        java.util.Timer timer = new Timer();//实例化Timer类
//        while (i > 0) {
//            int finalI = i;
//            if (i==30){
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("30");
//                        this.cancel();
//                    }
//                }, 1000);//毫秒
//            }else if (i==29) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        statusLabel.setText("29");
//                        this.cancel();
//                    }
//                }, (31-29) * 1000);//毫秒
//            }else {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        statusLabel.setText("28");
//                        this.cancel();
//                    }
//                }, (31-28) * 1000);//毫秒
//            }
//            i--;
//
//        }
        statusLabel.setLocation(HEIGTH + 34, (HEIGTH / 10) - 12);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
        add(statusLabel);
//        countLabel.setLocation(HEIGTH + 34, (HEIGTH / 10) - 200);
//        countLabel.setSize(200, 60);
//        countLabel.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
//        add(countLabel);
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

//    private void addCountDown() {
//        JLabel countLabel = new JLabel("30");
//
//        java.util.Timer timer = new Timer();//实例化Timer类
//        while (i > 0) {
//            int finalI = i;
//            if (i == 30) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("30");
//                        this.cancel();
//                    }
//                }, 1000);//毫秒
//            } else if (i == 29) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("29");
//                        this.cancel();
//                    }
//                }, (31 - 29) * 1000);//毫秒
//            } else if (i == 28) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("28");
//                        this.cancel();
//                    }
//                }, (31 - 28) * 1000);//毫秒
//            } else if (i == 27) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("27");
//                        this.cancel();
//                    }
//                }, (31 - 27) * 1000);//毫秒
//            } else if (i == 26) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("26");
//                        this.cancel();
//                    }
//                }, (31 - 26) * 1000);//毫秒
//            } else if (i == 25) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("25");
//                        this.cancel();
//                    }
//                }, (31 - 25) * 1000);//毫秒
//            } else if (i == 24) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("24");
//                        this.cancel();
//                    }
//                }, (31 - 24) * 1000);//毫秒
//            } else if (i == 23) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("23");
//                        this.cancel();
//                    }
//                }, (31 - 23) * 1000);//毫秒
//            } else if (i == 22) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("22");
//                        this.cancel();
//                    }
//                }, (31 - 22) * 1000);//毫秒
//            } else if (i == 21) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("21");
//                        this.cancel();
//                    }
//                }, (31 - 21) * 1000);//毫秒
//            } else if (i == 20) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("20");
//                        this.cancel();
//                    }
//                }, (31 - 20) * 1000);//毫秒
//            } else if (i == 19) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("19");
//                        this.cancel();
//                    }
//                }, (31 - 19) * 1000);//毫秒
//            } else if (i == 18) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("18");
//                        this.cancel();
//                    }
//                }, (31 - 18) * 1000);//毫秒
//            } else if (i == 17) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("17");
//                        this.cancel();
//                    }
//                }, (31 - 17) * 1000);//毫秒
//            } else if (i == 16) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("16");
//                        this.cancel();
//                    }
//                }, (31 - 16) * 1000);//毫秒
//            } else if (i == 15) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("15");
//                        this.cancel();
//                    }
//                }, (31 - 15) * 1000);//毫秒
//            } else if (i == 14) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("14");
//                        this.cancel();
//                    }
//                }, (31 - 14) * 1000);//毫秒
//            } else if (i == 13) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("13");
//                        this.cancel();
//                    }
//                }, (31 - 13) * 1000);//毫秒
//            } else if (i == 12) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("12");
//                        this.cancel();
//                    }
//                }, (31 - 12) * 1000);//毫秒
//            } else if (i == 11) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("11");
//                        this.cancel();
//                    }
//                }, (31 - 11) * 1000);//毫秒
//            } else if (i == 10) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("10");
//                        this.cancel();
//                    }
//                }, (31 - 10) * 1000);//毫秒
//            } else if (i == 9) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("9");
//                        this.cancel();
//                    }
//                }, (31 - 9) * 1000);//毫秒
//            } else if (i == 8) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("8");
//                        this.cancel();
//                    }
//                }, (31 - 8) * 1000);//毫秒
//            } else if (i == 7) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("7");
//                        this.cancel();
//                    }
//                }, (31 - 7) * 1000);//毫秒
//            } else if (i == 6) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("6");
//                        this.cancel();
//                    }
//                }, (31 - 6) * 1000);//毫秒
//            } else if (i == 5) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("5");
//                        this.cancel();
//                    }
//                }, (31 - 5) * 1000);//毫秒
//            } else if (i == 4) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("4");
//                        this.cancel();
//                    }
//                }, (31 - 4) * 1000);//毫秒
//            } else if (i == 3) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("3");
//                        this.cancel();
//                    }
//                }, (31 - 3) * 1000);//毫秒
//            } else if (i == 2) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("2");
//                        this.cancel();
//                    }
//                }, (31 - 2) * 1000);//毫秒
//            } else if (i == 1) {
//                timer.schedule(new TimerTask() {
//                    public void run() {
//                        countLabel.setText("1");
//                        this.cancel();
//                    }
//                }, (31 - 1) * 1000);//毫秒
//                if (chessboard.getCurrentColor().equals(ChessColor.WHITE)) {
//                    chessboard.setCurrentColor(ChessColor.BLACK);
//                } else if (chessboard.getCurrentColor().equals(ChessColor.BLACK)) {
//                    chessboard.setCurrentColor(ChessColor.WHITE);
//                }
//            }
////            }else if (i==0){
////                i=30;
////                timer.schedule(new TimerTask() {
////                    public void run() {
////                        countLabel.setText("0");
////                        this.cancel();
////                    }
////                }, (31 - 0) * 1000);//毫秒
////                if (chessboard.getCurrentColor().equals(ChessColor.WHITE)){
////                    chessboard.setCurrentColor(ChessColor.BLACK);
////                }else if (chessboard.getCurrentColor().equals(ChessColor.BLACK)){
////                    chessboard.setCurrentColor(ChessColor.WHITE);
////                }
////            }
//            i--;
//
//        }
//        countLabel.setLocation(HEIGTH - 20, (HEIGTH / 10) - 20);
//        countLabel.setSize(200, 60);
//        countLabel.setFont(new Font("Berlin Sans FB", Font.BOLD, 30));
//        countLabel.setVisible(true);
//        add(countLabel);
//    }

}


