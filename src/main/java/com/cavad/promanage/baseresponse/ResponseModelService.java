package com.cavad.promanage.baseresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseModelService {


    public static ResponseEntity<Object> responseBuilder(LocalDateTime timeStamp,
                                                         Object responseObject,
                                                         String message, HttpStatus status
    ) {
        ResponseModel response = ResponseModel.builder()
                .timeStamp(timeStamp)
                .object(responseObject)
                .message(message)
                .status(status)
                .build();
        return new ResponseEntity<>(response, status);

    }

    public static ResponseEntity<List<Object>> responseBuilderaList(LocalDateTime timeStamp,
                                                                    Object responseObject,
                                                                    String message, HttpStatus status
    ) {
        List<Object> list = new ArrayList<>();
        ResponseModel response = ResponseModel.builder()
                .timeStamp(timeStamp)
                .object(responseObject)
                .message(message)
                .status(status)
                .build();
        list.add(response);
        return new ResponseEntity<>(list, status);

    }
}