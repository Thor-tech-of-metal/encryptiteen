package com.thor.tech.encryptiteen;


import com.thor.tech.encryptiteen.utils.Utils;
import org.junit.*;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

public class BasicTest {


    @Test
    public void testOutPutEncryptiteenDirectoryPath() {

        final URL url = BasicTest.class.getClassLoader().getResource("testDirEncryptiteen");
        final String inputDirectory = url.getFile();
        final  ProtectMyFilesEncryptiteen protectMyFilesEncryptiteen = new ProtectMyFilesEncryptiteen("test", inputDirectory, "test", "pass");

        protectMyFilesEncryptiteen.getOutputDirectory();
        protectMyFilesEncryptiteen.createOutputEncryptDirectory();
        assertEquals(protectMyFilesEncryptiteen.getOutputDirectory(),inputDirectory+ File.separator+Utils.OUTPUT_ENCRYPTED_FILES_DIR);
        assertTrue(Utils.checkIfDirectoryHasBeenCreated(protectMyFilesEncryptiteen.getOutputDirectory()));
    }

    @Test
    public void testOutPuDecryptiteenDirectoryPath() {

        final URL url = BasicTest.class.getClassLoader().getResource("testDirEncryptiteen");
        final String inputDirectory = url.getFile();
        final  UnProtectMyFilesEncryptiteen unProtectMyFilesEncryptiteen = new UnProtectMyFilesEncryptiteen("test", inputDirectory,  "pass");

        unProtectMyFilesEncryptiteen.getOutputDirectory();
        unProtectMyFilesEncryptiteen.createOutputDecryptDirectory();
        assertEquals(unProtectMyFilesEncryptiteen.getOutputDirectory(),inputDirectory+ File.separator+Utils.OUTPUT_DECRYPTED_FILES_DIR);
        assertTrue(Utils.checkIfDirectoryHasBeenCreated(unProtectMyFilesEncryptiteen.getOutputDirectory()));
    }

}