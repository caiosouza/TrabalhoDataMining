package ufrj.coppe.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultadoSearch {

	private String query;
	private int categoriaEsperada;
	private Map<Integer,Integer> categoriasFrequencia;
	private List<DocumentoRetornado> documentosRetornados;
	private int acertos;
	private int numDocsRetornados;
	private Double f1;
	private Double precision;
	private Double recall;
	private Double cg;
	private Double dcg;
	
	
	public ResultadoSearch(String query, int categoriaEsperada,	Map<Integer, Integer> categoriasFrequencia, int acertos,
			int numDocsRetornados, Double f1, Double recall, Double precision, List<DocumentoRetornado> documentosRetornados,
			Double cg, Double dcg) {
		this.setQuery(query);
		this.setCategoriaEsperada(categoriaEsperada);
		this.setCategoriasFrequencia(categoriasFrequencia);
		this.setAcertos(acertos);
		this.setNumDocsRetornados(numDocsRetornados);
		this.setF1(precision, recall);
		this.setRecall(recall);
		this.setPrecision(precision);
		this.setDocumentosRetornados(documentosRetornados);	
		this.setCg(cg);
		this.setDcg(dcg);
	}

	public ResultadoSearch(String queryStr, List<DocumentoRetornado> documentosRetornados, int numDocsRetornados) {
		
		this.setQuery(queryStr);
		this.setNumDocsRetornados(numDocsRetornados);
		processaResultados(documentosRetornados);
	}
	
	public ResultadoSearch() {
	}

	private void setPrecision(Double precision) {
		this.precision = precision;
	}
	
	public Double getPrecision(){
		return this.precision;
	}

	private void processaResultados(List<DocumentoRetornado> documentosRetornados) {
		
		int frequencia;
		int categoria;
		Map<Integer,Integer> categoriasFrequencia = new HashMap<Integer,Integer>();
		
		for (DocumentoRetornado documentoRetornado : documentosRetornados) {
			
			categoria = documentoRetornado.getCategoria();
			if (categoriasFrequencia.containsKey(categoria))
			{	frequencia = categoriasFrequencia.get(categoria) +1;
				categoriasFrequencia.put(categoria, frequencia);
			}
			else categoriasFrequencia.put(categoria, 1);
		}
		this.setCategoriasFrequencia(categoriasFrequencia);
		this.setNumDocsRetornados(documentosRetornados.size());
		this.setDocumentosRetornados(documentosRetornados);
		
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getCategoriaEsperada() {
		return categoriaEsperada;
	}

	public void setCategoriaEsperada(int categoriaEsperada) {
		
		this.categoriaEsperada = categoriaEsperada;
		
		if (categoriasFrequencia.containsKey(categoriaEsperada)) this.setAcertos(categoriasFrequencia.get(categoriaEsperada));
		else this.setAcertos(0);
		
		if (numDocsRetornados > 0)	this.setPrecision(1.0*acertos/numDocsRetornados);
		else this.setPrecision(0.0);
		
		this.setRecall((1.0*acertos/1000));
		this.setF1(precision, recall);
		
		Double cg = 0.0;
		Double dcg = 0.0;
		
		for(int i = 0; i < this.documentosRetornados.size(); i++ ){
			if (this.documentosRetornados.get(i).getCategoria() ==categoriaEsperada){
				cg = cg + this.documentosRetornados.get(i).getLuceneScore();
				dcg = dcg + calcDcg(this.documentosRetornados.get(i).getLuceneScore(), i);
			}
		}
		this.setCg(cg);
		this.setDcg(dcg);
		
	}

	private Double calcDcg(float luceneScore, int pos) {
		
		Double co =  Math.pow(2,luceneScore) + 1;
		Double dn = Math.log(pos +1 + 1);
		return co/dn;
	}

	public Map<Integer, Integer> getCategoriasFrequencia() {
		return categoriasFrequencia;
	}

	public void setCategoriasFrequencia(Map<Integer, Integer> categoriasFrequencia) {
		this.categoriasFrequencia = categoriasFrequencia;
	}

	public int getAcertos() {
		return acertos;
	}

	public void setAcertos(int acertos) {
		this.acertos = acertos;
	}

	public int getNumDocsRetornados() {
		return numDocsRetornados;
	}

	public void setNumDocsRetornados(int numDocsRetornados) {
		this.numDocsRetornados = numDocsRetornados;
	}

	public Double getF1() {
		return f1;
	}

	public void setF1(Double precision, Double recall) {
		Double f1;
		if (precision + recall == 0) f1 = 0.0;
		else f1 =(2*precision*recall)/(precision + recall); 
		this.setF1(f1);
	}

	public Double getRecall() {
		return recall;
	}

	public void setRecall(Double recall) {
		this.recall = recall;
	}

	public List<DocumentoRetornado> getDocumentosRetornados() {
		return documentosRetornados;
	}

	public void setDocumentosRetornados(
			List<DocumentoRetornado> documentosRetornados) {
		this.documentosRetornados = documentosRetornados;
	}

	public Double getCg() {
		return cg;
	}

	public void setCg(Double cg) {
		this.cg = cg;
	}

	public Double getDcg() {
		return dcg;
	}

	public void setDcg(Double dcg) {
		this.dcg = dcg;
	}

	public void setF1(Double f1) {
		this.f1 = f1;
	}
	
	public Double getCg(int pos){
		
		Double cg = 0.0;
		if (pos > this.documentosRetornados.size()) pos = this.documentosRetornados.size();
		for(int i = 0; i < pos; i++ ){
			if (this.documentosRetornados.get(i).getCategoria() == this.categoriaEsperada){
				cg = cg + this.documentosRetornados.get(i).getLuceneScore();
			}
		}
		return cg;
	}
	
	public Double getDcg(int pos){
		
		Double dcg = 0.0;
		if (pos > this.documentosRetornados.size()) pos = this.documentosRetornados.size();
		for(int i = 0; i < pos; i++ ){
			if (this.documentosRetornados.get(i).getCategoria() == this.categoriaEsperada){
				dcg = dcg + calcDcg(this.documentosRetornados.get(i).getLuceneScore(), i);
			}
		}
		return dcg;
	}
	
}
