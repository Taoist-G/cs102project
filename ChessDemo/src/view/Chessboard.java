package view;


import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static model.ChessColor.BLACK;
import static model.ChessColor.WHITE;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = WHITE;

//    public ClickController getClickController() {
//        return clickController;
//    }


    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public JLabel statusLabel;

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);

        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 0, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 1, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 2, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 3, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 4, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 5, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 6, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 7, ChessColor.WHITE);
    }

    public void initialGame() {
        initiateEmptyChessboard();


        this.currentColor = WHITE;
        statusLabel.setText(currentColor.toString());

        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, 7, ChessColor.BLACK);
        initRookOnBoard(7, 0, ChessColor.WHITE);
        initRookOnBoard(7, 7, ChessColor.WHITE);
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(7, 4, ChessColor.WHITE);

        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, 6, ChessColor.BLACK);
        initKnightOnBoard(7, 1, ChessColor.WHITE);
        initKnightOnBoard(7, 6, ChessColor.WHITE);

        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(7, 3, ChessColor.WHITE);

        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(6, 0, ChessColor.WHITE);
        initPawnOnBoard(6, 1, ChessColor.WHITE);
        initPawnOnBoard(6, 2, ChessColor.WHITE);
        initPawnOnBoard(6, 3, ChessColor.WHITE);
        initPawnOnBoard(6, 4, ChessColor.WHITE);
        initPawnOnBoard(6, 5, ChessColor.WHITE);
        initPawnOnBoard(6, 6, ChessColor.WHITE);
        initPawnOnBoard(6, 7, ChessColor.WHITE);

        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, 5, ChessColor.BLACK);
        initBishopOnBoard(7, 2, ChessColor.WHITE);
        initBishopOnBoard(7, 5, ChessColor.WHITE);


        repaint();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        statusLabel.setText(currentColor.toString());
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData,String path) {

        if (!path.endsWith(".txt")){
            JOptionPane.showMessageDialog(this,
                    "Wrong file format", "error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (chessData.size()==0){
            JOptionPane.showMessageDialog(this,
                    "The board is not 8*8.", "error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!(chessData.size() == 9)){
            if (chessData.get(chessData.size()-1).equals("w")||chessData.get(chessData.size()-1).equals("b")){
                JOptionPane.showMessageDialog(this,
                        "The board is not 8*8.", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }else {
                JOptionPane.showMessageDialog(this,
                        " Missing next move", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

//        boolean n = true;
        for (int i = 0; i < 8; i++) {
            if ( !(chessData.get(i).length() == 8)) {
//                n = false;
                JOptionPane.showMessageDialog(this,
                        "The board is not 8*8.", "error", JOptionPane.ERROR_MESSAGE);
                return;

            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char qi = chessData.get(i).charAt(j);
                if (qi != 'B' && qi != 'b' && qi != 'K' && qi != 'k' && qi != 'N' && qi != 'n' && qi != 'P'
                        && qi != 'p' && qi != 'Q' && qi != 'q' && qi != 'R' && qi != 'r' && qi != '0'){
                    JOptionPane.showMessageDialog(this,
                            "The pieces are not one of six, or not black and white.",
                            "error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

        }

        if (!(chessData.get(8).equals("w")||chessData.get(8).equals("b"))){
            JOptionPane.showMessageDialog(this,
                    " Missing next move", "error", JOptionPane.ERROR_MESSAGE);
            return;
        }


//        if (n) {
        chessData.forEach(System.out::println);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char chess = chessData.get(i).charAt(j);
                if (chess == 'B') {
                    initBishopOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'b') {
                    initBishopOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'K') {
                    initKingOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'k') {
                    initKingOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'N') {
                    initKnightOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'n') {
                    initKnightOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'P') {
                    initPawnOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'p') {
                    initPawnOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'Q') {
                    initQueenOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'q') {
                    initQueenOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'R') {
                    initRookOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'r') {
                    initRookOnBoard(i, j, ChessColor.WHITE);
                }
            }
        }
        if (chessData.get(8).equals("w")) {
            currentColor = WHITE;
            statusLabel.setText(currentColor.toString());
        }
        if (chessData.get(8).equals("b")) {
            currentColor = BLACK;
            statusLabel.setText(currentColor.toString());
        }
        repaint();
    }
//    }

    public String getChessboardGraph() {
        StringBuilder graph = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessComponent chess = chessComponents[i][j];
                if (chess instanceof BishopChessComponent && chess.getChessColor().equals(BLACK)) {
                    graph.append("B");
                }
                if (chess instanceof BishopChessComponent && chess.getChessColor().equals(WHITE)) {
                    graph.append("b");
                }
                if (chess instanceof KingChessComponent && chess.getChessColor().equals(BLACK)) {
                    graph.append("K");
                }
                if (chess instanceof KingChessComponent && chess.getChessColor().equals(WHITE)) {
                    graph.append("k");
                }
                if (chess instanceof KnightChessComponent && chess.getChessColor().equals(BLACK)) {
                    graph.append("N");
                }
                if (chess instanceof KnightChessComponent && chess.getChessColor().equals(WHITE)) {
                    graph.append("n");
                }
                if (chess instanceof PawnChessComponent && chess.getChessColor().equals(BLACK)) {
                    graph.append("P");
                }
                if (chess instanceof PawnChessComponent && chess.getChessColor().equals(WHITE)) {
                    graph.append("p");
                }
                if (chess instanceof QueenChessComponent && chess.getChessColor().equals(BLACK)) {
                    graph.append("Q");
                }
                if (chess instanceof QueenChessComponent && chess.getChessColor().equals(WHITE)) {
                    graph.append("q");
                }
                if (chess instanceof RookChessComponent && chess.getChessColor().equals(BLACK)) {
                    graph.append("R");
                }
                if (chess instanceof RookChessComponent && chess.getChessColor().equals(WHITE)) {
                    graph.append("r");
                }
                if (chess instanceof EmptySlotComponent) {
                    graph.append("0");
                }
                if (j == 7) {
                    graph.append("\n");
                }
            }
        }
        if (currentColor.equals(WHITE)) {
            graph.append("w");
        }
        if (currentColor.equals(BLACK)) {
            graph.append("b");
        }

        return graph.toString();

    }

//    public

    public void loadGame(String chessData) {

//        if (n) {
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char chess = chessData.charAt(i*9+j);
                if (chess == 'B') {
                    initBishopOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'b') {
                    initBishopOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'K') {
                    initKingOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'k') {
                    initKingOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'N') {
                    initKnightOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'n') {
                    initKnightOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'P') {
                    initPawnOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'p') {
                    initPawnOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'Q') {
                    initQueenOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'q') {
                    initQueenOnBoard(i, j, ChessColor.WHITE);
                } else if (chess == 'R') {
                    initRookOnBoard(i, j, ChessColor.BLACK);
                } else if (chess == 'r') {
                    initRookOnBoard(i, j, ChessColor.WHITE);
                }
            }
        }
        if (chessData.charAt(72)=='w') {
            currentColor = WHITE;
            statusLabel.setText(currentColor.toString());
        }
        if (chessData.charAt(72)=='b') {
            currentColor = BLACK;
            statusLabel.setText(currentColor.toString());
        }
        repaint();
    }
//    }

//    public void playback() {
//
//        int i = 0;
//        Timer timer = new Timer();//实例化Timer类
//        while (i < chessboard.clickController.getGraph().) {
//            int finalI = i;
//            timer.schedule(new TimerTask() {
//                public void run() {
//
//                    System.out.println(a.get(finalI));
//                    this.cancel();
//
//                }
//
//            }, (finalI + 2) * 1000);//毫秒
//            i++;
//
//        }
//
//
//    }
}
