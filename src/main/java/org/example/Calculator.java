package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.mariuszgromada.math.mxparser.*;

public class Calculator {
    public JFrame window = new JFrame("Calculator");
    public JTextField input = new JTextField();

    public Calculator() {

        window.setSize(480,405);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(Color.BLUE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setLayout(null);

        enter_area();
        buttons();
//        result();

        window.setVisible(true);
    }

    public void enter_area() {
        input.setFont(new Font("Arial", Font.BOLD, 25));
        input.setBackground(Color.WHITE);
        input.setBounds(16,10, 248, 36);
        input.setHorizontalAlignment(JTextField.RIGHT);

        window.add(input);
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyDispatcher());

    }
    class KeyDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
//                result();
            }
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                input.setText("");
            }
            return false;
        }
    }
    public void buttons(){
        String[][] arr = {{"1","2","3","C"}, {"4","5","6","*"}, {"7","8","9","-"}, {"0",".","+","/"}, {"(",")","="}};

        for(int i = 0; i < arr.length; i++) {
            for(int e = 0; e < arr[i].length; e++) {
                JButton jbutton = new JButton();
                jbutton.setText(arr[i][e]);
                jbutton.setMargin(new Insets(0, 0, 0,0));
                if(arr[i].length == 3 && e == 2) {
                    jbutton.setBounds(16 + e * 62, 55 + i * 62, 122, 60);
                }
                else {
                    jbutton.setBounds(16 + e * 62, 55 + i * 62, 60, 60);
                }
                jbutton.setFocusable(false);
                JButton jButton1 = new JButton(arr[1][2]);
                window.add(jbutton);
                window.add(jButton1);

                jbutton.addActionListener(
                        new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                String buttonText = ((JButton) e.getSource()).getText();
                                System.out.println(buttonText);

                                if (buttonText.equals("=")) {
                                    Expression exp = new Expression(input.getText());
                                    input.setText(
                                            String.valueOf(
                                                    exp.calculate()
                                            )
                                    );
                                } else if (buttonText.equals("C")) {
                                    input.setText("");
                                } else {
                                    input.setText(
                                            input.getText() + buttonText
                                    );
                                }
                            }
                        }
                );
            }
        }
    }}