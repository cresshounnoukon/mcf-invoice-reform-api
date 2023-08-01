package tech.omeganumeric.sfe.payload.request;

import lombok.Data;
import tech.omeganumeric.sfe.config.Utils;

@Data
public class Operator {
    private String id;
    private String name;

    public String convertToString() {
        return id + "," +Utils.replaceSpeciacharAccordingMecef( name);

    }

 
    
}
