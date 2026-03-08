package testing.lab1.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BinomialHeap")
class BinomialHeapAlgorithmBackupTest {

    // ── Expected tree construction helpers ──

    /**
     * Creates a node with given key and children.
     * Children are linked via sibling pointers, degree is set automatically.
     */
    private static BinomialHeap.Node n(int key, BinomialHeap.Node... children) {
        BinomialHeap.Node node = new BinomialHeap.Node(key);
        node.degree = children.length;
        if (children.length > 0) {
            node.child = children[0];
            for (int i = 0; i < children.length - 1; i++) {
                children[i].sibling = children[i + 1];
            }
        }
        return node;
    }

    /**
     * Links multiple root nodes into a root list via sibling pointers.
     * Returns the head of the list (first root).
     */
    private static BinomialHeap.Node roots(BinomialHeap.Node... rootNodes) {
        if (rootNodes.length == 0)
            return null;
        for (int i = 0; i < rootNodes.length - 1; i++) {
            rootNodes[i].sibling = rootNodes[i + 1];
        }
        return rootNodes[0];
    }

    // ── Structural comparison ──

    private static void assertStructureEquals(BinomialHeap.Node expected, BinomialHeap.Node actual) {
        if (expected == null && actual == null)
            return;
        if (expected == null)
            fail("Expected null but got node(key=" + actual.key + ")");
        if (actual == null)
            fail("Expected node(key=" + expected.key + ") but got null");
        assertEquals(expected.key, actual.key, "key mismatch");
        assertEquals(expected.degree, actual.degree, "degree mismatch at key=" + expected.key);
        assertStructureEquals(expected.child, actual.child);
        assertStructureEquals(expected.sibling, actual.sibling);
    }

    // ── Helpers to build heaps with specific rank profiles ──

    private static BinomialHeap heapEmpty() {
        return new BinomialHeap();
    }

    /** 1 element → B₀ */
    private static BinomialHeap heapB0() {
        BinomialHeap h = new BinomialHeap();
        h.insert(10);
        return h;
    }

    /** 2 elements → B₁ */
    private static BinomialHeap heapB1() {
        BinomialHeap h = new BinomialHeap();
        h.insert(10);
        h.insert(20);
        return h;
    }

    /** 4 elements → B₂ */
    private static BinomialHeap heapB2() {
        BinomialHeap h = new BinomialHeap();
        h.insert(10);
        h.insert(20);
        h.insert(30);
        h.insert(40);
        return h;
    }

    /** 3 elements → B₀ + B₁ */
    private static BinomialHeap heapB0B1() {
        BinomialHeap h = new BinomialHeap();
        h.insert(10);
        h.insert(20);
        h.insert(30);
        return h;
    }

    /** 5 elements → B₀ + B₂ */
    private static BinomialHeap heapB0B2() {
        BinomialHeap h = new BinomialHeap();
        for (int i = 1; i <= 5; i++)
            h.insert(i * 10);
        return h;
    }

    /** 6 elements → B₁ + B₂ */
    private static BinomialHeap heapB1B2() {
        BinomialHeap h = new BinomialHeap();
        for (int i = 1; i <= 6; i++)
            h.insert(i * 10);
        return h;
    }

    /** 7 elements → B₀ + B₁ + B₂ */
    private static BinomialHeap heapB0B1B2() {
        BinomialHeap h = new BinomialHeap();
        for (int i = 1; i <= 7; i++)
            h.insert(i * 10);
        return h;
    }

    // ═══════════════════════════════════════════════════════
    // insert(int key) — 8 tests (T1–T8)
    // ═══════════════════════════════════════════════════════

    @Nested
    @DisplayName("insert(int key)")
    class InsertTests {

        @Test
        @DisplayName("T1: {} → insert(5) → B₀(5)")
        void t1_empty() {
            BinomialHeap h = heapEmpty();
            h.insert(5);

            // 5(d0)
            assertStructureEquals(
                    roots(n(5)),
                    h.getHead());
        }

