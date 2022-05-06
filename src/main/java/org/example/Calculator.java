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

        window.setSize(300,400);

        Dimension minSizeDimension = new Dimension();
        minSizeDimension.setSize(300, 400);
        window.setMinimumSize(minSizeDimension);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(Color.BLUE);
        window.setLocationRelativeTo(null);
//        window.setResizable(false);
        window.setLayout(null);
        window.setIconImage(new ImageIcon("img/Calculator_icon.ico").getImage());

        enter_area();
        buttons();
//        result();

        window.setVisible(true);
        window.addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        inputBufer = input.getText();
                        window.getContentPane().removeAll();
                        enter_area();
                        buttons();
                        input.setText(inputBufer);
                    }
                }
        );
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e);
                switch (e.getKeyCode()){
                    case KeyEvent.VK_0, KeyEvent.VK_1,
                            KeyEvent.VK_2, KeyEvent.VK_3,
                            KeyEvent.VK_4, KeyEvent.VK_5,
                            KeyEvent.VK_6, KeyEvent.VK_7,
                            KeyEvent.VK_8, KeyEvent.VK_9,
                            KeyEvent.VK_QUOTE, KeyEvent.VK_BACK_QUOTE,
                            KeyEvent.VK_PLUS, KeyEvent.VK_MINUS,
                            KeyEvent.VK_BACK_SLASH, KeyEvent.VK_MULTIPLY -> input.setText(
                                    input.getText().concat(String.valueOf(e.getKeyChar()))
                    );
                    case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> mathResults();
                    case KeyEvent.VK_DELETE, KeyEvent.VK_BACK_SPACE -> input.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void enter_area() {
        input.setFont(new Font("Arial", Font.BOLD, 25));
        input.setBackground(Color.WHITE);
        input.setBounds(16,10, window.getWidth() - 28, 40);
        input.setHorizontalAlignment(JTextField.RIGHT);

        window.add(input);
        input.setFocusable(false);

    }
    public void mathResults(){
        Expression exp = new Expression(input.getText());
        double result = exp.calculate();
        if (Double.isNaN(result)){
            System.err.println(exp.getErrorMessage());
            JOptionPane.showMessageDialog(
                    window, exp.getErrorMessage(),
                    "Expression Error", JOptionPane.ERROR_MESSAGE);
            input.setText("");
        }
        else {
            input.setText(
                    String.valueOf(
                            result
                    )
            );
        }
    }
    public void actionPerformed(ActionEvent e) {
        String eventButtonText = ((JButton) e.getSource()).getText();
        System.out.println(eventButtonText);

        if (eventButtonText.equals("=")) {
            mathResults();
        } else if (eventButtonText.equals("C")) {
            input.setText("");
        } else {
            input.setText(
                    input.getText() + eventButtonText
            );
        }
    }
    public void buttons(){
        String[][] arr = {{"1","2","3","C"}, {"4","5","6","*"}, {"7","8","9","-"}, {"0",".","+","/"}, {"(",")","="}};

        Font buttonFont = new Font("Arial", Font.BOLD, 25);
        Insets buttonMargin = new Insets(0, 0, 0,0);

        int i = 0;
        for(String[] buttonSubArray: arr) {
            int j = 0;
            for(String buttonText: buttonSubArray) {
                final JButton jbutton = new JButton();
                jbutton.setText(buttonText);

                jbutton.setFont(buttonFont);
                jbutton.setMargin(buttonMargin);

                if(!buttonText.equals("=")) {
                    jbutton.setBounds(
                            16 + j * (window.getWidth() - 28) / 4,
                            60 + i * (window.getHeight() - 90) / 5 - 2,
                            (window.getWidth() - 28) / 4,
                            (window.getHeight() - 90) / 5 - 2
                    );
                }
                else {
                    jbutton.setBounds(
                            16 + j * (window.getWidth() - 28) / 4,
                            60 + i * (window.getHeight() - 90) / 5 - 2,
                            (window.getWidth() - 28) / 2,
                            (window.getHeight() - 90) / 5 - 2
                    );
                }
                jbutton.setFocusable(false);
                window.add(jbutton);

                jbutton.addActionListener(this::actionPerformed);
                j += 1;
            }
            i += 1;
        }
    }}