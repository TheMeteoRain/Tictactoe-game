package fi.akash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tic Tac Toe-game with graphical user interface.
 *
 * User plays against a computer without a proper AI. Computer's movements are random.
 * Board size is resizable and the amount of symbols needed in a row to win, is also changeable.
 *
 * @author Akash Singh
 * @version 2015.1205
 * @since 1.8
 */

public class TictactoeMain extends JFrame implements ActionListener {
    // Setting up ALL the variables.
    /**
     * The number needed to get in a row to win the game.
     */
    private int amountToWin;

    /**
     * Game board's size.
     */
    private int size;

    /**
     * Symbols for empty button, player's button and computer's button, in that order.
     */
    private String[] symbols = {"","X","0"};

    /**
     * This turns true, when there is no more buttons to press, and ends the game.
     */
    private boolean endOfTurns = false;

    /**
     * Panel used to manage space in TicTacToeMain-frame.
     */
    private JPanel grid,
            optionPanel = new JPanel(new GridLayout(2,1));

    /**
     * Displays the current situation in the game.
     */
    private JLabel statusLabel = new JLabel("Your turn.", SwingConstants.CENTER);

    /**
     * Button-array for JButtons, used to handle win conditions, for example.
     */
    private JButton[][] buttons;

    /**
     * Button named accordingly for what it does.
     */
    private JButton newGame = new JButton("New Game"),
            settings = new JButton("Settings");

    /**
     * This font is used for buttons.
     */
    private Font font = new Font("Arial",Font.BOLD, 50);

