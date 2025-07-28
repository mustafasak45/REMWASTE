package com.opensource.utils;

import org.apache.commons.csv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    public void updateCsvByRow(List<List<String>> csvDataListByRow, String fileLocation, boolean isAppend){
        try {
            BufferedWriter writer = createWriter(fileLocation, isAppend);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                    .setQuoteMode(QuoteMode.ALL)
                    .setQuote('"')
                    .setDelimiter(',')
                    .setTrim(true).build());

            for(List<String> dataRow: csvDataListByRow){
                csvPrinter.printRecord(dataRow);
            }
            csvPrinter.flush();
            csvPrinter.close();
            if(writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateCsvByColumn(List<List<String>> csvDataListByColumn, String fileLocation,boolean isAppend){
        List<List<String>> newList = new ArrayList<>();
        int headerSize = csvDataListByColumn.get(0).size();
        List<String> tempList = new ArrayList<>();
        for(int i=0;i<csvDataListByColumn.size();i++){
            for(int y =0;y<headerSize;y++){
                tempList.add(y,csvDataListByColumn.get(i).get(y));
            }
            newList.add(i,tempList);
        }
        for(List<String> list: newList){
            updateCsvByRow(newList,fileLocation,isAppend);
        }
    }


    public void createCsv(String[] header, List<List<String>> csvDataList, String fileLocation, boolean isAppend) {

        try {
            BufferedWriter writer = createWriter( fileLocation, isAppend);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                    .setHeader(header)
                    .setQuoteMode(QuoteMode.ALL)
                    .setQuote('"')
                    .setDelimiter(',')
                    .setTrim(true).build());

            for (List<String> list : csvDataList) {
                csvPrinter.printRecord(list);
            }
            csvPrinter.flush();
            csvPrinter.close();
            if(writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCsv(List<String> csvDataList, String fileLocation, boolean isAppend) {

        try {
            BufferedWriter writer = createWriter(fileLocation, isAppend);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.builder()
                    .setQuoteMode(QuoteMode.ALL)
                    .setQuote('"')
                    .setDelimiter(',')
                    .setTrim(true).build());

            csvPrinter.printRecord(csvDataList);
            csvPrinter.flush();
            csvPrinter.close();
            if(writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> readCsv(String fileLocation){

        CSVParser csvParser = null;
        try {
            csvParser = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setQuoteMode(QuoteMode.ALL)
                    .setQuote('"')
                    .setDelimiter(',')
                    .setTrim(true).build()
                    .parse(Files.newBufferedReader(Paths.get(fileLocation), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<String>> csvList = new ArrayList<List<String>>();
        // System.out.println(csvParser.getHeaderNames());
        csvList.add(csvParser.getHeaderNames());
        for (CSVRecord record : csvParser) {
            List<String> list = new ArrayList<String>();
            for (int k=0; k < record.size(); k++) {
                list.add(record.get(k));
            }
            csvList.add(list);
        }
        try {
            if (csvParser != null)
                csvParser.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return csvList;
    }

    public static BufferedWriter createWriter(String dir, boolean appendCondition) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(dir, StandardCharsets.UTF_8, appendCondition));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public static BufferedReader createReader(String dir) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(dir), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    public void writeFile(String text, String fileName){

        try {
            BufferedWriter writer3 = createWriter(fileName,false);
            writer3.append(text);writer3.newLine();
            Thread.sleep(1000);
            writer3.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
