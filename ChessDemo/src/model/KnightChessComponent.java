package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的马
 */
public class KnightChessComponent extends ChessComponent {
    /**
     * 黑马和白马的图片，static使得其可以被所有马对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    /**
     * 马棋子对象自身的图片，是上面两种中的一种
     */
    private Image knightImage;

    /**
     * 读取加载马棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定knightImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateknightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateknightImage(color);
    }

    /**
     * 马棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 马棋子移动的合法性
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
        }else if (yMax - yMin == 2 && xMax - xMin == 1){
            return true;
        }else if (yMax-yMin==1&&xMax-xMin==2){
            return true;
        }else {
            return false;
        }
    }
//    public void allCanMoveTo(ChessComponent[][] allCanChessboard){
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                ChessboardPoint chessboardPoint =new ChessboardPoint(i,j) ;
//                if (canMoveTo(allCanChessboard,chessboardPoint)){
////                            chessComponent.getChessComponents()[chessboardPoint.getX()][chessboardPoint.getY()]
////                            getChessComponents()[allCan.getX()][allCan.getY()].getChessComponents();
//                    System.out.println(i+","+j);
//                }
//            }
//        }
//    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(knightImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
