/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.ralfh.medico.libs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Ralfh
 */
public class DateUtil {

    public static String formataData(LocalDate data) {        
        String out;
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            out = data.format(format);            
        }
        catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", data);
            throw exc;
        }        
        
        return out;        
    }

    public static String formataDataExtenso(LocalDate data) {
        String out;
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy, EEEE");
            out = data.format(format);            
        }
        catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", data);
            throw exc;
        }        
        
        return out;        
    }
    

    
    public static Date preparaDataIn(String data) {

        String dia = data.substring(8,10);
        String mes = data.substring(5,7);
        String ano = data.substring(0,4);
        String datas = dia+"-"+mes+"-"+ano;

        Date datar = null;
        SimpleDateFormat dataformato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            datar = dataformato.parse(datas);
        } catch(ParseException e) {
            System.err.println("Formato de data imcompatível");
        }        
        return datar;
    }    

//    public static Date preparaDataOut(Date data) {

/*        String sData = data.
        SimpleDateFormat dataformato = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String sData = dataformato.format(data);
            
            
        } catch(IllegalArgumentException e) {
            System.err.println("Formato de data imcompatível");
        }        
        return datar; */
//    }    

/*    
    public static Date getHoje() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            System.err.println("Formato de data imcompatível");
        }
        return date;
    }
*/
    
/*
    public static java.sql.Date castDataToSqlData(Date data)  {  
      java.sql.Date dataS;
      try {  
        dataS = new java.sql.Date(data.getTime());        
        return dataS;
      }  
      catch(Exception ex)  
      {   
          throw new RuntimeException(ex);  
      }  
    }      
*/    
    
    
    
}
