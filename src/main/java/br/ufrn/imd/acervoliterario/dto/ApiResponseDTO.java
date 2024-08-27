package br.ufrn.imd.acervoliterario.dto;
/**
 * Classe para encapsular respostas de APIs.
 * Pode ser usada para padronizar as respostas de sucesso e erro em todas as
 * operações de uma API, incluindo mensagens e dados adicionais.
 *
 * @author Gabriel Ribeiro
 * @version 1.0
 *
 * @param <DTO> O tipo de dado retornado na resposta, que pode ser um DTO ou qualquer outro objeto.
 */
public class ApiResponseDTO<DTO> {
    private boolean success;
    private String message;
    private DTO data;

    private DTO error;

    /**
     * Construtor completo para ApiResponseDTO.
     *
     * @param success Indica se a operação foi bem-sucedida.
     * @param message Mensagem descritiva da operação.
     * @param data    Dados retornados em caso de sucesso.
     * @param error   Informações de erro em caso de falha.
     */
    public ApiResponseDTO(boolean success, String message, DTO data, DTO error) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    /**
     * Construtor vazio para ApiResponseDTO.
     */
    public ApiResponseDTO() {
    }

    /**
     * Verifica se a operação foi bem-sucedida.
     *
     * @return true se a operação foi bem-sucedida, false caso contrário.
     */
    public boolean isSuccess() {
        return success;
    }
    /**
     * Define o status de sucesso da operação.
     *
     * @param success true se a operação foi bem-sucedida, false caso contrário.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Obtém a mensagem descritiva da operação.
     *
     * @return Mensagem da operação.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define a mensagem descritiva da operação.
     *
     * @param message Mensagem da operação.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Obtém os dados retornados em caso de sucesso.
     *
     * @return Dados de sucesso.
     */
    public DTO getData() {
        return data;
    }

    /**
     * Define os dados retornados em caso de sucesso.
     *
     * @param data Dados de sucesso.
     */
    public void setData(DTO data) {
        this.data = data;
    }

    /**
     * Obtém as informações de erro em caso de falha.
     *
     * @return Informações de erro.
     */
    public DTO getError() {
        return error;
    }

    /**
     * Define as informações de erro em caso de falha.
     *
     * @param error Informações de erro.
     */
    public void setError(DTO error) {
        this.error = error;
    }
}