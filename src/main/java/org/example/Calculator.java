package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.BadLocationException;

import org.mariuszgromada.math.mxparser.*;

public class Calculator {
    public JFrame window = new JFrame("Calculator");
    public JTextField input = new JTextField();
    public JLabel resultsLabel = new JLabel("Last results:");
    public String[][] buttonTextArray = {
            {"1","2","3","C"},
            {"4","5","6","*"},
            {"7","8","9","-"},
            {"0",".","+","/"},
            {"(",")","="}
    };
    public ArrayList<ArrayList<JButton>> keypadButtons = new ArrayList<ArrayList<JButton>>();
    public Insets buttonMargin = new Insets(0, 0, 0,0);
    public ArrayList<String> resultsList = new ArrayList<String>();
    public JList jResultsList = new JList(resultsList.toArray());
    public String inputBufer = "";
    public KeyListener keyListener = new KeyListener() {
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
                        KeyEvent.VK_QUOTE, KeyEvent.VK_BACK_QUOTE, KeyEvent.VK_MINUS,
                        KeyEvent.VK_PERIOD,
                        KeyEvent.VK_SLASH, KeyEvent.VK_MULTIPLY -> input.setText(
                        input.getText().concat(String.valueOf(e.getKeyChar()))
                );
                case KeyEvent.VK_COMMA -> input.setText(
                        input.getText().concat(".")
                );
                case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> mathResults();
                case KeyEvent.VK_EQUALS -> {
                    if (e.isShiftDown()) {
                        input.setText(
                                input.getText().concat(String.valueOf(e.getKeyChar()))
                        );
                    } else {
                        mathResults();
                    }
                }
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
                case KeyEvent.VK_UP, KeyEvent.VK_KP_UP, KeyEvent.VK_PAGE_UP -> {
                    if (jResultsList.isSelectionEmpty()) {
                        jResultsList.setSelectedIndex(0);
                    } else {
                        jResultsList.setSelectedIndex(
                                jResultsList.getSelectedIndex() - 1
                        );
                    }
                }
                case KeyEvent.VK_DOWN, KeyEvent.VK_KP_DOWN, KeyEvent.VK_PAGE_DOWN -> {
                    if (jResultsList.isSelectionEmpty()) {
                        jResultsList.setSelectedIndex(
                                jResultsList.getLastVisibleIndex()
                        );
                    } else {
                        jResultsList.setSelectedIndex(
                                jResultsList.getSelectedIndex() + 1
                        );
                    }
                }
                case KeyEvent.VK_ESCAPE -> {
                    if (!jResultsList.isSelectionEmpty()) {
                        jResultsList.clearSelection();
                    }
                    input.setText("");
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println(e);
        }
    };

    public Calculator() {

        window.setSize(540,400);

        Dimension minSizeDimension = new Dimension();
        minSizeDimension.setSize(540, 400);
        window.setMinimumSize(minSizeDimension);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBackground(Color.BLUE);
        window.setLocationRelativeTo(null);
//        window.setResizable(false);
        window.setLayout(null);
        window.setIconImage(new ImageIcon("img/Calculator_icon.ico").getImage());

        window.add(input);

        window.add(resultsLabel);

        window.add(jResultsList);
        jResultsList.addListSelectionListener(this::valueChanged);
        jResultsList.addKeyListener(keyListener);

        for(String[] buttonSubArray: buttonTextArray) {

            ArrayList<JButton> buttonsRow = new ArrayList<JButton>();

            for (String buttonText : buttonSubArray) {
                final JButton newButton = new JButton(buttonText);
                newButton.addActionListener(this::actionPerformed);
                window.add(newButton);
                buttonsRow.add(
                        newButton
                );
            }
            keypadButtons.add(
                    buttonsRow
            );
        }

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
        window.addKeyListener(keyListener);
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
            resultsList.add(String.valueOf(result));
            jResultsList.setListData(resultsList.toArray());
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
                    input.getText().concat(eventButtonText)
            );
        }
    }
    public void valueChanged(ListSelectionEvent e) {
        System.out.println(e);
        System.out.println(jResultsList.getSelectedValue());
        String selectedValue = (String) jResultsList.getSelectedValue();
        if (selectedValue != null && !selectedValue.isEmpty()){
            input.setText(selectedValue);
        }
        window.requestFocus();
    }
    public void createUserInterface(){
//        window.getContentPane().removeAll();

        int windowHeight = window.getHeight();
        int windowWidth = window.getWidth() * 3 / 5;

        Font customFont = new Font("Arial", Font.BOLD, (windowHeight) / (12));

        resultsLabel.setFont(new Font("Arial", Font.BOLD, 25));
        resultsLabel.setBounds(
                windowWidth + 16,
                10,
                windowWidth / 2 - 28,
                windowHeight / 10
        );

        jResultsList.setFont(new Font("Arial", Font.BOLD, 20));
        jResultsList.setBounds(
                windowWidth + 16,
                windowHeight / 10 + 20,
                windowWidth * 2 / 3 - 40,
                windowHeight - (windowHeight / 10 + 60)
        );

        input.setFont(customFont);
        input.setBackground(Color.WHITE);
        input.setBounds(16,10, windowWidth - 28, windowHeight / 6);
        input.setHorizontalAlignment(JTextField.RIGHT);
        input.setFocusable(false);

        int buttonHeight = (windowHeight * 5 / 6 - 28) / 5 - 4;
        int buttonWidth;

        int i = 0;
        for(ArrayList<JButton> buttonSubArray: keypadButtons) {

            int j = 0;
            int buttonY = windowHeight / 6 + 20 + i * buttonHeight - 2;

            for(JButton jbutton: buttonSubArray) {

                int buttonX = 16 + j * (windowWidth - 28) / 4;

                jbutton.setFont(customFont);
                jbutton.setMargin(buttonMargin);

                if(!jbutton.getText().equals("=")) {
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
                j += 1;
            }
            i += 1;
        }
        window.setFocusable(true);
    }}