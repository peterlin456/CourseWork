package edu.qc.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(9, mycustomstring.countNumbers());
    }

    @Test
    public void testCountNumbers2() {
    	mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, r1ght?");
        assertEquals(10, mycustomstring.countNumbers());
    }

    @Test
    public void testCountNumbers3() {
    	mycustomstring.setString("1n");
        assertEquals(1, mycustomstring.countNumbers());
    }

    @Test
    public void testCountNumbers4() {
    	mycustomstring.setString("");
        assertEquals(0, mycustomstring.countNumbers());
    }
    //NullPointerException If the current string is null
    @Test(expected = NullPointerException.class)
    public void testCountNumbers5() {
    	mycustomstring.setString(null);
        mycustomstring.countNumbers();
    }

    @Test
    public void testCountNumbers6() {
    	mycustomstring.setString("null");
        assertEquals(0, mycustomstring.countNumbers());
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("d33p md1  i51,it", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd2() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals("'bt t0 6snh r6rh", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd3() {
    	  mycustomstring.setString("I'd b3tt3r");
          assertEquals("Idbt3", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, true));
    }					

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd4() {
    	  mycustomstring.setString("b3tt3r");
          assertEquals("t", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(4, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd5() {
    	  mycustomstring.setString("s0me d161ts");
          assertEquals("e6", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(4, true));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd6() {
    	  mycustomstring.setString("s0me d161ts");
          assertEquals("e6", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(4, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd7() {
    	  mycustomstring.setString("put s0me d161ts");
          assertEquals("p md1", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }					

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd8() {
    	  mycustomstring.setString("s0me d161ts");
          assertEquals("md1", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd9() {
    	  mycustomstring.setString("3e6t is an un1que digit");
          assertEquals("eti nu1u ii", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, true));
    }					

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd10() {
    	  mycustomstring.setString("3e6t is an un1que digit");
          assertEquals("eti nu1u ii", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(2, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd11() {
    	  mycustomstring.setString("Is this 5tr1n6, right?");
          assertEquals("sh r6rh", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, true));
    }					

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd12() {
    	  mycustomstring.setString("Is this 5tr1n6, right?");
          assertEquals(" i51,it", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(3, false));
    }

    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd13() {
    	  mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
          assertEquals("br  t  ni", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(5, true));
    }					
    @Test
    public void testGetEveryNthCharacterFromBeginningOrEnd14() {
    	  mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
          assertEquals("br  t  ni", mycustomstring.getEveryNthCharacterFromBeginningOrEnd(5, false));
    }

    @Test
    public void testConvertDigitsToNamesInSubstring1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put sZerome dOneSix1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    @Test
    public void testConvertDigitsToNamesInSubstring2() {
    	 mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
         mycustomstring.convertDigitsToNamesInSubstring(1, 10);
         assertEquals("I'd bThreettThreer put s0me d161ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    @Test
    public void testConvertDigitsToNamesInSubstring3() {
    	 mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
         mycustomstring.convertDigitsToNamesInSubstring(23, 36);
         assertEquals("I'd b3tt3r put s0me d1SixOnets in this Fivetr1n6, right?", mycustomstring.getString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring4() {
    	 mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
         mycustomstring.convertDigitsToNamesInSubstring(35, 23);
         
    }

    @Test(expected = NullPointerException.class)
    public void testConvertDigitsToNamesInSubstring5() {
    	 mycustomstring.setString(null);
         mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        
    }

    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring6() {
    	 mycustomstring.setString("is this  right?");
         mycustomstring.convertDigitsToNamesInSubstring(17, 23);
       
    }

    @Test
    public void testConvertDigitsToNamesInSubstring7() {
    	 mycustomstring.setString("I'm b4tt3r");
         mycustomstring.convertDigitsToNamesInSubstring(1,6 );
         assertEquals("I'm bFourtt3r", mycustomstring.getString());
    }

    @Test
    public void testConvertDigitsToNamesInSubstring8() {
    	 mycustomstring.setString("s0me d161ts in this 5tr1n6, right?");
         mycustomstring.convertDigitsToNamesInSubstring(10, 23);
         assertEquals("s0me d161ts in this Fivetr1n6, right?", mycustomstring.getString());
    }

}
