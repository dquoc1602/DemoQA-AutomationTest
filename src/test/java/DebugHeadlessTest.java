import core.DriverManager;
import core.TestSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DebugHeadlessTest {

    @Test
    public void captureScreenshot() throws Exception {
        System.setProperty("headless", "true");
        DriverManager manager = new DriverManager();
        WebDriver driver = DriverManager.getDriver();
        driver.get("https://demoqa.com/checkbox");
        Thread.sleep(5000); // 5 seconds wait

        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("target/headless-screenshot.png");
        Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Screenshot saved to " + destFile.getAbsolutePath());
        
        System.out.println("Page Title: " + driver.getTitle());
        try {
            System.out.println("Tree node displayed: " + driver.findElement(By.id("tree-node")).isDisplayed());
        } catch (Exception e) {
            System.out.println("Tree node not found: " + e.getMessage());
        }

        manager.quit();
    }
}
