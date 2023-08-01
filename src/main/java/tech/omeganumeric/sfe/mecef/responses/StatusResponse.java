package tech.omeganumeric.sfe.mecef.responses;

import lombok.Data;


import java.util.HashSet;
import java.util.Set;

@Data
public class StatusResponse {
    private String deviceNo;
    private String taxPayerIdentifier;

    private Company company;
    private String dateFromDevice;
    private String totalCounter;
    private String totalSaleInvoiceCounter;
    private String totalGivingBackInvoiceCounter;
    private Set<TaxGroup> taxGroups;


    public void fromString(String statusResponse) {
        System.out.println("statusResponse = " + statusResponse);
        String[] splitData = statusResponse.replaceAll("\\u0003", "").split(",");

        this.company = new Company(splitData[0], splitData[1]);
        //   this.   deviceNo = splitData[0];
        // this.   taxPayerIdentifier = splitData[1];
        this.dateFromDevice = splitData[2];
        this.totalCounter = splitData[3];
        this.totalSaleInvoiceCounter = splitData[4];
        this.totalGivingBackInvoiceCounter = splitData[5];
        //  this.   totalCounter =   String.Parse(splitData[6]);
        // Tax group
        TaxGroup taxGroupA = new TaxGroup();
        taxGroupA.setGroupeCode('A');
        taxGroupA.setTaxValue(Double.parseDouble((splitData[6])));
        // Tax group
        TaxGroup taxGroupB = new TaxGroup();
        taxGroupB.setGroupeCode('B');
        taxGroupB.setTaxValue(Double.parseDouble(splitData[7]));

        // Tax group
        TaxGroup taxGroupC = new TaxGroup();
        taxGroupC.setGroupeCode('C');
        taxGroupC.setTaxValue(Double.parseDouble(splitData[8]));
        // Tax group
        TaxGroup taxGroupD = new TaxGroup();
        taxGroupD.setGroupeCode('D');

        taxGroupD.setTaxValue(Double.parseDouble((splitData[9])));

        this.taxGroups = new HashSet< >();
        this.taxGroups.add(taxGroupA);
        this.taxGroups.add(taxGroupB);
        this.taxGroups.add(taxGroupC);
        this.taxGroups.add(taxGroupD);


    }


}
