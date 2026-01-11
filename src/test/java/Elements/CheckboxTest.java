package Elements;

import core.BaseTest;
import org.junit.jupiter.api.*;
import pages.demoQA.Elements.CheckboxPage;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Lead / Principal-ready UI Test Suite for Checkbox Tree
 *
 * Design goals:
 * - Business-readable tests
 * - No duplication
 * - Deterministic assertions
 * - Path-based validation only
 * - No UI-implementation assumptions
 */
@DisplayName("Checkbox Tree – Lead Level UI Tests")
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class CheckboxTest extends BaseTest {

    private CheckboxPage tree;

    // ===================== TEST DATA =====================

    private static final String[] HOME = {"Home"};
    private static final String[] DESKTOP_NOTES = {"Home", "Desktop", "Notes"};
    private static final String[] DESKTOP_COMMANDS = {"Home", "Desktop", "Commands"};
    private static final String[] WORKSPACE_REACT = {"Home", "Documents", "WorkSpace", "React"};
    private static final String[] WORKSPACE_ANGULAR = {"Home", "Documents", "WorkSpace", "Angular"};
    private static final String[] DOWNLOADS = {"Home", "Downloads"};

    // ===================== SETUP =====================

    @BeforeEach
    void setup() {
        tree = new CheckboxPage();
    }

    // ===================== BASIC BEHAVIOR =====================

    @Test
    @DisplayName("01 – Select and unselect single node")
    void shouldToggleSingleNode() {
        tree.selectByPath(HOME)
            .verifyNodeChecked(HOME)
            .unselectByPath(HOME)
            .verifyNodeUnchecked(HOME);
    }

    @Test
    @DisplayName("02 – Idempotent select/unselect")
    void shouldBeIdempotent() {
        tree.selectByPath(HOME)
            .selectByPath(HOME)
            .verifyNodeChecked(HOME)
            .unselectByPath(HOME)
            .unselectByPath(HOME)
            .verifyNodeUnchecked(HOME);
    }

    // ===================== PATH RESOLUTION =====================

    @Test
    @DisplayName("03 – Select leaf node by path")
    void shouldSelectLeafNodeByPath() {
        tree.selectByPath(DESKTOP_NOTES)
            .verifyNodeChecked(DESKTOP_NOTES);
    }

    @Test
    @DisplayName("04 – Select deeply nested node")
    void shouldSelectDeepNestedNode() {
        tree.selectByPath(WORKSPACE_REACT)
            .verifyNodeChecked(WORKSPACE_REACT);
    }

    // ===================== MULTI-SELECTION =====================

    @Test
    @DisplayName("05 – Select nodes across branches")
    void shouldSelectAcrossBranches() {
        tree.selectByPath(DESKTOP_NOTES)
            .selectByPath(WORKSPACE_REACT)
            .selectByPath(DOWNLOADS)
            .verifyNodeChecked(DESKTOP_NOTES)
            .verifyNodeChecked(WORKSPACE_REACT)
            .verifyNodeChecked(DOWNLOADS);
    }

    @Test
    @DisplayName("06 – Unselect multiple nodes")
    void shouldUnselectMultipleNodes() {
        tree.selectByPath(DESKTOP_NOTES)
            .selectByPath(WORKSPACE_REACT)
            .unselectByPath(DESKTOP_NOTES)
            .unselectByPath(WORKSPACE_REACT)
            .verifyNodeUnchecked(DESKTOP_NOTES)
            .verifyNodeUnchecked(WORKSPACE_REACT);
    }

    // ===================== PARENT / CHILD RELATIONSHIP =====================

    @Test
    @DisplayName("07 – Selecting parent affects children")
    void shouldSelectChildrenWhenParentSelected() {
        tree.expandByPath("Home", "Desktop")
            .selectByPath("Home", "Desktop")
            .verifyChildrenCount(2, "Home", "Desktop");
    }

    @Test
    @DisplayName("08 – Half-check state on parent")
    void shouldHalfCheckParentWhenSomeChildrenSelected() {
        tree.selectByPath(DESKTOP_NOTES)
            .verifyNodeHalfChecked("Home", "Desktop");
    }

    // ===================== EXPAND / COLLAPSE =====================

    @Test
    @DisplayName("09 – Expand and collapse tree safely")
    void shouldExpandAndCollapseTree() {
        tree.expandAll()
            .verifyNodeExists(DESKTOP_NOTES)
            .collapseAll()
            .expandAll()
            .verifyNodeExists(WORKSPACE_REACT);
    }

    // ===================== STATE PERSISTENCE =====================

    @Test
    @DisplayName("10 – Selection persists after collapse/expand")
    void shouldPersistStateAfterCollapse() {
        tree.selectByPath(WORKSPACE_REACT)
            .collapseAll()
            .expandAll()
            .verifyNodeChecked(WORKSPACE_REACT);
    }

    // ===================== NEGATIVE & EDGE =====================

    @Test
    @DisplayName("11 – Non-existing node fails deterministically")
    void shouldFailOnInvalidPath() {
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> tree.selectByPath("Home", "Invalid", "Node")
        );
        assertTrue(ex.getMessage().contains("Node not found"));
    }

    // ===================== END-TO-END =====================

    @Test
    @DisplayName("12 – Full realistic workflow")
    void shouldHandleCompleteWorkflow() {
        tree.expandAll()
            .selectByPath(DESKTOP_NOTES)
            .selectByPath(DESKTOP_COMMANDS)
            .selectByPath(WORKSPACE_REACT)
            .selectByPath(WORKSPACE_ANGULAR)
            .selectByPath(DOWNLOADS)

            .verifyNodeChecked(DESKTOP_NOTES)
            .verifyNodeChecked(DESKTOP_COMMANDS)
            .verifyNodeChecked(WORKSPACE_REACT)
            .verifyNodeChecked(WORKSPACE_ANGULAR)
            .verifyNodeChecked(DOWNLOADS)

            .collapseAll()
            .expandAll()

            .verifyNodeChecked(DESKTOP_NOTES)
            .verifyNodeChecked(WORKSPACE_REACT);
    }
}
