package controller;


import model.ChessComponent;
import view.Chessboard;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ClickController extends Component {
    private final Chessboard chessboard;
    private ChessComponent first;
    int counter = 0;
    ArrayList<String> graph=new ArrayList<>();


    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public int getCounter() {
        return counter;
    }

    public ArrayList<String> getGraph() {
        return graph;
    }

    public void onClick(ChessComponent chessComponent) {
        ClickController.play();
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                graph.add(chessboard.getChessboardGraph());
                System.out.println(graph.get(counter));
                if (!(graph.get(counter).contains("K"))){
                    JOptionPane.showMessageDialog(this,
                            "The white player win",
                            "END", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else if (!(graph.get(counter).contains("k"))){
                    JOptionPane.showMessageDialog(this,
                            "The black player win",
                            "END", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                counter++;


                first.setSelected(false);
                first = null;
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    public static void play() {

        final String f = "music/press.wav";
        Clip c = null;

        try {
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(new File(f)));
            c.loop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
