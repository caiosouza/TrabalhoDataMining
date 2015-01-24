package ufrj.coppe.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ufrj.coppe.utils.ArquivoUtils;

public class TxtToDb {

	/**
	 * @param args
	 */
	
	private String pathOriginais = "Amostra1kOriginal";
	private String pathProcessados = "Amostra1kpreProssed";
	
	public static void main(String[] args) {
		
		TxtToDb TxtToDb = new TxtToDb();
		TxtToDb.exec(args);
	}

	private void exec(String[] args) {

		montacsv(pathOriginais);
		montacsv(pathProcessados);
	}

	private void montacsv(String path) {
		
		ArquivoUtils arquivoUtils = new ArquivoUtils();
		File root = new File(path);
		File[] subdiretorios = root.listFiles();
		
		List<String> linhas = new ArrayList<String>();
		String linha;
		
		for (File subdiretorio : subdiretorios) {
			String categoria = subdiretorio.getName();
			File [] arquivos = subdiretorio.listFiles();
			for (File arquivo : arquivos) {
				linha = categoria +";"+ arquivo.getName() +";"+ arquivoUtils.pegaLinhas(arquivo);
				linhas.add(linha);
			}
			
		}
		
		arquivoUtils.salvaArquivo(linhas, path+ ".csv");
		
	}

}
