package org.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.ErrorDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultResponse {
    private String accessToken;
    private String refreshToken;
    private ErrorDto error;
}
