package br.ufrn.imd.acervoliterario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal que inicializa a aplicação Spring Boot.
 * Esta classe é a entrada principal para a aplicação, configurando e iniciando o contexto do Spring Boot.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 */
@SpringBootApplication
public class AcervoLiterarioApplication {

    /**
     * Método principal que inicia a aplicação.
     *
     * @param args Argumentos da linha de comando passados para a aplicação.
     */
    public static void main(String[] args) {
        // Inicia a aplicação Spring Boot
        SpringApplication.run(AcervoLiterarioApplication.class, args);

        // Mensagem exibida no console quando a aplicação é iniciada
        System.out.println("Hello, World!");
    }
}
