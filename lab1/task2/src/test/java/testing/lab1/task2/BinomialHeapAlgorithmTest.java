package testing.lab1.task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static testing.lab1.task2.TracePoint.*;

@DisplayName("Binomial Heap")
class BinomialHeapAlgorithmTest {

    private TraversalTracer tracer;
    private BinomialHeap heap;

    @BeforeEach
    void setUp() {
        tracer = new TraversalTracer();
        heap = new BinomialHeap(tracer);
    }

    @Test
    @DisplayName("Insert into empty heap")
    void insertIntoEmptyHeap_traceSequence() {
        heap.insert(10);

        List<TracePoint> expected = List.of(INSERT, MERGE);
        assertEquals(expected, tracer.getSequence());
    }

    @Test
    @DisplayName("Merge of two T0")
    void twoInserts_mergeTwoDegreeZeroTrees() {
        heap.insert(5);
        tracer.clear();

        heap.insert(10);

        List<TracePoint> expected = List.of(INSERT, MERGE, MERGE_LISTS);
        assertEquals(expected, tracer.getSequence());
    }

    @Test
    @DisplayName("Merge of T1 and T0")
    void threeInserts_123_traceSequence() {
        heap.insert(1);
        heap.insert(2);
        tracer.clear();

        heap.insert(3);

        List<TracePoint> expected = List.of(INSERT, MERGE, MERGE_LISTS, CONSOLIDATE);
        assertEquals(expected, tracer.getSequence());
    }

    @Test
    @DisplayName("getMin on heap with one element")
    void getMinSingleElement_traceSequence() {
        heap.insert(42);
        tracer.clear();

        int min = heap.getMin();

        assertEquals(42, min);
        List<TracePoint> expected = List.of(GET_MIN);
        assertEquals(expected, tracer.getSequence());
        assertEquals(heap.isEmpty(), false);
    }

    @Test
    @DisplayName("extractMin on heap with one element")
    void extractMinSingleElement_traceSequence() {
        heap.insert(7);
        tracer.clear();

        int min = heap.extractMin();

        assertEquals(7, min);
        List<TracePoint> expected = List.of(EXTRACT_MIN);
        assertEquals(expected, tracer.getSequence());
        assertEquals(heap.isEmpty(), true);
    }

    @Test
    @DisplayName("extractMinon heap with one element with reverse")
    void extractMinTwoElements_traceSequence() {
        heap.insert(1);
        heap.insert(2);
        tracer.clear();

        int min = heap.extractMin();

        assertEquals(1, min);
        List<TracePoint> expected = List.of(EXTRACT_MIN, REVERSE, MERGE);
        assertEquals(expected, tracer.getSequence());
    }

    @Test
    @DisplayName("Correctness of operations - several inserts and extractMins")
    void insertAndExtractMin_correctOrder() {
        BinomialHeap heap = new BinomialHeap();
        heap.insert(30);
        heap.insert(10);
        heap.insert(20);
        heap.insert(5);
        heap.insert(15);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(15, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(true, heap.isEmpty());
    }

    @Test
    @DisplayName("Exception when getMin on empty heap")
    void getMinEmptyHeap_throws() {
        assertThrows(NoSuchElementException.class, heap::getMin);
    }

    @Test
    @DisplayName("Exception when extractMin on empty heap")
    void extractMinEmptyHeap_throws() {
        assertThrows(NoSuchElementException.class, heap::extractMin);
    }
}
