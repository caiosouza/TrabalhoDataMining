/**
 * PTStemmer - A Stemming toolkit for the Portuguese language (C) 2008-2010 Pedro Oliveira
 * 
 * This file is part of PTStemmer.
 * PTStemmer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * PTStemmer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with PTStemmer. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package ptstemmer;

import java.util.Scanner;

import ptstemmer.Stemmer;
import ptstemmer.Stemmer.StemmerType;
import ptstemmer.exceptions.PTStemmerException;


/**
 * System demo
 * @author Pedro Oliveira
 *
 */
public class PTStemmerEX {

	private static int cacheLimit = 1000;
	
	public static void main(String[] args) throws PTStemmerException {
		PTStemmerEX ex = new PTStemmerEX();
		ex.starter();
	}
	
	public void starter() throws PTStemmerException
	{
		
		String line;
		
		Scanner s = new Scanner(System.in);
		System.out.println("Insert one word per line:");
		while(s.hasNext())
		{
			line = s.nextLine();
			System.out.println("StemP: "+ execStemming(line, Stemmer.StemmerType.PORTER));
			System.out.println("StemS: "+ execStemming(line, Stemmer.StemmerType.SAVOY));
			System.out.println("StemO: "+ execStemming(line, Stemmer.StemmerType.ORENGO));
			
		}	
		s.close();
	}
	
	public String execStemming(String line, StemmerType stype) {
		
		Stemmer st;
		String[]  tokens;
		String stemmedTokens= "";// = new ArrayList<String>();

		try {
			st = Stemmer.StemmerFactory(stype);
			st.enableCaching(cacheLimit);
			
			tokens = line.split(" ");
			
			for (String token : tokens) {
				//stemmedTokens.add(st.getWordStem(token));
				stemmedTokens = stemmedTokens + st.getWordStem(token) + " ";
			}

		} catch (PTStemmerException e) {
			e.printStackTrace();
		}
		
		return stemmedTokens.trim();
	}
	
}
