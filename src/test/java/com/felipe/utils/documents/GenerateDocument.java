package com.felipe.utils.documents;

import java.util.InputMismatchException;
import java.util.Random;

public class GenerateDocument {

	private int random(int n) {
		int ranNum = (int) (Math.random() * n);
		return ranNum;
	}

	private int calculateRemainder(int dividend, int divisor) {
		return (int) Math.round(dividend - (Math.floor(dividend / divisor) * divisor));
	}

	public String cpf(boolean withSpecialCharacters) {
		int n = 9;
		int n1 = random(n);
		int n2 = random(n);
		int n3 = random(n);
		int n4 = random(n);
		int n5 = random(n);
		int n6 = random(n);
		int n7 = random(n);
		int n8 = random(n);
		int n9 = random(n);
		int d1 = n9 * 2 + n8 * 3 + n7 * 4 + n6 * 5 + n5 * 6 + n4 * 7 + n3 * 8 + n2 * 9 + n1 * 10;

		d1 = 11 - (calculateRemainder(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n9 * 3 + n8 * 4 + n7 * 5 + n6 * 6 + n5 * 7 + n4 * 8 + n3 * 9 + n2 * 10 + n1 * 11;

		d2 = 11 - (calculateRemainder(d2, 11));

		String result = null;

		if (d2 >= 10)
			d2 = 0;
		result = "";

		if (withSpecialCharacters)
			result = "" + n1 + n2 + n3 + '.' + n4 + n5 + n6 + '.' + n7 + n8 + n9 + '-' + d1 + d2;
		else
			result = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + d1 + d2;

		return result;
	}

	public String cnpj(boolean withSpecialCharacters) {
		int n = 9;
		int n1 = random(n);
		int n2 = random(n);
		int n3 = random(n);
		int n4 = random(n);
		int n5 = random(n);
		int n6 = random(n);
		int n7 = random(n);
		int n8 = random(n);
		int n9 = 0; // random(n);
		int n10 = 0; // random(n);
		int n11 = 0; // random(n);
		int n12 = 1; // random(n);
		int d1 = n12 * 2 + n11 * 3 + n10 * 4 + n9 * 5 + n8 * 6 + n7 * 7 + n6 * 8 + n5 * 9 + n4 * 2 + n3 * 3 + n2 * 4
				+ n1 * 5;

		d1 = 11 - (calculateRemainder(d1, 11));

		if (d1 >= 10)
			d1 = 0;

		int d2 = d1 * 2 + n12 * 3 + n11 * 4 + n10 * 5 + n9 * 6 + n8 * 7 + n7 * 8 + n6 * 9 + n5 * 2 + n4 * 3 + n3 * 4
				+ n2 * 5 + n1 * 6;

		d2 = 11 - (calculateRemainder(d2, 11));

		if (d2 >= 10)
			d2 = 0;

		String retorno = null;

		if (withSpecialCharacters)
			retorno = "" + n1 + n2 + "." + n3 + n4 + n5 + "." + n6 + n7 + n8 + "/" + n9 + n10 + n11 + n12 + "-" + d1
					+ d2;
		else
			retorno = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + d1 + d2;

		return retorno;
	}

	public String rg(boolean withSpecialCharacters) {
		String nDigResult;
		String concatenatedNumbers;
		String generatedNumber;
		Random randomNumber = new Random();
		// numeros gerados
		int n1 = randomNumber.nextInt(10);
		int n2 = randomNumber.nextInt(10);
		int n3 = randomNumber.nextInt(10);
		int n4 = randomNumber.nextInt(10);
		int n5 = randomNumber.nextInt(10);
		int n6 = randomNumber.nextInt(10);
		int n7 = randomNumber.nextInt(10);
		int n8 = randomNumber.nextInt(10);
		int n9 = randomNumber.nextInt(10);

		// Conctenando os numeros
		concatenatedNumbers = String.valueOf(n1) + String.valueOf(n2) + String.valueOf(n3) + String.valueOf(n4)
				+ String.valueOf(n5) + String.valueOf(n6) + String.valueOf(n7) + String.valueOf(n8)
				+ String.valueOf(n9);
		generatedNumber = concatenatedNumbers;

		if (withSpecialCharacters)
			generatedNumber = "" + n1 + n2 + "." + n3 + n4 + n5 + "." + n6 + n7 + n8 + "-" + n9;
		else
			generatedNumber = "" + n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9;

		return generatedNumber;
	}

	public static void main(String[] args) {
		GenerateDocument generator = new GenerateDocument();
		String cpf = generator.cpf(true);
		System.out.printf("CPF: %s, Valido: %s\n", cpf, generator.isCPF(cpf));

		String cnpj = generator.cnpj(false);
		System.out.printf("CNPJ: %s, Valido: %s\n", cnpj, generator.isCNPJ(cnpj));

		String rg = generator.rg(true);
		System.out.printf("RG: %s", rg);
	}

	public boolean isCPF(String CPF) {

		CPF = removeSpecialCharacters(CPF);

		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere numerico

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = CPF.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verifica se os digitos calculados conferem com os digitos informados.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public boolean isCNPJ(String CNPJ) {

		CNPJ = removeSpecialCharacters(CNPJ);

		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222")
				|| CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
				|| CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888")
				|| CNPJ.equals("99999999999999") || (CNPJ.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o código para eventuais erros de conversao de tipo (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-ésimo caractere do CNPJ em um número:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posição de '0' na tabela ASCII)
				num = CNPJ.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = CNPJ.charAt(i) - 48;
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os dígitos calculados conferem com os dígitos informados.
			if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	private String removeSpecialCharacters(String doc) {
		if (doc.contains(".")) {
			doc = doc.replace(".", "");
		}
		if (doc.contains("-")) {
			doc = doc.replace("-", "");
		}
		if (doc.contains("/")) {
			doc = doc.replace("/", "");
		}
		return doc;
	}

	public static String formatCNPJ(String CNPJ) {
		// máscara do CNPJ: 99.999.999.9999-99
		return (CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "."
				+ CNPJ.substring(8, 12) + "-" + CNPJ.substring(12, 14));
	}
}
