import javax.swing.JOptionPane;

public class Positive_Input {
    protected static int loop_input() {
        boolean flag = true;
        int ans = 0;

        while (flag) {
            try {
                String input = JOptionPane.showInputDialog(null, "Enter an positive integer (or type 'exit' to cancel):");

                if (input == null || input.equalsIgnoreCase("exit")) {
                    JOptionPane.showMessageDialog(null, "Input cancelled.");
                    return -1;
                }

                ans = Integer.parseInt(input);
                if (ans > 0) {
                    flag = false;
                }else{
                    JOptionPane.showMessageDialog(null, "Enter Positive input.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
            }
        }
        return ans;
    }
}