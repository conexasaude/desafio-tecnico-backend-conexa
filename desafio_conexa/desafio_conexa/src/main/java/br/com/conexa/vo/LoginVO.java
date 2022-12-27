package br.com.conexa.vo;

public class LoginVO {

	private String email;

	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LoginVO(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}
	
	
	

}
