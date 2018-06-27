package com.wonky_productions;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;

public class FileManager {
    String name;
    int numGoes, totalNumb, percent;

    public FileManager(String name){
        this.name = name;

        try {
            getDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataFromFile() throws IOException {
        FileHandle handle = Gdx.files.local(name + "Data.txt");
        if(!handle.exists()){
            handle.writeString("0,0,0", false);
            numGoes = 0;
            totalNumb = 0;
            percent = 0;

        } else {
            BufferedReader bufferedReader = handle.reader(100);
            int index1 = 0, index2 = 0, index3 = 0;
            String line = bufferedReader.readLine();
            for (int i = 0; i < line.length() ; i++) {
                if(line.charAt(i) == ',') {
                    if (index1 == 0){
                        index1 = i;
                    } else if(index2 == 0){
                        index2 = i;
                    }
                }
                index3 = line.length();
            }
            try {
                numGoes = Integer.parseInt(line.substring(0, index1));
                totalNumb = Integer.parseInt(line.substring(index1 + 1, index2));
                percent = Integer.parseInt(line.substring(index2 + 1, index3));

            } catch ( Exception e){
                handle.writeString("0,0,0", false);
                numGoes = 0;
                totalNumb = 0;
                percent = 0;
            }
            bufferedReader.close();
        }
    }

    public int getNumGoes() {
        return numGoes;
    }

    public void setNumGoes(int numGoes) {
        this.numGoes = numGoes;
    }

    public int getTotalNumb() {
        return totalNumb;
    }

    public void setTotalNumb(int totalNumb) {
        this.totalNumb = totalNumb;
    }

    public boolean isComplete(){
        if (percent == 100) return true;
        else return false;
    }


    public void write(){
        FileHandle handle = Gdx.files.local(name + "Data.txt");
        handle.writeString(numGoes + "," + totalNumb + "," + percent, false);
    }


    public int getPercent(){
        return percent;
    }

    public void setPercent(int percent){

        if(percent >= this.percent){
           this.percent = percent;
        }
    }

    public void addRecord(int percent) {
        numGoes++;
        totalNumb++;
        setPercent(percent);
    }

    public void readFile() {

    }

}

