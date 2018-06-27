package com.wonky_productions;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.IOException;

public class MenuManager {
    String name;
    int easy, tricky, hard, extra;

    public MenuManager(String name){
        this.name = name;

        try {
            getDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromFile() throws IOException {
        FileHandle handle = Gdx.files.local(name + "Data.txt");
        if(!handle.exists()){
            handle.writeString("1,0,0,0", false);
            easy = 1;
            tricky = 0;
            hard = 0;
            extra = 0;

        } else {
            BufferedReader bufferedReader = handle.reader(100);
            int index1 = 0, index2 = 0, index3 = 0, index4 = 0;
            String line = bufferedReader.readLine();
            for (int i = 0; i < line.length() ; i++) {
                if(line.charAt(i) == ',') {
                    if (index1 == 0){
                        index1 = i;
                    } else if(index2 == 0){
                        index2 = i;
                    } else if(index3 == 0) {
                        index3 = i;
                    }
                }
                index4 = line.length();
            }
            try {
                easy = Integer.parseInt(line.substring(0, index1));
                tricky = Integer.parseInt(line.substring(index1 + 1, index2));
                hard = Integer.parseInt(line.substring(index2 + 1, index3));
                extra = Integer.parseInt(line.substring(index3 + 1, index4));
            } catch ( Exception e){
                handle.writeString("1,0,0,0", false);
                easy = 1;
                tricky = 0;
                hard = 0;
                extra = 0;


            }
            bufferedReader.close();
        }
    }


    public void write(){
        FileHandle handle = Gdx.files.local(name + "Data.txt");
        handle.writeString(easy + "," + tricky + "," + hard + "," + extra, false);
    }

    public void dispose(){
        // TODO
    }

    public int getEasy() {
        return easy;
    }

    public void setEasy(int easy) {
        this.easy = easy;
    }

    public int getTricky() {
        return tricky;
    }

    public void setTricky(int tricky) {
        this.tricky = tricky;
    }

    public int getHard() {
        return hard;
    }

    public void setHard(int hard) {
        this.hard = hard;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }
}

