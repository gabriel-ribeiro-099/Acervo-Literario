package br.ufrn.imd.acervoliterario.model.builder;

import br.ufrn.imd.acervoliterario.model.Book;

/**
 * Builder para a classe Book. Utilizado para criar instâncias de Paper
 * de forma mais flexível e controlada.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
public class BookBuilder {

    private long id;
    private String nome;
    private String autor;
    private String areaConhecimento;
    private String fileName;
    private String path;
    private Long size;
    private String extensionType;
    private String anoPublicacao;
    private String edicao;
    private String editora;
    private String ISNB;

    /**
     * Define o ID do livro.
     *
     * @param id ID do livro.
     * @return Instância atual do builder.
     */
    public BookBuilder id(long id) {
        this.id = id;
        return this;
    }

    /**
     * Define o nome do livro.
     *
     * @param nome Nome do livro.
     * @return Instância atual do builder.
     */
    public BookBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Define o autor do livro.
     *
     * @param autor Autor do livro.
     * @return Instância atual do builder.
     */
    public BookBuilder autor(String autor) {
        this.autor = autor;
        return this;
    }

    /**
     * Define a área de conhecimento do livro.
     *
     * @param areaConhecimento Área de conhecimento do livro.
     * @return Instância atual do builder.
     */
    public BookBuilder areaConhecimento(String areaConhecimento) {
        this.areaConhecimento = areaConhecimento;
        return this;
    }

    /**
     * Define o nome do arquivo do livro.
     *
     * @param fileName Nome do arquivo.
     * @return Instância atual do builder.
     */
    public BookBuilder fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Define o caminho do arquivo do livro.
     *
     * @param path Caminho do arquivo.
     * @return Instância atual do builder.
     */
    public BookBuilder path(String path) {
        this.path = path;
        return this;
    }

    /**
     * Define o tamanho do arquivo do livro.
     *
     * @param size Tamanho do arquivo em bytes.
     * @return Instância atual do builder.
     */
    public BookBuilder size(Long size) {
        this.size = size;
        return this;
    }

    /**
     * Define o tipo de extensão do arquivo do livro.
     *
     * @param extensionType Tipo de extensão do arquivo.
     * @return Instância atual do builder.
     */
    public BookBuilder extensionType(String extensionType) {
        this.extensionType = extensionType;
        return this;
    }

    /**
     * Define o ano de publicação do livro.
     *
     * @param anoPublicacao Ano de publicação.
     * @return Instância atual do builder.
     */
    public BookBuilder anoPublicacao(String anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
        return this;
    }

    /**
     * Define a edição do livro.
     *
     * @param edicao Edição do livro.
     * @return Instância atual do builder.
     */
    public BookBuilder edicao(String edicao) {
        this.edicao = edicao;
        return this;
    }

    /**
     * Define a editora do livro.
     *
     * @param editora Editora do livro.
     * @return Instância atual do builder.
     */
    public BookBuilder editora(String editora) {
        this.editora = editora;
        return this;
    }

    /**
     * Define o ISBN do livro.
     *
     * @param ISNB ISBN do livro.
     * @return Instância atual do builder.
     */
    public BookBuilder ISBN(String ISNB) {
        this.ISNB = ISNB;
        return this;
    }

    /**
     * Constrói e retorna uma nova instância de Book com os valores definidos.
     *
     * @return Uma nova instância de Book.
     */
    public Book build() {
        Book book = new Book();
        book.setEdicao(edicao);
        book.setEditora(editora);
        book.setISBN(ISNB);
        book.setAnoPublicacao(anoPublicacao);
        book.setId(id);
        book.setAreaConhecimento(areaConhecimento);
        book.setAutor(autor);
        book.setExtensionType(extensionType);
        book.setFileName(fileName);
        book.setNome(nome);
        book.setPath(path);
        book.setSize(size);
        return book;
    }
}
