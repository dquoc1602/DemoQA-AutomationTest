package Elements;

import core.BaseTest;
import models.enums.CheckState;
import org.junit.jupiter.api.*;
import pages.demoQA.Elements.CheckboxPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Checkbox Tree – Lead Level UI Tests")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CheckboxTest extends BaseTest {

    private CheckboxPage tree;

    // ===================== TEST DATA =====================

    private static final String[] HOME = { "Home" };
    private static final String[] DESKTOP_NOTES = { "Home", "Desktop", "Notes" };
    private static final String[] DESKTOP_COMMANDS = { "Home", "Desktop", "Commands" };
    private static final String[] WORKSPACE_REACT = { "Home", "Documents", "WorkSpace", "React" };
    private static final String[] WORKSPACE_ANGULAR = { "Home", "Documents", "WorkSpace", "Angular" };
    private static final String[] DOWNLOADS = { "Home", "Downloads" };

    @BeforeEach
    void setup() {
        tree = new CheckboxPage();
    }

    @Test
    @DisplayName("01 – Select and unselect single node")
    void shouldToggleSingleNode() {
        tree.selectByPath(HOME);
        assertEquals(CheckState.CHECKED, tree.getNodeState(HOME));

        tree.unselectByPath(HOME);
        assertEquals(CheckState.UNCHECKED, tree.getNodeState(HOME));
    }

    @Test
    @DisplayName("02 – Idempotent select/unselect")
    void shouldBeIdempotent() {
        tree.selectByPath(HOME)
                .selectByPath(HOME);
        assertEquals(CheckState.CHECKED, tree.getNodeState(HOME));

        tree.unselectByPath(HOME)
                .unselectByPath(HOME);
        assertEquals(CheckState.UNCHECKED, tree.getNodeState(HOME));
    }

    @Test
    @DisplayName("03 – Select leaf node by path")
    void shouldSelectLeafNodeByPath() {
        tree.selectByPath(DESKTOP_NOTES);
        assertEquals(CheckState.CHECKED, tree.getNodeState(DESKTOP_NOTES));
    }

    @Test
    @DisplayName("04 – Select deeply nested node")
    void shouldSelectDeepNestedNode() {
        tree.selectByPath(WORKSPACE_REACT);
        assertEquals(CheckState.CHECKED, tree.getNodeState(WORKSPACE_REACT));
    }

    @Test
    @DisplayName("05 – Select nodes across branches")
    void shouldSelectAcrossBranches() {
        tree.selectByPath(DESKTOP_NOTES)
                .selectByPath(WORKSPACE_REACT)
                .selectByPath(DOWNLOADS);

        assertEquals(CheckState.CHECKED, tree.getNodeState(DESKTOP_NOTES));
        assertEquals(CheckState.CHECKED, tree.getNodeState(WORKSPACE_REACT));
        assertEquals(CheckState.CHECKED, tree.getNodeState(DOWNLOADS));
    }

    @Test
    @DisplayName("06 – Unselect multiple nodes")
    void shouldUnselectMultipleNodes() {
        tree.selectByPath(DESKTOP_NOTES)
                .selectByPath(WORKSPACE_REACT)
                .unselectByPath(DESKTOP_NOTES)
                .unselectByPath(WORKSPACE_REACT);

        assertEquals(CheckState.UNCHECKED, tree.getNodeState(DESKTOP_NOTES));
        assertEquals(CheckState.UNCHECKED, tree.getNodeState(WORKSPACE_REACT));
    }

    @Test
    @DisplayName("07 – Selecting parent affects children")
    void shouldSelectChildrenWhenParentSelected() {
        tree.expandByPath("Home", "Desktop")
                .selectByPath("Home", "Desktop");

        assertEquals(2, tree.getChildrenCount("Home", "Desktop"));
    }

    @Test
    @DisplayName("08 – Half-check state on parent")
    void shouldHalfCheckParentWhenSomeChildrenSelected() {
        tree.selectByPath(DESKTOP_NOTES);
        assertEquals(CheckState.HALF_CHECKED, tree.getNodeState("Home", "Desktop"));
    }

    @Test
    @DisplayName("09 – Expand and collapse tree safely")
    void shouldExpandAndCollapseTree() {
        tree.expandAll();
        assertTrue(tree.isNodeExists(DESKTOP_NOTES));

        tree.collapseAll()
                .expandAll();

        assertTrue(tree.isNodeExists(WORKSPACE_REACT));
    }

    @Test
    @DisplayName("10 – Selection persists after collapse/expand")
    void shouldPersistStateAfterCollapse() {
        tree.selectByPath(WORKSPACE_REACT)
                .collapseAll()
                .expandAll();

        assertEquals(CheckState.CHECKED, tree.getNodeState(WORKSPACE_REACT));
    }

    @Test
    @DisplayName("11 – Non-existing node fails deterministically")
    void shouldFailOnInvalidPath() {
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> tree.selectByPath("Home", "Invalid", "Node"));
        assertTrue(ex.getMessage().contains("Node not found"));
    }

    @Test
    @DisplayName("12 – Full realistic workflow")
    void shouldHandleCompleteWorkflow() {
        tree.expandAll()
                .selectByPath(DESKTOP_NOTES)
                .selectByPath(DESKTOP_COMMANDS)
                .selectByPath(WORKSPACE_REACT)
                .selectByPath(WORKSPACE_ANGULAR)
                .selectByPath(DOWNLOADS);

        assertEquals(CheckState.CHECKED, tree.getNodeState(DESKTOP_NOTES));
        assertEquals(CheckState.CHECKED, tree.getNodeState(DESKTOP_COMMANDS));
        assertEquals(CheckState.CHECKED, tree.getNodeState(WORKSPACE_REACT));
        assertEquals(CheckState.CHECKED, tree.getNodeState(WORKSPACE_ANGULAR));
        assertEquals(CheckState.CHECKED, tree.getNodeState(DOWNLOADS));

        tree.collapseAll()
                .expandAll();

        assertEquals(CheckState.CHECKED, tree.getNodeState(DESKTOP_NOTES));
        assertEquals(CheckState.CHECKED, tree.getNodeState(WORKSPACE_REACT));
    }
}
