 import java.awt.*;
 import java.awt.event.*;
 import java.util.Arrays;
 import javax.swing.*;
 import javax.swing.border.LineBorder;
 
 public class Calculator {
    int boardWidth = 360;
    int boardHeight =540; 

    String A ="0";
    String B = null;
    String operator = null;
    
    
    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displaypanel = new JPanel();
    JPanel buttonspanel = new JPanel();

     String[] buttonValues = {
        "AC", "+/-", "%", "÷", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};
    
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customOrange = new Color(255, 149, 0);
    Color customBlack = new Color(28, 28, 28);

    Calculator() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout() );

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setFont(new Font("Arial", Font.BOLD, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displaypanel.setLayout(new BorderLayout());
        displaypanel.add(displayLabel);
        frame.add(displaypanel, BorderLayout.NORTH);


        buttonspanel.setLayout(new GridLayout(5, 4));
        buttonspanel.setBackground(customBlack);
        frame.add(buttonspanel );
        for (int i=0; i<buttonValues.length; i++)
        {
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setText(buttonValue);
            button.setFont(new Font("Arial", Font.BOLD, 30));
            buttonspanel.add(button);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack, 2));
            if (Arrays.asList(topSymbols).contains(buttonValue))
            {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            }
            else if (Arrays.asList(rightSymbols).contains(buttonValue))
            {
                button.setBackground(customOrange);
                button.setForeground(Color.WHITE);
            }
            else
            {
                button.setBackground(customDarkGray);
                button.setForeground(Color.WHITE);
            }

            button.addActionListener(new ActionListener() 
            {
                public void actionPerformed(ActionEvent e){
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();   
             if (Arrays.asList(topSymbols).contains(buttonValue))
                {if (buttonValue == "AC")
                    {
                        clearAll();
                        displayLabel.setText("0");

                    }
                    else if (buttonValue == "+/-")
                    {
                        double numDisplay = Double.parseDouble(displayLabel.getText());
                        numDisplay *= -1;
                        displayLabel.setText(removeZeroDecimal(numDisplay)); 

                    }
                    else if (buttonValue == "%")
                    {
                        double numDisplay = Double.parseDouble(displayLabel.getText());
                        numDisplay /= 100;
                        displayLabel.setText(removeZeroDecimal(numDisplay));
                    }
                } 
                else if (Arrays.asList(rightSymbols).contains(buttonValue))
                    {
                        if (buttonValue == "=")
                        {
                            if (A != null)
                            {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+")
                                {
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                }
                                else if (operator == "-")
                                {
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                }
                                else if (operator == "×")
                                {
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                }
                                else if (operator == "÷")
                                {
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                }
                                A = displayLabel.getText();
                                operator = null;
                                B = null;
                            }
                        }
                        else if ("+-×÷".contains(buttonValue))
                        {
                            if (operator == null)
                            {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }

                    }
                else
                {
                    if (buttonValue == ".")
                    {
                        if(!displayLabel.getText().contains(buttonValue))
                        {
                            displayLabel.setText(displayLabel.getText() + buttonValue);
                        }
                    }
                    else if ("0123456789".contains(buttonValue))
                    {
                        if (displayLabel.getText() == "0")
                        {
                            displayLabel.setText(buttonValue);
                        }
                        else
                        {
                            displayLabel.setText(displayLabel.getText() + buttonValue);
                        }
                    }

                }
                }
            });
        }
    }


    void clearAll(){
        A= "0";
        B= null;
        operator = null;
    }

    String removeZeroDecimal(double numDisplay)
    {
        if (numDisplay % 1 == 0)
        {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
