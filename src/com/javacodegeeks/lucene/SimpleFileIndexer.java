package com.javacodegeeks.lucene;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

public class SimpleFileIndexer {
	
	private static String pastaRaiz = "C:/Users/caio.souza/Projects/";
	private static String indexStemmed = pastaRaiz + "LuceneIntroProject/indexStemmed/";
	private static String indexOriginal = pastaRaiz + "LuceneIntroProject/indexOrigial/";
	private static String pathStemmed = pastaRaiz + "DSP/data/preProssed/";
	private static String pathOriginal = pastaRaiz + "DSP/data/txts/";
	
	public static void main(String[] args) throws Exception {

		
		SimpleFileIndexer simpleFileIndexer = new SimpleFileIndexer();
		simpleFileIndexer.exec(args);
	}
	
	private void exec(String[] args){
		
		File indexDir = pegaPastaIndice(args);
		File dataDir = pegaPastaDados(args);
		//File indexDir = new File("C:/Users/caio.souza/Desktop/teste");
		//File dataDir = new File("C:/Users/caio.souza/Projects/LuceneIntroProject/teste/");
		String suffix = "txt";
		
		SimpleFileIndexer indexer = new SimpleFileIndexer();
		
		int numIndex = 0;
		try {
			numIndex = indexer.index(indexDir, dataDir, suffix);
		} catch (Exception e) {
			System.out.println("Erro ao gerar o indice.");
			e.printStackTrace();
		}
		
		System.out.println("Total files indexed " + numIndex);
		
	}
	
	private File pegaPastaDados(String[] args) {
		
		System.out.println("Entre com a pasta dos dados:");
		String nomePasta;
		if (args.length > 0 ){
			int nOptPasta = Integer.parseInt(args[0]);
			if (nOptPasta == 1) nomePasta = pathStemmed;
			else nomePasta = pathOriginal;
		} else {
			Scanner s = new Scanner(System.in);
			nomePasta = s.next();
		}
		File pastaDados = new File (nomePasta); 
		
		return pastaDados;
	}

	private File pegaPastaIndice(String[] args) {
	
		System.out.println("Entre com a pasta do indice:");
		String nomePasta;
		if (args.length > 0 ){
			int nOptPasta = Integer.parseInt(args[0]);
			if (nOptPasta == 1) nomePasta = indexStemmed;
			else nomePasta = indexOriginal;
		} else {
			Scanner s = new Scanner(System.in);
			nomePasta = s.next();
		}
		File pastaIndice = new File (nomePasta); 
		
		return pastaIndice;
	}

	private int index(File indexDir, File dataDir, String suffix) throws Exception {
		
		IndexWriter indexWriter = new IndexWriter(
				FSDirectory.open(indexDir), 
				new SimpleAnalyzer(),
				true,
				IndexWriter.MaxFieldLength.UNLIMITED);
		indexWriter.setUseCompoundFile(false);
		
		indexDirectory(indexWriter, dataDir, suffix);
		
		int numIndexed = indexWriter.maxDoc();
		indexWriter.optimize();
		indexWriter.close();
		
		return numIndexed;
		
	}
	
	private void indexDirectory(IndexWriter indexWriter, File dataDir, String suffix) throws IOException {
		File[] files = dataDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				indexDirectory(indexWriter, f, suffix);
			}
			else {
				indexFileWithIndexWriter(indexWriter, f, suffix);
			}
		}
	}
	
	private void indexFileWithIndexWriter(IndexWriter indexWriter, File f, String suffix) throws IOException {
		if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
			return;
		}
		if (suffix!=null && !f.getName().endsWith(suffix)) {
			return;
		}
		System.out.println("Indexing file " + f.getCanonicalPath());
		
		Document doc = new Document();
		doc.add(new Field("contents", new FileReader(f)));		
		doc.add(new Field("filename", f.getCanonicalPath(), Field.Store.YES, Field.Index.ANALYZED));
		
		indexWriter.addDocument(doc);
	}

}
