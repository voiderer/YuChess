package UI;

import Definition.ServerInformation;
import MainClass.TheApp;
import MyDataSets.PeerList;
import MyDataSets.PlateInformation;
import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.SimplisticSoftBorderPainter;
import org.jvnet.substance.skin.FieldOfWheatSkin;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TreeMap;

/**
 * Created by AUTOY on 2016/4/26.
 */
public class MainWindow extends JFrame implements ActionListener {
    private TheApp theApp;
    private JButton buJoinRoom, buMakeRoom, buSearchRoom, buLeaveRoom;
    private ChessPanel chesspanel;
    private ChatPanel chatPanel;
    private PlayerPanel playerPanel;
    private RoomPanel roomPanel;

    public MainWindow(String title, TheApp theApp, PlateInformation information) {
        super(title);
        try {
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            //  SubstanceLookAndFeel.setCurrentTheme(new SubstancePurpleTheme());
            SubstanceLookAndFeel.setSkin(new FieldOfWheatSkin());
            //   SubstanceLookAndFeel.setFontPolicy(new DefaultMacFontPolicy());
            //   SubstanceLookAndFeel.setCurrentButtonShaper(new ClassicButtonShaper());
            SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
            //    SubstanceLookAndFeel.setCurrentWatermark(new SubstanceMagneticFieldWatermark());
            SubstanceLookAndFeel.setCurrentBorderPainter(new SimplisticSoftBorderPainter());
            //    SubstanceLookAndFeel.setCurrentGradientPainter(new StandardGradientPainter());
            //    SubstanceLookAndFeel.setCurrentTitlePainter(new Glass3DTitlePainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }

        this.theApp = theApp;
        JToolBar toolBar = new JToolBar();
        toolBar.add(buSearchRoom = new JButton("搜索房间"));
        buSearchRoom.addActionListener(this);
        toolBar.add(buMakeRoom = new JButton("创建房间"));
        buMakeRoom.addActionListener(this);
        toolBar.add(buJoinRoom = new JButton("加入房间"));
        buJoinRoom.addActionListener(this);
        toolBar.add(buLeaveRoom = new JButton("离开房间"));
        buLeaveRoom.addActionListener(this);
        buLeaveRoom.setEnabled(false);
        toolBar.setFloatable(false);
        this.setSize(798, 807);
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.add(toolBar);
        chesspanel = new ChessPanel(information);
        this.add(chesspanel);
        playerPanel = new PlayerPanel();
        roomPanel = new RoomPanel();
        this.add(roomPanel);
        this.add(playerPanel);
        chatPanel = new ChatPanel(theApp, title);
        this.add(chatPanel);
        this.setIconImage(new ImageIcon(MainWindow.class.getResource("/红将1.png")).getImage());

        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.HORIZONTAL;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(toolBar, s);


        s.fill = GridBagConstraints.NONE;
        s.gridheight = 2;
        s.gridwidth = 1;
        s.weightx = 0.5;
        s.weighty = 0.5;
        layout.setConstraints(chesspanel, s);

        s.fill = GridBagConstraints.BOTH;
        s.gridheight = 1;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(roomPanel, s);

        s.fill = GridBagConstraints.BOTH;
        s.gridheight = 1;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(playerPanel, s);

        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 0.1;
        layout.setConstraints(chatPanel, s);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        // this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                theApp.shutDown();
            }
        });
    }

    public void updateRoomPanel(TreeMap<String, ServerInformation> players) {
        roomPanel.updateTable(players);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object == buMakeRoom) {
            if (theApp.setUpRoom()) {
                buMakeRoom.setEnabled(false);
                buLeaveRoom.setEnabled(true);
                buJoinRoom.setEnabled(false);
            }
        } else if (object == buSearchRoom) {
            theApp.searchServer();
        } else if (object == buJoinRoom) {
            String string = roomPanel.getSelected();
            if (string.equals("")) {
                JOptionPane.showMessageDialog(this,
                        "请选择一个房间!", "游戏信息", JOptionPane.ERROR_MESSAGE);
                return;
            }
            theApp.joinGame(string);

        } else if (object == buLeaveRoom) {
            theApp.leaveRoom();
            buMakeRoom.setEnabled(true);
            buJoinRoom.setEnabled(true);
            buLeaveRoom.setEnabled(false);
        }
    }

    public void appendChatLn(String s, Color color) {
        chatPanel.appendLn(s, color);
    }

    public void handlePlate(String string) {
        chesspanel.readFromString(string);
    }

    public void handleMove(String string) {
        chesspanel.handleMove(string);
    }

    public void handleStart(String string) {
        chesspanel.handleStart(string);
    }

    public void updatePlayerPanel(PeerList peerList) {
        playerPanel.updateTable(peerList);
    }

    public void joinSucceeded() {
        buMakeRoom.setEnabled(false);
        buJoinRoom.setEnabled(false);
        buLeaveRoom.setEnabled(true);
    }
}

