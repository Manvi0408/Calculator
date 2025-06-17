import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class calculator {

    int boardWidth = 360;
    int boardHeight = 540;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] buttonValues = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };

    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    calculator() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel, BorderLayout.CENTER);

        for (String buttonValue : buttonValues) {
            JButton button = new JButton(buttonValue);
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));

            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String buttonText = button.getText();

                    if (Arrays.asList(rightSymbols).contains(buttonText)) {
                        if (buttonText.equals("=")) {
                            if (A != null && operator != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                switch (operator) {
                                    case "+":
                                        displayLabel.setText(removeZeroDecimal(numA + numB));
                                        break;
                                    case "-":
                                        displayLabel.setText(removeZeroDecimal(numA - numB));
                                        break;
                                    case "×":
                                        displayLabel.setText(removeZeroDecimal(numA * numB));
                                        break;
                                    case "÷":
                                        if (numB == 0) {
                                            displayLabel.setText("Error");
                                        } else {
                                            displayLabel.setText(removeZeroDecimal(numA / numB));
                                        }
                                        break;
                                }
                                clearAll();
                            }
                        } else {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = null;
                            }
                            operator = buttonText;
                        }

                    } else if (Arrays.asList(topSymbols).contains(buttonText)) {
                        if (buttonText.equals("AC")) {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (buttonText.equals("+/-")) {
                            double value = Double.parseDouble(displayLabel.getText());
                            value *= -1;
                            displayLabel.setText(removeZeroDecimal(value));
                        } else if (buttonText.equals("%")) {
                            double value = Double.parseDouble(displayLabel.getText());
                            value /= 100;
                            displayLabel.setText(removeZeroDecimal(value));
                        }
                    } else {
                        if (buttonText.equals(".")) {
                            if (!displayLabel.getText().contains(".")) {
                                displayLabel.setText(displayLabel.getText() + ".");
                            }
                        } else if ("0123456789".contains(buttonText)) {
                            if (displayLabel.getText().equals("0")) {
                                displayLabel.setText(buttonText);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonText);
                            }
                        } else if (buttonText.equals("√")) {
                            double value = Double.parseDouble(displayLabel.getText());
                            if (value >= 0) {
                                displayLabel.setText(removeZeroDecimal(Math.sqrt(value)));
                            } else {
                                displayLabel.setText("Error");
                            }
                        }
                    }
                }
            });
        }

        frame.setVisible(true); // Show the frame after adding everything
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double value) {
        if (value % 1 == 0) {
            return Integer.toString((int) value);
        } else {
            return Double.toString(value);
        }
    }

    public static void main(String[] args) {
        new calculator();
    }
}
