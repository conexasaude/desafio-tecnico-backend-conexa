package com.conexa.desafio.payload;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    
    @JsonProperty("code")
    private Integer code;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("payload")
    private Object payload;

    public static BaseResponse buildBaseResponse(HttpStatus status){
        return BaseResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .build();
    }

    public static BaseResponse buildBaseResponse(HttpStatus status, Object payload){
        return BaseResponse.builder()
                .code(status.value())
                .message(status.getReasonPhrase())
                .payload(payload)
                .build();
    }

    public static BaseResponse buildBaseResponse(HttpStatus status, String customMessage){
        return BaseResponse.builder()
                .code(status.value())
                .message(StringUtils.hasText(customMessage) ? customMessage : status.getReasonPhrase())
                .build();
    }

}
