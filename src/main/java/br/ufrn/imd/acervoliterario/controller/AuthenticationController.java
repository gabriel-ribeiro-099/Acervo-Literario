package br.ufrn.imd.acervoliterario.controller;

import br.ufrn.imd.acervoliterario.utils.exception.InvalidCredentialsException;
import br.ufrn.imd.acervoliterario.utils.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.ufrn.imd.acervoliterario.dto.ApiResponseDTO;
import br.ufrn.imd.acervoliterario.dto.AuthRequest;
import br.ufrn.imd.acervoliterario.dto.AuthResponse;
import br.ufrn.imd.acervoliterario.service.AuthenticationService;

/**
 * Controlador responsável pela autenticação dos usuários.
 * Este controlador expõe endpoints relacionados ao processo de autenticação.
 *
 * @author Nicole Nogueira
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Construtor da classe AuthenticationController.
     *
     * @param authenticationService Serviço responsável pela lógica de autenticação dos usuários.
     */
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint para autenticar o usuário.
     * Recebe uma requisição contendo as credenciais do usuário e retorna uma resposta com o status e dados da autenticação.
     *
     * @param request Objeto AuthRequest contendo as credenciais do usuário (usuário e senha).
     * @return Objeto ResponseEntity contendo um ApiResponseDTO com o resultado da autenticação.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponseDTO<AuthResponse>> authenticate(
            @RequestBody AuthRequest request) {
        try {
            AuthResponse authResponse = authenticationService.authenticate(request);
            return ResponseEntity.ok(
                    new ApiResponseDTO<>(true, "Authentication completed successfully", authResponse, null));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponseDTO<>(false, "Invalid username or password", null, null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>(false, "User not found", null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "An unexpected error occurred", null, null));
        }
    }


}