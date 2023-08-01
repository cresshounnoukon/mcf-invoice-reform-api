package tech.omeganumeric.sfe.mecef.responses;

import lombok.Data;

@Data

public class TaxGroupSubTotal {
    private char group;
    private String name;
    private int totalAmount;
    private int totalVATAmount;


    public TaxGroupSubTotal(char group, String name, int totalAmount, int totalVATAmount) {
        this.group = group;
        this.name = name;
        this.totalAmount = totalAmount;
        this.totalVATAmount = totalVATAmount;
    }

    public TaxGroupSubTotal() {
    }

//    public char getGroup() {
//        return group;
//    }
//
//    public void setGroup(char group) {
//        this.group = group;
//    }
//
//    public String getName() {
//        return name.replaceAll("\\u0003", "");
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getTotalAmount() {
//        return totalAmount.replaceAll("\\u0003", "");
//    }
//
//    public void setTotalAmount(String totalAmount) {
//        this.totalAmount = totalAmount;
//    }
//
//    public String getTotalVATAmount() {
//        return totalVATAmount.replaceAll("\\u0003", "");
//    }
//
//    public void setTotalVATAmount(String totalVATAmount) {
//        this.totalVATAmount = totalVATAmount;
//    }
//
    public int getHorsTax() {
        return totalAmount - totalVATAmount;
    }



}
