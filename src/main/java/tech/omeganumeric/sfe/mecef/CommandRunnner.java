package tech.omeganumeric.sfe.mecef;

import com.fazecast.jSerialComm.SerialPort;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CommandRunnner {


    SerialPort serialPort;
    private byte seq = 0x20;

    public CommandRunnner(String serialPortName) {
        try {
             serialPort = SerialPort.getCommPort(serialPortName);
            // serialPort.BaudRate = 115200;
            //  serialPort.PortName = serialPortName;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public String byteArrayToString(byte[] hashInBytes)  {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    private byte[] builCommand(byte commandName, String commandData) {
        // String commandData = "1,Jan,9999900000154,0.00,18.00,0.00,18.00,FV";
        int j = 0;

        byte[] command = commandData == null ? new byte[10] : new byte[10+commandData.length()];
        int index = 0;
        System.out.println("command.length = " + command.length);
        System.out.println("index = " + index);
        // SOH
        command[index++] = 0x01;
        System.out.println("index = " + index);
        // LEN 
        command[index++] = commandData != null ? (byte) (0x20 + commandData.getBytes().length + 4) : (byte) 0x24;


        // SEQ
        seq++;
        if (seq ==  0xff) seq = 0x20;
        command[index++] = seq;

        // CMD
        command[index++] = commandName;
       //  System.out.println("toDigit('1') = " + toDigit('1'));
        // DATA
        if (commandData != null) {
            byte[] commandDataBytes = commandData.getBytes(StandardCharsets.UTF_8);
            //  Encoding.ASCII.GetBytes(commandData);

            for (byte commandDataByte : commandDataBytes) {
                j++;
                command[index++] = commandDataByte;
            }
        }

        // AMB
        command[index++] = 0x05;
        // BCC
        //01 02 03 05
        //index 4
        byte[] chksum = calculateCheckSum(command, 1, index - 1);
        command[index++] = chksum[0];
        command[index++] = chksum[1];
        command[index++] = chksum[2];
        command[index++] = chksum[3];

        // ETX
        System.out.println("index = " + index);
        command[index++] = 0x03;


        return j == 0 ? command : subArray(command, 0, j + 10);
    }


    private byte[] calculateCheckSum(byte[] command, int start, int end) {
        //example: if sum is 0x15AC, returns array:
        //ret[0]=0x31 ret[1]=0x35 ret[2]=0x3A ret[3]=0x3C
        byte [] ends = new byte[1];


        // 24 21 c1 00
       // System.out.println("byteArrayToString(subArray(command, start,end)) = " + byteArrayToString(subArray(command,start, end+1)));

            byte total = 0;
            for (byte b : command) {
                if (b!=0x01){
                    total += b;
                }

            }

       //  System.out.println("TOTAL==>"+byteToHex( total));

        int sum =0;
        for (int i = start; i <= end; i++) {

             sum+=command[i] & 0xff;

        }


        byte[] ret = new  byte[4];
        ret[0] = (byte) (((sum >> 12) & 0x0F) + 0x30); //0x15AC -> 0x1 -> 0x1 -> 0x31
        ret[1] = (byte) (((sum >> 8) & 0x0F) + 0x30); //0x15AC -> 0x15 -> 0x5 -> 0x35
        ret[2] = (byte) (((sum >> 4) & 0x0F) + 0x30); //0x15A -> 0xA  ->         0x3A
        ret[3] = (byte) (((sum >> 0) & 0x0F) + 0x30); //0x15AC -> 0xC ->         0x3C
      //  System.out.println("byteArrayToString(subArray(command, start,end)) = " + byteArrayToString(subArray(ret,0, ret.length)));

        return ret;
    }


    public String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    public String executeCommand(byte cmd, String data) {
        try {
            byte[] command = builCommand(cmd, data);
           System.out.println("COMMAND =>"+ byteArrayToString(command));


            InputStream in;

            if (!serialPort.isOpen()) {
                serialPort.openPort();
                  System.out.println ("PORT IS OPENED");
            }


            byte readByte = 0x15;

            do {
                serialPort.writeBytes(hexStringToByteArray(byteArrayToString(command)), hexStringToByteArray(byteArrayToString(command)).length);
                serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
               // System.out.println("serialPort.bytesAvailable() = " + serialPort.bytesAvailable());
                in = serialPort.getInputStream();
                readByte = (byte) in.read();
            } while (readByte == 0x15);


            while (readByte == 0x16) {
                serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
              //  System.out.println("serialPort.bytesAvailable() = " + serialPort.bytesAvailable());
                in = serialPort.getInputStream();
                readByte = (byte) in.read();
            }

            System.out.print("readByte = " + readByte);

            int datalength = serialPort.bytesAvailable() > 0 ? serialPort.bytesAvailable() : 1;
            byte[] response = new byte[datalength + 1];
            int i = 1;

            response[0] = readByte;

            //   System.out.println ("SYN DATA LENGHT=>" + datalength);


                /*if (i>0)
                {
                      response = new byte[datalength+1];
                    response[0] = (byte) startByte;
                }*/


            byte[] nullResponse = new byte[1];
            nullResponse[0] = 0x00000000;


            int j = 0;
            boolean isDone = false;
            boolean canCountDataByte = false;
            String responseString="";




            while (!isDone) {

                byte b = (byte) in.read();

                if (b == 0x04) {
                    isDone = true;
                    canCountDataByte = false;
                }else{
                    if(i>=4){
                        responseString+=(char)b;
                    }
                }



//                if (b == 0x04) {
//                    canCountDataByte = false;
//                }
//
//                if (canCountDataByte) {
//                    j++;
//                    //responseString+=(char)b;
//                   //System.out.println("responseString = " + responseString);
//
//                }
//                if (b == cmd) {
//                    canCountDataByte = true;
//
//                }
//
//                if (b == 0x03) {
//                    isDone = true;
//                }

                response[i] = b;
                i++;

            }


            serialPort.closePort();


            System.out.println("RESPONSE =>" + byteArrayToString(response ));
           // System.out.println("responseString = " + responseString);

           // response = subArray(response, 4, j);
            //System.out.println("DATA ON  RESPONSE =>" + byteArrayToString(response));

            //responseString = responseString.substring(0, responseString.length() - 1);

            return responseString;
        } catch (Exception e) {
            System.out.println("ERREUR ===>" + e.getMessage());
           e.printStackTrace();
            serialPort.closePort();

        }

        return null;

    }

    private void verifyResponse(byte[] response) {
        byte[] beforeData = subArray(response, 0, 3);
        byte[] afterData = subArray(response, response.length - 13, response.length);
        byte[] data = subArray(response, 4, response.length - 12);


        System.out.println("before=> " + byteArrayToString(beforeData));
    }


    private void decodeCommandResponse(byte[] decode) {
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));

        }
        return data;
    }
    private byte[] subArray(byte[] data, int start, int length) {

        byte[] response = new byte[length];
        int j = 0;
        for (int i = start; i <length; i++) {

            response[j] = data[i];
            j++;
        }
        //   Array.Copy(data, start, response, 0, length);
        return response;
    }

    private String removeCharAtIndex(String str, int index) {
        return new StringBuilder(str).deleteCharAt(index).toString();
    }
}
