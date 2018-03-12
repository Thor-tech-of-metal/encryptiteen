package com.thor.tech.encryptiteen;

import com.thor.tech.encryptiteen.utils.Utils;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Paths;

/**
 * Created by thor on 06/02/17.
 */
@SuppressWarnings("Since15")
public class UnProtectMyFilesEncryptiteen {

    private String inputDirectory;
    private String outputDirectory;
    private String password;

    public UnProtectMyFilesEncryptiteen(){}

    public UnProtectMyFilesEncryptiteen(String inputDirectory, String outputDirectory, String password) {

        this.inputDirectory = inputDirectory;
        this.outputDirectory = buildOutputDecryptDirectory(outputDirectory);
        this.password = password;
    }

    public static void main(String[] args) {

        if ( args.length==0){
            System.out.print("Please provide mode");
            System.exit(0);
        }else if(args[0].equals("decryptFiles")){
            new UnProtectMyFilesEncryptiteen().decryptFilesCommandMode();
            System.out.println("\n");
            System.out.println(Utils.MESSAGE_RESULT_TOTAL_DECRYPT);
        }
    }

    public void decryptFilesGui(){

        final File inputDirectoryFile= new File(inputDirectory);
        decryptFiles( inputDirectoryFile,inputDirectory,outputDirectory,password);

    }

    public void decryptFilesCommandMode(){

        inputDirectory= Utils.readFileInput("Please provide the encrypted input file path. \n -->");
        System.out.println("\n");
        outputDirectory = buildOutputDecryptDirectory( Utils.readFileInput("Please provide the output path where your files are going to be decrypted. \n -->"));
        password = Utils.readKey();
        decryptFiles(new File(inputDirectory),inputDirectory,outputDirectory ,password );
    }

    public void decryptFiles(File directory, String inputDirectory, String outputEncryptedDirectory , String key) {
        try {
            final File[] files = directory.listFiles();
            for (File currentFile : files) {
                final String inputDirectoryParent = new File(inputDirectory).getParent();
                if (currentFile.isDirectory()) {
                    final String outputDirectory = currentFile.getPath().replace(inputDirectoryParent,outputEncryptedDirectory);

                    System.out.println("input directory:" + currentFile.getAbsolutePath());
                    System.out.println("input directory:" + outputDirectory);
                    Utils.createFolder(outputDirectory);
                    decryptFiles(currentFile,inputDirectory, outputEncryptedDirectory, key);
                } else {
                    System.out.println("input file to decrypt:" + currentFile.getCanonicalPath());
                    final String outputDirectory = currentFile.getParent().replace(inputDirectoryParent,outputEncryptedDirectory);
                    System.out.println("output file to decrypted:" + outputDirectory);

                    Utils.createFolder(outputDirectory);
                    final String outputFilePath=outputDirectory+ File.separator+ Paths.get(currentFile.getCanonicalPath()).getFileName();
                    decryptOneFile(currentFile.getCanonicalPath(),key,outputFilePath);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public void decryptOneFile(String inputFilePath,String key,String outputDirectoryPath){

        try {
            final FileInputStream fileInputStream = new FileInputStream(inputFilePath);
            final FileOutputStream fileOutputStream = new FileOutputStream(outputDirectoryPath);
            decrypt(key, fileInputStream, fileOutputStream);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    public String decryptOneFileView(){

        try {
            final InputStream fileInputStream = new FileInputStream(inputDirectory);
            return Utils.decryptOrDecrypt(password,fileInputStream);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    private void decrypt(String key, InputStream is, OutputStream os) throws Throwable {

        Utils.encryptOrDecrypt(key, Cipher.DECRYPT_MODE, is, os);
    }

    private String buildOutputDecryptDirectory(String outPutDirectory){

        return outPutDirectory + File.separator + Utils.OUTPUT_DECRYPTED_FILES_DIR;
    }

    public void createOutputDecryptDirectory(){
        try {
            Utils.createFolder(outputDirectory);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    public String getInputDirectory() {
        return inputDirectory;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }
}