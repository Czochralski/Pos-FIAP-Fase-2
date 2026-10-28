package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            RestauranteNaoEncontradoException.class,
            TipoUsuarioNaoEncontradoException.class,
            ItemCardapioNaoEncontradoException.class
    })
    public ResponseEntity<ProblemDetail> naoEncontrado(RuntimeException exception) {

        return resposta(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({
            UsuarioSemPermissaoCardapioException.class,
            DonoRestauranteInvalidoException.class,
            UsuarioSemPermissaoCriarRestauranteException.class,
            UsuarioSemPermissaoAtualizarRestauranteException.class
    })
    public ResponseEntity<ProblemDetail> semPermissao(RuntimeException exception) {

        return resposta(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler({
            TipoUsuarioJaExisteException.class,
            TipoUsuarioSistemaProtegidoException.class
    })
    public ResponseEntity<ProblemDetail> conflito(RuntimeException exception) {

        return resposta(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity<ProblemDetail> requisicaoInvalida(RuntimeException exception) {

        return resposta(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> validacaoEntrada(
            MethodArgumentNotValidException exception) {

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Dados da requisição inválidos");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                mensagem
        );

        problem.setTitle("Erro de validação");

        return ResponseEntity.badRequest().body(problem);
    }

    private ResponseEntity<ProblemDetail> resposta(
            HttpStatus status,
            String mensagem) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                status,
                mensagem
        );

        return ResponseEntity
                .status(status)
                .body(problem);
    }
}
