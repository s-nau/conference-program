package View.Account;

import Presenter.Central.SubMenu;
import Presenter.Exceptions.InvalidChoiceException;
import Presenter.PersonController.UserInfoController;
import Presenter.PersonController.UserInfoMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UserInfoView extends AccountView {
    UserInfoController controller;
    UserInfoMenu presenter;
    String[] menuOp;
    JLabel dialogPrompt, pwordPrompt;
    JTextField inputField, passwordField;
    JButton submit;

    public UserInfoView(SubMenu controller) {
        super(controller);
        this.controller = (UserInfoController) controller;
        this.presenter = (UserInfoMenu) controller.getPresenter();

        menuOp = this.presenter.getMenuOptions();

        pwordPrompt = new JLabel(presenter.printEnterPassword());
        initializeObject(pwordPrompt);

        passwordField = new JTextField(30);
        initializeObject(passwordField);

        dialogPrompt = new JLabel("");
        initializeObject(dialogPrompt);

        inputField = new JTextField(30);
        initializeObject(inputField);

        submit = new JButton("submit");
        initializeObject(submit);

        contentPane.setBackground(blueBG);
    }

    private void showVerifyWithPassword() {
        pwordPrompt.setVisible(true);
        passwordField.setText("");
        passwordField.setVisible(true);
    }

    private void showPwordPrompt() {
        dialogPrompt.setText(presenter.printNewPasswordPrompt());
        dialogPrompt.setVisible(true);

        inputField.setText("");
        inputField.setVisible(true);

        showVerifyWithPassword();

        frame.setTitle(presenter.changePwordTitle());

        submit.setActionCommand("password");
        submit.addActionListener(this);
        submit.setVisible(true);
    }

    private void showUsernamePrompt() {
        dialogPrompt.setText(presenter.printNewUsernamePrompt());
        dialogPrompt.setVisible(true);

        inputField.setText("");
        inputField.setVisible(true);

        showVerifyWithPassword();

        frame.setTitle(presenter.changeUsernameTitle());

        submit.setActionCommand("username");
        submit.addActionListener(this);
        submit.setVisible(true);
    }

    private void showEmailPrompt() {
        dialogPrompt.setText(presenter.printNewEmailPrompt());
        dialogPrompt.setVisible(true);

        inputField.setText("");
        inputField.setVisible(true);

        showVerifyWithPassword();

        frame.setTitle(presenter.changeEmailTitle());

        submit.setActionCommand("email");
        submit.addActionListener(this);
        submit.setVisible(true);
    }

    private void hideInput(){
        inputField.setVisible(false);
        passwordField.setVisible(false);
        pwordPrompt.setVisible(false);
        submit.setVisible(false);
        backButton.setVisible(true);
    }

    private void changePassword() {
        try {
            hideInput();
            dialogPrompt.setText(controller.changePassword(inputField.getText(), passwordField.getText()));
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void changeUsername() {
        try {
            hideInput();
            dialogPrompt.setText(controller.changeUsername(inputField.getText(), passwordField.getText()));
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }

    private void changeEmail() {
        try {
            hideInput();
            dialogPrompt.setText(controller.changeEmail(inputField.getText(), passwordField.getText()));
        } catch (InvalidChoiceException e) {
            exceptionDialogBox(presenter.printException(e));
        }
    }




    @Override
    public void actionPerformed(ActionEvent event) {
        super.actionPerformed(event);

        if(eventName.equals(menuOp[0])) {
            hideMainDropDownMenu();
            showUsernamePrompt();
        }

        if(eventName.equals(menuOp[1])) {
            hideMainDropDownMenu();
            showPwordPrompt();
        }

        if(eventName.equals(menuOp[2])) {
            showEmailPrompt();
        }

        if (eventName.equals("password")) {
            changePassword();
        }

        if (eventName.equals("username")) {
            changeUsername();
        }

        if (eventName.equals("email")) {
            changeEmail();
        }

        if (eventName.equals("back")) {
            showMainDropDownMenu();
            frame.setTitle(presenter.getMenuTitle());
        }
    }
}
