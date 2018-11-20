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

        String[] vetor = {padrao, "."};

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

            Noh1 n = estados.percorrer(s);

            while (!n.equals(estados.getRaiz())) {

                n = n.getPai();//! 
                if (n.ehTerminal()) {
                    continue;
                }

                if (n.getVizinhos().size() == 1) {
                    Noh1 filho = n.getVizinhos().get(0);

                    n.setEntrada(filho.getEntrada());

                    for (Noh1 p : filho.getVizinhos()) {
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
                Noh1 n = estados.percorrer(palavra);

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

        private Noh1 raiz;
        private int tamPalavraMin = 1000;
        private int tamPalavraMax = 0;
        private int size = 0;

        public Tree() {
            raiz = new Noh1(".", false, size++);
        }

        public Noh1 percorrer(String s) {

            Noh1 n = raiz;
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
            this.tamPalavraMin = t;
        }

        public void setTamPalavraMax(int t) {
            this.tamPalavraMax = t;
        }

        public int getTamPalavraMax() {
            return this.tamPalavraMax;
        }

        public int getTamPalavraMin() {
            return this.tamPalavraMin;
        }

        public void addNoh1(String s) {
            Noh1 n = raiz;
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
                Noh1 novo = new Noh1("" + s.charAt(controle), false, size++);
                n.addFilho(novo);
                n = n.getFilho("" + s.charAt(controle));
            }
            n.setTerminal();
        }

        public Noh1 getRaiz() {
            return this.raiz;
        }

    }

    private class Noh1 {

        private int estado;
        private String entrada;
        private String palavra;
        private List<Noh1> filhos;
        private boolean ehTerminal = false;
        private Noh1 link;
        private Noh1 pai;

        public Noh1(String entrada, boolean ehTerminal, int state) {
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

        public void setPai(Noh1 noh) {
            this.pai = noh;
        }

        public Noh1 getPai() {
            return this.pai;
        }

        public void addFilho(Noh1 noh) {
            noh.setPai(this);
            noh.palavra = this.palavra + noh.palavra;
            this.filhos.add(noh);
            //System.out.println(noh.getEntrada()+":"+noh.palavra+" <"+this.getEntrada()+">;");//p/ verificar arvore
        }

        public void addFilhoSC(Noh1 noh) {//adicionar filho sem calculo da palavra ou entrada
            noh.setPai(this);
            this.filhos.add(noh);
        }

        public Noh1 getFilho(String letra) {
            for (Noh1 n : this.filhos) {
                if (n.getEntrada().compareTo(letra) == 0) {
                    return n;
                }
            }

            return null;
        }

        public List<Noh1> getVizinhos() {
            return this.filhos;
        }

        public Noh1 getLink() {
            return this.link;
        }

        public boolean ehTerminal() {
            return this.ehTerminal;
        }

        public void setTerminal() {
            this.ehTerminal = true;
        }

        public void setLink(Noh1 n) {
            this.link = n;
        }
    }
}
