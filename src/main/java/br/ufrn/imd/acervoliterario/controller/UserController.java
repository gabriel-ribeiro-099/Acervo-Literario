package br.ufrn.imd.acervoliterario.controller;

import br.ufrn.imd.acervoliterario.dto.*;
import br.ufrn.imd.acervoliterario.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import br.ufrn.imd.acervoliterario.model.User;
import br.ufrn.imd.acervoliterario.service.UserService;

/**
 * Controlador responsável pela gestão de usuários na aplicação.
 * Fornece endpoints para operações CRUD relacionadas a usuários,
 * incluindo registro, edição, exclusão e busca de informações de usuários.
 *
 * @author Nicole Nogueira
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/users")
public class UserController extends GenericController<User, UserDTO, UserService> {

    private final AuthenticationService authenticationService;

    /**
     * Construtor para UserController.
     *
     * @param userService           Serviço responsável pelas operações relacionadas a usuários.
     * @param authenticationService Serviço responsável pela autenticação dos usuários.
     */
    public UserController(UserService userService, AuthenticationService authenticationService) {
        super(userService);
        this.authenticationService = authenticationService;
    }

    /**
     * Registra um novo usuário.
     *
     * @param dto DTO contendo as informações do usuário a ser criado.
     * @return ResponseEntity contendo o DTO do usuário criado.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<EntityDTO>> createUser(
            @Valid @RequestBody UserDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: User created successfully.",
                        service.create(dto).toResponse(),
                        null
                )
        );
    }

    /**
     * Atualiza as informações de um usuário existente.
     *
     * @param userDetails Detalhes do usuário autenticado.
     * @param dto DTO contendo as informações atualizadas do usuário.
     * @return ResponseEntity contendo o DTO do usuário atualizado.
     */
    @PutMapping("/edit")
    public ResponseEntity<ApiResponseDTO<UserDTO>> updateUser(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserDTO dto) {

        UserDTO userDTO = service.findByLogin(userDetails.getUsername());

        UserDTO updatedDTO = new UserDTO(
                userDTO.id(),
                dto.login(),
                dto.password(),
                dto.email()
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDTO<>(
                        true,
                        "Success: User updated successfully.",
                        service.update(userDTO.id(), updatedDTO),
                        null
                )
        );
    }

    /**
     * Exclui o usuário autenticado.
     *
     * @param userDetails Detalhes do usuário autenticado.
     * @return ResponseEntity com status 200 (OK).
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDTO<UserDTO>> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = service.findByLogin(userDetails.getUsername());
        service.deleteById(userDTO.id());
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: User has been successfully removed.",
                null,
                null));
    }

    /**
     * Recupera as informações do usuário autenticado.
     *
     * @param userDetails Detalhes do usuário autenticado.
     * @return ResponseEntity contendo o DTO do usuário.
     */
    @GetMapping("/find")
    public ResponseEntity<ApiResponseDTO<UserDTO>> findUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = service.findByLogin(userDetails.getUsername());
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: User retrieved successfully.",
                service.findById(userDTO.id()),
                null
        ));
    }

    /**
     * Recupera todos os usuários com paginação.
     *
     * @param pageable Objeto para especificar paginação e ordenação.
     * @return ResponseEntity contendo uma página de usuários.
     */
    @GetMapping("/find-all")
    public ResponseEntity<ApiResponseDTO<Page<UserDTO>>> findAllUsers(
            @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: users retrieved successfully",
                service.findAll(pageable),
                null
        ));
    }

}