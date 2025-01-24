package br.com.alura.screenmatch.Excesao;

public class ErroDeConversaoDeAno extends RuntimeException {
    private  String menssagem;
    public ErroDeConversaoDeAno(String mensssagem) {
        this.menssagem = mensssagem;

    }

    public String getmenssagem() {
        return this.menssagem;
    }
}
