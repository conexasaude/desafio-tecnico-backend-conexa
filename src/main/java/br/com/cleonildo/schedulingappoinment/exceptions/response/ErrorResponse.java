package br.com.cleonildo.schedulingappoinment.exceptions.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonPropertyOrder({"timestamp", "status", "status_message", "error_message"})
public class ErrorResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer status;
    @JsonProperty("status_message")
    private String statusErrorMessage;
    @JsonProperty("error_message")
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:SS")
    private LocalDateTime timestamp;
}