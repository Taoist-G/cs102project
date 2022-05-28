package controller;

import view.Chessboard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData,path);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String loadGameFromString(String str) {
        String chessData = str;
        chessboard.loadGame(chessData);
        return chessData;
    }

    public void initialGame() {
        chessboard.initialGame();
    }

    public void writeDataToFile(String fileName) {
        //todo: write data into file
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(fileName));
            wr.write(chessboard.getChessboardGraph());
            System.out.println(chessboard.getChessboardGraph());
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
//    public void backGame(){
//        chessboard.backGame();
//    }



}

