package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ClientAnotherCusException;
import org.example.exception.ClientNotFoundException;
import org.example.exception.RequestTokenException;
import org.example.model.ErrorDto;
import org.example.model.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.example.model.ErrorDescription.CLIENT_ANOTHER_CUS;
import static org.example.model.ErrorDescription.CLIENT_NOT_FOUND;
import static org.example.model.ErrorDescription.FORMAT_ERROR;
import static org.example.model.ErrorDescription.INVALID_REQUEST;
import static org.example.model.ErrorDescription.REQUEST_TOKEN_ERROR;
import static org.example.model.ErrorDescription.UNKNOWN_ERROR;

@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {
    //TODO: Вместо ErrorResponse можно возвращать обычный DefaultResponse, но заполнять у него только error.
    // В таком случае все остальные поля (accessToken, refreshToken) тоже будут передаваться, но заполняться null. (в данном конкретном примере это норм, потому что полей всего 2)
    // {
    //  "accessToken": null,
    //  "refreshToken": null,
    //  "error" : {
    //      //информация об ошибке
    // }
    // Если таких полей == null будет штук 30, то будет не очень красиво, этого можно избежать, если повесить над классом @JsonInclude(Include.NON_NULL)
    // Но если в ответе должно быть поле (например accessToken), которое при отсутствии ошибок должно быть @NotNull, то возникнут проблемы при попытке повесить на поле констрейнт. Именно поэтому тут такой обработчик с отдельными дтошками

    private final static String DEFAULT_ERROR_MESSAGE = "Processing ended with an error. Response: {}";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorDto errorDto = new ErrorDto(UNKNOWN_ERROR.getCode(), UNKNOWN_ERROR.getText());
        ErrorResponse errorInfoDto = new ErrorResponse(errorDto);

        log.error("Processing ended with an error: {}. Response: {}", e.getMessage(), errorInfoDto, e);
        return ResponseEntity
                .status(UNKNOWN_ERROR.getStatus())
                .body(errorInfoDto);
    }

    @ExceptionHandler({ClientNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleClientNotFoundException(ClientNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(CLIENT_NOT_FOUND.getCode(), CLIENT_NOT_FOUND.getText());
        ErrorResponse errorInfoDto = new ErrorResponse(errorDto);

        log.error(DEFAULT_ERROR_MESSAGE, errorInfoDto);
        return ResponseEntity
                .status(CLIENT_NOT_FOUND.getStatus())
                .body(errorInfoDto);
    }

    @ExceptionHandler({ClientAnotherCusException.class})
    public ResponseEntity<ErrorResponse> handleClientAnotherCusException(ClientAnotherCusException e) {
        ErrorDto errorDto = new ErrorDto(CLIENT_ANOTHER_CUS.getCode(), CLIENT_ANOTHER_CUS.getText());
        ErrorResponse errorInfoDto = new ErrorResponse(errorDto);

        log.error(DEFAULT_ERROR_MESSAGE, errorInfoDto);
        return ResponseEntity
                .status(CLIENT_ANOTHER_CUS.getStatus())
                .body(errorInfoDto);
    }

    @ExceptionHandler({RequestTokenException.class})
    public ResponseEntity<ErrorResponse> handleException(RequestTokenException e) {
        ErrorDto errorDto = new ErrorDto(REQUEST_TOKEN_ERROR.getCode(), REQUEST_TOKEN_ERROR.getText() + e.getMessage());
        ErrorResponse errorInfoDto = new ErrorResponse(errorDto);

        log.error(DEFAULT_ERROR_MESSAGE, errorInfoDto);
        return ResponseEntity
                .status(FORMAT_ERROR.getStatus())
                .body(errorInfoDto);
    }

    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<ErrorResponse> handleException(MissingRequestHeaderException e) {
        ErrorDto errorDto = new ErrorDto(INVALID_REQUEST.getCode(), INVALID_REQUEST.getText() + e.getHeaderName());
        ErrorResponse errorInfoDto = new ErrorResponse(errorDto);

        log.error(DEFAULT_ERROR_MESSAGE, errorInfoDto);
        return ResponseEntity
                .status(INVALID_REQUEST.getStatus())
                .body(errorInfoDto);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException e) {
        ErrorDto errorDto = new ErrorDto(FORMAT_ERROR.getCode(), FORMAT_ERROR.getText());
        ErrorResponse errorInfoDto = new ErrorResponse(errorDto);

        e.getBindingResult().getFieldErrors().forEach(error ->
                log.info("Validation error: {} {}", error.getField(), error.getDefaultMessage()));

        log.error(DEFAULT_ERROR_MESSAGE, errorInfoDto);
        return ResponseEntity
                .status(FORMAT_ERROR.getStatus())
                .body(errorInfoDto);
    }
}
