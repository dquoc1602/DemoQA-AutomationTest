package pages.demoQA.Elements;

import core.Basepage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static util.Constants.DEMOQA_CHECKBOX_URL;
import static util.Constants.TREE_POLLING_INTERVAL;
import static util.Constants.TREE_STABILIZATION_TIMEOUT;

public class CheckboxPage extends Basepage {


    // ================= SELECTORS =================

    private static final class Selectors {
        static final By TREE_ROOT = By.id("tree-node");

        static final By EXPAND_ALL_BUTTON = By.cssSelector("button[aria-label='Expand all']");
        static final By COLLAPSE_ALL_BUTTON = By.cssSelector("button[aria-label='Collapse all']");

        static final String REL_TITLE = ".//span[@class='rct-title' and normalize-space(.)='%s']";
        static final String REL_NODE_LI = "./ancestor::li[contains(@class,'rct-node')]";

        static final By REL_TOGGLE = By.xpath(".//button[contains(@class,'rct-collapse-btn')]");
        static final By REL_LABEL = By.xpath(".//label");
        static final By REL_CHILD_NODES = By.xpath("./ol/li[contains(@class,'rct-node')]");
        static final By REL_CHECKBOX_ICON =
                By.xpath(".//span[contains(@class,'rct-checkbox')]/*[local-name()='svg']");

        static final String CLASS_EXPANDED = "rct-node-expanded";

        private Selectors() {}
    }

    // ================= DOMAIN MODELS =================

    public enum CheckState {
        CHECKED,
        UNCHECKED,
        HALF_CHECKED
    }

    // ================= CONSTRUCTOR =================

    public CheckboxPage() {
        super();
        logger.info("Opening Checkbox page: {}", DEMOQA_CHECKBOX_URL);
        openSite(DEMOQA_CHECKBOX_URL);
    }

    // ================= HIGH-LEVEL PUBLIC API =================

    public CheckboxPage expandAll() {
        logger.info("Expanding all nodes");
        clickButton(Selectors.EXPAND_ALL_BUTTON);
        return this;
    }

    public CheckboxPage collapseAll() {
        logger.info("Collapsing all nodes");
        clickButton(Selectors.COLLAPSE_ALL_BUTTON);
        return this;
    }

    public CheckboxPage selectByPath(String... path) {
        logger.info("Selecting node by path: {}", String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.EXPAND_PARENTS_ONLY);
        setCheckboxState(node, true);
        return this;
    }

    public CheckboxPage unselectByPath(String... path) {
        logger.info("Unselecting node by path: {}", String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.EXPAND_PARENTS_ONLY);
        setCheckboxState(node, false);
        return this;
    }

    public CheckboxPage verifyNodeChecked(String... path) {
        logger.info("Verifying CHECKED state for: {}", String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.NO_EXPAND);
        verifyTrue(getCheckState(node) == CheckState.CHECKED,
                "Expected CHECKED but was " + getCheckState(node));
        return this;
    }

    public CheckboxPage verifyNodeHalfChecked(String... path) {
        logger.info("Verifying HALF_CHECKED state for: {}", String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.NO_EXPAND);
        verifyTrue(getCheckState(node) == CheckState.HALF_CHECKED,
                "Expected HALF_CHECKED but was " + getCheckState(node));
        return this;
    }

    public CheckboxPage verifyNodeUnchecked(String... path) {
        logger.info("Verifying UNCHECKED state for: {}", String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.NO_EXPAND);
        verifyTrue(getCheckState(node) == CheckState.UNCHECKED,
                "Expected UNCHECKED but was " + getCheckState(node));
        return this;
    }

    public CheckboxPage verifyNodeExists(String... path) {
        logger.info("Verifying node exists: {}", String.join(" > ", path));
        resolveNode(path, ResolvePolicy.NO_EXPAND);
        return this;
    }

    public CheckboxPage verifyNodeNotExists(String... path) {
        logger.info("Verifying node NOT exists: {}", String.join(" > ", path));
        Optional<WebElement> node = tryResolveNode(path);
        verifyTrue(node.isEmpty(), "Node unexpectedly exists");
        return this;
    }

    public CheckboxPage expandByPath(String... path) {
        logger.info("Expanding node by path: {}", String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.NO_EXPAND);
        expandIfNeeded(node);
        return this;
    }

    public CheckboxPage collapseByPath(String... path) {
        logger.info("Collapsing node by path: {}", String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.NO_EXPAND);
        collapseIfNeeded(node);
        return this;
    }

    public CheckboxPage verifyChildrenCount(int expected, String... path) {
        logger.info("Verifying children count={} for path: {}", expected, String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.EXPAND_SELF);
        List<WebElement> children = node.findElements(Selectors.REL_CHILD_NODES);
        verifyTrue(children.size() == expected,
                "Expected " + expected + " children but found " + children.size());
        return this;
    }

    public CheckboxPage verifyChildrenNames(List<String> expectedNames, String... path) {
        logger.info("Verifying children names {} for path: {}", expectedNames, String.join(" > ", path));
        WebElement node = resolveNode(path, ResolvePolicy.EXPAND_SELF);
        List<WebElement> children = node.findElements(Selectors.REL_CHILD_NODES);

        for (String expected : expectedNames) {
            boolean found = children.stream()
                    .anyMatch(c -> c.findElement(By.xpath(".//span[@class='rct-title']"))
                            .getText().trim().equals(expected));
            verifyTrue(found, "Missing child '" + expected + "'");
        }
        return this;
    }

