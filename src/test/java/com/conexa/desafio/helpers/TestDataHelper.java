package com.conexa.desafio.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TestDataHelper {

  public static final String SIGNUP_ROUTE = "/api/v1/signup";

  public static final String LOGIN_ROUTE = "/api/v1/login";

  public static final String LOGOFF_ROUTE = "/api/v1/logoff";

  public static final String ATTENDANCE_ROUTE = "/api/v1/attendance";

  public static final String SIGNUP_VALIDO_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste\",\"confirmacaoSenha\":\"teste\",\"especialidade\":\"Cardiologista\",\"cpf\":\"657.376.520-68\",\"dataNascimento\":\"10/03/1980\",\"telefone\":\"(21) 3232-6565\"}";

  public static final String SIGNUP_SENHAS_DISTINTAS_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste\",\"confirmacaoSenha\":\"teste1\",\"especialidade\":\"Cardiologista\",\"cpf\":\"028.071.180-89\",\"dataNascimento\":\"10/03/1980\",\"telefone\":\"(21) 3232-6565\"}";

  public static final String CAMPOS_INVALIDOS_SIGNUP_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste\",\"especialidade\":\"\",\"cpf\":\"657.376.520-68\",\"dataNascimento\":\"10/03/1980\",\"telefone\":\"(21) 3232-6565\"}";

  public static final String CPF_INVALIDO_SIGNUP_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste\",\"confirmacaoSenha\":\"teste\",\"especialidade\":\"Cardiologista\",\"cpf\":\"101.202.303-11\",\"dataNascimento\":\"10/03/1980\",\"telefone\":\"(21) 3232-6565\"}";

  public static final String DATA_NASCIMENTO_INVALIDA_SIGNUP_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste\",\"confirmacaoSenha\":\"teste\",\"especialidade\":\"Cardiologista\",\"cpf\":\"657.376.520-68\",\"dataNascimento\":\"10/03/9998\",\"telefone\":\"(21) 3232-6565\"}";

  public static final String TELEFONE_INVALIDO_SIGNUP_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste\",\"confirmacaoSenha\":\"teste\",\"especialidade\":\"Cardiologista\",\"cpf\":\"657.376.520-68\",\"dataNascimento\":\"10/03/1980\",\"telefone\":\"21 3232-6565\"}";

  public static final String LOGIN_VALIDO_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste\"}";

  public static final String CAMPOS_INVALIDOS_LOGIN_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"\"}";

  public static final String SENHA_INVALIDA_LOGIN_REQUEST =
      "{\"email\":\"medico@email.com\",\"senha\":\"teste1\"}";

  public static final String CONSULTA_VALIDA_REQUEST =
      "{\"dataHora\":\"9989-08-03 09:00:00\",\"paciente\":{\"nome\":\"João Castro\",\"cpf\":\"432.576.720-71\"}}";

  public static final String CAMPO_INVALIDO_CONSULTA_REQUEST =
      "{\"dataHora\":\"9989-08-03 09:00:00\",\"paciente\":{\"nome\":\"\",\"cpf\":\"432.576.720-71\"}}";

  public static final String DATA_CONSULTA_PASSADA_REQUEST =
      "{\"dataHora\":\"2015-08-03 09:00:00\",\"paciente\":{\"nome\":\"João Castro\",\"cpf\":\"432.576.720-71\"}}";

  public static final String TOKEN_TEST_COM_PREFIXO =
      "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";

  public static final String TOKEN_TEST_SEM_PREFIXO =
      "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiaWF0IjoxNjc4OTgzNDgzLCJleHAiOjE2Nzg5ODQzODN9.7OzsjKb63COaau6X8mbO5N1xX6F0jpvnYGG2jRE-9sfDjWhrCe7SakBx5Hm2osr4YHanAqn2_YrplwL9sZwWhg";

  public static final String DADOS_PACIENTE =
      "{\"nome\":\"Nome distinto\",\"cpf\":\"432.576.720-71\"}";

  public static final ObjectMapper objectMapper = new ObjectMapper();


  public static <T> String serializeObject(T object) {
    try {
      objectMapper.findAndRegisterModules();
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T deserializeObject(String json, Class<T> clazz) {
    try {
      objectMapper.findAndRegisterModules();
      return objectMapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> HttpEntity<String> constroiHttpEntity(String token, T request) {
    HttpHeaders headers = new HttpHeaders();
    if (token != null) headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<>(serializeObject(request), headers);
  }
}
