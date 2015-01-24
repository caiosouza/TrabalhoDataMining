package ufrj.coppe.main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ufrj.coppe.entities.KeyWords;
import ufrj.coppe.entities.ResultadoFinal;
import ufrj.coppe.entities.ResultadoSearch;
import ufrj.coppe.execs.TestaKeywords;

public class TopKWords {

	private static String pastaRaiz = "/Users/admin/Documents/workspace/";
	private static File indexStemmed = new File(pastaRaiz + "LuceneIntroProject/indexAmostra1kpreProssed/");
	private static File indexOriginal = new File(pastaRaiz + "LuceneIntroProject/indexAmostra1kOriginal/");
	//private static int[] categoriasIndexadas = {1,7,8,9,12,14,15,18,19,63};
	private static int hitsDefault = 1000;
	//private static int hitsEsperados = 1000;
	
	public static void main(String[] args) {
		
		TopKWords topKWords = new TopKWords();
		topKWords.exec(args);
	}

	private void exec(String[] args){
		execBaseline(args);
		//execBaselineOu(args);
		//execReduzKeywords(args);
		//execAmpliaKeywords(args);
		
	}
	
	public static class Test {
	    public static void main(String... args) throws Exception {
	        URL location = Test.class.getProtectionDomain().getCodeSource().getLocation();
	        System.out.println(location.getFile());
	    }
	}
	
	private void execAmpliaKeywords(String[] args) {
		int hits = pegaHits(args);
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords  testaKeywords = new TestaKeywords();
		
		System.out.println("Testes buscando nos arquivos originais:\n");
		System.out.println("Resultados para keywords Originais\n");
		resultados = testaKeywords.ampliaKeywods(hits, indexOriginal, KeyWords.reduzidasOriginais);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));
		System.out.println("\nResultados para keywords com Stemme + stopwords:\n");
		resultados = testaKeywords.ampliaKeywods(hits, indexOriginal, KeyWords.reduzidasSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		
		System.out.println("\nTestes buscando nos arquivos pre-processados (Stemme + StopWords) :\n");
		System.out.println("Resultados para keywords Originais/n");
		resultados = testaKeywords.ampliaKeywods(hits, indexStemmed, KeyWords.reduzidasOriginais);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		System.out.println("\nResultados para keywords com Stemme + stopwords:\n");
		resultados = testaKeywords.ampliaKeywods(hits, indexStemmed, KeyWords.reduzidasSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		

	}

	private void execReduzKeywords(String[] args) {
		
		int hits = pegaHits(args);
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords  testaKeywords = new TestaKeywords();
		
		System.out.println("Testes buscando nos arquivos originais:\n");
		System.out.println("Resultados para keywords Originais\n");
		resultados = testaKeywords.reduzKeywods(hits, indexOriginal, KeyWords.originais);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));
		System.out.println("\nResultados para keywords com Stemme + stopwords:\n");
		resultados = testaKeywords.reduzKeywods(hits, indexOriginal, KeyWords.originaisSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		
		System.out.println("\nTestes buscando nos arquivos pre-processados (Stemme + StopWords) :\n");
		System.out.println("Resultados para keywords Originais/n");
		resultados = testaKeywords.reduzKeywods(hits, indexStemmed, KeyWords.originais);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		System.out.println("\nResultados para keywords com Stemme + stopwords:\n");
		resultados = testaKeywords.reduzKeywods(hits, indexStemmed, KeyWords.originaisSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
	}

	private void execBaseline(String [] args){
		
		int hits = pegaHits(args);
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords testaKeywords = new TestaKeywords();
		
		System.out.println("Testes buscando nos arquivos originais:\n");
		System.out.println("Resultados para keywords Originais\n");
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originais);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		System.out.println("\nResultados para keywords com Stemme + stopwords:\n");
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originaisSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		
		System.out.println("\nTestes buscando nos arquivos pre-processados (Stemme + StopWords) :\n");
		//indexDir = new File(indexStemmed);
		System.out.println("Resultados para keywords Originais/n");
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originais);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		System.out.println("\nResultados para keywords com Stemme + stopwords:\n");
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originaisSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		
	}
	
	private void execBaselineOu(String[] args){
		
		int hits = pegaHits(args);
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords testaKeywords = new TestaKeywords();
		
		System.out.println("Testes buscando nos arquivos originais:\n");
		//indexDir = new File(indexOriginal);
		System.out.println("Resultados para keywords Originais com OU\n");
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originaisOU);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		System.out.println("\nResultados para keywords com OU + Stemme + stopwords:\n");
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originaisOUSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		
		System.out.println("\nTestes buscando nos arquivos pre-processados (Stemme + StopWords) :\n");
		//indexDir = new File(indexStemmed);
		System.out.println("Resultados para keywords Originais com OU /n");
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originaisOU);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		System.out.println("\nResultados para keywords  com OU + Stemme + stopwords:\n");
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originaisOUSteme);
		ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados));		
		
	}
	
	private List<ResultadoFinal> sumarizaResultados(Map<Integer, List<ResultadoSearch>> resultadosKeywords){
		
		List<ResultadoFinal> resultadosFinais = new ArrayList<ResultadoFinal>();
		for (Map.Entry<Integer, List<ResultadoSearch>> entry : resultadosKeywords.entrySet()) {
			int categoria = entry.getKey();
			List<ResultadoSearch> resultadosSearch = resultadosKeywords.get(categoria);
			resultadosFinais.add(new ResultadoFinal(categoria , resultadosSearch));
		}
		
		return resultadosFinais;
	}
	
	private int pegaHits(String[] args) {
		//le o arg [0], se n�o tiver arg 0 pede para o cara digitar e testa se � inteiro
		return hitsDefault;
	}

	private File pegaPastaIndice(String[] args) {
		
		File pastaIndice;
		int tipo = 0;
		if (args.length> 0 ){
			tipo = Integer.parseInt(args[0]);
			
		}
		else {
			Scanner s = new Scanner(System.in);
			System.out.println("Entre com o tipo de indice: (0) Original, (1) Stemm+StopWords.");
			tipo = Integer.parseInt(s.next());
			
		}
		if (tipo == 0) {
			pastaIndice = indexOriginal;
		} else pastaIndice = indexStemmed;
		
		return pastaIndice;
	}
	
}
