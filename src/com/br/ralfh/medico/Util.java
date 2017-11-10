
package com.br.ralfh.medico;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


/**
 *
 * @author ralfh
 */
public class Util {
    
    public static Date dHoje() {
        LocalDateTime data = LocalDateTime.now();
        Instant instant = data.atZone(ZoneId.systemDefault()).toInstant();
        Date hoje = Date.from(instant);        
        return hoje;
    }

    public static LocalDate ldHoje() {
        return LocalDate.now();
    }
    
    public static Calendar calDate(LocalDateTime pDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Util.udate(pDate));
        return cal;
    }
    
    public static Date udate(LocalDateTime pDate) {
//        LocalDateTime data = LocalDateTime.of(2013, Month.MAY, 29, 0, 0);
        Instant instant = pDate.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);                
        return data;
    }    
    
    public static Date udate(LocalDate pDate) {
        Instant instant = pDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);                
        return data;
    }    
    
    
    public static Date udate(Calendar pDate) {
        Date data = pDate.getTime();        
        return data;
    }
    
    public static LocalDateTime ldt(Date pDate) {        
        Instant instant = Instant.ofEpochMilli(pDate.getTime());
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());        
        return ldt;
    }
    
    public static LocalDate ld(Date pDate) {        
        Instant instant = Instant.ofEpochMilli(pDate.getTime());
        LocalDate ld = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();        
        return ld;
    }
    
    public static LocalTime lt(Date pDate) {
        Instant instant = Instant.ofEpochMilli(pDate.getTime());
        LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
        return res;
    }

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
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd ' de ' MMMM 'de' yyyy");
            out = data.format(format);            
        }
        catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", data);
            throw exc;
        }        
        
        return out;        
    }
    
    public static String formataDataExtenso(Date data) {
        String out;
        LocalDate ldata = Util.ld(data);
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd ' de ' MMMM 'de' yyyy");
            out = ldata.format(format);            
        }
        catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", data);
            throw exc;
        }                
        return out;        
    }

    public static String formataData(Date data) {
        String out;
        LocalDate ldata = Util.ld(data);
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            out = ldata.format(format);            
        }
        catch (DateTimeException exc) {
            System.out.printf("%s can't be formatted!%n", data);
            throw exc;
        }        
        
        return out;        
    }
    
    public static String insereBarrasData(String data) {
        String datas = "";
        if (data.length() == 8) {
            String dia = data.substring(0,2);
            String mes = data.substring(2,4);
            String ano = data.substring(4,8);
            datas = dia+"/"+mes+"/"+ano;
        } else {
            Controller.ShowDialog("EX", "Data inválida", null,JDocplus.getMainStage());
        }
        return datas;
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
    
    public static LocalDate strDate(String strData) {
        Date data = null;
        SimpleDateFormat dataformato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            data = dataformato.parse(strData);
        } catch (ParseException exc) {
            data = null;
        }
        return ld(data);
    }
    
    private static String paraASCII(int num) {
        int aAscii = (int) 'a';
        char prim = (char)(aAscii+(num/16));
        char seg = (char)(aAscii+(num%16));
        return Character.toString(prim).concat(Character.toString(seg));
    }
    
    private static int paraInt(char ch1, char ch2) {
        int aAscii = (int) 'a';
        int ich1 = (int) ch1;
        int ich2 = (int) ch2;
        int enc = ((ich1-aAscii)*16)+ich2-aAscii;
        return enc;
    } 
    
    public static String BigDecToStr(BigDecimal bigdec) {
        String pattern = "#.##0,00";
        String valor = bigdec.toPlainString();
        return String.format(valor, pattern);     
    }
    
    public static BigDecimal StrToBigDec(String valor) {
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        String pattern = "#,##0.0#";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        decimalFormat.setParseBigDecimal(true);
        
        BigDecimal bigdec;        
        try {
            bigdec = (BigDecimal) decimalFormat.parse(valor);
        } catch (ParseException ex) {
            bigdec = new BigDecimal(BigInteger.ZERO);
        }
        return bigdec;
    }

    public static String criptografa(String str) {    
        int tam; int ant;
        String cifra;
        tam = str.length();
        int char0 = (int) str.charAt(0);
        ant = (char0^255)%256;
        cifra = paraASCII(ant);
        for(int i=1;i<tam;i++) {
            int charI = (int) str.charAt(i);
            ant = (ant + charI + 22) % 256;
            cifra = cifra + paraASCII(ant);
        }
        return cifra;
    }

    public static String decriptografa(String str) {
        int tam; int ant; int aux;
        String claro;

        tam = str.length()/2;
        ant = paraInt(str.charAt(0),str.charAt(1));
        char ch = (char) (ant^255);
        claro = Character.toString(ch);
        for(int i=1;i<tam;i++) {                  
            aux = paraInt(str.charAt(i*2),str.charAt(i*2+1));
            claro = claro.concat(Character.toString((char) ((char) (aux-ant-22) % 256)));// String.valueOf((aux-ant-22) % 256);
            ant = aux;
        }
        return claro;
    }
}
