package ufrj.coppe.entities;

import java.io.File;

public class DocumentoRetornado {
	
	private String documento;
	private int categoria;
	private float luceneScore;
	
	public DocumentoRetornado(String documento, int categoria,
			float luceneScore) {
		this.setDocumento(documento);
		this.setCategoria(categoria);
		this.setLuceneScore(luceneScore);
	}
	
	public DocumentoRetornado(String documento, float luceneScore) {
		this.setDocumento(documento.substring(documento.lastIndexOf(File.separator)+1));
		this.setCategoria(pegaCategoria(documento));
		this.setLuceneScore(luceneScore);
	}

	private int pegaCategoria(String documento) {
		//C:\Users\caio.souza\Projects\LuceneIntroProject\Amostra1k10cat\Categoria_7\100400000011.txt
		String categoria = documento.substring(documento.indexOf("_")+1, documento.lastIndexOf(File.separator));
		return Integer.parseInt(categoria);
	}

	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public float getLuceneScore() {
		return luceneScore;
	}
	public void setLuceneScore(float luceneScore) {
		this.luceneScore = luceneScore;
	}

}
