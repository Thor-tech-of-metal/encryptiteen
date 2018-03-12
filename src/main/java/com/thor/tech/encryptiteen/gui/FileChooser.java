package com.thor.tech.encryptiteen.gui;

import java.awt.*;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

public class FileChooser {

    public final String pathNotDefined = "path not defined.";

    public static void main(String[] args) {

        getFilePathFromFileChooser(JFileChooser.FILES_AND_DIRECTORIES);
    }

    public static String getFilePathFromFileChooser(int mode) {

        final JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setPreferredSize(UtilsGui.getFileChooserDimension());
        jfc.setFileSelectionMode(mode);
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return "not-selected";
    }
}