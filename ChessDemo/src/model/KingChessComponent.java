package model;

import controller.ClickController;
import view.Chessboard;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的王
 */
public class KingChessComponent extends ChessComponent {
    /**
     * 黑王和白王的图片，static使得其可以被所有王对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image KING_WHITE;
    private static Image KING_BLACK;


    /**
     * 王棋子对象自身的图片，是上面两种中的一种
     */
    private Image kingImage;

    /**
     * 读取加载王棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定kingImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatekingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatekingImage(color);
    }

    /**
     * 王棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 王棋子移动的合法性
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


        if (getChessColor() == ChessColor.WHITE){
            if (x==7&&y==4&&chessComponents[7][7]instanceof RookChessComponent
            &&chessComponents[7][5]instanceof EmptySlotComponent&&chessComponents[7][6]instanceof EmptySlotComponent){
                if (x1==7&&y1==6){
                    ChessboardPoint a = new ChessboardPoint(7,5);
                    swapChessComponentsWhite(chessComponents[7][5],chessComponents[7][7]);
//                    add(chessComponents[7][5]=new RookChessComponent(a,chessComponents[7][5].getLocation(),ChessColor.WHITE, clickController, CHESS_SIZE));
//                   initRookOnBoard(7,5,ChessColor.WHITE);
                    return true;
                }
            }
        }else if (getChessColor() == ChessColor.BLACK){
            if (x==0&&y==4&&chessComponents[0][7]instanceof RookChessComponent
                    &&chessComponents[0][5]instanceof EmptySlotComponent&&chessComponents[0][6]instanceof EmptySlotComponent){
                if (x1==0&&y1==6){
                    ChessboardPoint a = new ChessboardPoint(0,5);
                    swapChessComponentsBlack(chessComponents[0][5],chessComponents[0][7]);
//                    add(chessComponents[7][5]=new RookChessComponent(a,chessComponents[7][5].getLocation(),ChessColor.WHITE, clickController, CHESS_SIZE));
//                   initRookOnBoard(7,5,ChessColor.WHITE);
                    return true;
                }
            }
        }


        if (yMax-yMin==1){
            if (xMax-xMin==1||xMax==xMin){
                return true;
            }else {
                return false;
            }
        }else if (yMax==yMin){
            if (xMax-xMin==1){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }



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
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(kingImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
