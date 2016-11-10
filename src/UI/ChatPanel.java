package UI;

import MainClass.TheApp;
import org.jvnet.substance.SubstanceScrollPaneUI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by AUTOY on 2016/4/29
 */
class ChatPanel extends JPanel implements ActionListener {
    private JTextField textField;
    private JTextPane textPane;
    private JButton buttonSend;
    private TheApp theApp;
    private String playerName;

    ChatPanel(TheApp theApp, String playerName) {
        super();
        this.theApp = theApp;
        this.playerName = playerName;
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        textField = new JTextField();
        textPane = new JTextPane();
        textPane.setOpaque(false);
        textPane.setEditable(false);
        this.setOpaque(false);
        JScrollPane scrollPane1 = new JScrollPane(textPane);
        scrollPane1.setOpaque(false);
        scrollPane1.setUI(new SubstanceScrollPaneUI());
        buttonSend = new JButton("发送");
        buttonSend.addActionListener(this);
        this.add(scrollPane1);
        this.add(textField);
        this.add(buttonSend);
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth = 0;
        s.weightx = 1;
        s.weighty = 1;
        layout.setConstraints(scrollPane1, s);
        s.gridwidth = 1;
        s.weightx = 1;
        s.weighty = 0;
        layout.setConstraints(textField, s);
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0;
        layout.setConstraints(buttonSend, s);
        this.setPreferredSize(new Dimension(560, 100));
        this.registerKeyboardAction(e -> onSend(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    void appendLn(String string, Color color) {
        SimpleAttributeSet attributeSet = new SimpleAttributeSet();
        StyleConstants.setFontSize(attributeSet, 16);
        StyleConstants.setForeground(attributeSet, color);
        Document docs = textPane.getDocument();
        try {
            docs.insertString(docs.getLength(), string + "\n", attributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        textPane.setCaretPosition(docs.getLength());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object == buttonSend) {
            onSend();
        }
    }

    private void onSend() {
        String context = textField.getText();
        if (context.equals("")) {
            return;
        }
        String message = playerName + ':' + context;
        theApp.sendChatMessage(message);
        textField.setText("");
    }
}
