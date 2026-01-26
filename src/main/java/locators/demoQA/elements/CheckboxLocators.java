package locators.demoQA.elements;

import org.openqa.selenium.By;

public final class CheckboxLocators {
    public static final By TREE_ROOT = By.id("tree-node");

    public static final By EXPAND_ALL_BUTTON = By.cssSelector("button[aria-label='Expand all']");
    public static final By COLLAPSE_ALL_BUTTON = By.cssSelector("button[aria-label='Collapse all']");

    public static final String REL_TITLE = ".//span[@class='rct-title' and normalize-space(.)='%s']";
    public static final String REL_NODE_LI = "./ancestor::li[contains(@class,'rct-node')]";

    public static final By REL_TOGGLE = By.xpath(".//button[contains(@class,'rct-collapse-btn')]");
    public static final By REL_LABEL = By.xpath(".//label");
    public static final By REL_CHILD_NODES = By.xpath("./ol/li[contains(@class,'rct-node')]");
    public static final By REL_CHECKBOX_ICON = By
            .xpath(".//span[contains(@class,'rct-checkbox')]/*[local-name()='svg']");

    public static final String CLASS_EXPANDED = "rct-node-expanded";

    private CheckboxLocators() {
    }
}
