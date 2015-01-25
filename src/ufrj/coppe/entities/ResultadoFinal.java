package ufrj.coppe.entities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ResultadoFinal {

	
	private int categoria;
	private String keywordsConsolidadas;

	private Double mediaf1;
	private Double mediaPrecision;
	private Double mediaRecall;
	private Double mediaCG;
	private Double mediaDCG;
	private Double mediaNumDocs;
	
	public ResultadoFinal(int categoria, List<ResultadoSearch> resultadosSearch) {
		this.setCategoria(categoria);
		calculaMedidas(resultadosSearch);
	}

	private void calculaMedidas(List<ResultadoSearch> resultadosSearch) {
		
		int acertos = 0;
		int numDocsRetornados = 0;
		Double f1 = 0.0;
		Double precision = 0.0;
		Double recall = 0.0;
		Double cg = 0.0;
		Double dcg = 0.0;
		Double numDocs = 0.0;
		String keywordsConsolidadas = "";
		for (ResultadoSearch resultadoSearch : resultadosSearch) {
			acertos = acertos + resultadoSearch.getAcertos();
			numDocsRetornados = numDocsRetornados + resultadoSearch.getNumDocsRetornados();
			f1 = f1 + resultadoSearch.getF1();
			precision = precision + resultadoSearch.getPrecision();
			recall = recall + resultadoSearch.getRecall();
			cg = cg + resultadoSearch.getCg();
			dcg = dcg + resultadoSearch.getDcg();
			numDocs = numDocs + resultadoSearch.getNumDocsRetornados();
			keywordsConsolidadas = keywordsConsolidadas + resultadoSearch.getQuery()+ " ";
		}
		
		this.setMediaf1(f1/resultadosSearch.size());
		this.setMediaRecall(recall/resultadosSearch.size());
		this.setMediaPrecision(precision/resultadosSearch.size());
		this.setMediaCG(cg/resultadosSearch.size());
		this.setMediaDCG(dcg/resultadosSearch.size());
		this.setMediaNumDocs(numDocs/resultadosSearch.size());
		this.setKeywordsConsolidadas(keywordsConsolidadas);
	}

	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public Double getMediaf1() {
		return mediaf1;
	}
	public void setMediaf1(Double mediaf1) {
		this.mediaf1 = mediaf1;
	}
	public Double getMediaPrecision() {
		return mediaPrecision;
	}
	public void setMediaPrecision(Double mediaPrecision) {
		this.mediaPrecision = mediaPrecision;
	}
	public Double getMediaRecall() {
		return mediaRecall;
	}
	public void setMediaRecall(Double mediaRecall) {
		this.mediaRecall = mediaRecall;
	}

	public static List<String> imprimiResultadoFinal(List<ResultadoFinal> resultadosFinais) {
		
		List<String> resultados = new ArrayList<String>();
		
		List<String> keywords = new ArrayList<String>();
		String medidasCategoria;
		DecimalFormat decimal = new DecimalFormat( "0.0000" );  
		for (ResultadoFinal resultadoFinal : resultadosFinais) {
			
			medidasCategoria = "Categoria "+ resultadoFinal.getCategoria()+
			": Media f1: "+ decimal.format(resultadoFinal.getMediaf1())+
			": Media Precision: "+ decimal.format(resultadoFinal.getMediaPrecision())+
			": Media Recall: "+ decimal.format(resultadoFinal.getMediaRecall())+
			": Media CG: "+ decimal.format(resultadoFinal.getMediaCG())+
			": Media DCG: "+ decimal.format(resultadoFinal.getMediaDCG())+
			": NumKeywords: "+resultadoFinal.getKeywordsConsolidadas().split(" ").length+
			": Media Num_Docs: "+ decimal.format(resultadoFinal.getMediaNumDocs());
			
			System.out.println(medidasCategoria);
			resultados.add(medidasCategoria);
		
			keywords.add("Categoria "+ resultadoFinal.getCategoria()+" Keywords:"+ resultadoFinal.getKeywordsConsolidadas());
		}
		
		resultados.add("");
		System.out.println("");
		for (String linha : keywords) {
			resultados.add(linha);
			System.out.println(linha);
		}
		
		return resultados;
	}
	
	public Double getMediaCG() {
		return mediaCG;
	}

	public void setMediaCG(Double mediaCG) {
		this.mediaCG = mediaCG;
	}

	public Double getMediaDCG() {
		return mediaDCG;
	}

	public void setMediaDCG(Double mediaDCG) {
		this.mediaDCG = mediaDCG;
	}

	public Double getMediaNumDocs() {
		return mediaNumDocs;
	}

	public void setMediaNumDocs(Double mediaNumDocs) {
		this.mediaNumDocs = mediaNumDocs;
	}

	public String getKeywordsConsolidadas() {
		return keywordsConsolidadas;
	}

	public void setKeywordsConsolidadas(String keywordsConsolidadas) {
		this.keywordsConsolidadas = keywordsConsolidadas;
	}
}
