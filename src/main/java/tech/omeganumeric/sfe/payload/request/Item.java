package tech.omeganumeric.sfe.payload.request;

import lombok.Data;
import tech.omeganumeric.sfe.config.Utils;

@Data
public class Item {
    private String code;
    private String name;
    private int price;
    private double quantity;
    private String taxGroup;
    private int taxSpecific;

   

    public String convertToString() {

        String result;

        result = "[ "+code+" ]" +name;
        result=Utils.replaceSpeciacharAccordingMecef(result); 
        result+= " \t";
        result += taxGroup + "";


        result += price + "*" + quantity;


       if (taxSpecific>0){
           result += ";" + quantity * taxSpecific+"," ;
       }


        //result += "\t" + originalPrice + "," + priceModification;
 
        return result;
    }
}
