package dbarsukova;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;


/**
 * task-1-2-1 generic-class for a classic tree.
 */

public class Tree<T> implements Iterable<T> {
    private T data;
    private Tree<T> parent;
    private final ArrayList<Tree<T>> descendant;
    public int numberOfIterations;

    /**
     * type of crawl.
     */
    public enum type {
        DFS, BFS
    }

    /**
     * creating an empty tree.
     */
    public Tree() {
        this.data = null;
        this.parent = null;
        this.descendant = new ArrayList<>();
        this.numberOfIterations = 0;
    }

    /**
     * creating a tree with `data` value.
     *
     * @param data value in node.
     */
    public Tree(T data) {
        this.data = data;
        this.parent = null;
        this.descendant = new ArrayList<>();
        this.numberOfIterations = 0;
    }

    /**
     * returns size of tree.
     */
    public int size() {
        return descendant.size();
    }

    /**
     * value stored in `data` field.
     */
    public T value() {
        return this.data;
    }

    /**
     * establishing the current parent.
     *
     * @param parent parent.
     */
    public void setParent(Tree<T> parent) {
        this.parent = parent;
    }

    /**
     * returns a parent of current subtree.
     */
    public Tree<T> getParent() {
        return this.parent;
    }

    /**
     * returns a list of values of child nodes.
     */
    public ArrayList<T> descendantData() {
        ArrayList<T> data = new ArrayList<>();
        for (Tree<T> des : descendant) {
            data.add(des.value());
        }
        return data;
    }

    /**
     * list of child nodes.
     */
    public ArrayList<Tree<T>> getDescendant() {
        return this.descendant;
    }

    /**
     * adding node to descendants with value equal to `data`.
     * if tree does not contain values, method updates the value of `this.data`.
     *
     * @param data value that user wants to add as node.
     */
    public Tree<T> addChild(T data) {
        ++numberOfIterations;
        if (this.data != null) {
            Tree<T> bornDescendant = new Tree<>(data);
            bornDescendant.parent = this;
            descendant.add(bornDescendant);
            return bornDescendant;
        } else {
            this.data = data;
            return this;
        }
    }

    /**
     * adding subtree equal `subtree` to tree.
     *
     * @param subtree subtree witch user wants to add.
     */
    public void addChild(Tree<T> subtree) {
        ++numberOfIterations;
        this.descendant.add(subtree);
        subtree.setParent(this);
    }

    /**
     * deleting a subtree equal to `tree`.
     *
     * @param tree tree that user wants to delete.
     */
    public boolean remove(Tree<T> tree) {
        ++numberOfIterations;
        if (!descendant.contains(tree)) {
            for (Tree<T> tr : descendant) {
                if (tr.remove(tree)) {
                    return true;
                }
            }
            return false;
        } else {
            descendant.remove(tree);
            return true;
        }
    }

    /**
     * comparison of two trees.
     *
     * @param obj object with which user wants to compare.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tree<?> tree = (Tree<?>) obj;
        return Objects.equals(data, tree.data) && Objects.equals(descendant, tree.descendant);
    }

    /**
     * returns iterator with type of crawl.
     *
     * @param type type of crawl.
     */
    public Iterator<T> iterator(type type) {
        return new Iterate<>(this, type);
    }

    /**
     * DFS iterator for tree.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterate<>(this, type.DFS);
    }

    /**
     * Iterator class for Tree collection.
     *
     * @param <U> type of objects.
     */
    public static class Iterate<U> implements Iterator<U> {
        private final int numberOfIterations;
        private final Tree<U> root;
        private final ArrayList<Tree<U>> nodes;
        Tree.type type;

        /**
         * creating iterator, filling in list of nodes.
         *
         * @param tree iterated tree.
         * @param type of crawl.
         */
        public Iterate(Tree<U> tree, Tree.type type) {
            this.nodes = new ArrayList<>();
            this.type = type;
            nodes.add(tree);
            this.root = tree;
            this.numberOfIterations = tree.numberOfIterations;
        }

        /**
         * checking for presence of next node.
         */
        @Override
        public boolean hasNext() {
            if (root.numberOfIterations > this.numberOfIterations) {
                throw new ConcurrentModificationException();
            }
            return !nodes.isEmpty();
        }

        /**
         * returning next element.
         */
        @Override
        public U next() {
            if (root.numberOfIterations > this.numberOfIterations) {
                throw new ConcurrentModificationException();
            }
            if (type == Tree.type.DFS) {
                Tree<U> currentNode = nodes.get(0);
                nodes.remove(0);
                nodes.addAll(0, currentNode.getDescendant());
                return currentNode.value();
            } else {
                Tree<U> currentNode = nodes.get(0);
                nodes.remove(0);
                nodes.addAll(currentNode.getDescendant());
                return currentNode.value();
            }
        }
    }
}