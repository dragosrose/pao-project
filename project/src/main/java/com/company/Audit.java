package com.company;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Audit {
    File file;
    FileWriter outputfile;
    public CSVWriter writer;
    public Audit(){
        file = new File("audit.csv");
        try {
            outputfile = new FileWriter(file);
            writer = new CSVWriter(outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void audit(String command) {
        writer.writeNext(new String[]{command});
    }
}
