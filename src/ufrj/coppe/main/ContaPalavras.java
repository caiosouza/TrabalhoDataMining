package ufrj.coppe.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * classe ContaPalavras - recebe como entrada um arquivo texto, identifica as
 * diferentes palavras e contabiliza as frequências.
 *
 * uso: java ContaPalavras arquivo_texto
 *
 * @author Eduardo Correa
 *
 */


public class ContaPalavras {

    public static void main(String[] args) throws Exception {
 
    	
    	ContaPalavras contaPalavras = new ContaPalavras();
    	
    	contaPalavras.exec(args);
    }
    
    public void exec(String[] args){
    	
    	List<String> linhas = new ArrayList<String>();
    	
    	try {
			linhas = leArquivo(args);
		} catch (Exception e) {
			System.out.println("Erro ao ler arquivo");
			e.printStackTrace();
		}
    	Map<String,Integer> mapPalavras = contaFrequencia(linhas);
    	imprimeFrequencias(mapPalavras);
    }
    
	public List<String> leArquivo(String[] args) throws Exception{
    	
    	List<String> linhas = new ArrayList<String>();
    	
    	String nomeArq;     
        
        //testa se nome do arq. texto foi passado na chamada do programa
        
        if (args.length != 1) {
        	Scanner s = new Scanner(System.in);
        	System.out.println("Entre com o nome do arquivo.");
        	nomeArq = s.next();
        	
        }
        else {
        	nomeArq = args[0];
        }
        nomeArq = "data/100001000044.txt";
     
        //abre o arquivo
       
        BufferedReader txtBuffer = new BufferedReader(new InputStreamReader(
        	    new FileInputStream(nomeArq), "UTF-8"));
        
        
        //pega a primeira linha do arquivo
        String curLine; //recebe cada linha lida do arquivo texto
        
        curLine = txtBuffer.readLine();
         
        while (curLine != null) {
        	linhas.add(curLine);
            //pega a próxima linha do arquivo
            curLine = txtBuffer.readLine();
        }
         
        txtBuffer.close();
 
    	return linhas;
    }
    	
	
	public Map<String, Integer> filtraFrequencia(Map<String, Integer> mapOriginal){
		
		Map<String, Integer> mapFiltrado = new HashMap<String,Integer>();
		
		for (Map.Entry<String, Integer> entry : mapOriginal.entrySet()) {
    		mapFiltrado.put(entry.getKey(), entry.getValue());
    	}
		return mapFiltrado;
	}
	

    public Map<String, Integer> contaFrequencia(List<String> linhas) {

    	Map<String,Integer> mapPalavras = new HashMap<String,Integer>(); 
    	//mapa: Palavra -> Frequencia 
        //usado para contabilizar as frequencias das palavras
    	int totalTermos = 0;
    	for (String curLine : linhas) {
            //quebra a linha em tokens (palavras) utilizando expressão regular.
            //      O programa usa uma forma simplificada p/ obter os tokens. São considerados tokens:
            //      - uma sequência de 1 a n números e uma sequência de 1 a n letras
 
            //primeiro converte tudo para minúsculo
            String minusculo = curLine.toLowerCase();
             
            //depois aplica a expressão regular
            //Pattern p = Pattern.compile("(\\d+)|([a-záéíóúçõãôêâà]+)");
            Pattern p = Pattern.compile("([a-záéíóúçõãôêâà]+)");
            //Pattern p = Pattern.compile("(\\d+)|([a-z]+)");
            Matcher m = p.matcher(minusculo);
             
            //neste loop pegamos cada palavra e atualizamos o mapa de frequências
            
            while(m.find())
            {
            	String token = m.group(); //pega um token  
            	Integer freq = mapPalavras.get(token); //verifica se esse token já está no mapa   
            	totalTermos = totalTermos +1; 
            	if (freq != null) { //se palavra existe, atualiza a frequencia
            		mapPalavras.put(token, freq+1);
                }
                else { // se palavra não existe, insiro com um novo id e freq=1.
                    mapPalavras.put(token,1);
                }
            }
            //adiciona o total de termos por documento para depois usar isso para frequencia
        }
        //mapPalavras.put("TotalTermos", totalTermos);
    	//return mapPalavras;
        return filtraFrequencia(mapPalavras);
    }
    
