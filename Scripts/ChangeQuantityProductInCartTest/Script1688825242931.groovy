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

String reverseString (String s) {
	String rs = "";
	for(int i = s.length() - 1; i >= 0; i--) {
		rs += s.charAt(i);
	}
	return rs;
}

int convertMoneyStringToInt(String rs) {
    String newMoney = rs.substring(1);
    rs  = reverseString(newMoney);
    int count = 0;
    String result = "";
    for(int i = 0; i < rs.length(); i++) {
		if (count == 3 && rs.charAt(i) == '.') {
			count = 0;
			continue;
		}
		count ++;
		result += rs.charAt(i);
	}
	return Integer.parseInt(reverseString(result))
}

//login
WebUI.openBrowser("https://shopee.vn/buyer/login");
WebUI.maximizeWindow()

TestObject txtEmail = new TestObject();
txtEmail = WebUI.modifyObjectProperty(txtEmail, 'xpath', 'equals', '//*[@id="main"]/div/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/div[1]/input', true);
WebUI.setText(txtEmail, 'swp391.birdtrading@gmail.com')
TestObject txtPassword = new TestObject();
txtPassword = WebUI.modifyObjectProperty(txtPassword, 'xpath', 'equals', '//*[@id="main"]/div/div[2]/div/div/div/div[2]/form/div/div[2]/div[3]/div[1]/input', true);
WebUI.setText(txtPassword, '123123123@Abc')
TestObject btnLogin = new TestObject();
btnLogin = WebUI.modifyObjectProperty(btnLogin, 'xpath', 'equals', '//*[@id="main"]/div/div[2]/div/div/div/div[2]/form/div/div[2]/button', true);
WebUI.click(btnLogin)

//Qua cart
Thread.sleep(4000);
WebUI.navigateToUrl('https://shopee.vn/cart');

TestObject uPrice = new TestObject();
uPrice = WebUI.modifyObjectProperty(uPrice, 'xpath', 'equals', '//*[@id="main"]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[4]/div/span[2]', true);
String uPriceString = WebUI.getText(uPrice);
int unitPrice = convertMoneyStringToInt(uPriceString);

TestObject qty = new TestObject();
qty = WebUI.modifyObjectProperty(qty, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[5]/div/input', true);
String qtyString = WebUI.getAttribute(qty, "value");
int quantity = Integer.parseInt(qtyString);

TestObject btnIncrease = new TestObject();
btnIncrease = WebUI.modifyObjectProperty(btnIncrease, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[5]/div/button[2]', true);
WebUI.click(btnIncrease)

Thread.sleep(1000)

String qtyAddedString = WebUI.getAttribute(qty, "value");
int addedQuantity = Integer.parseInt(qtyAddedString);

WebUI.verifyEqual(addedQuantity, quantity + 1)

TestObject tPrice = new TestObject();
tPrice = WebUI.modifyObjectProperty(tPrice, 'xpath', 'equals', '/html/body/div[1]/div/div[2]/div/div/div[3]/div[1]/div[3]/div[2]/div/div/div[6]/span', true);
String priceString = WebUI.getText(tPrice);
int totalPrice = convertMoneyStringToInt(priceString);

int totalPriceExpected = unitPrice * addedQuantity;

WebUI.verifyEqual(totalPrice, totalPriceExpected)

WebUI.closeBrowser();

