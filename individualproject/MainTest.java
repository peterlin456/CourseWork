package edu.qc.seclass.replace;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MainTest {

    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;
    private Charset charset = StandardCharsets.UTF_8;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    // Some utilities

    private File createTmpFile() throws IOException {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    private File createInputFile1() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!");

        fileWriter.close();
        return file1;
    }

    private File createInputFile2() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice");

        fileWriter.close();
        return file1;
    }

    private File createInputFile3() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123");

        fileWriter.close();
        return file1;
    }
    private File createInputFile4() throws Exception {
        File file1 =  createTmpFile();
        FileWriter fileWriter = new FileWriter(file1);

        fileWriter.write("This is my project for cs370\n" +
                "This is my project for cs370." +
                "hahahahahaha\n"
                );

        fileWriter.close();
        return file1;
    }

    private String getFileContent(String filename) {
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filename)), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    // Actual test cases

    @Test
    public void mainTest1() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-i", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +	
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
      
    }

    @Test
    public void mainTest2() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();

        String args[] = {"-b", "-f", "Bill", "William", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy William,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy William,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }

    @Test
    public void mainTest3() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-f", "-l", "abc", "ABC", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your ABC and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: ABC and 123";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    @Test
    public void mainTest4() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"123", "<numbers removed>", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your abc and <numbers removed>?\n" +
                "It is important to know your abc and <numbers removed>," +
                "so you should study it\n" +
                "and then repeat with me: abc and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    @Test
    public void mainTest5() throws Exception {
        File inputFile = createInputFile2();

        String args1[] = {"-b", "--", "-a", "1", "--", inputFile.getPath()};
        Main.main(args1);
        String args2[] = {"--", "-b", "2", "--", inputFile.getPath()};
        Main.main(args2);

        String expected = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "1) Item 1\n" +
                "2) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }

    @Test
    public void mainTest6() {
        String args[] = {"blah",};
        Main.main(args);
        assertEquals("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- <filename> [<filename>]*", errStream.toString().trim());
    }
    
    // Additional 40 unit tests.
    
    //case 7
    @Test
   public void mainTest7() throws Exception{
    	 File inputFile = createInputFile2();
    	 String args1[] = {"-l", "Howdy", "Hello","--"};
         Main.main(args1);
         String expected = "";

         String actual = getFileContent(inputFile.getPath());

         assertEquals("The files differ!", expected, actual);
         assertTrue(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    //case 4
    @Test
    public void mainTest8() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();

        String args[] = { "Bill", "William", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "Howdy William,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy William,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy William\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }
    //case 37
    @Test
    public void mainTest9() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-f", "ABC", "lol", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your lol and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: ABC and 123";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    // case 38
    @Test
    
    public void mainTest10() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();

        String args[] = {"-b", "-f", "Howdy", "abc", "--", inputFile1.getPath(), inputFile2.getPath()};
        Main.main(args);

        String expected1 = "abc Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";
        String expected2 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }
    //case 41
    @Test
    public void mainTest11() throws Exception {
    	   File inputFile1 = createInputFile1();
         
        String args[] = {"-b","-f", "ABC", "ha", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill, have you learned your ha and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: ABC and 123";

        String actual1 = getFileContent(inputFile1.getPath());
     

        assertEquals("The files differ!", expected1, actual1);
       
        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
       
    }
    // case 47
    @Test
    public void mainTest12() throws Exception {
 	   File inputFile1 = createInputFile1();
      
     String args[] = {"-l", "ABC", "nice", "--", inputFile1.getPath()};
     Main.main(args);

     String expected1 = "Howdy Bill, have you learned your ABC and 123?\n" +
             "It is important to know your abc and 123," +
             "so you should study it\n" +
             "and then repeat with me: nice and 123";

     String actual1 = getFileContent(inputFile1.getPath());
  

     assertEquals("The files differ!", expected1, actual1);
    
     assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    
 }
    //case 54
    @Test
    public void mainTest13() throws Exception {
        
        File inputFile2 = createInputFile2();

        String args[] = {"-l", "-f", "Item", "William", "--", inputFile2.getPath()};
        Main.main(args);

    
        String expected2 = "Howdy William,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) William 1\n" +
                "-b) William 2\n" +
                "...\n" +
                "and says \"howdy Bill\" twice";

     
        String actual2 = getFileContent(inputFile2.getPath());

     
        assertEquals("The files differ!", expected2, actual2);
  
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
    }
    //case 56
    @Test
    public void mainTest14() throws Exception {
        
        File inputFile2 = createInputFile3();

        String args[] = {"-b", "-i", "123", "William", "--", inputFile2.getPath()};
        Main.main(args);

    
        String expected2 = "Howdy Bill, have you learned your abc and William?\n" +
                "It is important to know your abc and William," +
                "so you should study it\n" +
                "and then repeat with me: abc and William";

     
        String actual2 = getFileContent(inputFile2.getPath());

     
        assertEquals("The files differ!", expected2, actual2);
  
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
}
    //case 2
    @Test
    public void mainTest15() throws Exception {
        
        File inputFile2 = createInputFile3();

        String args[] = {"-f", "-l", "length", "William", "--", inputFile2.getPath()};
        Main.main(args);

    
        String expected2 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

     
        String actual2 = getFileContent(inputFile2.getPath());

     
        assertEquals("The files differ!", expected2, actual2);
  
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
}
    //case 3
    @Test
    public void mainTest16() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-l","repeat", "will", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your abc and <numbers removed>?\n" +
                "It is important to know your abc and <numbers removed>," +
                "so you should study it\n" +
                "and then will with me: abc and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    //start 30 test
    // OPT TEST: b,l,f 
    
    @Test
    public void mainTest17() throws Exception {
        File inputFile = createInputFile4();

        String args[] = {"-b","-l","-f","for", ":", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "This is my project : cs370\n" +
                "This is my project for cs370." +
                "hahahahahaha\n";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    // OPT TEST:b,i
    @Test
    public void mainTest18() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-b","-i","repeat", "will", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your abc and <numbers removed>?\n" +
                "It is important to know your abc and <numbers removed>," +
                "so you should study it\n" +
                "and then will with me: abc and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    // OPT TEST: l,i
    @Test
    public void mainTest19() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-l","-i","abc", "will", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your will and <numbers removed>?\n" +
                "It is important to know your will and <numbers removed>," +
                "so you should study it\n" +
                "and then repeat with me: will and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    // OPT TEST:f,i
    @Test
    public void mainTest20() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-f","-i","abc", "will", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your will and <numbers removed>?\n" +
                "It is important to know your will and <numbers removed>," +
                "so you should study it\n" +
                "and then repeat with me: will and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    // OPT TEST:b,f,i
    @Test
    public void mainTest21() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-b","-f","-i","abc", "will", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your abc and <numbers removed>?\n" +
                "It is important to know your abc and <numbers removed>," +
                "so you should study it\n" +
                "and then repeat with me: abc and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    // OPT TEST:b,l,i
    @Test
    public void mainTest22() throws Exception {
        File inputFile = createInputFile3();

        String args[] = {"-b","-l","-i","abc", "will", "--", inputFile.getPath()};
        Main.main(args);

        String expected = "Howdy Bill, have you learned your will and <numbers removed>?\n" +
                "It is important to know your will and <numbers removed>," +
                "so you should study it\n" +
                "and then repeat with me: will and <numbers removed>";

        String actual = getFileContent(inputFile.getPath());

        assertEquals("The files differ!", expected, actual);
        assertFalse(Files.exists(Paths.get(inputFile.getPath() + ".bck")));
    }
    // OPT TEST:b
    @Test
    public void mainTest23() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-b", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +	
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
      
    }
    // OPT TEST:l
    @Test
    public void mainTest24() throws Exception {
        File inputFile1 = createInputFile1();
        File inputFile2 = createInputFile2();
        File inputFile3 = createInputFile3();

        String args[] = {"-l", "Howdy", "Hello", "--", inputFile1.getPath(), inputFile2.getPath(), inputFile3.getPath()};
        Main.main(args);

        String expected1 = "Hello Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"Hello bill\" again!";
        String expected2 = "Hello Bill,\n" +	
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"Hello Bill\" twice";
        String expected3 = "Hello Bill, have you learned your abc and 123?\n" +
                "It is important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

        String actual1 = getFileContent(inputFile1.getPath());
        String actual2 = getFileContent(inputFile2.getPath());
        String actual3 = getFileContent(inputFile3.getPath());

        assertEquals("The files differ!", expected1, actual1);
        assertEquals("The files differ!", expected2, actual2);
        assertEquals("The files differ!", expected3, actual3);

        assertFalse(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
        assertFalse(Files.exists(Paths.get(inputFile3.getPath() + ".bck")));
      
    }
    // OPT TEST:i,b
    @Test
    public void myMainTest23() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-i", "-b", "Howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    // OPT TEST:f,l,b
    @Test
    public void myMainTest24() throws Exception{
        File inputFile1 = createInputFile2();

        String args[] = {"-f", "-l", "-b", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is another test file for the replace utility\n" +
                "that contains a list:\n" +
                "-a) Item 1\n" +
                "-b) Item 2\n" +
                "...\n" +
                "and says \"test Bill\" twice";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    // OPT TEST:l,i,b
    @Test
    public void myMainTest25() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-l", "-i", "-b", "Howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    // OPT TEST:f,i,b
    @Test
    public void myMainTest26() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-f", "-i", "-b", "Howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));
    }

    // no opt,no file
    @Test
    public void myMainTest27() throws Exception{

        String args[] = {"--"};
        Main.main(args);
    }

    // OPT TEST:no opt
    @Test
    public void myMainTest28() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {" ", "", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "HowdyBill,\n" +
                "Thisisatestfileforthereplaceutility\n" +
                "Let'smakesureithasatleastafewlines\n" +
                "sothatwecancreatesomeinterestingtestcases...\n" +
                "Andlet'ssay\"howdybill\"again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

    }

    // OPT TEST:-b,-f
    @Test
    public void myMainTest29() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-b", "-f", "Howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    // OPT TEST:-l,-f
    @Test
    public void myMainTest30() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-l", "-f", "a", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is test test file for the replace utility\n" +
                "Let's make sure it has at least test few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

    }

    // OPT TEST:-f
    @Test
    public void myMainTest31() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = { "-f", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

    }

    // OPT TEST:-b,-f
    @Test
    public void myMainTest32() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-b", "-f", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    // OPT TEST:-l
    @Test
    public void myMainTest33() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = { "-l", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

    }

    // OPT TEST:-b,-l
    @Test
    public void myMainTest34() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-b", "-l", "Howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    // OPT TEST:-i
    @Test
    public void myMainTest35() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = { "-i", "Howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    // OPT TEST:-i,-l,-f
    @Test
    public void myMainTest36() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-i", "-l", "-f", "Howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

    }

    // OPT TEST:-f,-b
    @Test
    public void myMainTest37() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-f", "-b", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    // OPT TEST:-l,-f
    @Test
    public void myMainTest38() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-l", "-f", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    // OPT TEST:-f,-b
    @Test
    public void myMainTest39() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = { "-f", "-b", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }

    // OPT TEST:-l,-b
    @Test
    public void myMainTest40() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = {"-l", "-b", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }
    // OPT TEST:-b
    @Test
    public void myMainTest41() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = { "-b", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }
    // OPT TEST:-b,-l,-f,-i
    @Test
    public void myMainTest42() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = { "-b","-l","-f","-i", "howdy", "test", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "test Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"test bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }
    // OPT TEST:-b,-l,-f,-i 
    @Test
    public void myMainTest43() throws Exception{
        File inputFile1 = createInputFile1();

        String args[] = { "-b","-l","-f","-i", "--", inputFile1.getPath()};
        Main.main(args);

        String expected1 = "Howdy Bill,\n" +
                "This is a test file for the replace utility\n" +
                "Let's make sure it has at least a few lines\n" +
                "so that we can create some interesting test cases...\n" +
                "And let's say \"howdy bill\" again!";

        String actual1 = getFileContent(inputFile1.getPath());

        assertEquals("Those strings differ", expected1, actual1);

        assertTrue(Files.exists(Paths.get(inputFile1.getPath() + ".bck")));

    }
    // OPT TEST: no opt,replace letter with number
    @Test
    public void mainTest44() throws Exception {
        
        File inputFile2 = createInputFile3();

        String args[] = { "s", "5", "--", inputFile2.getPath()};
        Main.main(args);

    
        String expected2 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It i5 important to know your abc and 123," +
                "5o you 5hould 5tudy it\n" +
                "and then repeat with me: abc and 123";

     
        String actual2 = getFileContent(inputFile2.getPath());

     
        assertEquals("The files differ!", expected2, actual2);
  
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
}
    // OPT TEST:-f,-f
    @Test
    public void mainTest45() throws Exception {
        
        File inputFile2 = createInputFile3();

        String args[] = { "-f","-f","s", "5", "--", inputFile2.getPath()};
        Main.main(args);

    
        String expected2 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It i5 important to know your abc and 123," +
                "so you should study it\n" +
                "and then repeat with me: abc and 123";

     
        String actual2 = getFileContent(inputFile2.getPath());

     
        assertEquals("The files differ!", expected2, actual2);
  
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
}
    // OPT TEST:-i,-i
    @Test
    public void mainTest46() throws Exception {
        
        File inputFile2 = createInputFile3();

        String args[] = { "-i","-i","S", "5", "--", inputFile2.getPath()};
        Main.main(args);

    
        String expected2 = "Howdy Bill, have you learned your abc and 123?\n" +
                "It i5 important to know your abc and 123," +
                "5o you 5hould 5tudy it\n" +
                "and then repeat with me: abc and 123";

     
        String actual2 = getFileContent(inputFile2.getPath());

     
        assertEquals("The files differ!", expected2, actual2);
  
        assertTrue(Files.exists(Paths.get(inputFile2.getPath() + ".bck")));
}
}