    /**
     * Main frame where all the components(buttons, labels) are laid onto.
     *
     * @param size size of the game board.
     * @param amountToWin the amount of symbols needed to get in a row to win.
     */
    public TictactoeMain(int size, int amountToWin) {
        // Defining grid size and the amount needed to win the game.
        this.amountToWin = amountToWin;
        this.size = size;
        // Creating grid for buttons and array for buttons.
        grid = new JPanel(new GridLayout(0,size));
        buttons = new JButton[size][size];

        // Adding buttons to array and adding them to grid.
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons.length; column++) {
                buttons[row][column] = new JButton(symbols[0]);
                buttons[row][column].addActionListener(this);
                buttons[row][column].setFont(font);
                grid.add(buttons[row][column]);
            }
        }

        // Adding buttons to option-panel.
        optionPanel.add(newGame);
        optionPanel.add(settings);
        optionPanel.add(statusLabel);
        optionPanel.setBackground(Color.white);

        // Adding both grid- and option-panels to this frame.
        add(optionPanel, "North");
        add(grid, "Center");

        // Adding Action Listener to all buttons.
        newGame.addActionListener(this);
        settings.addActionListener(this);

        // Press "X" and it will kill the program.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting title, size, location and making frame visible.
        setTitle("Tic Tac Toe");
        setSize(500,500);
        setLocation(ScreenSize.getWidth()/2-250,ScreenSize.getHeight()/2-250);
        setVisible(true);
    }

    /**
     * Is called when any button is pressed, and acts accordingly for that button.
     *
     * @param e pressed button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // When settings-button is pressed, dispose TicTacToeMain and open Setup.
        if (e.getSource() == settings) {
            new Setup();
            dispose();
        }
        // When newGame-button is pressed call reset-method.
        if (e.getSource() == newGame) {
            reset();
        } else {
            for (int row = 0; row < buttons.length; row++) {
                for (int column = 0; column < buttons.length; column++) {
                    if (e.getSource() == buttons[row][column]) {
                        playerClick(row, column);
                    }
                }
            }
        }
    }

    //-------------------START OF RULES/LOGIC-------------------------//
    /**
     * Goes through a list to check if anyone has won or board is full.
     *
     * Is called at the end of every turn.
     * Goes through every possible victory condition.
     * And also checks if the board is full.
     *
     * @param winner name of the method caller (Player/Computer).
     * @param symbolIndex number that represent method caller's symbol (X/0).
     */
    private void checkStatus(String winner, int symbolIndex) {
        // Checks if there are N amount of N-symbols in a row vertically, diagonally, or horizontally.
        // Then shows a message and disables buttons.
        if (columnWin(symbolIndex)) {
            endOfTheGame(winner + " won");
        } else if (rowWin(symbolIndex)) {
            endOfTheGame(winner + " won");
        } else if (diagonalWin(symbolIndex)) {
            endOfTheGame(winner + " won");
        } else if (noMoreSlots()) {
            endOfTheGame("Board is full. Draw. Game Ends.");
        }
    }

    /**
     * Checks if winning move is found in a column/vertical-shape.
     *
     * @param symbolIndex number that represent method caller's symbol (X/0).
     * @return true = found a winning move, false = winning move was not found.
     */
    private boolean columnWin(int symbolIndex) {
        // Goes through the buttons and compares theirs symbols to the method caller's symbol.
        // If it matches: add 1 to amountToWin
        // If it doesn't match: reset amountToWin
        // Break loop when: correct amount is reached, array ends.
        for (int row = 0; row < size; row++) {
            int amountToWin;
            int column;
            for (column = 0, amountToWin = 0; column < size; column++) {
                if (amountToWin == this.amountToWin) {
                    break;
                } else if (buttons[column][row].getText() != symbols[symbolIndex]) {
                    amountToWin = 0;
                } else if (buttons[column][row].getText() == symbols[symbolIndex]) {
                    amountToWin++;
                }
            }
            // When amountToWin reaches this.amountToWin, we have a winning move.
            if (amountToWin == this.amountToWin) {
                // Colors the winning move.
                for (int n = 0; n < amountToWin; n++) {
                    buttons[column-1-n][row].setBackground(new Color(224,247,255));
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if winning move is found in a row/horizontal-shape.
     *
     * @param symbolIndex number that represent method caller's symbol (X/0).
     * @return true = found a winning move, false = winning move was not found.
     */
    private boolean rowWin(int symbolIndex) {
        // Goes through the buttons and compares theirs symbols to the method caller's symbol.
        // If it matches: add 1 to amountToWin
        // If it doesn't match: reset amountToWin
        // Break loop when: correct amount is reached, array ends.
        for (int row = 0; row < size; row++) {
            int amountToWin;
            int column;
            for (column = 0, amountToWin = 0; column < size; column++) {
                if (amountToWin == this.amountToWin) {
                    break;
                } else if (buttons[row][column].getText() != symbols[symbolIndex]) {
                    amountToWin = 0;
                } else if (buttons[row][column].getText() == symbols[symbolIndex]) {
                    amountToWin++;
                }
            }
            // When amountToWin reaches this.amountToWin, we have a winning move.
            if (amountToWin == this.amountToWin) {
                // Colors the winning move.
                for (int n = 0; n < amountToWin; n++) {
                    buttons[row][column-1-n].setBackground(new Color(224,247,255));
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if winning move is found in a diagonal-shape.
     *
     * @param symbolIndex number that represent method caller's symbol (X/0).
     * @return true = found a winning move, false = winning move was not found.
     */
    private boolean diagonalWin(int symbolIndex) {
        int diagonal;
        int firstDiagonal = 0;
        int secondDiagonal = 0;
        int row;
        int column = 0;

        // LOGIC:
        // - Goes through the array until finds symbol that matches the caller's symbol.
        // - Then it checks from that X.Y position diagonally if there is a line of same symbols
        for (row = 0; row < size; row++) {
            for (column = 0; column < size; column++) {

                if (buttons[row][column].getText() == symbols[symbolIndex]) {

                    // Diagonal -> \
                    for (diagonal = 0, firstDiagonal = 0; diagonal < size; diagonal++) {

                        try {
                            // Helps to exit loops when a win has been found.
                            if (firstDiagonal == this.amountToWin) {
                                break;
                            } else if (buttons[row+diagonal][column+diagonal].getText() != symbols[symbolIndex]) {
                                firstDiagonal = 0;
                            } else if (buttons[row+diagonal][column+diagonal].getText() == symbols[symbolIndex]) {
                                firstDiagonal++;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            continue;
                        }
                    }

                    // Color placement helpers and helps to exit loops when a win has been found.
                    if (firstDiagonal == this.amountToWin) {
                        row = row + diagonal - 1;
                        column = column + diagonal - 1;
                        break;
                    }

                    // Diagonal -> /
                    for (diagonal = 0, secondDiagonal = 0; diagonal < size; diagonal++) {

                        try {
                            // Helps to exit loops when a win has been found.
                            if (secondDiagonal == this.amountToWin) {
                                break;
                            } else if (buttons[row+diagonal][column - diagonal].getText() != symbols[symbolIndex]) {
                                secondDiagonal = 0;
                            } else if (buttons[row+diagonal][column - diagonal].getText() == symbols[symbolIndex]) {
                                secondDiagonal++;
                            }
                        } catch (IndexOutOfBoundsException e) {
                            continue;
                        }
                    }

                    // Color placement helpers and helps to exit loops when a win has been found.
                    if (secondDiagonal == this.amountToWin) {
                        row = row + diagonal - 1;
                        column = column - diagonal + 1;
                        break;
                    }
                } else if (buttons[row][column].getText() != symbols[symbolIndex]) {
                    continue;
                }
            }

            // Helps to exit loops when a win has been found.
            if (firstDiagonal == this.amountToWin || secondDiagonal == this.amountToWin) {
                break;
            }

        }
        // When firstDiagonal or secondDiagonal reaches this.amountToWin, we have a winning move.
        if (firstDiagonal == this.amountToWin || secondDiagonal == this.amountToWin) {
            // Colors the winning move.
            if (firstDiagonal == this.amountToWin) {
                for (int n = 0; n < this.amountToWin; n++) {
                    buttons[row-n][column-n].setBackground(new Color(224,247,255));
                }
            } else {
                for (int n = 0; n < this.amountToWin; n++) {
                    buttons[row-n][column+n].setBackground(new Color(224,247,255));
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if there is pressable buttons.
     *
     * If there is no buttons left to press, the game ends.
     *
     * @return true = buttons left to press, false = no buttons left to press.
     */
    private boolean noMoreSlots() {
        int count = 0;

        // Count every pressable button.
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons.length; column++) {
                if (buttons[row][column].getText() == symbols[0]) {
                    count++;
                }
            }
        }

        // If there is 0 pressable buttons, do this.
        if (count == 0) {
            return true;
        }
        return false;
    }
    //-------------------END OF RULES/LOGIC-------------------------//

    //-------------------START OF PLAYER TURN AND COMPUTER TURN-------------------------//
    /**
     * When the player clicks a button.
     *
     * Player clicks a button. Leaves a symbol to that button and disables it from further pressing.
     * Checks status for wins or if the board is full. Next: computer makes a move.
     *
     * @param row pressed button's row number.
     * @param column pressed buttons's column number.
     */
    private void playerClick(int row, int column) {
        buttons[row][column].setText(symbols[1]);
        buttons[row][column].setEnabled(false);
        checkStatus("Player", 1);
        computerTurn();
    }

    /**
     * When the computer clicks a button.
     *
     * This is called after a player has pressed a button.
     * Checks if there are any pressable buttons, if so, continue normally, if not, skip turn.
     * Randomly picks a button to press. Leaves a symbol to that button and disables it from further pressing.
     * Checks status for wins or if the board is full.
     */
    private void computerTurn() {
        // When enOfTurns-variable is true, there is no more buttons to press, and then computer's turn is skipped.
        // This is to avoid overlapping when pressing last button.
        if (endOfTurns) return;

        // Generating random number for row and column.
        int randomRow = (int) (Math.random() * buttons.length);
        int randomColumn = (int) (Math.random() * buttons.length);

        // Computer presses button according to given random row and column.
        // If the given button is empty.
        // Else generate new numbers.
        while (true) {
            if (buttons[randomRow][randomColumn].getText() == symbols[0]) {
                buttons[randomRow][randomColumn].setEnabled(false);
                buttons[randomRow][randomColumn].setText(symbols[2]);
                checkStatus("Computer", 2);
                break;
            } else {
                randomRow = (int) (Math.random() * buttons.length);
                randomColumn = (int) (Math.random() * buttons.length);
            }
        }
    }
    //-------------------END OF PLAYER TURN AND COMPUTER TURN-------------------------//

    //-------------------START OF OTHER METHODS-------------------------//
    /**
     * Ends the game, prevents any further clicking.
     *
     * This is called if winner is found or board is full.
     * Stops the game. Announces the result.
     * Disables rest of the non-disabled buttons.
     *
     * @param status current situation of the game, in a plain text.
     */
    private void endOfTheGame(String status) {
        setStatus(status);
        endOfTurns = true;
        buttonReset(false, "end");
    }

    /**
     * Resets the game with current settings.
     *
     * This is called when user presses the newGame-button.
     * Reset buttons to their original state.
     */
    private void reset() {
        setStatus("Your turn.");
        buttonReset(true, "reset");
    }

    /**
     * Enables all buttons or disables all buttons.
     *
     * Used with reset(enable) and endOfTheGame(disable).
     *
     * @param enable true = enable all buttons, false = disable all buttons.
     * @param s determines method caller.
     */
    private void buttonReset(boolean enable, String s) {
        for (int row = 0; row < buttons.length; row++) {
            for (int column = 0; column < buttons.length; column++) {
                buttons[row][column].setEnabled(enable);
                if (s.equals("reset")) {
                    buttons[row][column].setText(symbols[0]);
                    buttons[row][column].setBackground(null);
                    endOfTurns = false;
                } else {
                    endOfTurns = true;
                }
            }
        }
    }

    /**
     * Sets status for statusLabel for player to see the current situation of the game.
     *
     * @param status current situation of the game, in a plain text.
     */
    private void setStatus(String status) {
        statusLabel.setText(status);
    }
    //-------------------END OF OTHER METHODS-------------------------//

    /**
     * Entry point of the application.
     *
     * @param args not used.
     */
    public static void main(String[] args){
        // Program starts with a setup-window. Can be accessed again by pressing "Settings".
        new Setup();
    }
}
