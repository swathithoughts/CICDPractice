package RSA.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{

	int count = 0;
	int maxTry = 1;
	
	@Override
	public boolean retry(ITestResult result) { // Whenever test fails, come to this block
		
		if(count<maxTry)
		{
			count++;
			return true;
		}
		return false;
	}
	
	
}