package com.thor.tech.encryptiteen;

import com.thor.tech.encryptiteen.utils.Utils;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Paths;

@SuppressWarnings("Since15")
public class ProtectMyFilesEncryptiteen {

    private String secretPhrase;
    private String inputDirectory;
    private String outputDirectory;
    private String password;


    public ProtectMyFilesEncryptiteen(){}

    public ProtectMyFilesEncryptiteen(String inputDirectory, String outputDirectory, String secretPhraseEncrypt, String password ){
        this.inputDirectory = inputDirectory;
        this.outputDirectory = buildOutputEncryptDirectory(outputDirectory);
        this.secretPhrase = secretPhraseEncrypt;
        this.password = password;
    }


    public static void main(String[] args) {

        if ( args.length==0){
            System.out.print("Please provide mode");
            System.exit(0);
        }else if(args[0].equals("encryptFiles")){
            final ProtectMyFilesEncryptiteen protectMyFilesEncryptiteen = new ProtectMyFilesEncryptiteen();
            protectMyFilesEncryptiteen.encryptFilesCommandMode();
            protectMyFilesEncryptiteen.createReportFile();
            System.out.println("\n");
            System.out.println(Utils.MESSAGE_RESULT);
        }
    }

    public void encryptFilesGui(){

        final File inputDirectoryFile= new File(inputDirectory);
        encryptFiles( inputDirectoryFile,inputDirectory,outputDirectory,password);
        this.createReportFile();

    }


    public void encryptFilesCommandMode(){

        inputDirectory = Utils.readFileInput("Please provide the input path. \n -->");
        System.out.println("\n");
        outputDirectory = buildOutputEncryptDirectory( Utils.readFileInput("Please provide the output path where your files are going to be encrypted. \n -->") ) ;
        System.out.println("\n");
        System.out.println(Utils.MESSAGE);
        secretPhrase = Utils.readFileInput("Please enter the the secret phrase which will help you to remember yor encrypt password.\n -->");
        final String key = Utils.readKey();
        final File inputDirectoryFile= new File(inputDirectory);
        encryptFiles( inputDirectoryFile,inputDirectory,outputDirectory,key );
    }


    public void createReportFile(){

        final File inputDirectoryFile= new File(inputDirectory);
        final StringBuilder resultContent = new StringBuilder(Utils.ENCRYPT_MESSAGE_RESULT).append("\n").append(secretPhrase).append("\n");
        final String resultFileName = outputDirectory + File.separator + Utils.OUTPUT_RESULT + File.separator + inputDirectoryFile.getName() + ".txt";

        Utils.createFolder(outputDirectory + File.separator + Utils.OUTPUT_RESULT);
        Utils.createResultFile(resultFileName,resultContent.toString());
    }


    public void encryptFiles(File directory, String inputDirectory, String outputEncryptedDirectory , String key) {
        try {
            final File[] files = directory.listFiles();
            for (File currentFile : files) {
                final String inputDirectoryParent = new File(inputDirectory).getParent();
                if (currentFile.isDirectory()) {
                    final String outputDirectory = currentFile.getPath().replace(inputDirectoryParent,outputEncryptedDirectory);

                    System.out.println("input directory:" + currentFile.getAbsolutePath());
                    System.out.println("input directory:" + outputDirectory);
                    Utils.createFolder(outputDirectory);
                    encryptFiles(currentFile,inputDirectory, outputEncryptedDirectory, key);
                } else {
                    System.out.println("input file to encrypt:" + currentFile.getCanonicalPath());
                    final String outputDirectory = currentFile.getParent().replace(inputDirectoryParent,outputEncryptedDirectory);
                    System.out.println("output file to encrypted:" + outputDirectory);

                    Utils.createFolder(outputDirectory);
                    final String outputFilePath=outputDirectory+ File.separator+ Paths.get(currentFile.getCanonicalPath()).getFileName();
                    encryptOneFile(currentFile.getCanonicalPath(),key,outputFilePath);
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public void encryptOneFile(String inputFilePath,String key,String outputDirectoryPath){

        try {
            final FileInputStream fileInputStream = new FileInputStream(inputFilePath.trim());
            final FileOutputStream fileOutputStream = new FileOutputStream(outputDirectoryPath);
            encrypt(key, fileInputStream, fileOutputStream);

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void encrypt(String key, InputStream is, OutputStream os) throws Throwable {

        Utils.encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, is, os);
    }

    private String buildOutputEncryptDirectory(String outPutDirectory){

        return outPutDirectory + File.separator + Utils.OUTPUT_ENCRYPTED_FILES_DIR;
    }

    public void createOutputEncryptDirectory(){
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