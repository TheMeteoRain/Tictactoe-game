package fi.akash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Setup screen for the Tic Tac Toe-game.
 */
public class Setup extends JFrame implements ActionListener {
    // Setting up ALL the variables.
    /**
     * Includes informative information for user.
     */
    private JTextArea sizeText = new JTextArea("Size of the board: "),
            amountText = new JTextArea("Amount to win: ");

    /**
     * Text field where user inputs his/her value.
     */
    private JTextField size = new JTextField("3", 3),
            amount = new JTextField("3", 3);

    /**
     * When pressed, allows user to play the game with given settings.
     */
    private JButton ok = new JButton("Ok");

    /**
     * Used to handle user errors.
     */
    private Frame error = new Frame();

    /**
     * Panel used to manage space in Setup-frame.
     */
    private JPanel grid = new JPanel(new GridLayout(0,2)),
            layout = new JPanel(new BorderLayout());

    /**
     * Setup screen for the Tic Tac Toe-game.
     *
     * User can set size of the board and the amount of symbols needed in a row to win, on this screen.
     */
    public Setup() {
        // Adding JTextAreas and JTextFields to grid-panel.
        grid.add(sizeText);
        grid.add(size);
        grid.add(amountText);
        grid.add(amount);

        // Adding JButton to layout-panel.
        layout.add(ok);

        // Adding both grid- and layout-panels to this frame.
        add(grid, "North");
        add(layout, "Center");

        // Adding Action Listener to all buttons.
        ok.addActionListener(this);

        // Press "X" and it will kill the program.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting title, size, location and making frame visible.
        // Frame cannot be resized.
        setTitle("Tic Tac Toe [Setup]");
        setSize(280,100);
        setLocation(ScreenSize.getWidth()/2-140,ScreenSize.getHeight()/2-50);
        setVisible(true);
        setResizable(false);
    }

    /**
     * Launches the game with given settings.
     *
     * Is called when ok-button is pressed.
     *
     * @param e pressed button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // User must give integers, and board size must be greater than or equal to amount to win.
        try {
            int sizeNumber = Integer.parseInt(size.getText());
            int amountNumber = Integer.parseInt(amount.getText());

            if (sizeNumber >= amountNumber) {
                new TictactoeMain(sizeNumber, amountNumber);
                dispose();
            } else {
                JOptionPane.showMessageDialog(error, "Board size must be greater than or equal to amount to win.");
            }
        } catch (NumberFormatException event) {
            JOptionPane.showMessageDialog(error, "Only integers allowed.");
        }
    }
}
