package View.Central;// Programmer: Cara McNeil
// Description: The main method
// Date Created: 01/11/2020
// Date Modified: 29/11/2020

public class Main {
    public static void main(String[] args) {
        MainMenuView view = new MainMenuView();

        javax.swing.SwingUtilities.invokeLater(view::run);
    }
}
