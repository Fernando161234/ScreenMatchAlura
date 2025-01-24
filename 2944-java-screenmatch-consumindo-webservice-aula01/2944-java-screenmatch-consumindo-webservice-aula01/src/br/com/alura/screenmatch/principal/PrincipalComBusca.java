package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.Excesao.ErroDeConversaoDeAno;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOMDB;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        List<Titulo> tituloList = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (!busca.equalsIgnoreCase("sair")) {


            System.out.println("Digite um filme para busca: ");
            busca = leitura.nextLine();

            if (busca.equalsIgnoreCase("sair")) {
                break;
            }

            String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=2974a7c1";
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String jsonMsg = response.body();
                System.out.println(jsonMsg);

                //Titulo meuTitulo = gson.fromJson(jsonMsg, Titulo.class);
                TituloOMDB meuTituloOMDB = gson.fromJson(jsonMsg, TituloOMDB.class);
                System.out.println("titulo " + meuTituloOMDB);
                //try {
                Titulo meuTitulo = new Titulo(meuTituloOMDB);
                System.out.println(meuTitulo);

                tituloList.add(meuTitulo);
            } catch (NumberFormatException e) {
                System.out.println("aconteceu um erro 1");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("ocorreu um erro 2 ");
                System.out.println(e.getMessage());
            } catch (ErroDeConversaoDeAno e) {
                System.out.println(e.getmenssagem());
            }

        }

        System.out.println(tituloList);
        FileWriter escrita = new FileWriter("titulos.json");
        escrita.write(gson.toJson(tituloList));
        escrita.close();
    }
}
