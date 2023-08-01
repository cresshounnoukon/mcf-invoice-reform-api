package tech.omeganumeric.sfe.mecef.responses;

import lombok.Data;

@Data
public class AddProductSellResponse
{
    private String  totalAmountWithAllTax;
    private String  totalAmountNoTaxe;
    private String  totalVATAmount;
    private String  totalTAmountSpecificTax;

    public void fromString(String addProductSellResponse)
    {   

        if(addProductSellResponse!=null){
            System.out.println("addProductSellResponse = " + addProductSellResponse);
            String[] splitData = addProductSellResponse.replaceAll("\\u0003", "").split(",");
            if(splitData.length>1){
                this. totalAmountWithAllTax = splitData[0];
            this. totalAmountNoTaxe = splitData[1];
            this. totalVATAmount = splitData[2];
            this. totalTAmountSpecificTax = splitData[3];
            }
        }
       


        


    }


}
