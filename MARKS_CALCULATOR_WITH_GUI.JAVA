import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MarksCalculatorGUI extends JFrame implements ActionListener {
    private final  JTextField[] marksFields;
    private  final JButton calculateButton;
    private final JTextArea resultArea;
    private final int numSubjects = 5; 

    public MarksCalculatorGUI() {
        setTitle("Marks Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(numSubjects + 1, 2, 10, 10));
        marksFields = new JTextField[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            inputPanel.add(new JLabel("Enter marks for Subject " + (i + 1) + " (out of 100):"));
            marksFields[i] = new JTextField();
            inputPanel.add(marksFields[i]);
        }

        calculateButton = new JButton("Calculate Result");
        calculateButton.addActionListener(this);
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        double totalMarks = 0;
        boolean validInput = true;

        for (int i = 0; i < numSubjects; i++) {
            try {
                double mark = Double.parseDouble(marksFields[i].getText());
                if (mark < 0 || mark > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                    validInput = false;
                    break;
                }
                totalMarks += mark;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for all subjects.", "Error", JOptionPane.ERROR_MESSAGE);
                validInput = false;
                break;
            }
        }

        if (validInput) {
            double average = totalMarks / numSubjects;


            String grade;

            if (average >= 90) grade = "A+";
            else if (average >= 80) grade = "A";
            else if (average >= 70) grade = "B";
            else if (average >= 60) grade = "C";
            else if (average >= 50) grade = "D";
            else grade = "F";

            resultArea.setText("----- Result -----\n");
            resultArea.append("Total Marks: " + totalMarks + "\n");
            resultArea.append("Average Percentage: " + String.format("%.2f", average) + "%\n");
            resultArea.append("Grade: " + grade + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MarksCalculatorGUI::new);
    }
}
