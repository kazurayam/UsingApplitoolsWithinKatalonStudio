import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable



import com.applitools.eyes.RectangleSize as RectangleSize
import com.applitools.eyes.TestResults as TestResults
import com.applitools.eyes.Eyes as Eyes
import com.kms.katalon.core.webui.driver.DriverFactory as DF
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.Keys as Keys

WebDriver innerDriver = DF.getWebDriver()

Eyes eyes = GlobalVariable.eyes

WebUI.openBrowser('')

WebUI.navigateToUrl('https://applitools.com/helloworld2')

eyes.checkWindow('Before enter name')

WebUI.setText(findTestObject('Page_Applitools/input_name'), 'My Name')

eyes.checkWindow('After enter name')

WebUI.click(findTestObject('Page_Applitools/button_Click me'))

eyes.checkWindow('After Click')

TestResults result = eyes.close(false)

WebUI.closeBrowser()



static private void handleResult(TestResults result) {
    String resultStr
    String url
    if (result == null) {
        resultStr = "Test aborted"
        url = "undefined"
    } else {
        url = result.getUrl()
        int totalSteps = result.getSteps()
        if (result.isNew()) {
            resultStr = "New Baseline created: ${totalSteps} steps"
        } else if (result.isPassed()) {
            resultStr = "All steps passed:     ${totalSteps} steps"
        } else {
            StringBuilder sb = new StringBuilder()
            sb.append(  "Test Failed     :     ${totalSteps} steps\n")
            sb.append(" matches=   ${result.getMatches()}\n")
            sb.append(" missing=   ${result.getMissing()}\n")
            sb.append(" mismatches=${result.getMismatches()}\n")
            resultStr = sb.toString()
        }
        resultStr += "\n" + "results at " + url
        WebUI.comment(resultStr)
    }
}
