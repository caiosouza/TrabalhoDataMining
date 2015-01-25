package ufrj.coppe.main;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufrj.coppe.entities.KeyWords;
import ufrj.coppe.entities.ResultadoFinal;
import ufrj.coppe.entities.ResultadoSearch;
import ufrj.coppe.execs.TestaKeywords;
import ufrj.coppe.utils.ArquivoUtils;

public class TopKWords {

	private static final String TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS = "\nTestes buscando nos arquivos pre-processados (Stemme + StopWords) :\n";
	private static final String RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS = "\nResultados para keywords com Stemme + stopwords:\n";
	private static final String RESULTADOS_PARA_KEYWORDS_ORIGINAIS = "Resultados para keywords Originais\n";
	private static final String TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS = "Testes buscando nos arquivos originais:\n";
	private static String pastaRaiz = "/Users/admin/Documents/workspace/";
	private static File indexStemmed = new File(pastaRaiz + "TrabalhoDataMining/indexAmostra1kpreProssed/");
	private static File indexOriginal = new File(pastaRaiz + "TrabalhoDataMining/indexAmostra1kOriginal/");
	//private static int[] categoriasIndexadas = {1,7,8,9,12,14,15,18,19,63};
	private static int hitsDefault = 1000;
	//private static int hitsEsperados = 1000;
	
	public static void main(String[] args) {
		
		TopKWords topKWords = new TopKWords();
		topKWords.exec();
	}

	private void exec(){
		
		execBaseline(hitsDefault);
		execBaselineOu(hitsDefault);
		execReduzKeywords(hitsDefault);
		execAmpliaKeywords(hitsDefault);
		
	}
	
	public static class Test {
	    public static void main(String... args) throws Exception {
	        URL location = Test.class.getProtectionDomain().getCodeSource().getLocation();
	        System.out.println(location.getFile());
	    }
	}
	
	private void execAmpliaKeywords(int hits) {

		String metodoAtual = getNomeMetodoAtual();
		List<String> linhasResultados = new ArrayList<String>();
		String nomeArqResultados = "Resultados/"+ metodoAtual+ ".txt";
		
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords testaKeywords = new TestaKeywords();
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		
		resultados = testaKeywords.ampliaKeywods(hits, indexOriginal, KeyWords.reduzidasOriginais);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));
		
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		
		resultados = testaKeywords.ampliaKeywods(hits, indexOriginal, KeyWords.reduzidasSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		
		resultados = testaKeywords.ampliaKeywods(hits, indexStemmed, KeyWords.reduzidasOriginais);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));
		
		System.out.println(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		
		resultados = testaKeywords.ampliaKeywods(hits, indexStemmed, KeyWords.reduzidasSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		

		ArquivoUtils arquivo = new ArquivoUtils();
		arquivo.salvaArquivo(linhasResultados, nomeArqResultados);
	}

	private void execReduzKeywords(int hits) {
		
		String metodoAtual = getNomeMetodoAtual();
		List<String> linhasResultados = new ArrayList<String>();
		String nomeArqResultados = "Resultados/"+ metodoAtual+ ".txt";
		
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords testaKeywords = new TestaKeywords();
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		
		resultados = testaKeywords.reduzKeywods(hits, indexOriginal, KeyWords.originais);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));
		
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		
		resultados = testaKeywords.reduzKeywods(hits, indexOriginal, KeyWords.originaisSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		
		resultados = testaKeywords.reduzKeywods(hits, indexStemmed, KeyWords.originais);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));
		
		System.out.println(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		
		resultados = testaKeywords.reduzKeywods(hits, indexStemmed, KeyWords.originaisSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
		
		ArquivoUtils arquivo = new ArquivoUtils();
		arquivo.salvaArquivo(linhasResultados, nomeArqResultados);
	}

	/**
	 * Rodando desta forma, as virgulas sao consideradas e por isso podem ser retornado menos de 1000 documentos.
	 * Com isso o esses resultados podem ter precision diferente do recall.
	 * 
	 * Como nesse trabalho, na primeira etapa o precision estava igual ao recall, certamente nao foi usada essa funcao.
	 * Por isso que ela foi colocada como deprecated 
	 * @param hits
	 */
	@Deprecated
	private void execBaseline(int hits){
		
		String metodoAtual = getNomeMetodoAtual();
		List<String> linhasResultados = new ArrayList<String>();
		String nomeArqResultados = "Resultados/"+ metodoAtual+ ".txt";
		
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords testaKeywords = new TestaKeywords();
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originais);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));
		
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originaisSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_ORIGINAIS);
		
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originais);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
		
		linhasResultados.add(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		System.out.println(RESULTADOS_PARA_KEYWORDS_COM_STEMME_STOPWORDS);
		
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originaisSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
		
		ArquivoUtils arquivo = new ArquivoUtils();
		arquivo.salvaArquivo(linhasResultados, nomeArqResultados);
	}

	private String getNomeMetodoAtual() {
		Throwable thr = new Throwable();  
		thr.fillInStackTrace();  
		StackTraceElement[] ste = thr.getStackTrace(); 
		String metodoAtual = ste[1].getMethodName();
		return metodoAtual;
	}
	
	private void execBaselineOu(int hits){
		
		String metodoAtual = getNomeMetodoAtual();
		List<String> linhasResultados = new ArrayList<String>();
		String nomeArqResultados = "Resultados/"+ metodoAtual+ ".txt";
		
		Map<Integer, List<ResultadoSearch>> resultados = new HashMap<Integer, List<ResultadoSearch>>();
		TestaKeywords testaKeywords = new TestaKeywords();
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		linhasResultados.add("Resultados para keywords Originais com OU\n");
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_ORIGINAIS);
		System.out.println("Resultados para keywords Originais com OU\n");
		
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originaisOU);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));

		linhasResultados.add("\nResultados para keywords com OU + Stemme + stopwords:\n");
		System.out.println("\nResultados para keywords com OU + Stemme + stopwords:\n");
		
		resultados = testaKeywords.execTeste(hits, indexOriginal, KeyWords.originaisOUSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
		
		linhasResultados.add(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		linhasResultados.add("Resultados para keywords Originais com OU \n");
		System.out.println(TESTES_BUSCANDO_NOS_ARQUIVOS_PRE_PROCESSADOS_STEMME_STOP_WORDS);
		System.out.println("Resultados para keywords Originais com OU \n");
		
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originaisOU);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));
		
		linhasResultados.add("\nResultados para keywords  com OU + Stemme + stopwords:\n");
		System.out.println("\nResultados para keywords  com OU + Stemme + stopwords:\n");
		
		resultados = testaKeywords.execTeste(hits, indexStemmed, KeyWords.originaisOUSteme);
		linhasResultados.addAll(ResultadoFinal.imprimiResultadoFinal(sumarizaResultados(resultados)));		
	
		ArquivoUtils arquivo = new ArquivoUtils();
		arquivo.salvaArquivo(linhasResultados, nomeArqResultados);
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
	/*
	private int pegaHits() {
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
			s.close();
		}
		if (tipo == 0) {
			pastaIndice = indexOriginal;
		} else pastaIndice = indexStemmed;
		
		return pastaIndice;
	}
	*/
	
}
