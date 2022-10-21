package paymentsservice.endpoints;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import paymentsservice.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundException> notFoundExceptionHandler(HttpServletRequest request,
                                                                      NotFoundException ex) {//here parameters are autowired
        return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<NotFoundException> illegalArgumentExceptionHandler(HttpServletRequest request,
                                                                             IllegalArgumentException ex) {//here parameters are autowired
        return new ResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

}
