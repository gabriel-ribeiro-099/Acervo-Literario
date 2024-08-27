package br.ufrn.imd.acervoliterario.model.builder;

/**
 * Builder para a classe Paper. Utilizado para criar instâncias de Paper
 * de forma mais flexível e controlada.
 *
 * @author Beatriz Santana
 * @version 1.0
 */
public class PaperBuilder {

    private long id;
    private String nome;
    private String autor;
    private String areaConhecimento;
    private String fileName;
    private String path;
    private Long size;
    private String extensionType;
    private String local;
    private String ano;

    /**
     * Define o ID do paper.
     *
     * @param id ID do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder id(long id) {
        this.id = id;
        return this;
    }

    /**
     * Define o nome do paper.
     *
     * @param nome Nome do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    /**
     * Define o autor do paper.
     *
     * @param autor Autor do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder autor(String autor){
        this.autor = autor;
        return this;
    }

    /**
     * Define a área de conhecimento do paper.
     *
     * @param areaConhecimento Área de conhecimento do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder areaConhecimento(String areaConhecimento){
        this.areaConhecimento = areaConhecimento;
        return this;
    }

    /**
     * Define o nome do arquivo do paper.
     *
     * @param fileName Nome do arquivo do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder fileName(String fileName){
        this.fileName = fileName;
        return this;
    }

    /**
     * Define o caminho do arquivo do paper.
     *
     * @param path Caminho do arquivo do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder path(String path){
        this.path = path;
        return this;
    }

    /**
     * Define o tamanho do arquivo do paper.
     *
     * @param size Tamanho do arquivo do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder size(Long size){
        this.size = size;
        return this;
    }

    /**
     * Define o tipo de extensão do arquivo do paper.
     *
     * @param extensionType Tipo de extensão do arquivo do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder extensionType(String extensionType){
        this.extensionType = extensionType;
        return this;
    }

    /**
     * Define o local de publicação ou apresentação do paper.
     *
     * @param local Local de publicação ou apresentação do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder local(String local){
        this.local = local;
        return this;
    }

    /**
     * Define o ano de publicação ou apresentação do paper.
     *
     * @param ano Ano de publicação ou apresentação do paper.
     * @return Instância atual do builder.
     */
    public PaperBuilder ano(String ano){
        this.ano = ano;
        return this;
    }

}
