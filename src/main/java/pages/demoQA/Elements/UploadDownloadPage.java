package pages.demoQA.Elements;

import core.Basepage;
import locators.demoQA.elements.UploadDownloadLocators;

import static util.Constants.DEMOQA_UPLOAD_DOWNLOAD_URL;

public class UploadDownloadPage extends Basepage {

    public UploadDownloadPage() {
        super();
        logger.info("Opening Upload and Download page: {}", DEMOQA_UPLOAD_DOWNLOAD_URL);
        openSite(DEMOQA_UPLOAD_DOWNLOAD_URL);
    }

    public UploadDownloadPage clickDownload() {
        logger.info("Clicking download button");
        clickButton(UploadDownloadLocators.DOWNLOAD_BUTTON);
        return this;
    }

    public String getDownloadHref() {
        return getElementAttribute(UploadDownloadLocators.DOWNLOAD_BUTTON, "href");
    }

    public UploadDownloadPage uploadFile(String absolutePath) {
        logger.info("Uploading file from path: {}", absolutePath);
        // Standard Selenium upload: sendKeys to the input[type='file']
        driver.findElement(UploadDownloadLocators.UPLOAD_FILE_INPUT).sendKeys(absolutePath);
        return this;
    }

    public String getUploadedFilePathText() {
        try {
            String text = getElementText(UploadDownloadLocators.UPLOADED_FILE_PATH);
            logger.info("Uploaded file path text: {}", text);
            return text;
        } catch (Exception e) {
            logger.warn("Uploaded file path text not found");
            return "";
        }
    }
}
