package com.conexa.desafio.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrosResponse {

    private String objectName;

    private List<Erro> erros;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Erro{

        private String campo;

        private String erro;

    }

}
