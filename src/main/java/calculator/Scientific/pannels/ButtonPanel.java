package calculator.Scientific.pannels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private ActionListener listener;

    public ButtonPanel(ActionListener listener){
        this.listener = listener;
    }
    public void addButton(String buttonName) {
        JButton button = new JButton(buttonName);
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        this.add(button);
        button.addActionListener(listener);
    }
}
