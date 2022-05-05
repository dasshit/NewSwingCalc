package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.mariuszgromada.math.mxparser.*;

public class Calculator {
    public JFrame window = new JFrame("Calculator");
    public JTextField input = new JTextField();
    public String inputBufer = "";

    public Calculator() {

        window.setSize(240,400);

        Dimension minSizeDimension = new Dimension();
        minSizeDimension.setSize(240, 400);
        window.setMinimumSize(minSizeDimension);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(Color.BLUE);
        window.setLocationRelativeTo(null);
//        window.setResizable(false);
        window.setLayout(null);

        enter_area();
        buttons();
//        result();

        window.setVisible(true);
        window.addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        System.out.println(e);
                        inputBufer = input.getText();
                        window.getContentPane().removeAll();
                        enter_area();
                        buttons();
                        input.setText(inputBufer);
                    }
                }
        );
    }

    public void enter_area() {
        input.setFont(new Font("Arial", Font.BOLD, 25));
        input.setBackground(Color.WHITE);
        input.setBounds(16,10, window.getWidth() - 28, 40);
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
                final JButton jbutton = new JButton();
                jbutton.setText(arr[i][e]);
                jbutton.setFont(new Font("Arial", Font.BOLD, 25));
                jbutton.setMargin(new Insets(0, 0, 0,0));
                if(arr[i].length == 3 && e == 2) {
                    jbutton.setBounds(
                            16 + e * (window.getWidth() - 28) / 4,
                            60 + i * (window.getHeight() - 90) / 5 - 2,
                            (window.getWidth() - 28) / 2,
                            (window.getHeight() - 90) / 5 - 2
                    );
                }
                else {
                    jbutton.setBounds(
                            16 + e * (window.getWidth() - 28) / 4,
                            60 + i * (window.getHeight() - 90) / 5 - 2,
                            (window.getWidth() - 28) / 4,
                            (window.getHeight() - 90) / 5 - 2
                    );
                }
                jbutton.setFocusable(false);
                JButton jButton1 = new JButton(arr[1][2]);
                window.add(jbutton);

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