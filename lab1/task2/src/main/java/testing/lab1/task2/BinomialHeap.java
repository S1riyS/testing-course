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

    public BinomialHeap() {
        this.head = null;
    }

    Node getHead() {
        return head;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insert(int key) {
        Node x = new Node(key);
        BinomialHeap H = new BinomialHeap();
        H.head = x;
        head = merge(head, H.head);
    }

    public int getMin() {
        if (head == null)
            throw new NoSuchElementException("Heap is empty");
        Node min = findMinNode();
        return min.key;
    }

    public int extractMin() {
        if (head == null)
            throw new NoSuchElementException("Heap is empty");
        Node min = findMinNode();
        head = removeFromList(head, min);
        if (min.child != null) {
            Node reversed = reverseList(min.child);
            min.child = null;
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
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;
        Node merged = mergeLists(h1, h2);
        return consolidate(merged);
    }

    private Node mergeLists(Node x, Node y) {
        if (x == null)
            return y;
        if (y == null)
            return x;
        if (x.degree < y.degree) {
            x.sibling = mergeLists(x.sibling, y);
            return x;
        }
        if (y.degree < x.degree) {
            y.sibling = mergeLists(x, y.sibling);
            return y;
        }
        Node rest = mergeLists(x.sibling, y.sibling);
        Node combined = combineTrees(x, y);
        combined.sibling = rest;
        return combined;
    }

    private Node combineTrees(Node a, Node b) {
        if (a.key > b.key) {
            Node t = a;
            a = b;
            b = t;
        }
        b.sibling = a.child;
        a.child = b;
        b.parent = a;
        a.degree++;
        return a;
    }

    private Node consolidate(Node head) {
        if (head == null || head.sibling == null)
            return head;

        Node prev = null;
        Node curr = head;
        Node next = curr.sibling;
        while (next != null) {
            boolean noDegreeConflict = curr.degree != next.degree;
            boolean hasTripleConflict = next.sibling != null && next.sibling.degree == curr.degree;
            if (noDegreeConflict || hasTripleConflict) {
                prev = curr;
                curr = next;
                next = curr.sibling;
            } else if (curr.key <= next.key) {
                curr.sibling = next.sibling;
                combineTrees(curr, next);
                next = curr.sibling;
            } else {
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
