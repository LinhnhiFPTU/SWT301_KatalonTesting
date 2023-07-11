import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

String revertString(String s) {
	String rs = "";
		for(int i = s.length() - 1; i >= 0; i --) {
			rs += s.charAt(i);
		}
		return rs;
}

int convertMoneyStringToInt(String rs) {
	String newMoney = rs.substring(1);
	rs  = revertString(newMoney);
	int count = 0
	String result = "";
	for(int i = 0; i < rs.length(); i++) {
		if (count == 3 && rs.charAt(i) == '.')
		{
			count = 0;
			continue
		}
		count ++;
		result += rs.charAt(i)
	}
	return Integer.parseInt(revertString(result))
}



WebUI.openBrowser("https://shopee.vn/buyer/login");

WebUI.maximizeWindow()

TestObject txtEmail = new TestObject();
txtEmail = WebUI.modifyObjectProperty(txtEmail, 'xpath', 'equals', '/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/div[2]/div[1]/input[1]', true)
WebUI.setText(txtEmail, "swp391.birdtrading@gmail.com")

TestObject txtPassword = new TestObject();
txtPassword = WebUI.modifyObjectProperty(txtPassword, 'xpath', 'equals', '/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/div[3]/div[1]/input[1]', true)
WebUI.setText(txtPassword, "123123123@Abc")

TestObject btnLogin = new TestObject();
btnLogin = WebUI.modifyObjectProperty(btnLogin, 'xpath', 'equals', '/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/button[1]', true)
WebUI.click(btnLogin)

Thread.sleep(4000)
WebUI.navigateToUrl("https://shopee.vn/cart")

TestObject checkProduct = new TestObject();
checkProduct = WebUI.modifyObjectProperty(checkProduct, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[1]/label/div', true)
WebUI.click(checkProduct)


TestObject totalProduct = new TestObject();
totalProduct = WebUI.modifyObjectProperty(totalProduct, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[6]/span', true)
String totalP = WebUI.getText(totalProduct)


TestObject totalOrder = new TestObject();
totalOrder = WebUI.modifyObjectProperty(totalOrder, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[2]/div[7]/div[4]/div[1]/div/div/div[1]/div[2]', true)
String totalO = WebUI.getText(totalOrder)

WebUI.verifyEqual(totalO, totalP)


TestObject btnCheckout = new TestObject();
btnCheckout = WebUI.modifyObjectProperty(btnCheckout, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[2]/div[7]/button[4]', true)
WebUI.click(btnCheckout)

Thread.sleep(3000)

TestObject totalCheckoutProduct = new TestObject();
totalCheckoutProduct = WebUI.modifyObjectProperty(totalCheckoutProduct, 'xpath', 'equals', '/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[5]', true)
int totalCP = convertMoneyStringToInt(WebUI.getText(totalCheckoutProduct))

TestObject shippingFee = new TestObject();
shippingFee = WebUI.modifyObjectProperty(shippingFee, 'xpath', 'equals', '/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div[1]/div[2]/div[2]/div[5]/div[1]', true)
int fee = convertMoneyStringToInt(WebUI.getText(shippingFee))

int totalPF = totalCP + fee

TestObject totalCheckoutOrder = new TestObject();
totalCheckoutOrder = WebUI.modifyObjectProperty(totalCheckoutOrder, 'xpath', 'equals', '/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[2]/div[2]/div[1]/div[3]/div[1]/div[2]', true)
int totalCO = convertMoneyStringToInt(WebUI.getText(totalCheckoutOrder))

WebUI.verifyEqual(totalCO, totalPF)

WebUI.closeBrowser();
