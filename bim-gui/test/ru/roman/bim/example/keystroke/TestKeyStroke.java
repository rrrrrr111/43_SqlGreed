package ru.roman.bim.example.keystroke;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/** @author Roman 23.03.13 14:36 */
public class TestKeyStroke {
    public static void main(String[] args) {
        JButton button = new JButton();
        button.setAction(new AbstractAction("press me") {
            {
                putValue(Action.ACTION_COMMAND_KEY, getValue(Action.NAME));
            }
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
            }
        });
        JPanel panel = new JPanel();
        panel.add(button);
        panel.setPreferredSize(new Dimension(200,100));
        // Bind a keystroke to the button to act as accelerator.
        int c = JComponent.WHEN_IN_FOCUSED_WINDOW;
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        panel.getInputMap(c).put(ks, "PRESS");
        panel.getActionMap().put("PRESS", button.getAction());
        JOptionPane.showMessageDialog(null, panel,"", -1);
    }
}
