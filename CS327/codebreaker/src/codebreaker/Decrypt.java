package codebreaker;
import java.util.HashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Decrypt {
	
	
	public static void main(String[] args) throws IOException{
		String[][] encryptedWords = new String[26][200]; //List of encrypted words by size
		String[][] dictionaryWords = new String[26][200]; //List of dictionary words by size
		HashSet<String> wordList = new HashSet<String>(); //Hashset for word list (constant check)
		char[] mapping = new char[26];
		
		try {
			File textFile = new File("A.txt");
			File wordFile = new File("words-100");
			BufferedReader textFileReader = new BufferedReader(new FileReader(textFile)); //Input of encrypted words
			BufferedReader wordFileReader = new BufferedReader(new FileReader(wordFile)); //Input of word list
			while(textFileReader.ready()){
				String currLine = textFileReader.readLine();
				Scanner temp = new Scanner(currLine);
				
				while(temp.hasNext()){
					String currWord = temp.next();
					currWord = currWord.toLowerCase();
					String newCurrWord = "";
					for(int count = 0; count < currWord.length(); count++){
						if( !(currWord.charAt(count) < 'a' || currWord.charAt(count) > 'z')){
							newCurrWord += currWord.charAt(count);
						}
					}
					//Code above ensures all words entering array have no misc characters(non letter) and are
					//all lower case
					
					for(int i = 0; i < encryptedWords[newCurrWord.length()].length; i++ ) {//WILL MAKE THIS MORE EFFIECIENT 
						if(encryptedWords[newCurrWord.length()][i]==null){
							encryptedWords[newCurrWord.length()][i] = newCurrWord;
							break;
						}
					}
					
				}
			}
			
			while(wordFileReader.ready()){
				String currLine = wordFileReader.readLine();
				Scanner temp = new Scanner(currLine);
				
				while(temp.hasNext()){
					String currWord = temp.next();
					currWord = currWord.toLowerCase();
					String newCurrWord = "";
					for(int count = 0; count < currWord.length(); count++){
						if( !(currWord.charAt(count) < 'a' || currWord.charAt(count) > 'z')){
							newCurrWord += currWord.charAt(count);
						}
					}
					//Code above ensures all words entering array have no misc characters(non letter) and are
					//all lower case
					
					for(int i = 0; i < dictionaryWords[newCurrWord.length()].length; i++ ) {//WILL MAKE THIS MORE EFFIECIENT 
						if(dictionaryWords[newCurrWord.length()][i]==null){
							dictionaryWords[newCurrWord.length()][i] = newCurrWord;
							wordList.add(newCurrWord);
							break;
						}
					}

				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("No file found ya idiot!");
		}
		int mappingCount = 0;
		for(int eWordLength = 1; eWordLength < encryptedWords.length; eWordLength++){
			if(encryptedWords[eWordLength] == null){
				//Do nothing (no words of this length)
			}else{				
				for(int currWord = 0; currWord < encryptedWords[eWordLength].length; currWord++){
					if(dictionaryWords[eWordLength] == null){
						//no dictionary word matching encrypted word, will handle later
					}else{
						for(int dictWord = 1; dictWord < dictionaryWords[eWordLength].length; dictWord++){
							genMapping(encryptedWords[eWordLength][currWord], dictionaryWords[eWordLength][dictWord], mapping);
							for(int soFar = 1; soFar < encryptedWords.length; soFar++){
								for(int i = 0; i < encryptedWords[eWordLength].length; i++){
									if(!(checkMapping(encryptedWords[soFar][i], mapping, wordList))){
										//TODO This means it's not a mapping, need to go back to prev mapping
									}
								}
							}
						}
					}
				}
			}
		}


	}

	/**
	 * This method takes an encrypted word of length n and a word from the dictionary of length n
	 * and permutates the current mapping based on the given encryption and newly chosen dictionary
	 * word. 
	 * @param encryptedWord
	 * @param dictionaryWords
	 * @param mapping
	 */
	
	protected static char[] genMapping(String encryptedWord, String dictionaryWord, char[] mapping){
		for(int i = 0; i < encryptedWord.length(); i++){
			mapping[dictionaryWord.charAt(i-'a')] = encryptedWord.charAt(i);
		}
		return mapping;
	}
	
	protected static boolean checkMapping(String eWord, char[] mapping, HashSet<String> dictionary){
		String temp = "";
		for(int count = 0; count < eWord.length(); count++){
			//TODO IMPROVE
			char ch = eWord.charAt(count);
			for(int i = 0; i < mapping.length; i++){
				if(mapping[i] == ch)
					temp += (char)(i+97);
			}
		}
		return dictionary.contains(temp);
	}
	
}

