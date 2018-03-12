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
public class DecryptiteenGUI extends JDialog {


    private final JPanel contentPane=new JPanel();

    private final JButton inputDirectoryButton = new JButton("Input encrypted directory path");
    private final JTextField inputDirTextField = new JTextField();

    private final JButton outputDirectoryButton = new JButton("Output directory path");;
    private final JTextField outputDirTextField = new JTextField();

    private final JLabel passwordLabel = new JLabel("Please provide the password.");
    private final JPasswordField passwordField = new JPasswordField();

    private final JButton buttonOK = new JButton("Ok");
    private final JButton buttonCancel= new JButton("Cancel");

    private final ImageIcon image = UtilsGui.createImageIcon("/images/sapo2.jpg",this);

    private final JLabel labelImage = new JLabel(
            "",
            UtilsGui.getScaledImage(image,UtilsGui.getToolkitDimensionWidth()/3,UtilsGui.getToolkitDimensionHeight()/3),
            JLabel.CENTER
    );


    public DecryptiteenGUI() {

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
                final String inputPath = FileChooser.getFilePathFromFileChooser(JFileChooser.DIRECTORIES_ONLY);
                inputDirTextField.setText(inputPath);
            }
        });

        outputDirectoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String outputPath = FileChooser.getFilePathFromFileChooser(JFileChooser.DIRECTORIES_ONLY);
                outputDirTextField.setText(outputPath);
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

        labelImage.setPreferredSize(UtilsGui.getImageDimension());

        final GridBagConstraints gridBagConstraints = UtilsGui.getConstraints(0, 0,0,0,new Insets(5,5,5,5),GridBagConstraints.BOTH);
        gridBagConstraints.gridwidth=2;
        pane.add(labelImage,gridBagConstraints);


        inputDirectoryButton.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(inputDirectoryButton, UtilsGui.getConstraints(0, 1,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

        inputDirTextField.setPreferredSize(UtilsGui.getTextDimension());
        pane.add(inputDirTextField,UtilsGui.getConstraints(1, 1,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));


        outputDirectoryButton.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(outputDirectoryButton, UtilsGui.getConstraints(0, 2,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

        outputDirTextField.setPreferredSize(UtilsGui.getTextDimension());
        pane.add(outputDirTextField,UtilsGui.getConstraints(1, 2,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));


        passwordLabel.setPreferredSize(UtilsGui.getTextDimension());
        pane.add(passwordLabel, UtilsGui.getConstraints(0, 3,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

        passwordField.setPreferredSize(UtilsGui.getTextDimension());
        pane.add(passwordField,UtilsGui.getConstraints(1, 3,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));


        buttonOK.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(buttonOK, UtilsGui.getConstraints(0, 4,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

        buttonCancel.setPreferredSize(UtilsGui.getButtonDimension());
        pane.add(buttonCancel,UtilsGui.getConstraints(1, 4,0,0,new Insets(10,10,10,10),GridBagConstraints.HORIZONTAL));

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
                    outputDirTextField.getText(),
                    passwordField.getText()
            );

            unProtectMyFilesEncryptiteen.decryptFilesGui();

            JOptionPane.showMessageDialog(
                    this,
                    "All files has been decryptiteen. \n if you cannot see the content this is because you did something worng!",
                    "Encrytiteen.",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        }
    }

    private Boolean validateInput() {

        final Boolean passwordValidationResult = Utils.isAValidPassword( passwordField.getText() );

        UtilsGui.shouldDisplayPasswordValidation(passwordValidationResult, this);

        return (
                    inputDirTextField.getText().isEmpty() ||
                    outputDirTextField.getText().isEmpty() ||
                    passwordField.getPassword().length == 0 ||
                    ! passwordValidationResult
                );
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {

        UtilsGui.setUIFont (new FontUIResource("Serif",Font.PLAIN,UtilsGui.getFontSize()));
        final Image image = new ImageIcon(DecryptiteenGUI.class.getResource("/images/icon.png")).getImage();
        final DecryptiteenGUI dialog = new DecryptiteenGUI();

        dialog.setTitle("Decryptiteen !!!");
        dialog.setIconImage(image);
        dialog.pack();
        dialog.setSize(UtilsGui.getToolkitDimensionWidth()/2,new Double(UtilsGui.getToolkitDimensionHeight()*0.7).intValue());
        dialog.validate();
        dialog.setVisible(true);
        System.exit(0);
    }

}
