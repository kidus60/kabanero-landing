package io.kabanero.selenium;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class InstanceIT {
    private static WebDriver driver;
    private String baseUrl = "https://localhost:9443/instance/";
    private JavascriptExecutor js;

    @BeforeClass
    public static void setupClass() {
        // Manages the browser driver binary for us
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-certificate-errors");
        options.setHeadless(true);
        driver = new ChromeDriver(options);
    }

    @Before
    public void beforeTest(){
        // selenium stuff
        driver.get(baseUrl);
        js = (JavascriptExecutor) driver;
    }

    @AfterClass
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void hasCorrectTitleIT(){
        String expectedTitle = "Kabanero";
        String actualTitle = driver.getTitle();
        assertEquals("title equals " + expectedTitle, expectedTitle, actualTitle);    
    }

    @Test
    public void displaysCorrectInstanceNameIT() throws InterruptedException {
        // execute javascript function setInstanceSelections() and pass it fake data to test against
        js.executeScript("setInstanceSelections({\"apiVersion\":\"kabanero.io/v1alpha1\",\"items\":[{\"apiVersion\":\"kabanero.io/v1alpha1\",\"kind\":\"Kabanero\",\"metadata\":{\"annotations\":{\"kubectl.kubernetes.io/last-applied-configuration\":\"{\\\"apiVersion\\\":\\\"kabanero.io/v1alpha2\\\",\\\"kind\\\":\\\"Kabanero\\\",\\\"metadata\\\":{\\\"annotations\\\":{},\\\"name\\\":\\\"kabanero\\\",\\\"namespace\\\":\\\"kabanero\\\"},\\\"spec\\\":{\\\"github\\\":{\\\"apiUrl\\\":\\\"https://api.github.com\\\",\\\"organization\\\":\\\"alohr51-kabanero\\\",\\\"teams\\\":[\\\"admin\\\"]},\\\"stacks\\\":{\\\"pipelines\\\":[{\\\"https\\\":{\\\"skipCertVerification\\\":false,\\\"url\\\":\\\"https://github.com/kabanero-io/collections/releases/download/0.5.0-rc.4/incubator.common.pipeline.default.tar.gz\\\"},\\\"id\\\":\\\"default\\\",\\\"sha256\\\":\\\"6537a5a25f845266d6b81c47d97a97c711a98306a78339d039d5af8ed700bff5\\\"}],\\\"repositories\\\":[{\\\"https\\\":{\\\"url\\\":\\\"https://github.com/kabanero-io/collections/releases/download/0.5.0/kabanero-index.yaml\\\"},\\\"name\\\":\\\"central\\\"}]},\\\"version\\\":\\\"0.6.0\\\"}}\\n\"},\"clusterName\":null,\"creationTimestamp\":\"2020-02-07T13:24:35.000-05:00\",\"deletionGracePeriodSeconds\":null,\"deletionTimestamp\":null,\"finalizers\":[\"kabanero.io.kabanero-operator\"],\"generateName\":null,\"generation\":3,\"initializers\":null,\"labels\":null,\"name\":\"kabanero\",\"namespace\":\"kabanero\",\"ownerReferences\":null,\"resourceVersion\":\"47362018\",\"selfLink\":\"/apis/kabanero.io/v1alpha1/namespaces/kabanero/kabaneros/kabanero\",\"uid\":\"17b455ca-49d7-11ea-9580-00000a101619\"},\"spec\":{\"admissionControllerWebhook\":{\"image\":null,\"repository\":null,\"tag\":null,\"version\":null},\"che\":null,\"cliServices\":{\"image\":null,\"repository\":null,\"sessionExpirationSeconds\":null,\"tag\":null,\"version\":null},\"collectionController\":{\"image\":null,\"repository\":null,\"tag\":null,\"version\":null},\"collections\":null,\"events\":{\"enable\":null,\"image\":null,\"repository\":null,\"tag\":null,\"version\":null},\"github\":{\"apiUrl\":\"https://api.github.com\",\"organization\":\"alohr51-kabanero\",\"teams\":[\"admin\"]},\"landing\":{\"enable\":null,\"version\":null},\"targetNamespaces\":null,\"tekton\":null,\"version\":\"0.6.0\"},\"status\":{\"admissionControllerWebhook\":{\"errorMessage\":null,\"ready\":\"True\"},\"appsody\":{\"errorMessage\":null,\"ready\":\"True\",\"version\":\"0.3.0\"},\"che\":null,\"cli\":{\"errorMessage\":null,\"hostnames\":[\"kabanero-cli-kabanero.apps.alohr.os.fyre.ibm.com\"],\"ready\":\"True\"},\"collectionController\":{\"errorMessage\":null,\"ready\":\"True\",\"version\":\"0.6.0-alpha.4\"},\"events\":null,\"kabaneroInstance\":{\"errorMessage\":null,\"ready\":\"False\",\"version\":\"0.6.0\"},\"kappnav\":null,\"knativeEventing\":null,\"landing\":{\"errorMessage\":null,\"ready\":\"True\",\"version\":\"0.5.0\"},\"serverless\":{\"errorMessage\":null,\"knativeServing\":{\"errorMessage\":null,\"ready\":\"False\",\"version\":\"0.10.0\"},\"ready\":\"False\",\"version\":\"1.3.0\"},\"tekton\":{\"errorMessage\":null,\"ready\":\"True\",\"version\":\"v0.8.0\"}}}],\"kind\":\"KabaneroList\",\"metadata\":{\"continue\":\"\",\"remainingItemCount\":null,\"resourceVersion\":\"47524486\",\"selfLink\":\"/apis/kabanero.io/v1alpha1/namespaces/kabanero/kabaneros\"}})");

        String expectedInstanceName = "kabanero";
        String actualInstanceName = driver.findElement(By.id("instance-accordion")).findElement(By.className("bx--accordion__title")).getText();
        assertEquals("instance name equals " + expectedInstanceName, expectedInstanceName, actualInstanceName);
        
    }
}
