package curso.springboot.restfull.model;

import java.io.Serializable;

public class UsuarioRelatorio implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String dataInicial;
	private String dataFinal;
	
	// GET E SET
	public String getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}
	public String getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	
}
