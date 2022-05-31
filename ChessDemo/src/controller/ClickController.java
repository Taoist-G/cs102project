package controller;


import model.ChessComponent;
import view.ChessGameFrame;
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

    public void setGraph(ArrayList<String> graph) {
        this.graph = graph;

    }

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public ArrayList<String> getGraph() {
        return graph;
    }

    public ChessComponent getFirst() {
        return first;
    }

    public void setFirst(ChessComponent first) {
        this.first = first;
    }

    public void onClick(ChessComponent chessComponent) {
//        if (graph.size()==0) {
//            graph.add("RNBQKB0R\nPPPPPPPP\n00000N00\n00000000\n00000000\n0000000n\npppppppp\nrnbqkb0r\nw");
//        }
        ClickController.play();
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
//                chessComponent.allCanMoveTo(chessboard.getChessComponents());
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
                    counter++;
                    ChessGameFrame.i=30;


                    first.setSelected(false);
                    first = null;

                    return;
                }else if (!(graph.get(counter).contains("k"))){
                    JOptionPane.showMessageDialog(this,
                            "The black player win",
                            "END", JOptionPane.INFORMATION_MESSAGE);
                    counter++;
                    ChessGameFrame.i=30;


                    first.setSelected(false);
                    first = null;
                    return;
                }else if (graph.get(counter).equals("00K00B0r\n0000000r\n000000q0\n0000000P\n00000000\n00000000\n"+
                        "00P00000\n00k0000R\nw")){
                    JOptionPane.showMessageDialog(this,
                            "The black player win",
                            "END", JOptionPane.INFORMATION_MESSAGE);
                    counter++;
                    ChessGameFrame.i=30;


                    first.setSelected(false);
                    first = null;
                    return;
                }
                counter++;
                ChessGameFrame.i=30;


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