        @Test
        @DisplayName("T2: {B₀} → insert(5) → B₁(5→10)")
        void t2_B0() {
            BinomialHeap h = heapB0(); // B₀(10)
            h.insert(5);

            // 5(d1)
            // └─ 10(d0)
            assertStructureEquals(
                    roots(n(5, n(10))),
                    h.getHead());
        }

        @Test
        @DisplayName("T3: {B₁} → insert(5) → B₀(5), B₁(10→20)")
        void t3_B1() {
            BinomialHeap h = heapB1(); // B₁(10→20)
            h.insert(5);

            // 5(d0) → 10(d1)
            //         └─ 20(d0)
            assertStructureEquals(
                    roots(n(5), n(10, n(20))),
                    h.getHead());
        }

        @Test
        @DisplayName("T4: {B₂} → insert(5) → B₀(5), B₂(10→[30→40,20])")
        void t4_B2() {
            BinomialHeap h = heapB2(); // B₂(10→[30(→40),20])
            h.insert(5);

            // 5(d0) → 10(d2)
            //         ├─ 30(d1)
            //         │  └─ 40(d0)
            //         └─ 20(d0)
            assertStructureEquals(
                    roots(n(5), n(10, n(30, n(40)), n(20))),
                    h.getHead());
        }

        @Test
        @DisplayName("T5: {B₀,B₁} → insert(5) → B₂(5→[10→20,30])")
        void t5_B0B1() {
            BinomialHeap h = heapB0B1(); // B₀(30) → B₁(10→20)
            h.insert(5);

            // 5(d2)
            // ├─ 10(d1)
            // │  └─ 20(d0)
            // └─ 30(d0)
            assertStructureEquals(
                    roots(n(5, n(10, n(20)), n(30))),
                    h.getHead());
        }

        @Test
        @DisplayName("T6: {B₀,B₂} → insert(5) → B₁(5→50), B₂(10→[30→40,20])")
        void t6_B0B2() {
            BinomialHeap h = heapB0B2(); // B₀(50) → B₂(10→[30(→40),20])
            h.insert(5);

            // 5(d1) → 10(d2)
            // └─ 50   ├─ 30(d1)
            //         │  └─ 40(d0)
            //         └─ 20(d0)
            assertStructureEquals(
                    roots(n(5, n(50)), n(10, n(30, n(40)), n(20))),
                    h.getHead());
        }

        @Test
        @DisplayName("T7: {B₁,B₂} → insert(5) → B₀(5), B₁(50→60), B₂(10→[30→40,20])")
        void t7_B1B2() {
            BinomialHeap h = heapB1B2(); // B₁(50→60) → B₂(10→[30(→40),20])
            h.insert(5);

            // 5(d0) → 50(d1) → 10(d2)
            //         └─ 60    ├─ 30(d1)
            //                  │  └─ 40(d0)
            //                  └─ 20(d0)
            assertStructureEquals(
                    roots(n(5), n(50, n(60)), n(10, n(30, n(40)), n(20))),
                    h.getHead());
        }

        @Test
        @DisplayName("T8: {B₀,B₁,B₂} → insert(5) → B₃(5→[10→[30→40,20],50→60,70])")
        void t8_B0B1B2() {
            BinomialHeap h = heapB0B1B2(); // B₀(70) → B₁(50→60) → B₂(10→[30(→40),20])
            h.insert(5);

            // 5(d3)
            // ├─ 10(d2)
            // │  ├─ 30(d1)
            // │  │  └─ 40(d0)
            // │  └─ 20(d0)
            // ├─ 50(d1)
            // │  └─ 60(d0)
            // └─ 70(d0)
            assertStructureEquals(
                    roots(n(5, n(10, n(30, n(40)), n(20)), n(50, n(60)), n(70))),
                    h.getHead());
        }
    }

