package br.ufrn.imd.acervoliterario.model.builder;

/**
 * Builder para a classe FinalProject. Utilizado para criar instâncias de FinalProject
 * de forma mais flexível e controlada.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
public class FinalProjectBuilder {

    private long id;
    private String nome;
    private String autor;
    private String areaConhecimento;
    private String fileName;
    private String path;
    private Long size;
    private String extensionType;
    private String anoDefesa;
    private String curso;
    private String instituicao;
    private String orientador;

    /**
     * Define o ID do projeto final.
     *
     * @param id ID do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder id(long id) {
        this.id = id;
        return this;
    }

    /**
     * Define o nome do projeto final.
     *
     * @param nome Nome do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Define o autor do projeto final.
     *
     * @param autor Autor do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder autor(String autor){
        this.autor = autor;
        return this;
    }

    /**
     * Define a área de conhecimento do projeto final.
     *
     * @param areaConhecimento Área de conhecimento do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder areaConhecimento(String areaConhecimento){
        this.areaConhecimento = areaConhecimento;
        return this;
    }

    /**
     * Define o nome do arquivo do projeto final.
     *
     * @param fileName Nome do arquivo do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder fileName(String fileName){
        this.fileName = fileName;
        return this;
    }

    /**
     * Define o caminho do arquivo do projeto final.
     *
     * @param path Caminho do arquivo do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder path(String path){
        this.path = path;
        return this;
    }

    /**
     * Define o tamanho do arquivo do projeto final.
     *
     * @param size Tamanho do arquivo do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder size(Long size){
        this.size = size;
        return this;
    }

    /**
     * Define o tipo de extensão do arquivo do projeto final.
     *
     * @param extensionType Tipo de extensão do arquivo do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder extensionType(String extensionType){
        this.extensionType = extensionType;
        return this;
    }

    /**
     * Define o ano de defesa do projeto final.
     *
     * @param anoDefesa Ano de defesa do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder anoDefesa(String anoDefesa){
        this.anoDefesa = anoDefesa;
        return this;
    }

    /**
     * Define o curso ao qual o projeto final está associado.
     *
     * @param curso Curso ao qual o projeto final está associado.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder curso(String curso){
        this.curso = curso;
        return this;
    }

    /**
     * Define a instituição onde o projeto final foi defendido.
     *
     * @param instituicao Instituição onde o projeto final foi defendido.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder instituicao(String instituicao){
        this.instituicao = instituicao;
        return this;
    }

    /**
     * Define o orientador do projeto final.
     *
     * @param orientador Orientador do projeto final.
     * @return Instância atual do builder.
     */
    public FinalProjectBuilder orientador(String orientador){
        this.orientador = orientador;
        return this;
    }
}
