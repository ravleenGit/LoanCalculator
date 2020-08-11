import javax.swing.*;

public class Main {


    // Git repo link - https://github.com/ravleenGit/LoanCalculator.git

    //Main method to start the form
    public static void main(String[] args) {
        LoanProjection ui = new LoanProjection();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
