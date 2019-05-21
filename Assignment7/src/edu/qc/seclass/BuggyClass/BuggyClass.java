package edu.qc.seclass.BuggyClass;

public class BuggyClass {


	    public int buggyMethod1(int a, int b){
	    	if(a/b != 2)//2:3,0 1:4,2
	    		return 2;
	    	else if(a / b == 2 )
	    		b+=2;
	    	else if(b % a ==0)
	    		b=4;
  	    	return 1;
	    }



	    public int buggyMethod2(int a, int b){
	    	int result = a+b;//1: 2,2 2:3,2
	    	if(result/b!=2)
	    		return 2;
	    	else if(result>2)
	    		return 1;	
		
	    }

	    public void buggyMethod3(int a, int b){
	    	
	     
	    }

	    public void buggyMethod4(int a, int b){
	        /* 
	        buggyMethod4 that contains a division by zero fault such that (1) every test suite that achieves 100%
			statement coverage reveals the fault, and (2) it is possible to create a test suite that achieves 100% 
			branch coverage and does not reveal the fault.
	        This is impossible, because if you have a 100% branch coverage with division by zero.
	        Which means we get 100% statement coverage that means every line of codes in the method
			happened to be executed,so every outcome from the method is tested. So with 100% branch coverage 
			will showing the error.
 
	         */
	    }

	    public void buggyMethod5(){
	        /*This is impossible to reach 100% statement coverage when there is an error
	         would interrupt the test that not will not reach 100%  statement coverage.
	         */
	    }
	}
