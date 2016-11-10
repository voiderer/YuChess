package UI;

import Definition.ClientInformation;
import MyDataSets.PeerList;
import org.jvnet.substance.SubstanceTableUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;

/**
 * Created by AUTOY on 2016/4/30.
 */
class PlayerPanel extends JPanel {
    private JTable table;

    PlayerPanel() {
        super();
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        String[] names = {"用户名", "IP地址", "端口", "身份"};
        String[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, names);
        table = new JTable(model);
        table.setUI(new SubstanceTableUI());
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(3).setPreferredWidth(20);
        JScrollPane pane = new JScrollPane(table);
        pane.setOpaque(false);
        this.add(pane);
        //  pane.getViewport().setBackground(new Color(153,204,255));
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 1;
        layout.setConstraints(pane, s);
        this.setPreferredSize(new Dimension(200, 200));
    }

    void updateTable(PeerList peerList) {
        DefaultTableModel tableModel = (DefaultTableModel) (table.getModel());
        tableModel.setRowCount(0);
        Collection<ClientInformation> collection = peerList.getCollection();
        for (ClientInformation info : collection) {
            String strings[] = new String[4];
            strings[0] = info.clientName;
            strings[1] = info.clientAddress.getHostAddress();
            strings[2] = "" + info.clientPort;
            strings[3] = info.type;
            tableModel.addRow(strings);
        }
        table.invalidate();

    }
}
