package tech.omeganumeric.sfe.mecef.responses;


import lombok.Data;

@Data
public class CloseInvoiceResponse
{
    private  String   totalSaleInvoiceCounter;
    private  String   totalCounter;
    private  String   typeInvoice;
    private  String   dateFromDevice;
    private  String   deviceNo;
    private  String   taxPayerIdentifier;
    private  String   signature;
    private  String   qrCode;


  

    public void fromString( String response)
    {
         String[] splitData = response.replaceAll("\\u0003", "").split(",");
          totalSaleInvoiceCounter= splitData[0];
          totalCounter= splitData[1];
          typeInvoice=splitData[2];
          dateFromDevice=splitData[3];
          deviceNo=splitData[4];
          taxPayerIdentifier=splitData[5];
          signature=splitData[6];
          qrCode= (  signature==null)?null: "F;"+  deviceNo+";"+  signature+";"+  taxPayerIdentifier+";"+  dateFromDevice ;
    }




}
