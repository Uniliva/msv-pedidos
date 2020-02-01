package br.com.unicontas.pedidos.resource.handlers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.unicontas.pedidos.exception.BusinessException;
import br.com.unicontas.pedidos.exception.NotFoundException;


@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler({NotFoundException.class, BusinessException.class})
	ResponseEntity<ErroPadrao> notFound(Exception e, HttpServletRequest request) {
		ErroPadrao erroPadrao = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ErroPadraoValidacao> validationError(MethodArgumentNotValidException e, HttpServletRequest request) {
		ErroPadraoValidacao erroPadrao = new ErroPadraoValidacao(HttpStatus.BAD_REQUEST.value(), "Erro de validação");
		e.getBindingResult().getFieldErrors().stream().forEach(erro -> erroPadrao.addErros(erro.getField(), erro.getDefaultMessage()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
	}

}
