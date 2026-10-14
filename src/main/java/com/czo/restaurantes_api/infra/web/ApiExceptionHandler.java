package com.czo.restaurantes_api.infra.web;

import com.czo.restaurantes_api.core.exception.DonoRestauranteInvalidoException;
import com.czo.restaurantes_api.core.exception.RestauranteNaoEncontradoException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioJaExisteException;
import com.czo.restaurantes_api.core.exception.TipoUsuarioNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({RestauranteNaoEncontradoException.class, TipoUsuarioNaoEncontradoException.class})
    public ResponseEntity<Map<String, String>> naoEncontrado(RuntimeException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", exception.getMessage()));
    }

    @ExceptionHandler(TipoUsuarioJaExisteException.class)
    public ResponseEntity<Map<String, String>> conflito(RuntimeException exception) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", exception.getMessage()));
    }

    @ExceptionHandler({DonoRestauranteInvalidoException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> requisicaoInvalida(RuntimeException exception) {

        return ResponseEntity.badRequest().body(Map.of("erro", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validacaoEntrada(MethodArgumentNotValidException exception) {

        String mensagem = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Dados da requisição inválidos");

        return ResponseEntity.badRequest().body(Map.of("erro", mensagem));
    }
}
