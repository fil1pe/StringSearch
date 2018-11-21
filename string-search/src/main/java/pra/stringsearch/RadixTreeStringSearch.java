package pra.stringsearch;

import java.util.ArrayList;
import java.util.List;

public class RadixTreeStringSearch extends StringSearchStrategy {

    private final Tree statesTree;
    private int minPatternLength = Integer.MAX_VALUE, maxPatternLength = 0;
    private final char auxChar;

    public RadixTreeStringSearch(String pattern) {
        super(pattern);

        statesTree = new Tree();

        auxChar = pattern.charAt(0) == (char) 255 ? (char) 254 : (char) (pattern.charAt(0) + 1);

        assemble(new String[]{pattern, auxChar + pattern});

    }

    private void assemble(String[] patterns) {

        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].length() > statesTree.maxPatternLength) {
                statesTree.maxPatternLength = patterns[i].length();
            }

            if (patterns[i].length() < statesTree.minPatternLength) {
                statesTree.minPatternLength = patterns[i].length();
            }

            statesTree.addNode(patterns[i]);
        }

        for (String s : patterns) {
            if (s.length() > maxPatternLength) {
                maxPatternLength = s.length();
            }

            if (s.length() < minPatternLength) {
                minPatternLength = s.length();
            }

            Node n = statesTree.getNode(s);

            while (!n.equals(statesTree.root)) {
                n = n.parent;
                if (n.isTerminal) {
                    continue;
                }

                if (n.children.size() == 1) {
                    Node child = n.children.get(0);

                    n.setInput(child.input);

                    for (Node p : child.children) {
                        n.simplyAddChild(p);
                    }

                    n.children.remove(child);
                    n.setTerminal();
                }
            }

        }

    }

    public int find(String content, int begin) {

        ArrayList<String> texts = new ArrayList();
        texts.add(content);

        int end = statesTree.minPatternLength;

        for (String t : texts) {
            while (begin < t.length() && end < t.length()) {

                String substr = t.substring(begin, end);
                Node n = statesTree.getNode(substr);

                if ((n == null || !n.isTerminal)) {
                    if (end - begin < statesTree.maxPatternLength) {
                        end++;

                    } else {
                        begin++;
                        end = begin + statesTree.minPatternLength;
                    }
                } else {
                    if (t.charAt(begin) == auxChar) {
                        begin++;
                    }
                    return begin;
                }

            }
            begin = 0;
            end = statesTree.minPatternLength;
        }

        return -1;
    }

    private class Tree {

        private final Node root;
        private int minPatternLength = Integer.MAX_VALUE;
        private int maxPatternLength = 0;
        private int size = 0;

        public Tree() {
            root = new Node(".", false, size++);
        }

        public Node getNode(String s) {
            Node n = root;
            int ctrl = 0, ctrlf = 1;
            String word = s.substring(ctrl, ctrlf);

            while (word.length() <= s.length()) {

                if (n.getChild(word) == null) {
                    ctrlf++;
                    if (ctrlf <= s.length()) {
                        word = s.substring(ctrl, ctrlf);
                    } else {
                        return null;
                    }
                } else {
                    n = n.getChild(word);
                    ctrl += word.length();

                    if (ctrl == s.length()) {
                        break;
                    }

                    word = s.substring(ctrl, ++ctrlf);
                }

            }

            if (n.word.substring(1).compareTo(s) != 0) {
                return null;
            }

            return n;
        }

        public void addNode(String s) {
            Node n = root;
            int ctrl = 0;

            for (; ctrl < s.length(); ctrl++) {

                if (n.getChild("" + s.charAt(ctrl)) == null) {
                    break;
                }

                n = n.getChild("" + s.charAt(ctrl));
            }

            if (ctrl == s.length()) {
                n.setTerminal();
                return;
            }

            for (; ctrl < s.length(); ctrl++) {
                Node newNode = new Node("" + s.charAt(ctrl), false, size++);
                n.addChild(newNode);
                n = n.getChild("" + s.charAt(ctrl));
            }
            n.setTerminal();
        }

    }

    private class Node {

        private final int state;
        private String input;
        private String word;
        private List<Node> children;
        private boolean isTerminal = false;
        private final Node link;
        private Node parent;

        public Node(String input, boolean isTerminal, int state) {
            this.input = input;
            word = new String();
            word = "" + input;
            this.children = new ArrayList();
            this.isTerminal = isTerminal;
            this.state = state;
            link = null;
        }

        public boolean compare(String word) {
            String newStr = this.word.substring(1);

            if (newStr.length() != word.length()) {
                return false;
            }

            for (int i = 0; i < newStr.length(); i++) {
                if (newStr.charAt(i) != word.charAt(i)) {
                    return false;
                }
            }

            return true;
        }

        public void setInput(String newChar) {
            this.input += newChar;
            this.word += newChar;
        }

        public void addChild(Node n) {
            n.parent = this;
            n.word = this.word + n.word;
            this.children.add(n);
        }

        public void simplyAddChild(Node n) {
            n.parent = this;
            this.children.add(n);
        }

        public Node getChild(String c) {
            for (Node n : this.children) {
                if (n.input.compareTo(c) == 0) {
                    return n;
                }
            }

            return null;
        }

        public void setTerminal() {
            this.isTerminal = true;
        }

    }

}
