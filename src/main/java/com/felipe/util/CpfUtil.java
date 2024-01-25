package com.felipe.util;

public class CpfUtil {
    public static String formatCPF(String cpf) {
        if (cpf == null) {
            return null;
        }

        cpf = StringUtil.removeNonNumeric(cpf);

        if (cpf.length() != 11) {
            // O CPF deve ter exatamente 11 dígitos numéricos
            return null;
        }

        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
