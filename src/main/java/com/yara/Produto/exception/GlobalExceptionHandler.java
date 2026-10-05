package com.yara.Produto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ProdutoNaoEncontradoException.class)
        public ProblemDetail naoEncontrado(ProdutoNaoEncontradoException ex) {
                return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        @ExceptionHandler(CodigoDuplicadoException.class)
        public ProblemDetail duplicado(CodigoDuplicadoException ex) {
                return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ProblemDetail validacao(MethodArgumentNotValidException ex) {
                ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                        HttpStatus.BAD_REQUEST, "Dados inválidos");

                Map<String, String> campos = new LinkedHashMap<>();
                ex.getBindingResult().getFieldErrors()
                        .forEach(erro -> campos.put(erro.getField(), erro.getDefaultMessage()));

                problema.setProperty("campos", campos);
                return problema;
        }
}