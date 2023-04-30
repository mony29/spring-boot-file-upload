package com.example.fileupload.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse <T>{
    private String status;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
}
