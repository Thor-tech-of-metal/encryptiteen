package com.thor.tech.encryptiteen.gui;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by thor on 06/02/17.
 */
public class UtilsGui {

    public static void setUIFont(FontUIResource UIFont) {

        final Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
        while (keys.hasMoreElements()) {

            final Object key = keys.nextElement();
            final Object value = UIManager.get(key);

            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, UIFont);
            }
        }
    }


    public static Dimension getImageDimension() {
        return new Dimension(UtilsGui.getToolkitDimensionWidth()/4, UtilsGui.getToolkitDimensionHeight()/4);
    }

    public static Dimension getPanelTextAreaDimension() {  return new Dimension(UtilsGui.getToolkitDimensionWidth()*55/100 , UtilsGui.getToolkitDimensionHeight()*55/100); }

    public static Dimension getFileChooserDimension() {
        return new Dimension(UtilsGui.getToolkitDimensionWidth()/3, UtilsGui.getToolkitDimensionWidth()/3);
    }

    public static Dimension getTextDimension() {
        return new Dimension(500, 40);
    }

    public static Dimension getButtonDimension() { return new Dimension(300, 40); }


    public static GridBagConstraints getConstraints(int x, int y, double weightx, double weighty, Insets padding, int fill) {

        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = fill;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.insets = padding;
        return gridBagConstraints;
    }


    public static ImageIcon createImageIcon(String path, Object context) {

        final URL imgURL = context.getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static ImageIcon getScaledImage(ImageIcon srcImg, int width, int height) {

        final Image img = srcImg.getImage();
        final Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    public static void shouldDisplayPasswordValidation( boolean passwordValidationResult, JDialog parentComponent) {

        if (!passwordValidationResult) {

            JOptionPane.showMessageDialog(
                    parentComponent,
                    "Nop !! The password is incorrect.",
                    "Encrytiteen error.",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static Dimension getToolkitDimension(){

        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit.getScreenSize();
    }

    public static int getToolkitDimensionWidth(){

        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit.getScreenSize().width;
    }

    public static int getToolkitDimensionHeight(){

        final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        return defaultToolkit.getScreenSize().height;
    }


    public static Integer getFontSize() { return  UtilsGui.getToolkitDimensionWidth()/110; }
}
