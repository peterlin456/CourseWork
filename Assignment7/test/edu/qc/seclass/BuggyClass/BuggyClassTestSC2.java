package edu.qc.seclass.BuggyClass;

import org.junit.*;

import static org.junit.Assert.*;

public class BuggyClassTestSC2 {

	 BuggyClass myBuggyClass;

	    @Before
	    public void setUp() {
	        myBuggyClass= new BuggyClass();
	    }

	    @After            //after each test
	    public void tearDown() {
	        myBuggyClass = null;
	    }
	    //100% statement coverage
	    @Test
	    public void buggyMethod2() {
	        assertEquals(1, myBuggyClass.buggyMethod2(2, 2));
	    }
}
