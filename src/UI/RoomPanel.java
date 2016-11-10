package UI;


import Definition.ServerInformation;
import org.jvnet.substance.SubstanceTableUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;
import java.util.TreeMap;

/**
 * Created by AUTOY on 2016/5/1.
 */
class RoomPanel extends JPanel {
    private JTable table;

    /**
     *
     */
    RoomPanel() {
        super();
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        String[] names = {"房主", "服务器IP", "端口号"};
        Object[][] data = {};

        table = new JTable();
        table.setModel(new DefaultTableModel(data, names));
        table.setUI(new SubstanceTableUI());
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(10);
        table.setUI(new SubstanceTableUI());
        JScrollPane pane = new JScrollPane(table);
        pane.setOpaque(false);
        this.add(pane);
        //    pane.getViewport().setBackground(new Color(153, 204, 255));
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 1;
        layout.setConstraints(pane, s);
        this.setPreferredSize(new Dimension(200, 300));

    }

    void updateTable(TreeMap<String, ServerInformation> players) {
        DefaultTableModel tableModel = (DefaultTableModel) (table.getModel());
        tableModel.setRowCount(0);
        Collection<ServerInformation> collection = players.values();
        for (ServerInformation info : collection) {
            String strings[] = new String[3];
            strings[0] = info.serverName;
            strings[1] = info.serverAddress.getHostAddress();
            strings[2] = "" + info.serverPort;
            tableModel.addRow(strings);
        }
        table.invalidate();
    }

    String getSelected() {
        int i;
        if (-1 == (i = table.getSelectedRow())) {
            return "";
        }
        return table.getValueAt(i, 1).toString() + "|" + table.getValueAt(i, 2);
    }

}

