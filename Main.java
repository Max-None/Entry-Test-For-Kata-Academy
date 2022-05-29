import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in); // create input stream from console

        String result;
        System.out.println("Enter math expression, please");
        result = calc(scanner.nextLine());
        System.out.println(result);
        scanner.close(); // resource leak prevention

    }
    public static String calc(String input) throws IOException {
        char[] charArray = input.toCharArray();
        char firstChar = charArray[0];
        int flag = 0;//flag for operation quantity checking
        boolean arabic = true;// type of expression
        String result="";
        if (firstChar!='1' && firstChar!='2' && firstChar!='3' && firstChar!='4' && firstChar!='5' && firstChar!='6' && firstChar!='7'
                && firstChar!='8' && firstChar!='9' && firstChar!='I' && firstChar!='V' && firstChar!='X') throw new IOException();
        for (char c: charArray){
            if (c!='0' && c!='1' && c!='2' && c!='3' && c!='4' && c!='5' && c!='6' && c!='7' && c!='8' && c!='9'
                    && c!='I' && c!='V' && c!='X' && c!='+' && c!='-' && c!='*' && c!='/') throw new IOException();
            if (c=='+'||c=='-'||c=='*'||c=='/') flag++;
        }

        if ((flag>1)||(flag==0)) throw new IOException();//Flag for operation quantity checking. One operation (two operands) must be.
        int[] operands = new int[3]; // This array keeps numbers and type of operation extracted from the expression
        if (charArray[0]>='1' && charArray[0]<='9'){
           operands = getArabicOperands(charArray);
        }
        else if (charArray[0] == 'I' || charArray[0] == 'V' || charArray[0] == 'X'){
            arabic = false;// Roman expression detected
            operands =getRomanOperands(charArray);
        }
        else throw new IOException();

        int calculation=0;
        switch (operands[2]){//operands[2] keeps type of operation. 1 - add, 2 - subtract, 3 - multiply, 4 - divide.
            case 1: calculation = operands[0]+operands[1];
            break;
            case 2: calculation = operands[0]-operands[1];
            break;
            case 3: calculation = operands[0]*operands[1];
            break;
            case 4: calculation = operands[0]/operands[1];
        }


        if (arabic==true) {
            result = Integer.toString(calculation);
        }
        else if (calculation<1) throw new IOException();
        else result = arabicToRoman(calculation);

        return result;


    }
    public static int[] getArabicOperands(char[] array) throws IOException {//extract numbers and type of operation from arabic expression
        for (char c: array){
            if (c=='I'||c=='V'||c=='X') throw new IOException();
        }

        int[] operands = new int[3];
        int i = 0;
        String operand = "";
        while (array[i]=='0'||array[i]=='1'||array[i]=='2'||array[i]=='3'||array[i]=='4'||array[i]=='5'||array[i]=='6'||array[i]=='7'||array[i]=='8'||array[i]=='9'){
          operand += String.valueOf(array[i]);
          i++;
        }
        if (Integer.parseInt(operand)>10){
            throw new IOException();
        }
        else operands[0]=Integer.parseInt(operand);
        operands[2] = getOperation(array[i]);
        i++;
        operand = "";
        while (i<array.length){
            operand += String.valueOf(array[i]);
            i++;
        }
        if (operand==""||Integer.parseInt(operand)>10){
            throw new IOException();
        }
        else operands[1]=Integer.parseInt(operand);

        return operands;
    }

    public static int[] getRomanOperands(char[] array) throws IOException {//extract numbers and type of operation from roman expression
        for (char c: array){
            if (c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9') throw new IOException();
        }
        int[] operands = new int[3];
        int i = 0;
        String operand = "";
        while (array[i]=='I'||array[i]=='V'||array[i]=='X'){
            operand += String.valueOf(array[i]);
            i++;
        }
        operands[0] = romanToArabic(operand);
        operands[2] = getOperation(array[i]);
        i++;
        operand = "";
        while (i<array.length){
            operand += String.valueOf(array[i]);
            i++;
        }
        if (operand=="") throw new IOException();
        else operands[1] = romanToArabic(operand);;
        return operands;
    }

    public static int romanToArabic(String roman) throws IOException {//Convert roman number to arabic number. Input range from I to X.
        int result = 0;
        switch (roman){
            case "I": result = 1;
                break;
            case "II": result = 2;
                break;
            case "III": result = 3;
                break;
            case "IV": result = 4;
                break;
            case "V": result = 5;
                break;
            case "VI": result = 6;
                break;
            case "VII": result = 7;
                break;
            case "VIII": result = 8;
                break;
            case "IX": result = 9;
                break;
            case "X": result = 10;
                break;
            default: throw new IOException();
        }
        return result;
    }

    public static int getOperation(char c){//Identify type of operation. 1 - add, 2 - subtract, 3 - multiply, 4 - divide.
        int result = 0;
        switch (c) {
            case '+': result = 1;
                break;
            case '-': result = 2;
                break;
            case '*': result= 3;
                break;
            case '/': result = 4;
        }
        return result;
    }

    public static String arabicToRoman(int arabicNumber){//Convert arabic number to roman number. Input range from 1 to 100.
        int[] romanValue = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanChar = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < romanValue.length; i += 1) {
            while (arabicNumber >= romanValue[i]){
                arabicNumber -= romanValue[i];
                result.append(romanChar[i]);
            }
        }
        return result.toString();
    }
}
