package com.javacodegeeks.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import ufrj.coppe.entities.DocumentoRetornado;
import ufrj.coppe.entities.ResultadoSearch;

public class SimpleSearcher {
	
	public static void main(String[] args) {
	
		SimpleSearcher simpleSearcher = new SimpleSearcher();
		simpleSearcher.exec(args);
	}
	
	private void exec(String[] args){
		
		File indexDir = new File("index1k10cat");
		int hits = 1000;
		
		String query= "casa";
			
		SimpleSearcher searcher = new SimpleSearcher();
		try {
			searcher.searchIndex(indexDir, query, hits);
		} catch (Exception e) {
			System.out.println("Erro ao procurar arquivos indexados.");
			e.printStackTrace();
		}
		
	}
	
	public ResultadoSearch searchIndex(File indexDir, String queryStr, int maxHits) throws Exception {
		
		List<DocumentoRetornado> documentosRetornados = new ArrayList<DocumentoRetornado>();
		Directory directory = FSDirectory.open(indexDir);

		IndexSearcher searcher = new IndexSearcher(directory);
		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new SimpleAnalyzer());
		Query query = parser.parse(queryStr);
		
		TopDocs topDocs = searcher.search(query, maxHits);
		
		ScoreDoc[] hits = topDocs.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			DocumentoRetornado documentoRetornado = new DocumentoRetornado(d.get("filename"), hits[i].score);
			documentosRetornados.add(documentoRetornado);
		}
		
		ResultadoSearch resultadoSearch = new ResultadoSearch(queryStr, documentosRetornados, hits.length);
		return resultadoSearch;
		
	}

}
