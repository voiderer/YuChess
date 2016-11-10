package UI;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.border.SimplisticSoftBorderPainter;
import org.jvnet.substance.skin.FieldOfWheatSkin;
import org.jvnet.substance.watermark.SubstanceBubblesWatermark;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnterInfoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private String tempString;
    private boolean flag;

    public EnterInfoDialog() {
        try {
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            SubstanceLookAndFeel.setSkin(new FieldOfWheatSkin());
            SubstanceLookAndFeel.setCurrentWatermark(new SubstanceBubblesWatermark());
            SubstanceLookAndFeel.setCurrentBorderPainter(new SimplisticSoftBorderPainter());
        } catch (Exception e) {
            System.err.println("Something went wrong!");
        }
        contentPane = new JPanel();
        buttonOK = new JButton("确定");
        buttonCancel = new JButton("取消");
        textField1 = new JTextField(21);
        contentPane.add(textField1, BorderLayout.CENTER);
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(buttonOK);
        panel.add(buttonCancel);
        contentPane.add(panel, BorderLayout.SOUTH);
        setContentPane(contentPane);
        setModal(true);
        setSize(300, 120);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here
        flag = true;
        tempString = textField1.getText();
        hide();
    }

    private void onCancel() {
// add your code here if necessary
        flag = false;
        hide();
    }

    public String getTempString() {
        return tempString;
    }

    public boolean isFlag() {
        return flag;
    }
}
