package UI;

import Definition.ChessInformation;
import Definition.ChessState;
import Definition.ChessType;
import Definition.Player;
import MyDataSets.PlateInformation;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by AUTOY on 2016/4/26.
 */
class ChessPanel extends JPanel implements MouseListener {
    private ChessSlot matrix[][] = new ChessSlot[10][9];
    private PlateInformation information;
    private ChessSlot chosen;
    private ChessSlot lastMoved1, lastMoved2;

    ChessPanel(PlateInformation information) {
        super(null);
        this.information = information;
        int i, j;
        ChessSlot label;
        this.setPreferredSize(new Dimension(588, 650));
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 9; j++) {
                label = matrix[i][j] = new ChessSlot(i, j);
                label.setBounds(j * 60 + 27, i * 60 + 27, 60, 60);
                label.setOpaque(true);
                label.setUI(new BasicLabelUI());
                label.setBackground(new Color(0, 0, 0, 0));
                label.addMouseListener(this);
                add(label);
            }
        }
        JLabel image;
        add(image = new JLabel(new ImageIcon(ChessPanel.class.getResource("/main.png"))));
        image.setBounds(0, 0, 588, 650);
    }

    private void setChess() {
        int i, j;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 9; j++) {
                matrix[i][j].setIcon(information.getImage(i, j));
            }
        }
    }

    private void refreshChess(ChessSlot slot) {
        slot.setIcon(information.getImage(slot.x, slot.y));
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!information.isMyTurn())
            return;
        if (information.isHasLastMove()) {
            information.clearLastMove();
            refreshChess(lastMoved1);
            refreshChess(lastMoved2);
        }
        ChessSlot label = (ChessSlot) e.getSource();
        ChessInformation temp = information.getInformation(label.x, label.y);
        if (information.isHasChosen()) {
            if (label == chosen)
                return;
            ChessInformation temp1 = information.getInformation(chosen.x, chosen.y);
            if (temp.isVirtual) {
                if (information.tryMoveChess(temp1, temp)) {
                    refreshChess(chosen);
                    refreshChess(label);
                    lastMoved1 = chosen;
                    lastMoved2 = label;
                    temp.isVirtual = false;
                }
            } else if (!information.isSameSide(temp.player)) {
                if (information.tryMoveChess(temp1, temp)) {
                    refreshChess(chosen);
                    refreshChess(label);
                    lastMoved1 = chosen;
                    lastMoved2 = label;
                }
            } else {
                temp1.state = ChessState.NORMAL;
                refreshChess(chosen);
                chosen = label;
                information.setChosen(temp);
                temp.state = ChessState.CHOZEN;
                temp.isVirtuallyChosen = false;
                refreshChess(label);
            }
        } else if (information.isSameSide(temp.player)) {
            information.setChosen(temp);
            information.setHasChosen(true);
            temp.state = ChessState.CHOZEN;
            temp.isVirtuallyChosen = false;
            chosen = label;
            refreshChess(label);
        }
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!information.isMyTurn())
            return;
        Object object = e.getSource();
        if (object instanceof ChessSlot) {
            ChessSlot label = (ChessSlot) object;
            ChessInformation temp = information.getInformation(label.x, label.y);
            if (temp.player == Player.BLANK) {
                if (information.isHasChosen()) {
                    ChessInformation temp1 = information.getInformation(chosen.x, chosen.y);
                    if (information.moveChess(temp1, temp)) {
                        temp.isVirtual = true;
                        temp.player = temp1.player;
                        temp.state = ChessState.VIRTUAL;
                        temp.type = temp1.type;
                        refreshChess(label);
                    }
                }
            } else if (information.isSameSide(temp.player)) {
                if (!temp.isChosen) {
                    temp.isVirtuallyChosen = true;
                    temp.state = ChessState.CHOZEN;
                    refreshChess(label);
                }
            } else {
                if (information.isHasChosen()) {
                    ChessInformation temp1 = information.getInformation(chosen.x, chosen.y);
                    if (information.moveChess(temp1, temp)) {
                        temp.state = ChessState.VIRTUAL;
                        refreshChess(label);
                    }
                }
            }

        }
        this.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!information.isMyTurn())
            return;
        Object object = e.getSource();
        if (object instanceof ChessSlot) {
            ChessSlot label = (ChessSlot) object;
            ChessInformation temp = information.getInformation(label.x, label.y);
            if (temp.isVirtual) {
                temp.isVirtual = false;
                temp.player = Player.BLANK;
                temp.state = ChessState.VIRTUAL;
                temp.type = ChessType.BLANK;
                refreshChess(label);
            } else if (information.isSameSide(temp.player)) {
                if (temp.isVirtuallyChosen) {
                    temp.state = ChessState.NORMAL;
                    refreshChess(label);
                }
            } else {
                if (information.isHasChosen()) {
                    temp.state = ChessState.NORMAL;
                    refreshChess(label);
                }
            }
        }
        this.repaint();
    }

    void readFromString(String string) {
        information.setPlayerBlank();
        information.setMyTurn(false);
        information.readFromString(string);
        setChess();
        repaint();
    }

    void handleMove(String string) {
        if (information.isHasLastMove()) {
            information.clearLastMove();
            refreshChess(lastMoved1);
            refreshChess(lastMoved2);
        }
        int[] ints = information.readMove(string);
        lastMoved1 = matrix[ints[1]][ints[0]];
        lastMoved2 = matrix[ints[3]][ints[2]];
        refreshChess(lastMoved1);
        refreshChess(lastMoved2);
        repaint();
    }

    void handleStart(String string) {
        information.handleStart(string);
        setChess();
        repaint();
    }
}

