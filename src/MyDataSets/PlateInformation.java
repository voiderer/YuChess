package MyDataSets;

import Definition.*;
import MainClass.TheApp;
import javafx.util.Pair;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import javax.swing.*;

/**
 * Created by AUTOY on 2016/5/5.
 */
public class PlateInformation {
    final private String initString = "rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR";
    ChessInformation[][] matrix = new ChessInformation[10][9];
    TheApp theApp;
    private ImageIcon icons[][][] = new ImageIcon[3][2][7];
    private ImageIcon justMovedIcon;
    private DualHashBidiMap<Character, Pair<Player, ChessType>> chessMap;
    private DualHashBidiMap<Integer, Character> rowMap = new DualHashBidiMap<>();
    private DualHashBidiMap<Integer, Character> colMap = new DualHashBidiMap<>();
    private String lastMove;
    private boolean hasChosen = false;
    private ChessInformation chosen = new ChessInformation(ChessType.BLANK, Player.BLANK, ChessState.BLANK, -1, -1);
    private ChessInformation lastMoved1, lastMoved2;
    private boolean myTurn;
    private boolean hasLastMove;
    private Player side = Player.BLANK;

    public PlateInformation(TheApp theApp) {
        int i, j;
        hasLastMove = false;
        this.theApp = theApp;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 9; j++) {
                matrix[i][j] = new ChessInformation(ChessType.BLANK, Player.BLANK, ChessState.NORMAL, i, j);
            }
        }
        initIcons();
    }

    private void initIcons() {
        justMovedIcon = new ImageIcon(PlateInformation.class.getResource("/empty.png"));
        icons[0][0][0] = new ImageIcon(PlateInformation.class.getResource("/黑将.png"));
        icons[0][0][1] = new ImageIcon(PlateInformation.class.getResource("/黑士.png"));
        icons[0][0][2] = new ImageIcon(PlateInformation.class.getResource("/黑象.png"));
        icons[0][0][3] = new ImageIcon(PlateInformation.class.getResource("/黑马.png"));
        icons[0][0][4] = new ImageIcon(PlateInformation.class.getResource("/黑车.png"));
        icons[0][0][5] = new ImageIcon(PlateInformation.class.getResource("/黑炮.png"));
        icons[0][0][6] = new ImageIcon(PlateInformation.class.getResource("/黑卒.png"));
        icons[0][1][0] = new ImageIcon(PlateInformation.class.getResource("/红将.png"));
        icons[0][1][1] = new ImageIcon(PlateInformation.class.getResource("/红士.png"));
        icons[0][1][2] = new ImageIcon(PlateInformation.class.getResource("/红象.png"));
        icons[0][1][3] = new ImageIcon(PlateInformation.class.getResource("/红马.png"));
        icons[0][1][4] = new ImageIcon(PlateInformation.class.getResource("/红车.png"));
        icons[0][1][5] = new ImageIcon(PlateInformation.class.getResource("/红炮.png"));
        icons[0][1][6] = new ImageIcon(PlateInformation.class.getResource("/红卒.png"));

        icons[1][0][0] = new ImageIcon(PlateInformation.class.getResource("/黑将1.png"));
        icons[1][0][1] = new ImageIcon(PlateInformation.class.getResource("/黑士1.png"));
        icons[1][0][2] = new ImageIcon(PlateInformation.class.getResource("/黑象1.png"));
        icons[1][0][3] = new ImageIcon(PlateInformation.class.getResource("/黑马1.png"));
        icons[1][0][4] = new ImageIcon(PlateInformation.class.getResource("/黑车1.png"));
        icons[1][0][5] = new ImageIcon(PlateInformation.class.getResource("/黑炮1.png"));
        icons[1][0][6] = new ImageIcon(PlateInformation.class.getResource("/黑卒1.png"));
        icons[1][1][0] = new ImageIcon(PlateInformation.class.getResource("/红将1.png"));
        icons[1][1][1] = new ImageIcon(PlateInformation.class.getResource("/红士1.png"));
        icons[1][1][2] = new ImageIcon(PlateInformation.class.getResource("/红象1.png"));
        icons[1][1][3] = new ImageIcon(PlateInformation.class.getResource("/红马1.png"));
        icons[1][1][4] = new ImageIcon(PlateInformation.class.getResource("/红车1.png"));
        icons[1][1][5] = new ImageIcon(PlateInformation.class.getResource("/红炮1.png"));
        icons[1][1][6] = new ImageIcon(PlateInformation.class.getResource("/红卒1.png"));

        icons[2][0][0] = new ImageIcon(PlateInformation.class.getResource("/黑将2.png"));
        icons[2][0][1] = new ImageIcon(PlateInformation.class.getResource("/黑士2.png"));
        icons[2][0][2] = new ImageIcon(PlateInformation.class.getResource("/黑象2.png"));
        icons[2][0][3] = new ImageIcon(PlateInformation.class.getResource("/黑马2.png"));
        icons[2][0][4] = new ImageIcon(PlateInformation.class.getResource("/黑车2.png"));
        icons[2][0][5] = new ImageIcon(PlateInformation.class.getResource("/黑炮2.png"));
        icons[2][0][6] = new ImageIcon(PlateInformation.class.getResource("/黑卒2.png"));
        icons[2][1][0] = new ImageIcon(PlateInformation.class.getResource("/红将2.png"));
        icons[2][1][1] = new ImageIcon(PlateInformation.class.getResource("/红士2.png"));
        icons[2][1][2] = new ImageIcon(PlateInformation.class.getResource("/红象2.png"));
        icons[2][1][3] = new ImageIcon(PlateInformation.class.getResource("/红马2.png"));
        icons[2][1][4] = new ImageIcon(PlateInformation.class.getResource("/红车2.png"));
        icons[2][1][5] = new ImageIcon(PlateInformation.class.getResource("/红炮2.png"));
        icons[2][1][6] = new ImageIcon(PlateInformation.class.getResource("/红卒2.png"));
        chessMap = new DualHashBidiMap<>();
        chessMap.put('k', new Pair<>(Player.BLUE, ChessType.KING));
        chessMap.put('a', new Pair<>(Player.BLUE, ChessType.ADVISOR));
        chessMap.put('b', new Pair<>(Player.BLUE, ChessType.BISHOP));
        chessMap.put('n', new Pair<>(Player.BLUE, ChessType.KNIGHT));
        chessMap.put('r', new Pair<>(Player.BLUE, ChessType.ROOK));
        chessMap.put('c', new Pair<>(Player.BLUE, ChessType.CANNON));
        chessMap.put('p', new Pair<>(Player.BLUE, ChessType.PAWN));
        chessMap.put('K', new Pair<>(Player.RED, ChessType.KING));
        chessMap.put('A', new Pair<>(Player.RED, ChessType.ADVISOR));
        chessMap.put('B', new Pair<>(Player.RED, ChessType.BISHOP));
        chessMap.put('N', new Pair<>(Player.RED, ChessType.KNIGHT));
        chessMap.put('R', new Pair<>(Player.RED, ChessType.ROOK));
        chessMap.put('C', new Pair<>(Player.RED, ChessType.CANNON));
        chessMap.put('P', new Pair<>(Player.RED, ChessType.PAWN));
    }

    void setPlayerRed() {
        side = Player.RED;
        rowMap.clear();
        rowMap.put(0, '9');
        rowMap.put(1, '8');
        rowMap.put(2, '7');
        rowMap.put(3, '6');
        rowMap.put(4, '5');
        rowMap.put(5, '4');
        rowMap.put(6, '3');
        rowMap.put(7, '2');
        rowMap.put(8, '1');
        rowMap.put(9, '0');
        colMap.clear();
        colMap.put(0, 'a');
        colMap.put(1, 'b');
        colMap.put(2, 'c');
        colMap.put(3, 'd');
        colMap.put(4, 'e');
        colMap.put(5, 'f');
        colMap.put(6, 'g');
        colMap.put(7, 'h');
        colMap.put(8, 'i');
        myTurn = true;
        readFromString(initString);
    }

    void setPlayerBlue() {
        side = Player.BLUE;
        rowMap.clear();
        rowMap.put(0, '0');
        rowMap.put(1, '1');
        rowMap.put(2, '2');
        rowMap.put(3, '3');
        rowMap.put(4, '4');
        rowMap.put(5, '5');
        rowMap.put(6, '6');
        rowMap.put(7, '7');
        rowMap.put(8, '8');
        rowMap.put(9, '9');
        colMap.clear();
        colMap.put(0, 'i');
        colMap.put(1, 'h');
        colMap.put(2, 'g');
        colMap.put(3, 'f');
        colMap.put(4, 'e');
        colMap.put(5, 'd');
        colMap.put(6, 'c');
        colMap.put(7, 'b');
        colMap.put(8, 'a');
        myTurn = false;
        readFromString(initString);
    }

    public boolean tryMoveChess(ChessInformation a, ChessInformation b) {
        if (moveChess(a, b)) {
            setLastMove(a, b);
            theApp.sendMoveMessage(lastMove);
            lastMoved1 = a;
            lastMoved2 = b;
            b.setInformation(a.type, a.player, ChessState.CHOZEN);
            a.setInformation(ChessType.BLANK, Player.BLANK, ChessState.NORMAL);
            a.justMoved = true;
            a.isChosen = false;
            myTurn = false;
            hasChosen = false;
            hasLastMove = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean isHasLastMove() {
        return hasLastMove;
    }

    public void clearLastMove() {
        hasLastMove = false;
        lastMoved1.justMoved = false;
        lastMoved2.state = ChessState.NORMAL;
    }

    public boolean moveChess(ChessInformation a, ChessInformation b) {
        switch (a.type) {
            case KING:
                return moveKing(a, b);
            case ADVISOR:
                return moveAdvisor(a, b);
            case BISHOP:
                return moveBishop(a, b);
            case KNIGHT:
                return moveKnight(a, b);
            case ROOK:
                return moveRook(a, b);
            case CANNON:
                return moveCannon(a, b);
            case PAWN:
                return movePawn(a, b);
            case BLANK:
                break;
        }
        return false;
    }

    private boolean moveKing(ChessInformation a, ChessInformation b) {
        int ax = a.x;
        int ay = a.y;
        int bx = b.x;
        int by = b.y;
        if (matrix[bx][by].type == ChessType.KING) {
            return countObstacle(ax, ay, bx, by) == 0;
        } else if (by < 3 || by > 5 || bx < 7) {
            return false;
        }
        return (ax - bx) * (ax - bx) + (ay - by) * (ay - by) == 1;
    }

    private boolean moveAdvisor(ChessInformation a, ChessInformation b) {
        int ax = a.x;
        int ay = a.y;
        int bx = b.x;
        int by = b.y;
        return by > 2 && by < 6 && bx > 6 && (ax - bx) * (ax - bx) + (ay - by) * (ay - by) == 2;
    }

    private boolean moveBishop(ChessInformation a, ChessInformation b) {
        int ax = a.x;
        int ay = a.y;
        int bx = b.x;
        int by = b.y;
        return bx >= 5 && (ax - bx) * (ax - bx) + (ay - by) * (ay - by) == 8 && matrix[(ax + bx) / 2][(ay + by) / 2].player == Player.BLANK;
    }

    private boolean moveKnight(ChessInformation a, ChessInformation b) {
        int ax = a.x;
        int ay = a.y;
        int bx = b.x;
        int by = b.y;
        if (Math.abs(ax - bx) == 1 && Math.abs(ay - by) == 2) {
            if (matrix[ax][ay + (by - ay) / Math.abs(by - ay)].player != Player.BLANK) {
                return false;
            }
        } else if (Math.abs(ax - bx) == 2 && Math.abs(ay - by) == 1) {
            if (matrix[ax + (bx - ax) / Math.abs(bx - ax)][ay].player != Player.BLANK) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean moveRook(ChessInformation a, ChessInformation b) {
        return countObstacle(a.x, a.y, b.x, b.y) == 0;
    }

    private boolean moveCannon(ChessInformation a, ChessInformation b) {
        int ax = a.x;
        int ay = a.y;
        int bx = b.x;
        int by = b.y;
        int n = countObstacle(ax, ay, bx, by);
        if (n == -1) {
            return false;
        } else if (n == 0) {
            return b.isVirtual || b.player == Player.BLANK;
        }
        return n == 1 && b.player != Player.BLANK;
    }

    private boolean movePawn(ChessInformation a, ChessInformation b) {
        int ax = a.x;
        int ay = a.y;
        int bx = b.x;
        int by = b.y;
        if ((ax - bx) * (ax - bx) + (ay - by) * (ay - by) != 1) {
            return false;
        }
        if (ax > 4 && ax - bx != 1) {
            return false;
        }
        if (ax < 5) {
            if (ax == bx && Math.abs(ay - by) != 1) {
                return false;
            }
            if (ay == by && ax - bx != 1) {
                return false;
            }
        }
        return true;
    }

    private int countObstacle(int ax, int ay, int bx, int by) {
        int i, j, k, n = 0;
        if (ax == bx) {
            i = ay < by ? ay : by;
            j = ay > by ? ay : by;
            for (k = i + 1; k < j; k++) {
                if (matrix[ax][k].player != Player.BLANK) {
                    n++;
                }
            }
            return n;
        } else if (ay == by) {
            i = ax < bx ? ax : bx;
            j = ax > bx ? ax : bx;
            for (k = i + 1; k < j; k++) {
                if (matrix[k][ay].player != Player.BLANK) {
                    n++;
                }
            }
            return n;
        }
        return -1;
    }

    public String saveToString() {
        Character a, b, c;
        int i;
        ChessInformation label;
        String s = "";
        for (a = '9'; a != '0' - 1; a--) {
            i = 0;
            for (b = 'a'; b != 'j'; b++) {
                label = matrix[rowMap.getKey(a)][colMap.getKey(b)];
                c = chessMap.getKey(new Pair<>(label.player, label.type));
                if (c == null) {
                    i++;
                } else {
                    if (i != 0) {
                        s += i;
                        i = 0;
                    }
                    s += c;
                }
            }
            if (i != 0) {
                s += i;
            }
            if (a != '0') {
                s += '/';
            }
        }
        return s;
    }

    public int[] readMove(String s) {
        lastMove = s;
        int[] ints = new int[4];
        ints[0] = colMap.getKey(s.charAt(0));
        ints[1] = rowMap.getKey(s.charAt(1));
        ints[2] = colMap.getKey(s.charAt(2));
        ints[3] = rowMap.getKey(s.charAt(3));
        ChessInformation a = matrix[ints[1]][ints[0]];
        ChessInformation b = matrix[ints[3]][ints[2]];
        lastMoved1 = a;
        lastMoved2 = b;
        b.setInformation(a.type, a.player, ChessState.CHOZEN);
        a.setInformation(ChessType.BLANK, Player.BLANK, ChessState.NORMAL);
        a.justMoved = true;
        a.isChosen = false;
        hasChosen = false;
        hasLastMove = true;
        if (side != Player.BLANK) {
            myTurn = true;
        }
        return ints;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public boolean isSameSide(Player player) {
        return player == side;
    }

    public void setChosen(ChessInformation chosen) {
        this.chosen.isChosen = false;
        this.chosen = chosen;
        this.chosen.isChosen = true;
    }

    public ChessInformation getInformation(int x, int y) {
        return matrix[x][y];
    }

    public boolean isHasChosen() {
        return hasChosen;
    }

    public void setHasChosen(boolean hasChosen) {
        this.hasChosen = hasChosen;
    }

    public boolean readFromString(String fen) {
        String[] split = fen.split(" ");
        String[] cheeses = split[0].split("/");
        if (cheeses.length != 10) {
            return false;
        }
        Character a, b, c;
        String temp;
        ChessInformation chess;
        Pair<Player, ChessType> pair;
        int i, j, k, l, m;
        for (a = '9', i = 0; i < 10; a--, i++) {
            temp = cheeses[i];
            j = 0;
            b = 'a';
            for (k = 0; k < temp.length(); k++) {
                c = temp.charAt(k);
                pair = chessMap.get(c);
                if (pair != null) {
                    chess = matrix[rowMap.getKey(a)][colMap.getKey(b)];
                    chess.setInformation(pair.getValue(), pair.getKey(), ChessState.NORMAL);
                    j++;
                    b++;
                } else if (c >= '0' & c <= '9') {
                    m = c - '0' + j;
                    for (; j < m; j++, b++) {
                        matrix[rowMap.getKey(a)][colMap.getKey(b)].setInformation(ChessType.BLANK, Player.BLANK, ChessState.NORMAL);
                        matrix[rowMap.getKey(a)][colMap.getKey(b)].justMoved = false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private void setLastMove(ChessInformation a, ChessInformation b) {
        lastMove = "" + colMap.get(a.y) + rowMap.get(a.x) + colMap.get(b.y) + rowMap.get(b.x);
    }

    public ImageIcon getImage(int x, int y) {
        ChessInformation chess = matrix[x][y];
        if (chess.justMoved) {
            return justMovedIcon;
        }
        int i = chess.state.ordinal();
        if (i == 3)
            return null;
        int j = chess.player.ordinal();
        if (j == 2)
            return null;
        int k = chess.type.ordinal();
        if (k == 7) {
            return null;
        }
        return icons[i][j][k];
    }

    public void handleStart(String string) {
        switch (string) {
            case ClientType.RED:
                setPlayerRed();
                break;
            case ClientType.BLUE:
                setPlayerBlue();
                break;
            default:
                side = Player.BLANK;
                setMyTurn(false);
        }
    }

    public void setPlayerBlank() {
        side = Player.BLANK;
        rowMap.clear();
        rowMap.put(0, '9');
        rowMap.put(1, '8');
        rowMap.put(2, '7');
        rowMap.put(3, '6');
        rowMap.put(4, '5');
        rowMap.put(5, '4');
        rowMap.put(6, '3');
        rowMap.put(7, '2');
        rowMap.put(8, '1');
        rowMap.put(9, '0');
        colMap.clear();
        colMap.put(0, 'a');
        colMap.put(1, 'b');
        colMap.put(2, 'c');
        colMap.put(3, 'd');
        colMap.put(4, 'e');
        colMap.put(5, 'f');
        colMap.put(6, 'g');
        colMap.put(7, 'h');
        colMap.put(8, 'i');
        myTurn = false;
    }
}
