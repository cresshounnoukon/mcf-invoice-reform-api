package tech.omeganumeric.sfe.mecef.responses;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@Data
public class MCFServerResponse
{
    private   String totalSuccessfullUploaded;
    private   String totalDocument;
    private   String dateToLastContactToServer;
    private   String error;




    public void fromString(  String data)
    {
        System.out.println("data = " + data);
          String[] splitData = data.replaceAll("\\u0003", "").split(",");

        totalSuccessfullUploaded = splitData[0];
        totalDocument = splitData[1];
        dateToLastContactToServer = splitData[2];
        error = splitData[3];

    }

    public  Long  getLastSynchroDay(){
        SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {

            Date date1 = dtf.parse(dateToLastContactToServer);
            Date date2 = new Date();
           return  Duration.between(date1.toInstant(), date2.toInstant()).toDays();

        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }

    }


}