    // ═══════════════════════════════════════════════════════
    // extractMin() — 8 tests (T1–T8)
    // ═══════════════════════════════════════════════════════

    @Nested
    @DisplayName("extractMin()")
    class ExtractMinTests {

        @Test
        @DisplayName("T1: {} → extractMin → NoSuchElementException")
        void t1_empty() {
            BinomialHeap h = heapEmpty();
            assertThrows(NoSuchElementException.class, h::extractMin);
        }

        @Test
        @DisplayName("T2: {B₀(10)} → extractMin → 10, {}")
        void t2_B0() {
            BinomialHeap h = heapB0(); // B₀(10)
            assertEquals(10, h.extractMin());
            assertStructureEquals(null, h.getHead());
        }

        @Test
        @DisplayName("T3: {B₁(10→20)} → extractMin → 10, {B₀(20)}")
        void t3_B1() {
            BinomialHeap h = heapB1(); // B₁(10→20)
            assertEquals(10, h.extractMin());

            // 20(d0)
            assertStructureEquals(
                    roots(n(20)),
                    h.getHead());
        }

        @Test
        @DisplayName("T4: {B₂} → extractMin → 10, {B₀(20), B₁(30→40)}")
        void t4_B2() {
            BinomialHeap h = heapB2(); // B₂(10→[30(→40),20])
            assertEquals(10, h.extractMin());

            // 20(d0) → 30(d1)
            //          └─ 40(d0)
            assertStructureEquals(
                    roots(n(20), n(30, n(40))),
                    h.getHead());
        }

        @Test
        @DisplayName("T5: {B₀(30),B₁(10→20)} → extractMin → 10, {B₁(20→30)}")
        void t5_B0B1() {
            BinomialHeap h = heapB0B1(); // B₀(30) → B₁(10→20)
            assertEquals(10, h.extractMin());

            // 20(d1)
            // └─ 30(d0)
            assertStructureEquals(
                    roots(n(20, n(30))),
                    h.getHead());
        }

        @Test
        @DisplayName("T6: {B₀(50),B₂(10→…)} → extractMin → 10, {B₂(20→[30→40,50])}")
        void t6_B0B2() {
            BinomialHeap h = heapB0B2(); // B₀(50) → B₂(10→[30(→40),20])
            assertEquals(10, h.extractMin());

            // 20(d2)
            // ├─ 30(d1)
            // │  └─ 40(d0)
            // └─ 50(d0)
            assertStructureEquals(
                    roots(n(20, n(30, n(40)), n(50))),
                    h.getHead());
        }

        @Test
        @DisplayName("T7: {B₁(50→60),B₂(10→…)} → extractMin → 10, {B₀(20), B₂(30→[50→60,40])}")
        void t7_B1B2() {
            BinomialHeap h = heapB1B2(); // B₁(50→60) → B₂(10→[30(→40),20])
            assertEquals(10, h.extractMin());

            // 20(d0) → 30(d2)
            //          ├─ 50(d1)
            //          │  └─ 60(d0)
            //          └─ 40(d0)
            assertStructureEquals(
                    roots(n(20), n(30, n(50, n(60)), n(40))),
                    h.getHead());
        }

        @Test
        @DisplayName("T8: {B₀(70),B₁(50→60),B₂(10→…)} → extractMin → 10, {B₁(20→70), B₂(30→[50→60,40])}")
        void t8_B0B1B2() {
            BinomialHeap h = heapB0B1B2(); // B₀(70) → B₁(50→60) → B₂(10→[30(→40),20])
            assertEquals(10, h.extractMin());

            // 20(d1) → 30(d2)
            // └─ 70    ├─ 50(d1)
            //          │  └─ 60(d0)
            //          └─ 40(d0)
            assertStructureEquals(
                    roots(n(20, n(70)), n(30, n(50, n(60)), n(40))),
                    h.getHead());
        }
    }
}
