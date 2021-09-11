package com.files.translator.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.springframework.util.StreamUtils;

@Service
public class TranslatorService {

    public void reverseOrderStrophe(String inputFile, String outFile) throws IOException {
        String inputFileName = inputFile;
        ArrayList<String> strophes=strophesArrayList(inputFileName);
        ArrayList<String> invertedStrophes = invertedStrophes(strophes);
        String stringStrophes = stringArrayList(invertedStrophes);
        copyStringToOuputStream(stringStrophes,outFile);
        //return "aqui va las estrofas invertidas";
    }

    public void copyStringToOuputStream(String string, String outFile) throws IOException {
        String outputFileName = outFile;
        File outputFile = new File(outputFileName);
        OutputStream out = new FileOutputStream(outputFile);
        StreamUtils.copy(string, StandardCharsets.UTF_8, out);
    }

    public int numberStrophe(String inputFile) throws IOException {
        ArrayList<String> invertedStrophes=strophesArrayList(inputFile);
        int numberStrophes=invertedStrophes.size();
        return numberStrophes;
    }

    public String stringArrayList(ArrayList<String> list){
        String string = "";
        int sizeList=list.size();
        for(int i=0; i<sizeList;i++) {
            if(i==sizeList-1){
                string += list.get(i);
            }
            else{
                string += list.get(i) + "\n";
            }
        }
        return string;
    }

    public ArrayList<String> strophesArrayList(String inputFile) throws FileNotFoundException, IOException {
        ArrayList<String> strophes=new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        String line;
        String strophe="";
        while ((line = bufferedReader.readLine()) != null) {
            if(line.equals("")){
                strophes.add(strophe);
                strophe = "";
            } else {
                strophe=strophe+ line+"\n";
            }
        }
        strophes.add(strophe);
        return strophes;
    }

    public ArrayList<String> invertedStrophes(ArrayList<String> list){
        ArrayList<String> invertedStrophes=new ArrayList<String>();
        int size = list.size();
        for (int i=1; i<=size; i++){
            int j = size-i;
            invertedStrophes.add(list.get(j));
        }
        return invertedStrophes;
    }

    public void printRepeatedWord(String inputFile, String outFile) throws IOException {
        String word = repeatedWord(inputFile);
        copyStringToOuputStream(word,outFile);
    }

    public String repeatedWord(String inputFile) throws IOException {
        String wordRepeated = "";
        String allContent = contenToString(inputFile, false);
        ArrayList<String> words = getWords(allContent);
        ArrayList<String> words2 = new ArrayList<String>(words);

        int size = words.size();
        int contadorMasRepet =  0;

        for (int i = 0; i < size; i++) {
            int contador = 0;
            String palabra = words.get(i);
            for (int j = 0; j < size; j++) {
                if (palabra.equalsIgnoreCase(words2.get(j))){
                    contador++;
                    words.set(j,"");
                }
            }
            if ((contador > 1)&& (contador > contadorMasRepet)){
                wordRepeated = palabra;
                contadorMasRepet = contador;
                System.out.print(words.get(i));
            }
            else if ((contador > 1)&& (contador == contadorMasRepet)){
                wordRepeated += " " + palabra;
            }
        }
        return wordRepeated;
    }

    public void replaceWord(String wordRepeatedFile, String inputFile, String outFile) throws IOException {
        String content = contenToString(inputFile,true);
        String repeatedWord = contenToString(wordRepeatedFile,false);
        content = content.replace(repeatedWord,"you ");
        copyStringToOuputStream(content,outFile);
    }

    public String contenToString(String inputFile, boolean skip) throws FileNotFoundException, IOException {
        String content = "";
        String line;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        while ((line = bufferedReader.readLine()) != null) {
            if(!line.equals("")){
                if(skip){
                    content = content+line+" "+"\n";
                }
                else {
                    content = content + line + " ";
                }
            }else{
                if(skip){
                    content = content + line+"\n";
                }
            }
        }
        return content;
    }

    public ArrayList<String> getWords(String string){
        String word = "";
        ArrayList<String> words = new ArrayList<String>();
        for (int i=0; i<string.length();i++) {
            char c = string.charAt(i);
            if(c!=' ' && c!=','){
                word = word + c;
            }
            else{
                if(!word.equals("")){
                    words.add(word);
                    word="";
                }
            }
        }
        return words;
    }
}
