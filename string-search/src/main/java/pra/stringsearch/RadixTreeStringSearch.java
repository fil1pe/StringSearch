package pra.stringsearch;

import java.util.ArrayList;
import java.util.List;

public class RadixTreeStringSearch extends StringSearchStrategy {

    private List<String> texto;
    private final Tree estados;
    private int palavraMin = 999, palavraMax = 0;

    public RadixTreeStringSearch(String padrao) {
        super(padrao);

        estados = new Tree();

        String[] vetor = {padrao, padrao};

        montador(vetor);

    }

    private void montador(String[] palavras) {

        for (int i = 0; i < palavras.length; i++) {
            if (palavras[i].length() > estados.getTamPalavraMax()) {
                estados.setTamPalavraMax(palavras[i].length());
            }

            if (palavras[i].length() < estados.getTamPalavraMin()) {
                estados.setTamPalavraMin(palavras[i].length());
            }

            estados.addNoh1(palavras[i]);
        }

        //Noh n = estados.getRaiz();
        for (String s : palavras) {
            if (s.length() > palavraMax) {
                palavraMax = s.length();
            }

            if (s.length() < palavraMin) {
                palavraMin = s.length();
            }

            Node n = estados.percorrer(s);

            while (!n.equals(estados.getRaiz())) {

                n = n.getPai();//! 
                if (n.ehTerminal()) {
                    continue;
                }

                if (n.getVizinhos().size() == 1) {
                    Node filho = n.getVizinhos().get(0);

                    n.setEntrada(filho.getEntrada());

                    for (Node p : filho.getVizinhos()) {
                        n.addFilhoSC(p);
                    }

                    n.getVizinhos().remove(filho);
                    n.setTerminal();
                }
            }

        }

    }

    public int find(String conteudo, int inicio) {

        ArrayList<String> arquivos = new ArrayList();
        arquivos.add(conteudo);

        int linha = 1, cont = 0, fim = estados.getTamPalavraMin();

        for (String arquivo : arquivos) {
            while (inicio < arquivo.length() && fim < arquivo.length()) {

                String palavra = arquivo.substring(inicio, fim);//; System.out.println(">>>"+palavra);
                Node n = estados.percorrer(palavra);

                if ((n == null || !n.ehTerminal())) {
                    if (fim - inicio < estados.getTamPalavraMax()) {
                        fim++;

                    } else {
                        inicio++;
                        fim = inicio + estados.getTamPalavraMin();
                    }
                } else {
                    //System.out.println("Encontrado: " + n.getPalavra() + " linha: " + linha + " coluna: " + inicio);
                    return inicio;
                }

            }
            inicio = 0;
            fim = estados.getTamPalavraMin();
            linha++;
        }

        return -1;
    }

    private class Tree {

        private Node root;
        private int minPatternLength = 1000;
        private int maxPatternLength = 0;
        private int size = 0;

        public Tree() {
            root = new Node(".", false, size++);
        }

        public Node percorrer(String s) {

            Node n = root;
            int controleI = 0, controleF = 1;
            String palavra = s.substring(controleI, controleF);

            while (palavra.length() <= s.length()) {

                if (n.getFilho(palavra) == null) {
                    controleF++;
                    if (controleF <= s.length()) {
                        palavra = s.substring(controleI, controleF);
                    } else {
                        return null;
                    }
                } else {
                    n = n.getFilho(palavra);
                    controleI += palavra.length();

                    if (controleI == s.length()) {
                        break;
                    }

                    palavra = s.substring(controleI, ++controleF);

                }

            }

            if (n.getPalavra().substring(1).compareTo(s) != 0)//comecando em 1 pq tem um ponto na raiz...
            {
                return null;
            }

            return n;
        }

        public void setTamPalavraMin(int t) {
            this.minPatternLength = t;
        }

        public void setTamPalavraMax(int t) {
            this.maxPatternLength = t;
        }

        public int getTamPalavraMax() {
            return this.maxPatternLength;
        }

        public int getTamPalavraMin() {
            return this.minPatternLength;
        }

        public void addNoh1(String s) {
            Node n = root;
            int controle = 0;

            for (; controle < s.length(); controle++) {

                if (n.getFilho("" + s.charAt(controle)) == null) {
                    break;
                }

                n = n.getFilho("" + s.charAt(controle));
            }

            if (controle == s.length()) {
                n.setTerminal();
                return;
            }

            for (; controle < s.length(); controle++) {
                Node novo = new Node("" + s.charAt(controle), false, size++);
                n.addFilho(novo);
                n = n.getFilho("" + s.charAt(controle));
            }
            n.setTerminal();
        }

        public Node getRaiz() {
            return this.root;
        }

    }

    private class Node {

        private int estado;
        private String entrada;
        private String palavra;
        private List<Node> filhos;
        private boolean ehTerminal = false;
        private Node link;
        private Node pai;

        public Node(String entrada, boolean ehTerminal, int state) {
            this.entrada = entrada;
            palavra = new String();
            palavra = "" + entrada;
            this.filhos = new ArrayList();
            this.ehTerminal = ehTerminal;
            this.estado = state;
            link = null;
        }

        public boolean compare(String outraPalavra) {

            String nova = this.palavra.substring(1);

            if (nova.length() != outraPalavra.length()) {
                //System.out.println("F: " + nova.length() + "!=" + outraPalavra.length());
                return false;
            }

            for (int i = 0; i < nova.length(); i++) {
                if (nova.charAt(i) != outraPalavra.charAt(i)) {
                    return false;//diferentes
                }
            }

            //System.out.println("Y");
            return true;//iguais
        }

        public String getPalavra() {
            return this.palavra;
        }

        public int getEstado() {
            return this.estado;
        }

        public void setEntrada(String novaEntrada) {
            this.entrada += novaEntrada;
            this.palavra += novaEntrada;

        }

        public String getEntrada() {
            return this.entrada;
        }

        public void setPai(Node noh) {
            this.pai = noh;
        }

        public Node getPai() {
            return this.pai;
        }

        public void addFilho(Node noh) {
            noh.setPai(this);
            noh.palavra = this.palavra + noh.palavra;
            this.filhos.add(noh);
            //System.out.println(noh.getEntrada()+":"+noh.palavra+" <"+this.getEntrada()+">;");//p/ verificar arvore
        }

        public void addFilhoSC(Node noh) {//adicionar filho sem calculo da palavra ou entrada
            noh.setPai(this);
            this.filhos.add(noh);
        }

        public Node getFilho(String letra) {
            for (Node n : this.filhos) {
                if (n.getEntrada().compareTo(letra) == 0) {
                    return n;
                }
            }

            return null;
        }

        public List<Node> getVizinhos() {
            return this.filhos;
        }

        public Node getLink() {
            return this.link;
        }

        public boolean ehTerminal() {
            return this.ehTerminal;
        }

        public void setTerminal() {
            this.ehTerminal = true;
        }

        public void setLink(Node n) {
            this.link = n;
        }
    }
}
