package ufrj.coppe.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArquivoUtils {
	
	private static String codificacao = "UTF-8";
	
	
	public String pegaLinhas(String nomeArq){
		
		String linhaSaida = "";
		
		List<String> linhas;
		try {
			linhas = abreArquivo(nomeArq);
			for (String linha : linhas) {
				linhaSaida = linhaSaida + " " + linha;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return linhaSaida;
	}
	
	
	public String pegaLinhas(File arquivo) {

		String nomeArq = arquivo.getAbsolutePath();
		return pegaLinhas(nomeArq);
	}


	public List<String> abreArquivo(String nomeArq) throws Exception{
		
		List<String> linhas = new ArrayList<String>();
		
		BufferedReader txtBuffer = new BufferedReader(new InputStreamReader(
        	    new FileInputStream(nomeArq), codificacao));
        
        String linha = txtBuffer.readLine();
         
        while (linha != null) {
        	linhas.add(linha);
            linha = txtBuffer.readLine();
        }
         
        txtBuffer.close();
 
    	return linhas;
		
	}

	public void salvaArquivo(List<String> linhas, String nomeArqSaida) {

		FileOutputStream arqSaida;
		PrintStream p;  
		
		try{  
			//cria o diretorio caso seja necessario
			criaDiretorioArquivo(nomeArqSaida);
						
		
			arqSaida = new FileOutputStream(nomeArqSaida);  
			p = new PrintStream(arqSaida, true, codificacao);
			 
			for (String linha : linhas) {
				p.println(linha);
			}	
			  
		    p.close();  
		} catch(Exception e) {  
			System.out.println("Erro ao salvar arquivo de saída.");
		}  
	}
	
	public List<File> getAllFilesRecursive( File aStartingDir ) throws FileNotFoundException {

		List<File> result = new ArrayList<File>();
		File[] filesAndDirs = aStartingDir.listFiles();
		List<File> filesDirs = Arrays.asList(filesAndDirs);
		
    	for(File file : filesDirs) {
			if(file.isFile()){
				result.add(file); //add only files
			}
			else {
				//must be a directory
				//recursive call!
				List<File> deeperList = getAllFilesRecursive(file);
				result.addAll(deeperList);
			}
		}
		return result;
	}

    public void copia(File origem, File destino) throws IOException {
      
    	criaDiretorioArquivo(destino);
    	
    	InputStream in = new FileInputStream(origem);
        OutputStream out = new FileOutputStream(destino);          
        // Transferindo bytes de entrada para saída
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght= in.read(buffer)) > 0) {
            out.write(buffer, 0, lenght);
        }
        in.close();
        out.close();
        
    }
    
	private void criaDiretorioArquivo(File arqSaida) {

		String dirPath = arqSaida.getParent();
		if (dirPath != null){
			File fDirPath = new File(dirPath);
			if (!fDirPath.exists()) {
				fDirPath.mkdirs();
			}
		}
		
	}
	
	private void criaDiretorioArquivo(String nomeArqSaida) {
		criaDiretorioArquivo(new File(nomeArqSaida));			
	}
	
}
