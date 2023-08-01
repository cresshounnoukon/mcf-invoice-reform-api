package tech.omeganumeric.sfe.mecef.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;



import java.util.HashSet;
import java.util.Set;

@Data
public class SubTotalResponse {
    private int totalInvoiceAmount;
    private int totalAibAmount;
    private int totalSpecificTaxAmount;
    private Set<TaxGroupSubTotal> taxGroupSubTotals = new HashSet<>();

    @JsonIgnore
    public String TotalInvoiceAmount;


    public SubTotalResponse(int totalInvoiceAmount, int totalAibAmount, int totalSpecificTaxAmount, Set<TaxGroupSubTotal> taxGroupSubTotals, String totalInvoiceAmount1) {
        this.totalInvoiceAmount = totalInvoiceAmount;
        this.totalAibAmount = totalAibAmount;
        this.totalSpecificTaxAmount = totalSpecificTaxAmount;
        this.taxGroupSubTotals = taxGroupSubTotals;
        TotalInvoiceAmount = totalInvoiceAmount1;
    }

    public SubTotalResponse() {
    }



    public int getTotalSpecificTaxAmount() {
        return totalSpecificTaxAmount;
//        try {
//            return Integer.parseInt(totalSpecificTaxAmount.replaceAll("\\u0003", "")) ;
//
//        } catch (Exception e) {
//            return 0;
//        }
    }

    public void fromString(String response) {
        System.out.println("response = " + response);
        String[] splitData = response.replaceAll("\\u0003", "").split(",");

        this.totalInvoiceAmount = parseToInt(splitData[0].replaceAll("\\u0003", ""));

        TaxGroupSubTotal subTotalA = new TaxGroupSubTotal('A', "EXONERE ", parseToInt(splitData[1]), parseToInt(splitData[7]));
        TaxGroupSubTotal subTotalB = new TaxGroupSubTotal('B', "TVA B(18%)", parseToInt(splitData[2]), parseToInt(splitData[8]));
        TaxGroupSubTotal subTotalC = new TaxGroupSubTotal('C', "TVA C(0 %)", parseToInt(splitData[3]), parseToInt(splitData[9]));
        TaxGroupSubTotal subTotalD = new TaxGroupSubTotal('D', " TVA D(18%)", parseToInt(splitData[4]), parseToInt(splitData[10]));
        TaxGroupSubTotal subTotalE = new TaxGroupSubTotal('E', "REGIME FISCAL TPS (E)", parseToInt(splitData[5]), parseToInt(splitData[11]));
        TaxGroupSubTotal subTotalF = new TaxGroupSubTotal('F', "RESERVE(F)", parseToInt(splitData[6]), parseToInt(splitData[12]));

        this.taxGroupSubTotals.add(subTotalA);
        this.taxGroupSubTotals.add(subTotalB);
        this.taxGroupSubTotals.add(subTotalC);
        this.taxGroupSubTotals.add(subTotalD);
        this.taxGroupSubTotals.add(subTotalE);
        this.taxGroupSubTotals.add(subTotalF);


        this.totalAibAmount = parseToInt(splitData[13]);


        this.totalSpecificTaxAmount = parseToInt(splitData[14]);


    }


    public int parseToInt(String value) {
        try {
            value.replaceAll("\\u0003", "");
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }


}
