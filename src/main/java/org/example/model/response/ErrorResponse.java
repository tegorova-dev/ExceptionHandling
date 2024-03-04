package org.example.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.ErrorDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private ErrorDto error;
}
