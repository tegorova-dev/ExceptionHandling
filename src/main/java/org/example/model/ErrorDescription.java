package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum ErrorDescription {
    INVALID_REQUEST("invalid_request","Некорректное значение ", 400),
    FORMAT_ERROR("format_error","Запрос не может быть обработан", 422),
    UNKNOWN_ERROR("unknown_error","При выполнении запроса возникла неизвестная ошибка.", 500),
    REQUEST_TOKEN_ERROR("request_token_error","При получении токена произошла ошибка. ", 500),
    CLIENT_NOT_FOUND("client_not_found","Клиент не найден", 404),
    CLIENT_ANOTHER_CUS("client_another_cus","У клиента другой cus ", 404),
    ;

    private final String code;
    private final String text;
    private final int status;
}
