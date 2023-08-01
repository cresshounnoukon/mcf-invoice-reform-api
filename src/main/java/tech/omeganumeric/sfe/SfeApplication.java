package tech.omeganumeric.sfe;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.omeganumeric.sfe.mecef.MecefService;
import tech.omeganumeric.sfe.mecef.responses.StatusResponse;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static java.lang.System.*;

@SpringBootApplication

public class SfeApplication implements CommandLineRunner {


    @Autowired
    private MecefService mecefService;

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SfeApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {



        try {
            mecefService.checkRightSerialPort();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            StatusResponse response = mecefService.checkWithCompanyInfoStatus();
            if (response == null) {
                out.println("***********************************");
                out.println("***********************************");
                out.println("***********************************");
                out.println("***********************************");
                out.println("Erreur = =====>> IFU dans la machine est différente de l'ifu dans l'application");
                out.println("***********************************");
                out.println("***********************************");
                out.println("***********************************");
                out.println("***********************************");

            } else {


            }


        } catch (Exception e) {
            e.printStackTrace();
            out.println("***********************************");
            out.println("***********************************");
            out.println("***********************************");
            out.println("***********************************");
            out.println("Erreur = =====>>  " + e.getLocalizedMessage());
            out.println("***********************************");
            out.println("***********************************");
            out.println("***********************************");
            out.println("***********************************");

        }


    }

    public static String[] getPortNames() {
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] result = new String[ports.length];
        for (int i = 0; i < ports.length; i++) {
            result[i] = ports[i].getSystemPortName();
        }
        return result;
    }


}
