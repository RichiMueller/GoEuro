package com.goeuro;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Integration test for BusinessLogic class.
 */
public class BusinessLogicITTest {

    private static final String EXTENSION = PropertySingleton.getInstance().getProperties().getProperty("csv.extension").trim();

    private BusinessLogic logic;
    private File          outputBerlin = new File("BERLIN." + EXTENSION);
    private File          outputLA     = new File("LOS_ANGELES." + EXTENSION);
    private File          outputXYZ    = new File("XYZ." + EXTENSION);

    @Before
    public void setUp() {
        // create clean new instance for tests
        logic = new BusinessLogic();
    }

    @After
    public void tearDown() {

        // clean up created files

        if (outputBerlin.exists()) {
            outputBerlin.delete();
        }
        if (outputLA.exists()) {
            outputLA.delete();
        }
        if (outputXYZ.exists()) {
            outputXYZ.delete();
        }

        // close resources
        logic.close();
    }

    @Test
    public void testExecuteSuccess() {


        logic.execute("BERLIN");

        assertTrue(outputBerlin.exists());

        logic.execute("LOS ANGELES");  // check for spaces in names

        assertTrue(outputLA.exists());
    }

    @Test
    public void testExecuteFail() {

        logic.execute("XYZ");

        assertFalse(outputXYZ.exists());   // no file should be created
    }
}
