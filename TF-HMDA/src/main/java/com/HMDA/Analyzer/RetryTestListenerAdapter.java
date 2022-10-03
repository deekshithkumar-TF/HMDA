package com.HMDA.Analyzer;

import java.util.Iterator;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class RetryTestListenerAdapter extends TestListenerAdapter{	
   @Override
   public void onFinish(ITestContext context) {
     Iterator<ITestResult> failedTestCases =context.getFailedTests().getAllResults().iterator();
     while (failedTestCases.hasNext()) 
     {
        ITestResult failedTestCase = failedTestCases.next();
        ITestNGMethod method = failedTestCase.getMethod();
        if (context.getFailedTests().getResults(method).size() > 1) 
        {
            System.out.println("Remove duplicate failed test case in the retries" + failedTestCase.getMethod().toString());
            failedTestCases.remove();
        }else {
            if (context.getPassedTests().getResults(method).size() > 0) {
                System.out.println("Remove failed test case if passed in retry:" + failedTestCase.getTestClass().toString());
                failedTestCases.remove();
            }    
        }
     } 
      Iterator<ITestResult> skippedTestCases = context.getSkippedTests().getAllResults().iterator();
      while (skippedTestCases.hasNext()) 
      {    
	      ITestResult skippedTestCase = skippedTestCases.next();
	      ITestNGMethod method = skippedTestCase.getMethod();
	      if (context.getSkippedTests().getResults(method).size() > 0) 
	      {
	          System.out.println("Removing:" + skippedTestCase.getTestClass().toString());
	          skippedTestCases.remove();
	      }
      }
    }
}
