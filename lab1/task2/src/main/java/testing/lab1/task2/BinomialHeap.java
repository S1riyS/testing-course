package testing.lab1.task2;

import java.util.NoSuchElementException;

public class BinomialHeap {

    public static class Node {
        public int key;
        public int degree;
        public Node parent;
        public Node child;
        public Node sibling;

        public Node(int key) {
            this.key = key;
            this.degree = 0;
        }
    }

    private Node head;
    private final TraversalTracer tracer;

    private void trace(TracePoint point) {
        if (tracer != null)
            tracer.hit(point);
    }

    public BinomialHeap() {
        this(null);
    }

    public BinomialHeap(TraversalTracer tracer) {
        this.head = null;
        this.tracer = tracer;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insert(int key) {
        trace(TracePoint.INSERT);
        Node x = new Node(key);
        // Wrap the new node in a temporary single-element heap and merge
        BinomialHeap H = new BinomialHeap(tracer);
        H.head = x;
        head = merge(head, H.head);
    }

    public int getMin() {
        trace(TracePoint.GET_MIN);
        if (head == null)
            throw new NoSuchElementException("Heap is empty");
        Node min = findMinNode();
        return min.key;
    }

    public int extractMin() {
        trace(TracePoint.EXTRACT_MIN);
        if (head == null)
            throw new NoSuchElementException("Heap is empty");

        Node min = findMinNode();
        head = removeFromList(head, min);
        if (min.child != null) {
            // Children are stored in decreasing degree order; reverse to get increasing
            Node reversed = reverseList(min.child);
            min.child = null; // detach before merge to avoid cycles
            head = merge(head, reversed);
        }

        return min.key;
    }

    private Node findMinNode() {
        Node min = head;
        Node current = head;
        while (current != null) {
            if (current.key < min.key)
                min = current;
            current = current.sibling;
        }
        return min;
    }

    private Node removeFromList(Node list, Node toRemove) {
        if (list == toRemove)
            return list.sibling;
        Node prev = null;
        Node curr = list;
        while (curr != null && curr != toRemove) {
            prev = curr;
            curr = curr.sibling;
        }
        if (curr != null && prev != null) {
            prev.sibling = curr.sibling;
        }
        return list;
    }

    private Node merge(Node h1, Node h2) {
        trace(TracePoint.MERGE);
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;
        Node merged = mergeLists(h1, h2);
        return consolidate(merged);
    }

    // Merges two root lists sorted by degree,
    // without consolidation
    private Node mergeLists(Node x, Node y) {
        if (x == null)
            return y;
        if (y == null)
            return x;
        trace(TracePoint.MERGE_LISTS);
        if (x.degree < y.degree) {
            x.sibling = mergeLists(x.sibling, y);
            return x;
        }
        if (y.degree < x.degree) {
            y.sibling = mergeLists(x, y.sibling);
            return y;
        }
        // Equal degrees: merge both pairs simultaneously, attach combined to the rest
        Node rest = mergeLists(x.sibling, y.sibling);
        Node combined = combineTrees(x, y);
        combined.sibling = rest;
        return combined;
    }

    // Links two Bₖ trees into one B{k+1};
    // The node with the smaller key becomes the root
    private Node combineTrees(Node a, Node b) {
        if (a.key > b.key) {
            Node t = a;
            a = b;
            b = t;
        }
        // b becomes the leftmost child of a (prepend to child list)
        b.sibling = a.child;
        a.child = b;
        b.parent = a;
        a.degree++;
        return a;
    }

    private Node consolidate(Node head) {
        if (head == null || head.sibling == null)
            return head;

        trace(TracePoint.CONSOLIDATE);
        Node prev = null;
        Node curr = head;
        Node next = curr.sibling;
        while (next != null) {
            boolean noDegreeConflict = curr.degree != next.degree;
            // Three consecutive trees of the same degree: merge the latter pair first
            boolean hasTripleConflict = next.sibling != null && next.sibling.degree == curr.degree;
            if (noDegreeConflict || hasTripleConflict) {
                prev = curr;
                curr = next;
                next = curr.sibling;
            } else if (curr.key <= next.key) {
                // curr wins: absorb next into curr, keep curr in place
                curr.sibling = next.sibling;
                combineTrees(curr, next);
                next = curr.sibling;
            } else {
                // next wins: splice it before curr in the list, then absorb curr
                if (prev == null)
                    head = next;
                else
                    prev.sibling = next;
                combineTrees(next, curr);
                curr = next;
                next = curr.sibling;
            }
        }
        return head;
    }

    private Node reverseList(Node first) {
        trace(TracePoint.REVERSE);
        Node prev = null;
        Node curr = first;
        while (curr != null) {
            Node next = curr.sibling;
            curr.sibling = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
