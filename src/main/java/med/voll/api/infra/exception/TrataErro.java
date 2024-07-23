package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TrataErro {

    private record DadosErroValidacao(String campo, String mensagemErro){
        public DadosErroValidacao(FieldError fieldError){
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> trataEntidadeNaoEncontrada(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> trataErroValidacao(MethodArgumentNotValidException exception){
        List<FieldError> errosValidacao = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(errosValidacao.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> trataErroAgendamento(ValidacaoException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