    public Map<String, Integer> contaPresenca(List<String> linhas) {

    	Map<String,Integer> mapPalavras = new HashMap<String,Integer>(); 
    	int totalTermos = 0;
    	for (String curLine : linhas) {
            //quebra a linha em tokens (palavras) utilizando expressão regular.
            //      O programa usa uma forma simplificada p/ obter os tokens. São considerados tokens:
            //      - uma sequência de 1 a n números e uma sequência de 1 a n letras
 
            //primeiro converte tudo para minúsculo
            String minusculo = curLine.toLowerCase();
             
            Pattern p = Pattern.compile("([a-záéíóúçõãôêâà]+)");
            //Pattern p = Pattern.compile("([a-z]+)");
            Matcher m = p.matcher(minusculo);
             
            //neste loop pegamos cada palavra e atualizamos o mapa de frequências
            
            while(m.find())
            {
            	String token = m.group(); //pega um token  
            	totalTermos = totalTermos +1;
            	token = trata(token);
                mapPalavras.put(token,1);
            }
            //adiciona o total de termos por documento para depois usar isso para frequencia
        }
        return mapPalavras;
    }
    
    public String trata (String passa){  
        passa = passa.replaceAll("[ÂÀÁÄÃ]","A");  
        passa = passa.replaceAll("[âãàáä]","a");  
        passa = passa.replaceAll("[ÊÈÉË]","E");  
        passa = passa.replaceAll("[êèéë]","e");  
        passa = passa.replaceAll("ÎÍÌÏ","I");  
        passa = passa.replaceAll("îíìï","i");  
        passa = passa.replaceAll("[ÔÕÒÓÖ]","O");  
        passa = passa.replaceAll("[ôõòóö]","o");  
        passa = passa.replaceAll("[ÛÙÚÜ]","U");  
        passa = passa.replaceAll("[ûúùü]","u");  
        passa = passa.replaceAll("Ç","C");  
        passa = passa.replaceAll("ç","c");   
        passa = passa.replaceAll("[ýÿ]","y");  
        passa = passa.replaceAll("Ý","Y");  
        passa = passa.replaceAll("ñ","n");  
        passa = passa.replaceAll("Ñ","N");  
        return passa;  
    }  
    
    
    public void imprimeFrequencias(Map<String, Integer> mapPalavras) {
	
    	for (Map.Entry<String, Integer> entry : mapPalavras.entrySet()) {
    		System.out.println(entry.getKey() + "\tfreq=" + entry.getValue());
    	}
    }

	public void consolidaTermoFrequencia(Map<String, Integer> termoFrequenciaDocumento,
			Map<String, Integer> termoFrequenciaCategoria) {

		if (termoFrequenciaCategoria.isEmpty()){
			termoFrequenciaCategoria = termoFrequenciaDocumento;
		}
		else 
		{
			//para toda chave no mapa do documento
			for (Map.Entry<String, Integer> entry : termoFrequenciaDocumento.entrySet()) {
				String termo = entry.getKey();
				int freqDoc = entry.getValue();
				//verifica se ela está no mapa da categoria
				if (termoFrequenciaCategoria.containsKey(termo))
				{
					//se estiver atualiza o valor com a soma dos valores
					int freqCategoria = termoFrequenciaCategoria.get(termo);
					termoFrequenciaCategoria.put(termo, freqCategoria+ freqDoc);
				} else {
					//se não estiver adiciona com o valor do documento
					termoFrequenciaCategoria.put(termo, freqDoc);
				}
			}
			
		}

	}
    
}
