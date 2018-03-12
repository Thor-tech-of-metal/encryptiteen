package com.thor.tech.encryptiteen.gui;

import com.thor.tech.encryptiteen.UnProtectMyFilesEncryptiteen;
import com.thor.tech.encryptiteen.utils.Utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
/**
 * Created by thor on 06/02/17.
 */
public class DecryptiteenOneFileViewGUI extends JDialog {


    private final JPanel contentPane=new JPanel();

    private final JButton inputDirectoryButton = new JButton("Input encrypted file path");
    private final JTextField inputDirTextField = new JTextField();


    private final JTextArea fileContent = new JTextArea();

    private final JLabel passwordLabel = new JLabel("Please provide the password.");
    private final JPasswordField passwordField = new JPasswordField();

    private final JButton buttonOK = new JButton("Ok");
    private final JButton buttonCancel= new JButton("Cancel");
    private final JButton buttonClean= new JButton("Clean all.");

    private final JScrollPane scrollPaneFileContent = new JScrollPane (
                                                                        fileContent,
                                                                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                                                        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS
    );




    public DecryptiteenOneFileViewGUI() {

        setContentPane(contentPane);
        addComponentsToPane(contentPane);

        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        inputDirectoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String inputPath = FileChooser.getFilePathFromFileChooser(JFileChooser.FILES_AND_DIRECTORIES);
                inputDirTextField.setText(inputPath);
            }
        });


        buttonClean.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanAll();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    public void addComponentsToPane(Container pane) {

        pane.setLayout(new GridBagLayout());
        fileContent.setEnabled(true);
        fileContent.setEditable(false);

        scrollPaneFileContent.setPreferredSize(UtilsGui.getPanelTextAreaDimension());

        final GridBagConstraints gridBagConstraints = UtilsGui.getConstraints(0, 0,0,0,new Insets(1,1,10,1),GridBagConstraints.BOTH);
        gridBagConstraints.gridwidth=2;
        pane.add(scrollPaneFileContent,gridBagConstraints);


        inputDirectoryButton.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(inputDirectoryButton, UtilsGui.getConstraints(0, 1,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

        inputDirTextField.setPreferredSize(UtilsGui.getTextDimension());
        pane.add(inputDirTextField,UtilsGui.getConstraints(1, 1,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));



        passwordLabel.setPreferredSize(UtilsGui.getTextDimension());
        pane.add(passwordLabel, UtilsGui.getConstraints(0, 2,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

        passwordField.setPreferredSize(UtilsGui.getTextDimension());
        pane.add(passwordField,UtilsGui.getConstraints(1, 2,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));


        buttonOK.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(buttonOK, UtilsGui.getConstraints(0, 3,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

        buttonClean.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(buttonClean,UtilsGui.getConstraints(1, 3,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));


        buttonCancel.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(buttonCancel,UtilsGui.getConstraints(0, 4,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

    }

    private void onOK() {

        if (validateInput()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Nop!! Encryptiteen will decrypt if all fields are not completed.",
                    "Encrytiteen error.",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {

            final UnProtectMyFilesEncryptiteen unProtectMyFilesEncryptiteen = new UnProtectMyFilesEncryptiteen(
                    inputDirTextField.getText(),
                    "",
                    passwordField.getText()
            );

            // clean the text area.
            fileContent.setText("");
            final String decryptedValue = unProtectMyFilesEncryptiteen.decryptOneFileView();
            fileContent.setText(decryptedValue);
            //set the scroll to the top.
            fileContent.setSelectionStart(0);
            fileContent.setSelectionEnd(0);

            JOptionPane.showMessageDialog(
                    this,
                    "A file has been decryptiteen view mode. \r if you cannot see the content this is because you did something worng!",
                    "Encrytiteen.",
                    JOptionPane.INFORMATION_MESSAGE
            );



        }
    }

    private void cleanAll(){
        fileContent.setText("");
        passwordField.setText("");
        inputDirTextField.setText("");
    }

    private Boolean validateInput() {

        final Boolean passwordValidationResult = Utils.isAValidPassword( passwordField.getText() );
        UtilsGui.shouldDisplayPasswordValidation(passwordValidationResult, this);


        return (
                    inputDirTextField.getText().isEmpty() ||
                    passwordField.getPassword().length == 0 ||
                    !passwordValidationResult
                );
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {

        UtilsGui.setUIFont (new FontUIResource("Serif",Font.PLAIN,UtilsGui.getFontSize()));
        final Image image = new ImageIcon(DecryptiteenGUI.class.getResource("/images/icon.png")).getImage();
        final DecryptiteenOneFileViewGUI dialog = new DecryptiteenOneFileViewGUI();
        dialog.setTitle("Decryptiteen one file view !!!");
        dialog.setIconImage(image);
        dialog.pack();
        dialog.setSize(UtilsGui.getToolkitDimensionWidth(),UtilsGui.getToolkitDimensionHeight());
        dialog.validate();
        dialog.setVisible(true);
        System.exit(0);
    }

}
