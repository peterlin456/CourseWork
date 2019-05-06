package edu.qc.seclass;

import org.junit.*;

import static org.junit.Assert.*;
public class BuggyClassTestSC1a {

    BuggyClass myBuggyClass;

    @Before
    public void setUp() {
        myBuggyClass= new BuggyClass();
    }

    @After            //after each test
    public void tearDown() {
        myBuggyClass = null;
    }

    //This test does 100% statement coverage
    @Test
    public void buggyMethod1() {
        assertEquals(1, myBuggyClass.buggyMethod1(4, 2));
    }
}