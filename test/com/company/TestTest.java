package com.company;

import com.company.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TestTest {
    @Test
    void Checks_coulmnLabel_string() throws Exception {
        CSVReader reader = new CSVReader("with-header.csv", ";", true);
        while (reader.next()) {
            int id = reader.getInt("id");
            String name = reader.get("imiê");
            String surname = reader.get("nazwisko");
            double nrmieszkania = reader.getDouble("nrmieszkania");
            System.out.printf(Locale.US, "%d %s %s %f ", id, name, surname, nrmieszkania);
            System.out.println(reader.getLong("nrdomu"));
        }
    }
    @Test
    void test()throws Exception{
        CSVReader reader = new CSVReader("admin-units.csv", ",", true);
        //id,parent,name,admin_level,population,area,density,x1,y1,x2,y2,x3,y3,x4,y4
        int counter=0;
        while (reader.next() ) {
            int id = reader.getInt("id");
            System.out.printf(Locale.US, "%d ", id);

            if (reader.isMissing("parent")) {    //parent
                System.out.printf(Locale.US, ",");
            } else {
                int parent = reader.getInt("parent");
                System.out.printf(Locale.US, "%d, ", parent);
            }

            //String name = reader.get("name");

            if (reader.isMissing("admin_level")) {    //parent
                System.out.printf(Locale.US, ",");
            } else {
                int admin_level = reader.getInt("admin_level");
                System.out.printf(Locale.US, "%d, ", admin_level);
            }

            if (reader.isMissing("population")) {    //parent
                System.out.printf(Locale.US, ",");
            } else {
                int population = reader.getInt("population");
                System.out.printf(Locale.US, "%d, ", population);
            }

            if (reader.isMissing("area")) {    //parent
                System.out.printf(Locale.US, ",");
            } else {
                double area = reader.getDouble("area");
                System.out.printf(Locale.US, "%f, ", area);
            }

            if (reader.isMissing("density")) {    //parent
                System.out.printf(Locale.US, ",");
            } else {
                double density = reader.getDouble("density");
                System.out.printf(Locale.US, "%f, ", density);
            }

            double x1=reader.getDouble("x1");
            System.out.print(x1+", ");
            double y1=reader.getDouble("y1");
            System.out.print(y1+", ");
            double x2=reader.getDouble("x2");
            System.out.print(x2+", ");
            double y2=reader.getDouble("y2");
            System.out.print(y2+", ");
            double x3=reader.getDouble("x3");
            System.out.print(x3+", ");
            double y3=reader.getDouble("y3");
            System.out.print(y3+", ");
            double x4=reader.getDouble("x4");
            System.out.print(x4+", ");
            double y4=reader.getDouble("y4");
            System.out.print(y4+"\n");
            counter++;
        }
    }
    @Test
    void Checks_coulmnLabel_index() throws IOException, RuntimeException {
        CSVReader reader = new CSVReader("with-header.csv",";",true);
        while(reader.next()) {
            int id = reader.getInt(0);
            String name = reader.get(1);
            String surname = reader.get(2);
            System.out.printf(Locale.US, "%d %s %s ", id, name, surname);
        }
    }
    @Test
    void different_types() throws IOException, RuntimeException {
        CSVReader reader = new CSVReader("accelerator.csv",";",true);
        while(reader.next()) {
            System.out.print(reader.getDouble(0)+", ");
            System.out.print(reader.getDouble(1)+", ");
            System.out.print(reader.getDouble(2)+", ");
            System.out.print(reader.getLong(6)+", ");
            System.out.print(reader.getInt(7)+"\n");
        }
    }

    @Test
    void no_header() throws IOException, RuntimeException {
        CSVReader reader = new CSVReader("no-header.csv",";",true);
        while(reader.next()) {
            int id = reader.getInt(0);
            String name = reader.get(1);
            String surname = reader.get(2);
            System.out.printf(Locale.US, "%d %s %s ", id, name, surname);
            System.out.println(reader.getDouble(4)+" "+reader.getLong(5));
        }
    }

    @Test
    void Checks_wrong_columns() throws IOException{
        CSVReader reader = new CSVReader("with-header.csv", ";", true);
        while (reader.next()) {
            try{
                int id = reader.getInt("Iddd");
                fail("abc");
            }catch(RuntimeException e){

            }
        }
    }
    @Test
    void Checks_wrong_index() throws IOException{
        CSVReader reader = new CSVReader("with-header.csv", ";", true);
        while (reader.next()) {
            try{
                int id = reader.getInt(20);
                fail("abc");
            }catch(RuntimeException e){

            }
        }
    }
    @Test
    void Missing_values() throws IOException {
        CSVReader reader = new CSVReader("missing-values.csv", ";", true);
        while (reader.next()) {
            System.out.printf(Locale.US, "%d, ", reader.getInt("id"));    //id
            if(reader.isMissing("parent")){    //parent
                System.out.printf(Locale.US, ",");
            }else {
                int parent = reader.getInt("parent");
                System.out.printf(Locale.US, "%d, ", parent);
            }
            System.out.printf(Locale.US,"%s, ", reader.get("name"));   //name
            System.out.printf(Locale.US,"%d, ", reader.getInt("admin_level"));  //admin_level
            if(reader.isMissing("population")){    //population
                System.out.printf(Locale.US, ",");
            }else {
                System.out.printf(Locale.US,"%f, ", reader.getDouble("population"));
            }
            System.out.printf(Locale.US,"%f, ", reader.getDouble("area"));   //area
            if(reader.isMissing("density")){    //density
                System.out.printf(Locale.US, ",\n");
            }else {
                System.out.printf(Locale.US,"%f \n", reader.getDouble("density"));
            }
        }
    }
    @Test
    void quote() throws IOException, RuntimeException {
        CSVReader reader = new CSVReader("titanic-part.csv",",",true);
        while(reader.next()) {
            int id = reader.getInt(0);
            String name = reader.get(3);
            String ticket = reader.get(8);

            System.out.printf(Locale.US, "%d, ", id);
            System.out.printf(Locale.US, "%s, ",name);
            System.out.printf(Locale.US, "%s \n",ticket);
        }
    }
    @Test
    void String_text() throws IOException {
        String text = "a,b,c\n123.4,567.8,91011.12";
        CSVReader reader = new CSVReader(new StringReader(text),",",true);
        while(reader.next()) {
            double id = reader.getDouble(0);
            double iid = reader.getDouble(1);
            double iiid = reader.getDouble(2);
            System.out.printf(Locale.US, "%f, ", id);
            System.out.printf(Locale.US, "%f, ", iid);
            System.out.printf(Locale.US, "%f, ", iiid);

        }
    }

    @Test
    void Time()throws IOException {
        String text = "12:55:56, 12:55:76, 12:55:98\n12:55:50, 12:55:73, 12:55:97";
        CSVReader reader = new CSVReader(new StringReader(text),",",false);

        while(reader.next()) {
            System.out.println(reader.getTime(0,"HH:mm:ss"));
            System.out.println(reader.getTime(1,"HH:mm:ss"));
            System.out.println(reader.getTime(2,"HH:mm:ss"));
        }
    }
    @Test
    void Date()throws IOException {
        String text = "2017-12-30, 2017-11-31, 2017-01-30\n2018-11-30, 2019-11-30, 2017-09-30";
        CSVReader reader = new CSVReader(new StringReader(text),",",false);

        while(reader.next()) {
            System.out.println(reader.getDate(0,"yyyy-MM-dd"));
            System.out.println(reader.getDate(1,"yyyy-MM-dd"));
            System.out.println(reader.getDate(2,"yyyy-MM-dd"));
        }

    }

}