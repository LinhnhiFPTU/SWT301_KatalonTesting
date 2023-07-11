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

//Chose product 1
TestObject checkProduct1 = new TestObject();
checkProduct1 = WebUI.modifyObjectProperty(checkProduct1, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[1]/label/div', true)
WebUI.click(checkProduct1)

TestObject totalProduct1 = new TestObject();
totalProduct1 = WebUI.modifyObjectProperty(totalProduct1, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[6]/span', true)
int totalP1 = convertMoneyStringToInt(WebUI.getText(totalProduct1))

//Chose product 2
TestObject checkProduct2 = new TestObject();
checkProduct2 = WebUI.modifyObjectProperty(checkProduct2, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[4]/div[2]/div/div/div[1]/label/div', true)
WebUI.click(checkProduct2)

TestObject totalProduct2 = new TestObject();
totalProduct2 = WebUI.modifyObjectProperty(totalProduct2, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[4]/div[2]/div/div/div[6]/span', true)
int totalP2 = convertMoneyStringToInt(WebUI.getText(totalProduct2))

int totalP = totalP1 + totalP2

//Compare
TestObject totalOrder = new TestObject();
totalOrder = WebUI.modifyObjectProperty(totalOrder, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[2]/div[7]/div[4]/div[1]/div/div/div[1]/div[2]', true)
int totalO = convertMoneyStringToInt(WebUI.getText(totalOrder))

WebUI.verifyEqual(totalO, totalP)
 
TestObject btnCheckout = new TestObject();
btnCheckout = WebUI.modifyObjectProperty(btnCheckout, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[2]/div[7]/button[4]', true)
WebUI.click(btnCheckout)

Thread.sleep(3000)

//Product 1
TestObject totalCheckoutProduct1 = new TestObject();
totalCheckoutProduct1 = WebUI.modifyObjectProperty(totalCheckoutProduct1, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[1]/div[1]/div/div[2]/div/div[5]', true)
int totalCP1 = convertMoneyStringToInt(WebUI.getText(totalCheckoutProduct1))

TestObject shippingFee1 = new TestObject();
shippingFee1 = WebUI.modifyObjectProperty(shippingFee1, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[1]/div[2]/div[2]/div[5]/div', true)
int fee1 = convertMoneyStringToInt(WebUI.getText(shippingFee1))

int totalPF1 = totalCP1 + fee1

TestObject totalCheckoutOrder1 = new TestObject();
totalCheckoutOrder1 = WebUI.modifyObjectProperty(totalCheckoutOrder1, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[1]/div[3]/div/div[2]', true)
int totalCO1 = convertMoneyStringToInt(WebUI.getText(totalCheckoutOrder1))

WebUI.verifyEqual(totalCO1, totalPF1)

//Product 2
TestObject totalCheckoutProduct2 = new TestObject();
totalCheckoutProduct2 = WebUI.modifyObjectProperty(totalCheckoutProduct2, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[2]/div[1]/div/div[2]/div[1]/div[5]', true)
int totalCP2 = convertMoneyStringToInt(WebUI.getText(totalCheckoutProduct2))

TestObject shippingFee2 = new TestObject();
shippingFee2 = WebUI.modifyObjectProperty(shippingFee2, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[2]/div[2]/div[2]/div[5]/div', true)
int fee2 = convertMoneyStringToInt(WebUI.getText(shippingFee2))

int totalPF2 = totalCP2 + fee2

TestObject totalCheckoutOrder2 = new TestObject();
totalCheckoutOrder2 = WebUI.modifyObjectProperty(totalCheckoutOrder2, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div[2]/div[2]/div[2]/div[2]/div[3]/div/div[2]', true)
int totalCO2 = convertMoneyStringToInt(WebUI.getText(totalCheckoutOrder2))

WebUI.verifyEqual(totalCO2, totalPF2)

//Total
TestObject total = new TestObject();
total = WebUI.modifyObjectProperty(total, 'xpath', 'equals', '/html[1]/body[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[4]/div[2]/div[3]', true)
int totalT = convertMoneyStringToInt(WebUI.getText(total))

int totalT12 = totalCO1 + totalCO2

WebUI.verifyEqual(totalT, totalT12)

WebUI.closeBrowser();