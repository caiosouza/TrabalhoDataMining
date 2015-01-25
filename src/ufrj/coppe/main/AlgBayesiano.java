package ufrj.coppe.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import ufrj.coppe.entities.DocFrequencia;
import ufrj.coppe.entities.ModeloBayesiano;
import ufrj.coppe.utils.ArquivoUtils;

public class AlgBayesiano {

	/**
	 * @param args
	 * 
	 */

	//private static Integer k = 1; //laplaciano
	
	public static void main(String[] args) {
		
		AlgBayesiano algBayesiano = new AlgBayesiano();
		algBayesiano.exec("Amostra1kOriginal", "categoria_termo_frequencia.txt");
		algBayesiano.exec("Amostra1kpreProssed", "categoria_termo_frequenciaPP.txt");
	}

	private void exec(String string, String string2) {
		String[] args = new String[2];
		args[0] = string;
		args[1] = string2;
		exec(args);
	}

	private void exec(String[] args) {

		System.out.println("Entre com o nome do Diret�rio a ser treinado");
		
		//Scanner scanner = new Scanner(System.in);
		//String nomeDiretorio = scanner.next();//"data/teste";
		String nomeDiretorio = args[0];
		File diretorio = new File(nomeDiretorio);
		
		try {
			ArquivoUtils arquivoUtils = new ArquivoUtils();
			List<File> arquivos = arquivoUtils.getAllFilesRecursive(diretorio);
			ModeloBayesiano modeloBayesiano = treinaModelo(arquivos);
			List<String> linhas = imprimeM(modeloBayesiano.getCategTermFreq());
			arquivoUtils.salvaArquivo(linhas, args[1]);
			//testaModelo(arquivos, modelo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("Fim");
		
		
	}

	private ModeloBayesiano treinaModelo(List<File> arquivos) {
		
		ModeloBayesiano modeloTreinado = new ModeloBayesiano();
		ContaPalavras contaPalavras = new ContaPalavras();
		ArquivoUtils arquivoUtils = new ArquivoUtils();

		int numDocs = 0;
		
		Set<String> categorias = new HashSet<String>();
		Set<String> termos = new HashSet<String>();
		
		Hashtable<String, Integer> categDFreq = new Hashtable<String,Integer>();
		
		Hashtable<String, Map<String, Integer>> categTermFreq = new Hashtable<String, Map<String, Integer>>();
	//	Hashtable<String, Map<String,Integer>> termDocFreq = new Hashtable<String, Map<String, Integer>>();
		
		Map<String,Integer> termoFrequencia = new HashMap<String,Integer>(); 
		
		List<DocFrequencia> docsFrequencias = new ArrayList<DocFrequencia>();
		
		for (File arquivo : arquivos) {
			numDocs = numDocs +1;
			
			List<String> linhas;
			try {

				linhas = arquivoUtils.abreArquivo(arquivo.getAbsolutePath());
				String categoria = pegaCategoria(arquivo.getParent());
				Map<String, Integer> termoFrequenciaDocumento = contaPalavras.contaPresenca(linhas);
				
				//prepara lista para montar matriz TF_IDF
				docsFrequencias.add(new DocFrequencia(arquivo.getAbsolutePath(),categoria, termoFrequenciaDocumento));
				
				//atualiza a lista de categorias e a lista de termos do dicionario
				categorias.add(categoria);
				termos.addAll(termoFrequenciaDocumento.keySet());
			
				//atualiza a lista de termos daquela categoria
				if (categTermFreq.keySet().contains(categoria)){
					termoFrequencia = categTermFreq.get(categoria);
					contaPalavras.consolidaTermoFrequencia(termoFrequenciaDocumento, termoFrequencia);
				}
				else {
					categTermFreq.put(categoria, termoFrequenciaDocumento);
				}
				
				//atualiza numero de docs naquela categoria
				int numDocsCat;
				if (categDFreq.keySet().contains(categoria)){
					numDocsCat = categDFreq.get(categoria)+1;
					categDFreq.put(categoria, numDocsCat);
				}
				else{
					categDFreq.put(categoria, 1);
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		modeloTreinado.setNumDocs(numDocs);
		modeloTreinado.setNumCats(categorias.size());
		modeloTreinado.setNumTermos(termos.size());
		
		modeloTreinado.setCategorias(categorias);
		modeloTreinado.setTermos(termos);
		
		modeloTreinado.setCategDFreq(categDFreq);
		modeloTreinado.setCategTFreq(categTermFreq);
				
		modeloTreinado.setCategTermFreq(categTermFreq);
		modeloTreinado.setDocsFrequencias(docsFrequencias);
		
		return modeloTreinado;
	}

	private List<String> imprimeM(Hashtable<String, Map<String, Integer>> categTermFreq) {

		List<String> linhas = new ArrayList<String>();
		
		for (Entry<String, Map<String, Integer>> entry : categTermFreq.entrySet()) {
			System.out.println(entry.getKey());
			linhas.addAll(imprime(entry.getValue(), entry.getKey()));
		}
		return linhas;
		
	}
	
	private List<String> imprime(Map<String, Integer> termoFrequenciaCategoria, String categoria) {
		
		List<String> linhas = new ArrayList<String>();
		System.out.println("");
		for (Map.Entry<String, Integer> entry : termoFrequenciaCategoria.entrySet())
		{
			if (entry.getValue() > 49){
				linhas.add(categoria+ ";"+ entry.getKey() + ";" + entry.getValue());
			}
		}
		return linhas;
	}

	private String pegaCategoria(String fileParent) {
		
		return fileParent.substring(fileParent.indexOf("_")+1);
	}
	
}