    // ================= RESOLUTION ENGINE =================

    private enum ResolvePolicy {
        NO_EXPAND,
        EXPAND_PARENTS_ONLY,
        EXPAND_SELF
    }

    private WebElement resolveNode(String[] path, ResolvePolicy policy) {
        logger.debug("Resolving node with policy {} for path: {}", policy, String.join(" > ", path));
        Optional<WebElement> resolved = tryResolveNode(path, policy);
        if (resolved.isEmpty()) {
            logger.error("Node NOT found at path: {}", String.join(" > ", path));
            throw new IllegalStateException("Node not found at path: " + String.join(" > ", path));
        }
        return resolved.get();
    }

    private Optional<WebElement> tryResolveNode(String... path) {
        return tryResolveNode(path, ResolvePolicy.NO_EXPAND);
    }

    private Optional<WebElement> tryResolveNode(String[] path, ResolvePolicy policy) {
        if (path == null || path.length == 0) {
            throw new IllegalArgumentException("Path must not be empty");
        }

        WebElement context = findElement(Selectors.TREE_ROOT);
        List<String> walked = new ArrayList<>();

        for (int i = 0; i < path.length; i++) {
            String segment = path[i];
            walked.add(segment);
            logger.info("Node {} is {}", i, segment);
            logger.debug("Resolving segment '{}' under context {}", segment, String.join(" > ", walked));
            waitForChildrenReady(context);
            Optional<WebElement> child = findChildNode(context, segment);
            if (child.isEmpty()) {
                logger.warn("Segment '{}' NOT found under {}", segment, String.join(" > ", walked));
                return Optional.empty();
            }

            boolean isLast = i == path.length - 1;
            if (!isLast || policy == ResolvePolicy.EXPAND_SELF) {
                expandIfNeeded(child.get());
            }
            context = child.get();

        }
        return Optional.of(context);
    }

    private Optional<WebElement> findChildNode(WebElement parent, String name) {
        List<WebElement> titles =
                parent.findElements(By.xpath(String.format(Selectors.REL_TITLE, name)));

        if (titles.isEmpty()) {
            logger.debug("No child title found for '{}'", name);
            return Optional.empty();
        }

        return Optional.of(titles.get(0)
                .findElement(By.xpath(Selectors.REL_NODE_LI)));
    }

    // ================= TREE STABILIZATION =================

    private void waitForChildrenReady(WebElement node) {
        logger.debug("Waiting for children to be ready");
        WebDriverWait wait = new WebDriverWait(driver, TREE_STABILIZATION_TIMEOUT);
        wait.pollingEvery(TREE_POLLING_INTERVAL);

        wait.until(d -> {
            if (node.findElements(Selectors.REL_TOGGLE).isEmpty()) {
                logger.debug("Node is leaf, children ready");
                return true;
            }
            List<WebElement> children = node.findElements(Selectors.REL_CHILD_NODES);
            boolean ready = !children.isEmpty();
            if (!ready) {
                logger.trace("Children not ready yet");
            }
            return ready;
        });
    }

    // ================= EXPAND / COLLAPSE =================

    private void expandIfNeeded(WebElement node) {
        if (isExpanded(node)) {
            logger.debug("Node already expanded, stabilizing");
            waitForChildrenReady(node);
            return;
        }

        List<WebElement> toggles = node.findElements(Selectors.REL_TOGGLE);
        if (toggles.isEmpty()) {
            logger.debug("Node has no toggle (leaf)");
            return;
        }

        logger.debug("Expanding node");
        scrollToWebElement(node);
        toggles.get(0).click();

        waitForWebElementAttributeContains(node, "class", Selectors.CLASS_EXPANDED);
        waitForChildrenReady(node);
    }

    private void collapseIfNeeded(WebElement node) {
        if (!isExpanded(node)) {
            logger.debug("Node already collapsed");
            return;
        }

        List<WebElement> toggles = node.findElements(Selectors.REL_TOGGLE);
        if (toggles.isEmpty()) {
            logger.debug("Node has no toggle (leaf)");
            return;
        }

        logger.debug("Collapsing node");
        scrollToWebElement(node);
        toggles.get(0).click();
        waitForWebElementAttributeNotContains(node, "class", Selectors.CLASS_EXPANDED);
    }

    private boolean isExpanded(WebElement node) {
        String clazz = getWebElementAttribute(node, "class");
        return clazz != null && clazz.contains(Selectors.CLASS_EXPANDED);
    }

    // ================= CHECKBOX STATE =================

    private void setCheckboxState(WebElement node, boolean shouldCheck) {
        CheckState current = getCheckState(node);
        logger.debug("Setting checkbox state to {} (current={})", shouldCheck, current);

        if (shouldCheck && current == CheckState.CHECKED) return;
        if (!shouldCheck && current == CheckState.UNCHECKED) return;

        WebElement label = node.findElement(Selectors.REL_LABEL);
        scrollToWebElement(label);
        label.click();
    }

    private CheckState getCheckState(WebElement node) {
        WebElement icon = node.findElement(Selectors.REL_CHECKBOX_ICON);
        String clazz = getWebElementAttribute(icon, "class");

        if (clazz == null) return CheckState.UNCHECKED;
        if (clazz.contains("rct-icon-check")) return CheckState.CHECKED;
        if (clazz.contains("rct-icon-half-check")) return CheckState.HALF_CHECKED;
        return CheckState.UNCHECKED;
    }
}
