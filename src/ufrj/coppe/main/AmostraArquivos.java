package ufrj.coppe.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ufrj.coppe.utils.ArquivoUtils;

public class AmostraArquivos {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		AmostraArquivos amostraArquivos = new AmostraArquivos();
		amostraArquivos.exec(args);
		
	}

	private void exec(String[] args) {
		
		selecionaArquivos(args);
		System.out.println("Fim");
	}

	private void salvaArquivos(List<File> arquivos, String diretorioRaiz) {

		System.out.println("Entre com a pasta destino para salvar:");
		Scanner s = new Scanner(System.in);
		String pastaDestino = s.next();
		s.close();
		ArquivoUtils arquivoUtils = new ArquivoUtils(); 
		for (File arquivoOriginal : arquivos) {
			String nomeArquivoNovo = arquivoOriginal.getPath().replace(diretorioRaiz, pastaDestino);
			File arqDestino = new File(nomeArquivoNovo);
			try {
				arquivoUtils.copia(arquivoOriginal, arqDestino);
			} catch (IOException e) {
				System.out.println("Erro ao salvar arquivo:" + arquivoOriginal.getPath());
				e.printStackTrace();
			}
		}
	}

	private void selecionaArquivos(String[] args) {
		
		int numArquivos = 0;
		String rootPath = "";
		List<File> arquivos = new ArrayList<File>();
		
		Scanner s = new Scanner(System.in);
		if (args.length == 0){
			//pede o numero de arquivos por pasta
			System.out.println("Entre com o numero maximo de arquivos por categoria.");
			numArquivos = validaNArquivos(s.next());
			//pede o nome da pasta raiz
			System.out.println("Enrte com o diretorio raiz das pastas a serem selecionadas");
			rootPath = s.next();
			arquivos = pegaSubpastas(rootPath, numArquivos);
		}
		else if (args.length == 1){
			numArquivos = validaNArquivos(args[0]);
			System.out.println("Entre com o diretorio raiz das pastas a serem selecionadas");
			rootPath = s.next();
			arquivos = pegaSubpastas(rootPath, numArquivos);
		} else {
			if (args.length > 2 ) System.out.println("Essa funcao so recebe como entrada 2 parametros, todos os demais serao ignorados."); 
			numArquivos = validaNArquivos(args[0]);
			rootPath = args[1];
			arquivos = pegaSubpastas(rootPath, numArquivos);
			
		}
		s.close();
		salvaArquivos(arquivos, rootPath);
			
		
	}

	private List<File> pegaSubpastas(String rootPath, int numArquivos) {
		
		List<File> arquivos = new ArrayList<File>();
		File root = new File(rootPath);
		File[] subPastas;
		subPastas = root.listFiles();
		String[] pastas;
		int[] numPastas;
		
		
		for (int i = 0; i < subPastas.length; i++) {
			System.out.println(i+ ": "+ subPastas[i].getName()+ " Numero de arquivos: "+ subPastas[i].listFiles().length);
		}
		
		System.out.println("Entre com o numero das pastas que gostaria de utilizar separadas por virgula. ex:1,2,54,3...");
		Scanner s = new Scanner(System.in);
		pastas = s.next().split(",");
		numPastas = new int[pastas.length];
		s.close();
		for (int i = 0; i < pastas.length; i++) {
			numPastas[i] = validaNArquivos(pastas[i]);
		}
		
		for (int i = 0; i < numPastas.length; i++) {
			arquivos.addAll(pegaXArquivos(numArquivos, subPastas[numPastas[i]]));
			System.out.println(arquivos.size());
			
		}
		
		return arquivos;
	}

	private List<File> pegaXArquivos(int numArquivos, File subPasta) {

		List<File> arquivos = new ArrayList<File>(); 
				//subPasta.listFiles();
		File[] aFiles = subPasta.listFiles();
		Set<Integer> indiceArquivos = new HashSet<Integer>();
		
		//int maxArquivos = arquivos.length;
		if (subPasta.listFiles().length < numArquivos){
			for (File file : aFiles) {
				arquivos.add(file);
			}
		} else {
			
			while (indiceArquivos.size() < numArquivos){
				indiceArquivos.add((int) (Math.random()*subPasta.listFiles().length));
			}
			
			for (Integer numArquivo : indiceArquivos) {
				arquivos.add(aFiles[numArquivo]);
			}
			
		}
		return arquivos;
		
	}

	private int validaNArquivos(String next) {
		
		int numArquivos = 0;
		try{
			numArquivos = Integer.parseInt(next);
		}catch (Exception e) {
			System.out.println("Erro ao ler numero de arquivos por categoria!");	
		}
		
		return numArquivos;
	}

}
