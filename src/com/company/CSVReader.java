package com.company;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;

    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    String[]current;

    /**
     * @param filename  - nazwa pliku
     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    public CSVReader(String filename, String delimiter, boolean hasHeader) throws IOException {
        this(new FileReader(filename), delimiter, hasHeader);
    }
    public CSVReader(String filename) throws IOException {
        this(filename, ",", true);
    }
    public CSVReader(String filename,String delimiter) throws IOException {
        this(filename, delimiter, true);
    }
    public CSVReader(Reader reader, String delimiter, boolean hasHeader) throws IOException {
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        if (hasHeader) parseHeader();
    }
    void parseHeader() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            return;
        }
        String[] header = line.split(delimiter);
        for (int i = 0; i < header.length; i++) {
            columnLabelsToInt.put(header[i],i);
        }
    }
    public boolean next() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            return false;
        }
        current = line.split(delimiter+"(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        return true;
    }
    List<String> getColumnLabels(){
        return columnLabels;
    }
    int getRecordLength(){
        return current.length;
    }

    boolean isMissing(int columnIndex){
        if(columnIndex >= current.length){
            return true;
        }
        return current[columnIndex].isEmpty();
    }

    boolean isMissing(String columnLabel){
        if(columnLabel==null){
            return true;
        }
        return !columnLabelsToInt.containsKey(columnLabel) || current==null || isMissing(columnLabelsToInt.get(columnLabel));
    }
    String get(int columnIndex) throws RuntimeException {
        if (isMissing(columnIndex)) throw new RuntimeException("The index does not exist");
        return current[columnIndex];
    }
    String get(String columnLabel) throws RuntimeException {
        if(isMissing(columnLabel)) throw new RuntimeException("The columnLabel does not exist");
        int idx=columnLabelsToInt.get(columnLabel);
        return current[idx];
    }
    int getInt(int columnIndex) throws RuntimeException {
        if (isMissing(columnIndex)) throw new RuntimeException("The index does not exist");
        return Integer.parseInt(current[columnIndex]);
    }
    int getInt(String columnLabel) throws RuntimeException {
        if(isMissing(columnLabel)) throw new RuntimeException("The columnLabel does not exist");
        return Integer.parseInt(current[columnLabelsToInt.get(columnLabel)]);
    }
    long getLong(int columnIndex) throws RuntimeException {
        if (isMissing(columnIndex)) throw new RuntimeException("The index does not exist");
        return Long.parseLong(current[columnIndex]);
    }
    long getLong(String columnLabel) throws RuntimeException {
        if(isMissing(columnLabel)) throw new RuntimeException("The columnLabel does not exist");
        return Long.parseLong(current[columnLabelsToInt.get(columnLabel)]);
    }
    double getDouble(int columnIndex) throws RuntimeException {
        if (isMissing(columnIndex)) throw new RuntimeException("The index does not exist");
        return Double.parseDouble(current[columnIndex]);
    }
    double getDouble(String columnLabel) throws RuntimeException {
        if(isMissing(columnLabel)) throw new RuntimeException("The columnLabel does not exist");
        return Double.parseDouble(current[columnLabelsToInt.get(columnLabel)]);
    }
    LocalTime getTime(int columnIndes, String format) throws IOException {
        LocalTime time = LocalTime.parse(current[columnIndes], DateTimeFormatter.ofPattern(format));
        return time;
    }
    LocalDate getDate(int columnIndes, String format){
        LocalDate date = LocalDate.parse(current[columnIndes], DateTimeFormatter.ofPattern(format));
        return date;
    }

}


