package tech.omeganumeric.sfe.mecef.requests;

import lombok.Data;
import tech.omeganumeric.sfe.payload.request.Client;
import tech.omeganumeric.sfe.payload.request.Operator;

@Data
public class OpenSellInvoiceRequest
{
    private  String aib;
    private Operator operator;
    private String ifu;
    private String taxGroupString ;
    private String typeInvoice;
    private String mecefReference;
    private Client customer;

    public OpenSellInvoiceRequest()
    {
    }

    public OpenSellInvoiceRequest(Operator operator, String  ifu, String taxGroupString, String typeInvoice, String mecefReference, Client customer, String aib)
    {
        this.operator = operator;
        this.ifu = ifu;
        this.taxGroupString = taxGroupString;
        this.typeInvoice = typeInvoice;
        this.mecefReference = mecefReference;
        this.customer = customer;
        this.aib = aib;
    }

  

    public String  convertToString()
    {
        String  result = operator.convertToString()
                +
                ","
                + ifu+","
                + taxGroupString
                +","
                +
                typeInvoice;

      if(mecefReference!=null){
          if (!mecefReference.equals("")
          )
          {
              result +=  "," +mecefReference;
          }
      }

        if (customer != null)
        {
            result += "," + customer.convertToString( );
        }

        if (aib!=null){
            result += ",";
            result += aib;
        }
       System.out.println("result = " + result);


        return result;
    }

    @Override
    public String toString() {
        return "OpenSellInvoiceRequest{" +
                "aib='" + aib + '\'' +
                ", operator=" + operator +
                ", ifu='" + ifu + '\'' +
                ", taxGroupString='" + taxGroupString + '\'' +
                ", typeInvoice='" + typeInvoice + '\'' +
                ", mecefReference='" + mecefReference + '\'' +
                ", customer=" + customer +
                '}';
    }
}
