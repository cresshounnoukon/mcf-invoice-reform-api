package tech.omeganumeric.sfe.payload.request;

import lombok.Data;
import tech.omeganumeric.sfe.config.Utils;

@Data
public class Client {
    private String ifu;
    private String name;
    private String contact;
    private String address;

    public String convertToString() {
        return ifu + "," + Utils.replaceSpeciacharAccordingMecef(name);


    }
    
    
}
