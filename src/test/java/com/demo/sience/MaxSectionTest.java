package com.demo.sience;

import java.util.Stack;

/**
 * @author wangqing
 */
public class MaxSectionTest {

    /**
     *                   1
     *              10       12
     *            9   13  11
     * @param args
     */
    public static void main(String[] args) {
        Tree root = new Tree();
        Tree left = new Tree(10, new Tree(9, null , null), new Tree(13, null, null));
        Tree right = new Tree(12, new Tree(11, null, null), null);
        root.setValue(1);
        root.setLeft(left);
        root.setRight(right);
//        pre(root);

        post(root);

    }

    private static void pre(Tree root) {
        if (root == null) {
            return;
        }
        Stack<Tree> stack = new Stack();
        stack.push(root);
        while (!stack.isEmpty()) {
            Tree item = stack.pop();
            System.out.println(getValue(item));
            if (item.getRight() != null) {
                stack.push(item.getRight());
            }
            if (item.getLeft() != null) {
                stack.push(item.getLeft());
            }
        }
    }

    private static void post(Tree root) {
        if (root == null) {
            return;
        }
        post(root.getLeft());
        post(root.getRight());
        System.out.println(root.getValue());
    }

    private static Integer getValue(Tree tree) {
        if (tree == null) {
            return null;
        }
        return tree.getValue();
    }


    private static class Tree {
        private int value;
        private Tree left;
        private Tree right;

        public Tree() {
        }

        public Tree(int value, Tree left, Tree right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Tree getLeft() {
            return left;
        }

        public void setLeft(Tree left) {
            this.left = left;
        }

        public Tree getRight() {
            return right;
        }

        public void setRight(Tree right) {
            this.right = right;
        }
    }
}
