package edu.qc.seclass;

public class MyCustomString implements MyCustomStringInterface{
	
	private String teststring;
	
	@Override
	public String getString() {
		// TODO Auto-generated method stub
		if(teststring != null)
			return teststring;
		return null;
	}

	@Override
	public void setString(String string) {
		// TODO Auto-generated method stub
		this.teststring=string;
	}

	@Override
	public int countNumbers() {
		// TODO Auto-generated method stub
		/* Test for null, then counting the number in the string*/
		if (this.teststring == null) {  
			throw new NullPointerException();
		}
		int count=0;
	
		String input = this.teststring;
		for (int i =0;i< input.length(); i++) {
		
			if (Character.isDigit(input.charAt(i))){
				count++;
			}
		}
		return count;
	}

	@Override
	public String getEveryNthCharacterFromBeginningOrEnd(int n, boolean startFromEnd) {
		// TODO Auto-generated method stub
		if (!startFromEnd) {
            String result = "";
            for (int i = n; i < teststring.length(); i += n) {
                result += teststring.charAt(i - 1);
            }
            return result;  
		   } else {
			   String rev = "";
			   
			    int count = 1;
			    int index = teststring.length() - n;
			    
			    while(index>=0){
			    rev += teststring.charAt(index);
			     index = teststring.length() - (n * ++count);
			    }
			    StringBuilder sb = new StringBuilder();
		        
		        for(int c = rev.length() - 1; c >= 0; c--)
		        {
		            sb.append(rev.charAt(c));
		        }
		        return sb.toString();
		   }
		   }
     
	
	
	private static final String[] numNames = {
		    " Zero",
		    " One",
		    " Two",
		    " Three",
		    " Four",
		    " Five",
		    " Six",
		    " Seven",
		    " Eight",
		    " Nine"
	};
	@Override
	public void convertDigitsToNamesInSubstring(int startPosition, int endPosition) {
		// TODO Auto-generated method stub
		
			if (startPosition > endPosition){
				throw new IllegalArgumentException();
			}
			
			if (startPosition <1 || endPosition > this.teststring.length()) {
				throw new MyIndexOutOfBoundsException();
			}
			if (this.teststring == null) {  
				throw new NullPointerException();
			}

			
			
			
			
			
			
		       String newstr = teststring.substring(startPosition-1, endPosition);
			
			
		

			
				newstr = newstr.replace("0", "Zero");
				newstr = newstr.replace("1", "One");
				newstr = newstr.replace("2", "Two");
				newstr = newstr.replace("3", "Three");
				newstr = newstr.replace("4", "Four");
				newstr = newstr.replace("5", "Five");
				newstr = newstr.replace("6", "Six");
				newstr = newstr.replace("7", "Seven");
				newstr = newstr.replace("8", "Eight");
				newstr = newstr.replace("9", "Nine");
				 
				
				
				this.teststring = teststring.substring(0, startPosition-1) + newstr 
				+ teststring.substring(endPosition, teststring.length());
			
			}

		
			

		
		
	}

	
	

