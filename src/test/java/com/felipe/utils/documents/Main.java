package com.felipe.utils.documents;

public class Main {

	public static void main(String[] args) {
		// Exemplos de CPFs válidos e inválidos
		String cpfValido = "123.456.789-09";
		String cpfInvalido = "111.111.111-11";
		GenerateDocument generateDocument = new GenerateDocument();

		// Teste com CPF válido
		if (generateDocument.isCPF(cpfValido)) {
			System.out.println(cpfValido + " é um CPF válido.");
		} else {
			System.out.println(cpfValido + " não é um CPF válido.");
		}

		// Teste com CPF inválido
		if (generateDocument.isCPF(cpfInvalido)) {
			System.out.println(cpfInvalido + " é um CPF válido.");
		} else {
			System.out.println(cpfInvalido + " não é um CPF válido.");
		}

		// Exemplos de CNPJs válidos e inválidos
		String cnpjValido = "40.779.850/0001-54";
		String cnpjInvalido = "40.779.850/0001-51";

		// Teste com CNPJ válido
		if (generateDocument.isCNPJ(cnpjValido)) {
			System.out.println(cnpjValido + " é um CNPJ válido.");
		} else {
			System.out.println(cnpjValido + " não é um CNPJ válido.");
		}

		// Teste com CNPJ inválido
		if (generateDocument.isCNPJ(cnpjInvalido)) {
			System.out.println(cnpjInvalido + " é um CNPJ válido.");
		} else {
			System.out.println(cnpjInvalido + " não é um CNPJ válido.");
		}

		// Você também pode gerar e validar RG da mesma forma
	}
}

