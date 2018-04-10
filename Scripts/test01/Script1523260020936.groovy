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
import com.applitools.eyes.selenium.Eyes as Eyes
import com.applitools.eyes.BatchInfo
import com.applitools.eyes.ProxySettings

import com.kms.katalon.core.webui.driver.DriverFactory as DF
import org.openqa.selenium.WebDriver as WebDriver

RectangleSize viewportSize = new RectangleSize(
    GlobalVariable.viewportSizeLandscapeWidth,
    GlobalVariable.viewportSizeLandscapeHeight)

WebUI.comment("now we open browser")
WebUI.openBrowser('')

// instanciate Eyes object based on the WebDriver object managed by Katalon Studio
WebDriver innerDriver = DF.getWebDriver()
Eyes eyes = createEyes()
eyes.open(innerDriver,
    GlobalVariable.appName, GlobalVariable.testName, viewportSize)

WebUI.navigateToUrl('https://applitools.com/helloworld2')
eyes.checkWindow('Before enter name')
WebUI.setText(findTestObject('Page_Applitools/input_name'), 'My Name')
eyes.checkWindow('After enter name')
WebUI.click(findTestObject('Page_Applitools/button_Click me'))
eyes.checkWindow('After Click')
TestResults result = eyes.close(false)
WebUI.closeBrowser()

static private Eyes createEyes() {
    URI serverURL
    try {
        serverURL = new URI(GlobalVariable.serverURLstr)
    } catch (URISyntaxException e) {
        println("URI Exception")
        return
    }
    Eyes eyes = new Eyes(serverURL)
    String apiKey = System.getenv("APPLITOOLS_API_KEY")
    eyes.setApiKey(apiKey)
    if (GlobalVariable.runAsBatch) {
        BatchInfo batchInfo = new BatchInfo("Hello World 2 Batch")
        eyes.setBatch(batchInfo)
    }
    
    // kazurayam needed to set PROXY to the Eyes
    //eyes.setProxy(new ProxySettings('http://172.24.2.10:8080'))
    
    return eyes
}

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
