package com.thor.tech.encryptiteen.utils;


import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by thor on 06/02/17.
 */
@SuppressWarnings("Since15")
public class Utils {

    public static final String OUTPUT_ENCRYPTED_FILES_DIR ="encryptedOutput";
    public static final String OUTPUT_DECRYPTED_FILES_DIR="decryptedOutput";
    public static final String OUTPUT_RESULT="results";

    public static final String ENCRYPT_MESSAGE_RESULT ="+------------------------------------------------------------------+\n" +
            "| This is the phrase which will help you to remember the password. |\n" +
            "| *Remember encryptitten always says do not lose this file  !!!!!!!|\n" +
            "| *Remember encryptitten always says do not store  passwords!!!!!!!|\n" +
            "+------------------------------------------------------------------+\n\n"+
            "The phrase:\n";

    public static final String ZIP_MESSAGE_RESULT ="+----------------------------------------------------------------------+\n" +
            "| This is the phrase which will help you to remember the zip password. |\n" +
            "| *Remember encryptitten always says do not lose this file  \\m/ !!!!!!!|\n" +
            "| *Remember encryptitten always says do not store  passwords!!!!!!!!!!!|\n" +
            "+----------------------------------------------------------------------+\n\n"+
            "The phrase:\n";

    public static final String MESSAGE= "+------------------------------------------------------------------------------+\n"+
            "|The phrase is something which describes your password.                        |\n"+
    "|You should be the only one who can work out the password based on this phrase.|\n"+
            "+------------------------------------------------------------------------------+\n";

    public static final String MESSAGE_RESULT ="+------------------------------------------------+\n" +
            "|                                                |\n" +
            "|        Encryptiteen has completed \\m/          |\n" +
            "|* Check result folder to find the summary file  |\n" +
            "|                                                |\n" +
            "+------------------------------------------------+\n";


    public static final String MESSAGE_RESULT_TOTAL_DECRYPT ="+--------------------------------------------------+\n" +
            "|                                                  |\n" +
            "|        Encryptiteen has completed \\m/            |\n" +
            "|        * Check result folder                     |\n" +
            "|                                                  |\n" +
            "+--------------------------------------------------+\n";

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30})";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);;



    public static void doCopy(InputStream inputStream, OutputStream outputStream) throws IOException {

        final byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, numBytes);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public static void doBuffer(InputStream inputStream, OutputStream outputStream) throws IOException {

        final byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, numBytes);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public static String readKey() {

        try {
            final Console cnsl = System.console();
            // if console is not null
            if (cnsl != null) {
                System.out.println("Please enter a password more than 8 digit password.\n");
                // read password into the char array
                final String key= cnsl.readPassword("Password: ").toString();
                return key;
            }
        } catch (Exception exception) {
            // if any error occurs
            exception.printStackTrace();
        }
        return "error";
    }

    public static String readFileInput(String messageToDisplay){

        System.out.print(messageToDisplay);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();

    }

    public  static void  createResultFile(String filePath,String content){

        FileWriter  fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

            fileWriter = new FileWriter(filePath);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (bufferedWriter != null) bufferedWriter.close();
                if (fileWriter != null) fileWriter.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    public static void createFolder(String folderToCreate) {

        final File theDir = new File(folderToCreate);
        try {
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * This method evaluates if the directory has been created.
     * @param directoryPath directory to be evaluated.
     * @return true if the directoy has been created.
     */
    public static boolean checkIfDirectoryHasBeenCreated(String directoryPath) {

        try {
            final File theDir = new File(directoryPath);
            return theDir.exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public static boolean isAValidPassword(final String password){

        final Matcher  matcher = pattern.matcher(password);
        return matcher.matches() && validateKeySize(password);

    }

    /**
     * This method is going to validate if the key is good enough to be DES secrete key
     * @param key this is the key to be evaluated.
     * @return true if the key is good enough to be DES secrete key.
     */
    private static boolean validateKeySize(String key) {

        try {
            final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
            final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            secretKeyFactory.generateSecret(desKeySpec);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }


    public static void encryptOrDecrypt(String key, int mode, InputStream inputStream, OutputStream outputStream) throws Throwable {

        final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
        final SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        final SecretKey desKey = secretKeyFactory.generateSecret(desKeySpec);
        final Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            final CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            Utils.doCopy(cipherInputStream, outputStream);
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            final CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            Utils.doCopy(inputStream, cipherOutputStream);
        }
    }

    public static String decryptOrDecrypt(String key,InputStream inputStream) throws Throwable {

        final DESKeySpec dks = new DESKeySpec(key.getBytes());
        final SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        final SecretKey desKey = skf.generateSecret(dks);
        final Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        cipher.init(Cipher.DECRYPT_MODE, desKey);
        final CipherOutputStream cipherOutputStream = new CipherOutputStream(byteArrayOutputStream,cipher);
        Utils.doBuffer(inputStream, cipherOutputStream);
        return byteArrayOutputStream.toString();
    }

    public static List<String> getAllFilesPathsInADirectory(String path) throws IOException{

        try( Stream<Path> paths = Files.walk(Paths.get(path)) ) {

            return paths.map(
                    element-> {
                        final File file = new File(element.toString());
                        if (! file.isDirectory()){
                            return element.toString();
                        }
                        return null;

                    }
            ).filter(element -> element !=  null).collect(Collectors.toList());
        }
    }
}
