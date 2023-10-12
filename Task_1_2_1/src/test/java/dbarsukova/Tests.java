package dbarsukova;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * task-1-2-1 test.
 */
public class Tests {

    @Test
    void test1() {
        Tree<String> firstTree = new Tree<>("A");
        firstTree.addChild("B");
        firstTree.addChild("C");
        assertEquals(firstTree.size(), 2);
    }

    @Test
    void test2() {
        Tree<String> firstTree = new Tree<>("A");
        firstTree.addChild("B");
        firstTree.addChild("C");
        ArrayList<String> list = new ArrayList<>();
        list.add("B");
        list.add("C");
        assertEquals(firstTree.descendantData(), list);
    }

    @Test
    void test3() {
        Tree<String> firstTree = new Tree<>();
        assertNull(firstTree.value());
        firstTree.addChild("A");
        assertEquals(firstTree.value(), "A");
    }

    @Test
    void test4() {
        Tree<String> firstTree = new Tree<>("A");
        Tree<String> secondTree = firstTree.addChild("B");
        assertEquals(secondTree.getParent(), firstTree);
    }

    @Test
    void test5() {
        Tree<String> treeA = new Tree<>("A");
        Tree<String> treeB = treeA.addChild("B");
        Tree<String> treeC = treeB.addChild("C");
        treeA.remove(treeC);
        assertEquals(treeB.size(), 0);
        treeA.remove(treeB);
        assertEquals(treeA.size(), 0);
    }

    @Test
    void test6() {
        Tree<String> firstTree = new Tree<>("R1");
        var a = firstTree.addChild("A");
        a.addChild("B");
        Tree<String> subtree1 = new Tree<>("R2");
        subtree1.addChild("C");
        subtree1.addChild("D");
        firstTree.addChild(subtree1);
        Tree<String> secondTree = new Tree<>("R1");
        Tree<String> subtree2 = new Tree<>("A");
        subtree2.addChild("B");
        secondTree.addChild(subtree2);
        var b = secondTree.addChild("R2");
        b.addChild("C");
        b.addChild("D");
        assert (firstTree.equals(secondTree));
        secondTree.remove(subtree2);
        assert (!firstTree.equals(secondTree));
    }

    @Test
    void test7() {
        Tree<String> firstTree = new Tree<>("R1");
        firstTree.addChild("A");
        Tree<String> secondTree = new Tree<>("R1");
        secondTree.addChild("B");
        assert (!firstTree.equals(secondTree));
    }

    @Test
    void test8() {
        Tree<String> treeA = new Tree<>("A");
        treeA.addChild("B");
        treeA.addChild("C");
        Tree<String> treeD = treeA.addChild("D");
        Tree<String> treeE = treeD.addChild("E");
        treeD.addChild("F");
        treeD.addChild("G");
        treeE.addChild("H");
        treeE.addChild("K");
        String[] array = {"A", "B", "C", "D", "E", "F", "G", "H", "K"};
        Iterator<String> iter = treeA.iterator(Tree.dfsOrBfs.BFS);
        for (String s : array) {
            assertTrue(iter.hasNext());
            assertEquals(iter.next(), s);
        }
    }

    @Test
    void test9() {
        Tree<Integer> treeA = new Tree<>(4);
        treeA.addChild(2);
        treeA.addChild(3);
        treeA.addChild(10);
        Tree<Integer> treeD = treeA.addChild(7);
        Tree<Integer> treeE = treeD.addChild(5);
        treeD.addChild(1);
        treeD.addChild(6);
        treeE.addChild(8);
        treeE.addChild(9);
        Integer[] array = {4, 2, 3, 10, 7, 5, 8, 9, 1, 6};
        Iterator<Integer> iterator = treeA.iterator(Tree.dfsOrBfs.DFS);
        for (Integer s : array) {
            assertTrue(iterator.hasNext());
            assertEquals(iterator.next(), s);
        }
    }

    @Test
    void test10() {
        Tree<String> firstTree = new Tree<>("A");
        firstTree.addChild("B");
        firstTree.addChild("C");
        Iterator<String> iterator = firstTree.iterator(Tree.dfsOrBfs.DFS);
        firstTree.addChild("D");
        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}