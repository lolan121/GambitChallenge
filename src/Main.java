import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {

    public static int decimalIntToBinaryInt(int decimalValue){
        return Integer.parseInt(Integer.toBinaryString(decimalValue));
    }

    public static void main(String[] args) {



        //Lower byte first when reading multiple registers

        //Certain discrepancies are found in the registers table documentation, e.g. register 62 is listed twice,
        //each time with a different variable name. Variable name "Current input at AI3" is listed three times. Assumptions
        //regarding such instances are mentioned along with the value correlating to the variable in question.

        //Real4 = Float


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;
        try {
            URL url = new URL("http://tuftuf.gambitlabs.fi/feed.txt");
            is = url.openStream ();
            byte[] byteChunk = new byte[4096];
            int n;

            while ( (n = is.read(byteChunk)) > 0 ) {
                baos.write(byteChunk, 0, n);
            }

            is.close();
        }
        catch (IOException e) {
            e.printStackTrace ();
        }

        byte[] binaryData = baos.toByteArray();

        String data = new String(binaryData, StandardCharsets.UTF_8);

        Scanner scanner = new Scanner(data);



        int registerNr = 1;

        System.out.println("Date and time: " + scanner.nextLine());

        while(scanner.hasNext()){
            if(registerNr<51 || (registerNr >= 77 && registerNr <= 89) || (registerNr >= 97 && registerNr <= 99)){
                //Handles variables contained in two registers
                String line = scanner.nextLine();
                String firstRegisterValue = line.substring(line.indexOf(":") + 1);
                line = scanner.nextLine();
                String secondRegisterValue = line.substring(line.indexOf(":") + 1);
                String variableName = "";
                String variableValue = "";

                switch(registerNr){
                    case 1:
                        variableName = "Flow Rate (m^3/h)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 3:
                        variableName = "Energy Flow Rate (m/s)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 5:
                        variableName = "Velocity (m/s)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 7:
                        variableName = "Fluid sound speed (m/s)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 9:
                        variableName = "Positive accumulator (Unit selected by M31 and depends on totalizer multiplier)";
                        variableValue = thirtyTwoBitBinaryToSignedInteger(secondRegisterValue,firstRegisterValue);
                        break;
                    case 11:
                        variableName = "Positive accumulator decimal fraction";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 13:
                        variableName = "Negative accumulator (Unit selected by M31 and depends on totalizer multiplier)";
                        variableValue = thirtyTwoBitBinaryToSignedInteger(secondRegisterValue,firstRegisterValue);
                        break;
                    case 15:
                        variableName = "Negative accumulator decimal fraction";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 17:
                        variableName = "Positive energy accumulator";
                        variableValue = thirtyTwoBitBinaryToSignedInteger(secondRegisterValue,firstRegisterValue);
                        break;
                    case 19:
                        variableName = "Positive energy accumulator decimal fraction";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 21:
                        variableName = "Negative energy accumulator";
                        variableValue = thirtyTwoBitBinaryToSignedInteger(secondRegisterValue,firstRegisterValue);
                        break;
                    case 23:
                        variableName = "Negative energy accumulator decimal fraction";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 25:
                        variableName = "Net accumulator";
                        variableValue = thirtyTwoBitBinaryToSignedInteger(secondRegisterValue,firstRegisterValue);
                        break;
                    case 27:
                        variableName = "Net accumulator decimal fraction";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 29:
                        variableName = "Net energy accumulator";
                        variableValue = thirtyTwoBitBinaryToSignedInteger(secondRegisterValue,firstRegisterValue);
                        break;
                    case 31:
                        variableName = "Net energy accumulator decimal fraction";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 33:
                        variableName = "Temperature #1/inlet (C)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 35:
                        variableName = "Temperature #2/inlet (C)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 37:
                        variableName = "Analog input AI3";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 39:
                        variableName = "Analog input AI4";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 41:
                        variableName = "Analog input AI5";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 43:
                        variableName = "Current input at AI3 (mA)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 45:
                        variableName = "Current input at AI4 (mA, manual states AI3 but this is believed to be an error)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 47:
                        variableName = "Current input at AI5 (mA, manual states AI3 but this is believed to be an error)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 49:
                        variableName = "System password";
                        variableValue = parseBCD(
                                decimalToSixteenBitBinary(secondRegisterValue) +
                                    decimalToSixteenBitBinary(firstRegisterValue));
                        break;
                    case 77:
                        variableName = "PT100 resistance of inlet (Ohm)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 79:
                        variableName = "PT100 resistance of outlet (Ohm)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 81:
                        variableName = "Total travel time (Microseconds)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 83:
                        variableName = "Delta travel time (Nanoseconds, documentation states ninoseconds)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 85:
                        variableName = "Upstream travel time (Microseconds)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 87:
                        variableName = "Downstream travel time (Microseconds)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 89:
                        variableName = "Output current (mA)";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 97:
                        variableName = "The rate of the measured travel time by the calculated travel time";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                    case 99:
                        variableName = "Reynolds number";
                        variableValue = thirtyTwoBitBinaryToFloat(secondRegisterValue,firstRegisterValue);
                        break;
                }

                System.out.println(variableName + ": " + variableValue);
                registerNr = registerNr + 2;

            } else if(registerNr == 51 || registerNr == 56 || (registerNr >= 59 && registerNr <= 63) || registerNr == 72 || (registerNr >= 92 && registerNr <= 94) || registerNr == 96) {
                //Variables stored in only one register are handled here
                //Integer values do not need converting, e.g. "Key to Input" in register 59
                //TODO verify all cases in this category
                String line = scanner.nextLine();
                String registerValue = line.substring(line.indexOf(":") + 1);
                String variableName = "";
                String variableValue = "";

                switch (registerNr) {
                    case 51:
                        variableName = "Password for hardware";
                        variableValue = parseBCD(decimalToSixteenBitBinary(registerValue));
                        break;
                    case 56:
                        //TODO Test this
                        variableName = "Day+Hour for Auto-Save";
                        variableValue = parseBCD(decimalToSixteenBitBinary(registerValue));
                        if (variableValue.substring(0, 2).equalsIgnoreCase("00")) {
                            variableValue = variableValue.substring(2) + ":00 everyday";
                        } else {
                            variableValue = variableValue.substring(2) + ":00 on " + Integer.parseInt(variableValue.substring(0, 2)) + "th";
                        }
                        break;
                    case 59:
                        variableName = "Key to Input";
                        variableValue = registerValue;
                        break;
                    case 60:
                        variableName = "Go to Window #";
                        variableValue = registerValue;
                        break;
                    case 61:
                        variableName = "LCD Back-lit lights for number of seconds (s)";
                        variableValue = registerValue;
                        break;
                    case 62:
                        variableName = "Times for the beeper (Max 255)";
                        variableValue = registerValue;
                        break;
                    case 63:
                        variableName = "Pulses left for OCT (Max 65535, this variable is assumed to be stored in register 63)";
                        variableValue = registerValue;
                        break;
                    case 72:
                        variableName = "Error code (Refer to documentation for details)";
                        variableValue = decimalToSixteenBitBinary(registerValue);
                        break;
                    case 92:
                        //Two variables are stored in this register
                        variableName = "Working step";
                        variableValue = decimalToSixteenBitBinary(registerValue);
                        String highByte = variableValue.substring(0, 8);
                        System.out.println(variableName + ": " + Integer.parseInt(highByte, 2));

                        variableName = "Signal step";
                        variableValue = Integer.toString(Integer.parseInt(variableValue.substring(8), 2));
                        break;
                    case 93:
                        variableName = "Upstream strength (Range 0 - 2047)";
                        variableValue = registerValue;
                        break;
                    case 94:
                        variableName = "Downstream strength (Range 0 - 2047)";
                        variableValue = registerValue;
                        break;
                    case 96:
                        variableName = "Language (0 = English, 1 = Chinese)";
                        if (decimalToSixteenBitBinary(registerValue).charAt(15) == '0') {
                            variableValue = "0";
                        } else {
                            variableValue = "1";
                        }
                        break;
                }

                System.out.println(variableName + ": " + variableValue);
                registerNr = registerNr + 1;
            } else if(registerNr == 53){
                //Special case where the variable is stored in three registers
                String variableName = "Calendar (date and time)";
                String line = scanner.nextLine();
                String firstRegisterValue = line.substring(line.indexOf(":") + 1);
                line = scanner.nextLine();
                String secondRegisterValue = line.substring(line.indexOf(":") + 1);
                line = scanner.nextLine();
                String thirdRegisterValue = line.substring(line.indexOf(":") + 1);

                String variableValue = "";

                SimpleDateFormat sdf = new SimpleDateFormat("ssmmHHddMMyy");
                try {
                    System.out.println(parseBCD(
                            decimalToSixteenBitBinary(thirdRegisterValue)
                                    + decimalToSixteenBitBinary(secondRegisterValue)
                                    + decimalToSixteenBitBinary(firstRegisterValue)));

                    variableValue = sdf.parse(parseBCD(
                            decimalToSixteenBitBinary(thirdRegisterValue)
                            + decimalToSixteenBitBinary(secondRegisterValue)
                            + decimalToSixteenBitBinary(firstRegisterValue))).toString();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                System.out.println(variableName + ": " + variableValue);
                registerNr = registerNr + 3;

            } else {
                scanner.nextLine();
                registerNr++;
            }



        }


        System.out.println();

        System.out.println(thirtyTwoBitBinaryToFloat("16611","15568"));
        System.out.println(thirtyTwoBitBinaryToSignedInteger("65535","65480"));
        String dateBits = decimalToSixteenBitBinary("5889") + decimalToSixteenBitBinary("4386") + decimalToSixteenBitBinary("6432");
        System.out.println(dateBits);
        System.out.println(parseBCD(dateBits));
        SimpleDateFormat sdf = new SimpleDateFormat("ssmmHHddMMyy");
        try {
            System.out.println(sdf.parse(parseBCD(decimalToSixteenBitBinary("5889") + decimalToSixteenBitBinary("4386") + decimalToSixteenBitBinary("6432"))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(parseErrorCode("1111111111111111"));


    }

    public static String decimalToSixteenBitBinary(String decimalString){
        String bitString = Integer.toBinaryString(Integer.parseInt(decimalString));
        StringBuilder sixteenBitString = new StringBuilder("0000000000000000");
        for(int i = bitString.length(); i>0; i--){
            if(bitString.charAt(i - 1) == '1'){
                sixteenBitString.setCharAt((15 - ((bitString.length()) - i)), '1');
            } else {
                sixteenBitString.setCharAt((15 - ((bitString.length()) - i)), '0');
            }
        }
        return sixteenBitString.toString();
    }

    public static String thirtyTwoBitBinaryToFloat(String significantBytes, String secondaryBytes){
        String thirtyTwoBitString = decimalToSixteenBitBinary(significantBytes) + decimalToSixteenBitBinary(secondaryBytes);
        //Using float here does not retain the precision, double is used instead
        double floatValue = Float.intBitsToFloat(new BigInteger(thirtyTwoBitString, 2).intValue());
        return Double.toString(floatValue);
    }

    public static String thirtyTwoBitBinaryToSignedInteger(String significantBytes, String secondaryBytes){
        String thirtyTwoBitString = decimalToSixteenBitBinary(significantBytes) + decimalToSixteenBitBinary(secondaryBytes);
        int integerValue = new BigInteger(thirtyTwoBitString,2).intValue();
        return Integer.toString(integerValue);
    }

    public static String parseBCD(String bits){
        int numbers = bits.length() / 4;
        StringBuilder result = new StringBuilder();

        for(int i=0; i<numbers; i++) {
            String subString = bits.substring(i * 4, i * 4 + 4);
            result.append(Integer.parseInt(subString, 2));
        }
        return result.toString();
    }

    public static String parseErrorCode(String errorCode){
        StringBuilder errorCodeString = new StringBuilder(errorCode);
        int numberOfErrors = 0;

        for (int i = 0; i <errorCodeString.length(); i++){
            if(errorCodeString.charAt(i) == '1'){
                numberOfErrors++;
            }
        }

        if(numberOfErrors == 0){
            return "No errors";
        } else {
            StringBuilder errorMessage = new StringBuilder();
            for (int i = 0; i <errorCodeString.length(); i++) {
                if (errorCodeString.charAt(i) == '1') {
                    switch (i) {
                        case 0:
                            errorMessage.append("analog input over range");
                            break;
                        case 1:
                            errorMessage.append("internal timer over flow");
                            break;
                        case 2:
                            errorMessage.append("reserved");
                            break;
                        case 3:
                            errorMessage.append("temperature circuits error");
                            break;
                        case 4:
                            errorMessage.append("ROM check-sum error");
                            break;
                        case 5:
                            errorMessage.append("parameters check-sum error");
                            break;
                        case 6:
                            errorMessage.append("main clock or timer clock error");
                            break;
                        case 7:
                            errorMessage.append("RAM check-sum error");
                            break;
                        case 8:
                            errorMessage.append("current at 4-20mA over flow");
                            break;
                        case 9:
                            errorMessage.append("frequency at the frequency output over flow");
                            break;
                        case 10:
                            errorMessage.append("receiving circuits gain in adjusting");
                            break;
                        case 11:
                            errorMessage.append("hardware failure");
                            break;
                        case 12:
                            errorMessage.append("pipe empty");
                            break;
                        case 13:
                            errorMessage.append("poor received signal");
                            break;
                        case 14:
                            errorMessage.append("low received signal");
                            break;
                        case 15:
                            errorMessage.append("no received signal");
                            break;
                    }
                    numberOfErrors--;
                    if (numberOfErrors == 0) {
                        break;
                    } else if (numberOfErrors > 1) {
                        errorMessage.append(", ");
                    } else if (numberOfErrors == 1) {
                        errorMessage.append(" and ");
                    }
                }
            }
            return errorMessage.toString();
        }
    }



}
