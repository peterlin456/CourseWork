package edu.qc.seclass.BuggyClass;

import org.junit.*;

import static org.junit.Assert.*;

public class BuggyClassTestBC2 {

	 BuggyClass myBuggyClass;

	    @Before
	    public void setUp() {
	        myBuggyClass= new BuggyClass();
	    }

	    @After            //after each test
	    public void tearDown() {
	        myBuggyClass = null;
	    }
	    
	    @Test  // more than 50%
	    public void buggyMethod2() {
	        assertEquals(2, myBuggyClass.buggyMethod2(3, 2));
	    }
}