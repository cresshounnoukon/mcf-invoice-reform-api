package tech.omeganumeric.sfe.config;

import java.text.Normalizer;

import org.apache.commons.lang3.StringUtils;

public class Utils {
    public  static String      replaceSpeciacharAccordingMecef(String data){
        
        data=StringUtils.stripAccents(data);
        
        data= Normalizer.normalize(data.toLowerCase(), Normalizer.Form.NFD);
         data= data.
        replace("\r", "^xa;")
        .replace("\n ", "^xd;")
        .replace(",", "^x2c;")
        .replace("<", "^lt;")
        .replace(">", "^gt;")
        .replace("&", "^amp;")
        .replace("ï", "i")
        .replace("é", "e")
        .replace("è", "e")
        .replace("ê", "e")
        .replace("ë", "e")
        .replace("à", "a")
        .replace("â", "a")
        .replace("ä", "a")
        .replace("Œ", "oe")
        .replace("œ", "oe")
         

       // .replaceAll("[^\\p{ASCII}]", "")
     //   .replaceAll("\\p{M}", "")

        ; 
       
        return data.toUpperCase();
    }
    
}
