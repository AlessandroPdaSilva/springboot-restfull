package curso.springboot.restfull.service;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public byte[] gerarRelatorio(String nomeRelatorio, Map<String,Object> params , ServletContext context) throws Exception{
		
		// Pegando conexao atual
		Connection connection = jdbcTemplate.getDataSource().getConnection();
		
		// caminho do jasper
		String caminhoJasper = context.getRealPath("relatorios")+ File.separator + nomeRelatorio + ".jasper";
		
		// gerando relatorio
		JasperPrint print = JasperFillManager.fillReport(caminhoJasper, params, connection);
		
		// exporta como pdf
		byte[] retorno = JasperExportManager.exportReportToPdf(print);
		
		connection.close();
		
		return retorno;
	}
	
}
