package tech.omeganumeric.sfe.mecef.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MecefReference   {

    private String totalSaleInvoiceCounter;
    private String totalCounter;
    private String typeInvoice;
    private String dateFromDevice;
    private String deviceNo;
    private String taxPayerIdentifier;
    private String signature;
    private String qrCode;

    @JsonIgnore

    private String subtotal;


    private SubTotalResponse subTotals;



    public String convertToString()
    {
        return deviceNo + "-" + totalCounter;
    }



    public  String getHumanDate() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss");
       Date date= simpleDateFormat1.parse(dateFromDevice);
       return  simpleDateFormat.format(date);
    }
    public void fromString(String response, SubTotalResponse subTotalResponse)
    {
        String[] splitData = response.split(",");
        totalSaleInvoiceCounter= splitData[0];
        totalCounter= splitData[1];
        typeInvoice=splitData[2];
        dateFromDevice=splitData[3];
        deviceNo=splitData[4];
        taxPayerIdentifier=splitData[5];
        if(splitData.length==7){
            signature=splitData[6];
            qrCode= (  signature==null)?null: "F;"+  deviceNo+";"+  signature+";"+  taxPayerIdentifier+";"+  dateFromDevice ;

        }

        Gson gson = new Gson();
        Object src;
        subtotal= gson.toJson(subTotalResponse);

    }

    public String getRefundReference(){
        return  deviceNo +"-"+totalCounter;
    }

    public String getMecefCounterNumber(){
        return  totalSaleInvoiceCounter+" / "+totalCounter+" "+ typeInvoice;
    }


    public String getSplitedSignature(){
       if(signature!=null){
           StringBuffer str = new StringBuffer(signature);

           str.insert(4,"-");
           str.insert(9,"-");
           str.insert(14,"-");
           str.insert(19,"-");
           str.insert(24,"-");

           System.out.println("str = " + str.toString());

           return str .toString().replaceAll("\\u0003", "");
       }
       return  null;
    }


    public SubTotalResponse getSubTotals() {
        Gson gson = new Gson();

        return gson.fromJson(subtotal, SubTotalResponse.class) ;


    }

    public  byte[] getQRCodeImage( ) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCode, BarcodeFormat.QR_CODE, 100, 100);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }


    public String generateBase64Image()
    {
        return Base64.encodeBase64String(this.getQRCodeImage());
    }


}
