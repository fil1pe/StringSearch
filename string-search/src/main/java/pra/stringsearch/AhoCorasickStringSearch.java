package pra.stringsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Aho-Corasick strategy for finding substring
 */
public class AhoCorasickStringSearch extends StringSearchStrategy {

    private final Tree statesTree;

    public AhoCorasickStringSearch(String pattern) {
        super(pattern);

        statesTree = new Tree();
        statesTree.addNode(pattern);
        makeLink();
    }

    private void makeLink() {
        String pattern = getPattern();

        statesTree.root.link = statesTree.root;

        for (Node n : statesTree.root.children) {
            n.link = statesTree.root;
        }

        while (pattern.length() > 0) {
            Node n = statesTree.getNode(pattern);
            int ctrl = 1;
            String subpattern = pattern.substring(1);
            while (subpattern.length() > 0) {
                Node n1 = statesTree.getNode(subpattern);
                if (n1 != null) {
                    n.link = n1;
                    break;
                } else {
                    n.link = statesTree.root;
                }
                subpattern = pattern.substring(++ctrl);
            }

            pattern = pattern.substring(0, pattern.length() - 1);
        }
    }

    public int find(String content, int begin) {
        Node n = statesTree.root;

        for (int i = begin; i < content.length(); i++) {
            if (n.isTerminal) {
                return i - n.word.length() + 1;
            }

            Node n1 = n.getChild(content.charAt(i));
            if (n1 == null) {
                n = n.link;
                continue;
            }

            n = n1;
        }

        return -1;
    }

    private class Tree {

        private final Node root;
        private int size = 0;

        private Tree() {
            root = new Node('.', false, size++);
        }

        private Node getNode(String s) {
            Node n = root;

            for (int i = 0; i < s.length(); i++) {
                n = n.getChild(s.charAt(i));
                if (n == null) {
                    return null;
                }
            }

            return n;
        }

        private void addNode(String s) {
            Node n = root;
            int ctrl = 0;

            for (; ctrl < s.length(); ctrl++) {
                if (n.getChild(s.charAt(ctrl)) == null) {
                    break;
                }
                n = n.getChild(s.charAt(ctrl));
            }

            if (ctrl == s.length()) {
                n.setTerminal();
                return;
            }

            for (; ctrl < s.length(); ctrl++) {
                Node newNode = new Node(s.charAt(ctrl), false, size++);
                n.addChild(newNode);
                n = n.getChild(s.charAt(ctrl));
            }

            n.setTerminal();
        }

    }

    private class Node {

        private final int state;
        private final char c;
        private String word;
        private final List<Node> children;
        private boolean isTerminal = false;
        private Node link;
        private Node parent;

        Node(char c, boolean isTerminal, int state) {
            this.c = c;
            word = new String();
            word = "" + c;
            this.children = new ArrayList();
            this.isTerminal = isTerminal;
            this.state = state;
            link = null;
        }

        void addChild(Node n) {
            n.parent = this;
            n.word = this.word + n.word;
            this.children.add(n);
        }

        Node getChild(char c) {
            for (Node n : this.children) {
                if (n.c == c) {
                    return n;
                }
            }

            return null;
        }

        void setTerminal() {
            this.isTerminal = true;
        }
    }

}
