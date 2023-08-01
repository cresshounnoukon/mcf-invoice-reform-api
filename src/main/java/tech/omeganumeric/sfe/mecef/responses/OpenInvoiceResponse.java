package tech.omeganumeric.sfe.mecef.responses;


import lombok.Data;

import java.util.Arrays;

@Data
public class OpenInvoiceResponse {
    private String totalSaleInvoiceCounter;
    private String totalCounter;
    private String error;


    public void fromString(String response) {
        if (response.replaceAll("\\u0003", "") != "") {
            String[] splitData = response.replaceAll("\\u0003", "").split(",");
            System.out.println("splitData = " + Arrays.toString(splitData));
            if (splitData.length == 2) {


                this.totalSaleInvoiceCounter = splitData[0];
                this.totalCounter = splitData[1];

            }
            if (splitData.length == 3) {


                this.totalSaleInvoiceCounter = splitData[0];
                this.totalCounter = splitData[1];
                this.error = splitData[2];
            }
        }

    }

}
