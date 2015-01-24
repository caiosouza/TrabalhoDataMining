package ufrj.coppe.execs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ufrj.coppe.entities.KeyWords;
import ufrj.coppe.entities.ResultadoSearch;

import com.javacodegeeks.lucene.SimpleSearcher;

public class TestaKeywords {

	private static int[] categoriasIndexadas = {1,7,8,9,12,14,15,18,19,63};
		
	public Map<Integer, List<ResultadoSearch>> execTeste(int hits, File indexDir, int keywordsType) {
		
		Map<Integer, List<ResultadoSearch>> resultadosKeywords = new HashMap<Integer, List<ResultadoSearch>>();
		
		for (int i = 0; i < categoriasIndexadas.length; i++) {
		
			List<ResultadoSearch> resultadosSearch = new ArrayList<ResultadoSearch>();
			int categoriaEsperada = categoriasIndexadas[i];
			String[] keywords = KeyWords.getkeyWords(keywordsType, categoriaEsperada);
			
			for (int j = 0; j < keywords.length; j++) {
					resultadosSearch.add(validaAcertos(indexDir, keywords[j], hits, categoriaEsperada));
			}
			resultadosKeywords.put(categoriaEsperada, resultadosSearch);
		}
		
		return resultadosKeywords;
		
	}
	
	
	public Map<Integer, List<ResultadoSearch>> reduzKeywods(int hits, File indexDir, int keywordsType){
		
		Map<Integer, List<ResultadoSearch>> resultadosKeywords = new HashMap<Integer, List<ResultadoSearch>>();
		
		for (int i = 0; i < categoriasIndexadas.length; i++) {
			//pega as palavras chave iniciais
			int categoriaEsperada = categoriasIndexadas[i];
			List<ResultadoSearch> resultadosSearch = new ArrayList<ResultadoSearch>();
			String[] keywordsIniciais = KeyWords.getkeyWords(keywordsType, categoriaEsperada);
			
			//pega o resultado de cada keyword
			for (String keyword : keywordsIniciais) {
				resultadosSearch.add(validaAcertos(indexDir, keyword, hits, categoriaEsperada));
			} 
			
			//ordena pelo valor do score de cada palavra
			ordenaResultSearch(resultadosSearch);
			
			//pega o score do OU de todas as palavras
			Double dcgScoreAnterior;
			keywordsIniciais = KeyWords.getkeyWords(keywordsType+ 2, categoriaEsperada);
			ResultadoSearch resultadoSearch = new ResultadoSearch();
			String keywordAtual = keywordsIniciais[0];
			resultadoSearch = validaAcertos(indexDir, keywordAtual, hits, categoriaEsperada);
			dcgScoreAnterior = resultadoSearch.getDcg();
			
			Double dcgScoreAtual = dcgScoreAnterior;
			String keywordTirada = "" ;
			while ((dcgScoreAtual.compareTo(dcgScoreAnterior) >= 0)&&(resultadosSearch.size()>0)){
				
				keywordTirada = resultadosSearch.get(0).getQuery();
				keywordAtual = "";
				resultadosSearch.remove(0);
				for (int j = 0; j < resultadosSearch.size(); j++) {
					keywordAtual = keywordAtual + resultadosSearch.get(j).getQuery()+ " ";
				}
				resultadoSearch = validaAcertos(indexDir, keywordAtual, hits, categoriaEsperada);
				dcgScoreAnterior = dcgScoreAtual;
				dcgScoreAtual = resultadoSearch.getDcg();
							
			}
			
			keywordAtual = keywordTirada+ " "+ keywordAtual;
			List<ResultadoSearch> resultadosFinais = new ArrayList<ResultadoSearch>();
			resultadosFinais.add(resultadoSearch);
			resultadosKeywords.put(categoriaEsperada, resultadosFinais);
		}
		
		return resultadosKeywords;
	}
	
	public Map<Integer, List<ResultadoSearch>> ampliaKeywods(int hits, File indexDir, int keywordsType) {
		
		Map<Integer, List<ResultadoSearch>> resultadosKeywords = new HashMap<Integer, List<ResultadoSearch>>();
		
		for (int i = 0; i < categoriasIndexadas.length; i++) {
			//pega as palavras chave iniciais
			int categoriaEsperada = categoriasIndexadas[i];
			//List<ResultadoSearch> resultadosSearch = new ArrayList<ResultadoSearch>();
			//pega as keywords reduzidas
			String[] keywordsReduzidas = KeyWords.getkeyWords(keywordsType, categoriaEsperada);
			String keywordTeste = keywordsReduzidas[0];
			
			Double dcgScoreAnterior;
			String[] keywordsCandidatas = KeyWords.getkeyWords(keywordsType+ 2, categoriaEsperada);
			ResultadoSearch resultadoSearch = new ResultadoSearch();
			ResultadoSearch resultadoSearchAtual = new ResultadoSearch();
			//String keywordIncluida = "" ;
			Double dcgScoreAtual = 0.0;
			
			//calcula o acerto desse conjunto
			resultadoSearch = validaAcertos(indexDir, keywordTeste, hits, categoriaEsperada);
			dcgScoreAnterior = resultadoSearch.getDcg();
			//System.out.println(dcgScoreAnterior);
			for (String keyword : keywordsCandidatas) {
				
				//verifica se a nova keyword ja existe no conjunto
				if(keywordTeste.indexOf(" "+keyword+" ") < 0) {
					resultadoSearch = validaAcertos(indexDir, keywordTeste+ " "+keyword , hits, categoriaEsperada);
					dcgScoreAtual = resultadoSearch.getDcg();
					if (dcgScoreAtual.compareTo(dcgScoreAnterior) >= 0){
						keywordTeste = keywordTeste+ " "+keyword;
						dcgScoreAnterior = dcgScoreAtual;
						resultadoSearchAtual = resultadoSearch; 
						//System.out.println(dcgScoreAtual+" "+ keyword);
					}
				}
			}
			//System.out.println(dcgScoreAnterior+" "+ keywordTeste);
			List<ResultadoSearch> resultadosFinais = new ArrayList<ResultadoSearch>();
			resultadosFinais.add(resultadoSearchAtual);
			resultadosKeywords.put(categoriaEsperada, resultadosFinais);
		}
		
		return resultadosKeywords;
	}
		
	private void ordenaResultSearch(List<ResultadoSearch> resultadosSearch){
		
		Comparator<ResultadoSearch> rsComparator = new Comparator<ResultadoSearch>() {
		    public int compare(ResultadoSearch c1, ResultadoSearch c2) {
		    	//multiplica por mil porque eh double, se comparar soh a parte inteira vai errar nas casas decimais
		    	//c2 -c1 -> ordem crescente no resultado
		        return (int) ((c1.getDcg()*1000) - (c2.getDcg()*1000));
		    }
		};
		Collections.sort(resultadosSearch, rsComparator);
	}
	
	private ResultadoSearch validaAcertos(File indexDir, String query, int hits, int categoriaEsperada) {
		
		SimpleSearcher searcher = new SimpleSearcher();
		ResultadoSearch resultadoSearch = new ResultadoSearch(); 
		try {
			//pega os resultados
			resultadoSearch = searcher.searchIndex(indexDir, query, hits);
			resultadoSearch.setCategoriaEsperada(categoriaEsperada);
			
		} catch (Exception e) {
			System.out.println("Erro ao validar resultados!");
			e.printStackTrace();
		}
		
		return resultadoSearch;
		
	}

}
