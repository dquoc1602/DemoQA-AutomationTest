package demoqa.elements;

import core.BaseTest;
import model.enums.CheckState;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.demoqa.elements.CheckboxPage;

public class CheckboxTest extends BaseTest {

    private CheckboxPage tree;

    // ===================== TEST DATA =====================

    private static final String[] HOME = { "Home" };
    private static final String[] DESKTOP_NOTES = { "Home", "Desktop", "Notes" };
    private static final String[] DESKTOP_COMMANDS = { "Home", "Desktop", "Commands" };
    private static final String[] WORKSPACE_REACT = { "Home", "Documents", "WorkSpace", "React" };
    private static final String[] WORKSPACE_ANGULAR = { "Home", "Documents", "WorkSpace", "Angular" };
    private static final String[] DOWNLOADS = { "Home", "Downloads" };

    @BeforeMethod
    void setup() {
        tree = new CheckboxPage();
    }

    @Test(priority = 1, description = "01 – Select and unselect single node")
    void shouldToggleSingleNode() {
        tree.selectByPath(HOME);
        Assert.assertEquals(tree.getNodeState(HOME), CheckState.CHECKED);

        tree.unselectByPath(HOME);
        Assert.assertEquals(tree.getNodeState(HOME), CheckState.UNCHECKED);
    }

    @Test(priority = 2, description = "02 – Idempotent select/unselect")
    void shouldBeIdempotent() {
        tree.selectByPath(HOME)
                .selectByPath(HOME);
        Assert.assertEquals(tree.getNodeState(HOME), CheckState.CHECKED);

        tree.unselectByPath(HOME)
                .unselectByPath(HOME);
        Assert.assertEquals(tree.getNodeState(HOME), CheckState.UNCHECKED);
    }

    @Test(priority = 3, description = "03 – Select leaf node by path")
    void shouldSelectLeafNodeByPath() {
        tree.selectByPath(DESKTOP_NOTES);
        Assert.assertEquals(tree.getNodeState(DESKTOP_NOTES), CheckState.CHECKED);
    }

    @Test(priority = 4, description = "04 – Select deeply nested node")
    void shouldSelectDeepNestedNode() {
        tree.selectByPath(WORKSPACE_REACT);
        Assert.assertEquals(tree.getNodeState(WORKSPACE_REACT), CheckState.CHECKED);
    }

    @Test(priority = 5, description = "05 – Select nodes across branches")
    void shouldSelectAcrossBranches() {
        tree.selectByPath(DESKTOP_NOTES)
                .selectByPath(WORKSPACE_REACT)
                .selectByPath(DOWNLOADS);

        Assert.assertEquals(tree.getNodeState(DESKTOP_NOTES), CheckState.CHECKED);
        Assert.assertEquals(tree.getNodeState(WORKSPACE_REACT), CheckState.CHECKED);
        Assert.assertEquals(tree.getNodeState(DOWNLOADS), CheckState.CHECKED);
    }

    @Test(priority = 6, description = "06 – Unselect multiple nodes")
    void shouldUnselectMultipleNodes() {
        tree.selectByPath(DESKTOP_NOTES)
                .selectByPath(WORKSPACE_REACT)
                .unselectByPath(DESKTOP_NOTES)
                .unselectByPath(WORKSPACE_REACT);

        Assert.assertEquals(tree.getNodeState(DESKTOP_NOTES), CheckState.UNCHECKED);
        Assert.assertEquals(tree.getNodeState(WORKSPACE_REACT), CheckState.UNCHECKED);
    }

    @Test(priority = 8, description = "08 – Half-check state on parent")
    void shouldHalfCheckParentWhenSomeChildrenSelected() {
        tree.selectByPath(DESKTOP_NOTES);
        Assert.assertEquals(tree.getNodeState("Home", "Desktop"), CheckState.HALF_CHECKED);
    }

    @Test(priority = 9, description = "09 – Expand and collapse tree safely")
    void shouldExpandAndCollapseTree() {
        tree.expandAll();
        Assert.assertTrue(tree.isNodeExists(DESKTOP_NOTES));

        tree.collapseAll()
                .expandAll();

        Assert.assertTrue(tree.isNodeExists(WORKSPACE_REACT));
    }

    @Test(priority = 10, description = "10 – Selection persists after collapse/expand")
    void shouldPersistStateAfterCollapse() {
        tree.selectByPath(WORKSPACE_REACT)
                .collapseAll()
                .expandAll();

        Assert.assertEquals(tree.getNodeState(WORKSPACE_REACT), CheckState.CHECKED);
    }

    @Test(priority = 11, description = "11 – Non-existing node fails deterministically")
    void shouldFailOnInvalidPath() {
        IllegalStateException ex = Assert.expectThrows(
                IllegalStateException.class,
                () -> tree.selectByPath("Home", "Invalid", "Node"));
        Assert.assertTrue(ex.getMessage().contains("Node not found"));
    }

    @Test(priority = 12, description = "12 – Full realistic workflow")
    void shouldHandleCompleteWorkflow() {
        tree.expandAll()
                .selectByPath(DESKTOP_NOTES)
                .selectByPath(DESKTOP_COMMANDS)
                .selectByPath(WORKSPACE_REACT)
                .selectByPath(WORKSPACE_ANGULAR)
                .selectByPath(DOWNLOADS);

        Assert.assertEquals(tree.getNodeState(DESKTOP_NOTES), CheckState.CHECKED);
        Assert.assertEquals(tree.getNodeState(DESKTOP_COMMANDS), CheckState.CHECKED);
        Assert.assertEquals(tree.getNodeState(WORKSPACE_REACT), CheckState.CHECKED);
        Assert.assertEquals(tree.getNodeState(WORKSPACE_ANGULAR), CheckState.CHECKED);
        Assert.assertEquals(tree.getNodeState(DOWNLOADS), CheckState.CHECKED);

        tree.collapseAll()
                .expandAll();

        Assert.assertEquals(tree.getNodeState(DESKTOP_NOTES), CheckState.CHECKED);
        Assert.assertEquals(tree.getNodeState(WORKSPACE_REACT), CheckState.CHECKED);
    }
}
