package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的象
 */
public class BishopChessComponent extends ChessComponent {
    /**
     * 黑象和白象的图片，static使得其可以被所有象对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    /**
     * 象棋子对象自身的图片，是上面两种中的一种
     */
    private Image bishopImage;

    /**
     * 读取加载象棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定bishopImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    /**
     * 象棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 象棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX();
        int y = source.getY();
        int x1 = destination.getX();
        int y1 = destination.getY();
        int xMin = Math.min(x, x1);
        int xMax = Math.max(x, x1);
        int yMin = Math.min(y, y1);
        int yMax = Math.max(y, y1);
        if (chessComponents[x][y].getChessColor().equals(chessComponents[x1][y1].getChessColor())){
            return false;
        }else if (x + y == x1 + y1) {
            for (int i = 1; i < yMax - yMin; i++) {
                if (!(chessComponents[xMax - i][yMin + i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (y - x == y1 - x1) {
            for (int i = 1; i < yMax - yMin; i++) {
                if (!(chessComponents[xMin + i][yMin + i] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
//    public void allCanMoveTo(ChessComponent[][] allCanChessboard){
//        for (int i = 0; i < 8; i++) {
//                    for (int j = 0; j < 8; j++) {
//                        ChessboardPoint chessboardPoint =new ChessboardPoint(i,j) ;
//                        if (canMoveTo(allCanChessboard,chessboardPoint)){
////                            chessComponent.getChessComponents()[chessboardPoint.getX()][chessboardPoint.getY()]
////                            getChessComponents()[allCan.getX()][allCan.getY()].getChessComponents();
//                            System.out.println(i+","+j);
//                        }
//                    }
//                }
//    }

//        if (source.getX() == destination.getX()) {
//            int row = source.getX();
//            for (int col = Math.min(source.getY(), destination.getY()) + 1;
//                 col < Math.max(source.getY(), destination.getY()); col++) {
//                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
//                    return false;
//                }
//            }
//        } else if (source.getY() == destination.getY()) {
//            int col = source.getY();
//            for (int row = Math.min(source.getX(), destination.getX()) + 1;
//                 row < Math.max(source.getX(), destination.getX()); row++) {
//                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
//                    return false;
//                }
//            }
//        } else { // Not on the same row or the same column.
//            return false;
//        }
//        return true;
//    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(bishopImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    Chessboard.
//                }
//            }
        }
    }
}

