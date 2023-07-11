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

//Processing Function
TestObject getWebElement(String xPath)
{
	TestObject element = new TestObject();
	element = WebUI.modifyObjectProperty(element, 'xpath', 'equals', xPath, true)
	return element;
}

void loginFunc(String email, String password) {
	TestObject txtEmail = getWebElement('//*[@id="main"]/div/div[2]/div/div/div/div[2]/form/div/div[2]/div[2]/div[1]/input')
	WebUI.setText(txtEmail, email)
	
	TestObject txtPassword = getWebElement('//*[@id="main"]/div/div[2]/div/div/div/div[2]/form/div/div[2]/div[3]/div[1]/input')
	WebUI.setText(txtPassword, password)
	
	TestObject btnLogin = getWebElement('//*[@id="main"]/div/div[2]/div/div/div/div[2]/form/div/div[2]/button')
	WebUI.click(btnLogin)
}

void addProductToCart()
{
	TestObject btnWhiteColor = getWebElement('//*[@id="main"]/div/div[2]/div[1]/div/div/div/div[2]/div[3]/div/div[4]/div/div[2]/div/div[1]/div/button[1]')
	WebUI.click(btnWhiteColor)
	
	TestObject btnSize36 = getWebElement('//*[@id="main"]/div/div[2]/div[1]/div/div/div/div[2]/div[3]/div/div[4]/div/div[2]/div/div[2]/div/button[1]')
	WebUI.click(btnSize36)
	
	TestObject btnAddToCart = getWebElement('//*[@id="main"]/div/div[2]/div[1]/div/div/div/div[2]/div[3]/div/div[5]/div/div/button[1]')
	WebUI.click(btnAddToCart)
}

void checkProductAddedInCart(String expectedName, String expectedCate)
{
	Thread.sleep(1000)
	TestObject itemInCart = getWebElement('//*[@id="main"]/div/div[2]/div[1]/div/div[3]/div[1]/div[3]/div[2]/div[1]/div/div[2]/div/div/a')
	String name = WebUI.getText(itemInCart)
	
	TestObject cateogory = getWebElement('//*[@id="main"]/div/div[2]/div[1]/div/div[3]/div[1]/div[3]/div[2]/div[1]/div/div[3]/div/div[1]/div[2]')
	String cate = WebUI.getText(cateogory)
	
	WebUI.verifyEqual(name, expectedName)
	WebUI.verifyEqual(cate, expectedCate)
}

//Init Variable
String productUrl = 'https://shopee.vn/D%C3%A9p-Quai-Ngang-%C4%90%E1%BA%BF-B%C3%A1nh-M%C3%AC-T%C4%83ng-Chi%E1%BB%81u-Cao-H%E1%BB%8Da-Ti%E1%BA%BFt-Qu%E1%BA%A3-Tr%C3%A1m-i.788908334.18249538621?sp_atk=0c9711d0-2b04-4d93-beba-1721bdf3a7f9&xptdk=0c9711d0-2b04-4d93-beba-1721bdf3a7f9';
String productName = "Dép Quai Ngang Đế Bánh Mì Tăng Chiều Cao Họa Tiết Quả Trám"
String productCate = "Trắng,36"
String email = "swp391.birdtrading@gmail.com"
String password = "123123123@Abc"


//Action
// open browser and login
WebUI.openBrowser("https://shopee.vn/buyer/login");
WebUI.maximizeWindow();
loginFunc(email, password)

// navigate qua san pham can test va add to cart
Thread.sleep(4000)
WebUI.navigateToUrl(productUrl)

TestObject numberOfProductInCart = getWebElement('//*[@id="cart_drawer_target_id"]/div')
String prevNopic = WebUI.getText(numberOfProductInCart)
int pppp = Integer.parseInt(prevNopic)

addProductToCart()

WebUI.verifyElementText(numberOfProductInCart, (pppp + 1) + "")

TestObject cartIcon = getWebElement('//*[@id="cart_drawer_target_id"]/div/div')
WebUI.click(cartIcon)

//check san pham vua add trong cart
checkProductAddedInCart(productName, productCate)

WebUI.closeBrowser();
