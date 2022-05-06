package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;

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

        createUserInterface();

        window.setVisible(true);
        window.addComponentListener(
                new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        System.out.println(e);
                        inputBufer = input.getText();
                        createUserInterface();
                        input.setText(inputBufer);
                    }
                }
        );
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e);
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
                    case KeyEvent.VK_DELETE -> input.setText("");
                    case KeyEvent.VK_BACK_SPACE -> {
                        try {
                            input.setText(
                                    input.getText(0, input.getText().length() - 1)
                            );
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(e);
            }
        });
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
    public void createUserInterface(){
        window.getContentPane().removeAll();

        int windowHeight = window.getHeight();
        int windowWidth = window.getWidth();

        Font customFont = new Font("Arial", Font.BOLD, (windowHeight) * 5 / (80));

        input.setFont(customFont);
        input.setBackground(Color.WHITE);
        input.setBounds(16,10, windowWidth - 28, windowHeight / 10);
        input.setHorizontalAlignment(JTextField.RIGHT);

        window.add(input);
        input.setFocusable(false);

        String[][] arr = {
                {"1","2","3","C"},
                {"4","5","6","*"},
                {"7","8","9","-"},
                {"0",".","+","/"},
                {"(",")","="}
        };
        Insets buttonMargin = new Insets(0, 0, 0,0);

        int buttonHeight = (windowHeight * 9 / 10 - 28) / 5 - 10;
        System.out.println(buttonHeight);
        int buttonWidth;

        int i = 0;
        for(String[] buttonSubArray: arr) {

            int j = 0;
            int buttonY = windowHeight / 10 + 20 + i * (windowHeight - 90) / 5 - 2;

            for(String buttonText: buttonSubArray) {

                int buttonX = 16 + j * (windowWidth - 28) / 4;

                final JButton jbutton = new JButton(buttonText);

                jbutton.setFont(customFont);
                jbutton.setMargin(buttonMargin);

                if(!buttonText.equals("=")) {
                   buttonWidth =  (windowWidth - 28) / 4;
                }else{
                    buttonWidth =  (windowWidth - 28) / 2;
                }

                jbutton.setBounds(
                        buttonX,
                        buttonY,
                        buttonWidth,
                        buttonHeight
                );

                jbutton.setFocusable(false);

                window.add(jbutton);

                jbutton.addActionListener(this::actionPerformed);
                j += 1;
            }
            i += 1;
        }
    }}