import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Algoritm {

    private static double max=0;

    //bid mærke i, at Arduinoen kører med 25 samples/sekund
    //svarende til 25*60 = 1500 samples på et minut.

    //vi anvender vores rådata til at finde den 1. afledte, og herefter den 2. afledte, hvilket gøres i funktionerne getFirst og GetSecond.
    //getSecondDerivative anvender getFirstDerivative, og begge tager et int[] ind for at aflede hinanden.

    //for at kunne bruge dette, er det altså vigtigt at I har konverteret jeres aflæste værdier fra sensorens String til int[].

    //Når vi så har vores f''(x) (den 2. afledte) array, kan vi bruge dette til at finde et gennemsnit for et antal samples (5 eller 25)
    //hvor antal samples matcher med en fraktion af et helt minut (fx. 25*antal sekunder man vil finde snit for).

    //ex: 25 samples / sekund * 5 sekunder = 125 samples. Det svarer til 1/12 af et minut. Du finder snittet af de samples' 2. afledte værdi

    //og så ganger man op med den fraktion for at finde værdien af SP02 eller pulsen.
    public static void main(String[] args) {


         //bruges til at udregne maximumværdien i vores anden afledte array.
        int[] testArray = new int[50];


        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = (int) (Math.random() * 100);
            // System.out.println(testArray[i]); Til at lave et random array.
        }
        // getFirstDerivative(testArray);

        int[] out= getSecondDerivative(testArray);
        getAverageArray(out);
        //getCurrentValue(out,12);


    }




    public static int[] getAverageArray(int[] input) {
        int[] averageArray = new int[(input.length / 25)+1];
        //laver et array til at opbevare data i, og gør det 25/ længden så stor, fordi der for 1 sekund er 25 samples.
        double average;
        int sum =0;
        System.out.println("længde af average array:"+averageArray.length);

        int counter=1;
        //bruges til at state
        for (int i = 0; i < input.length; i++) {
            sum += input[i];
            //
            if(i%25 ==counter){

//for hver 25 værdi findes gennemsnittet
                average=sum/25;
                //og lægges i average array
                averageArray[counter] = (int) average;
                System.out.println("Sum: "+sum);
                System.out.println("Average:"+average);
                sum=0;

                //Nulstil sum for hver 25 plads.
            }

        }
        System.out.println("Average array:"+ Arrays.toString(averageArray));

        return averageArray;
        //giv os et array tilbage som er 1/25 så stort som oprindeligt, svarende til 25 samples/1 sekund, svarende til at 1 plads = 1 sekund.


    }

    public static double getCurrentValue(int[] secondDerivative, int fraktion){

        int[] averages = getAverageArray(secondDerivative);
        // vi skaffer lige et array med hver værdi som værende gennemsnittet af vores 25 målinger.

        double currentValue =0;
        double sum =0;
        for (int i =0;i<averages.length;i++){
            sum += averages[i];
            //Find snittet over den del af dataerne vi ser på, fx. svarende til 5 sekunders data eller 1/12 del.
            if(i%fraktion==0){
                currentValue= sum/fraktion;
                System.out.println("Current value for...is:"+currentValue);
            }
        }
        /*
        Udregn gennemsnit af array'et, som er 2. afledt af den oprindelige funktion.
        og det array er så X antal gange mindre end det oprindelige. Dvs det skal have x * 25 samples for at virke
        hvorefter det så tager snittet over den tids målinger, og ganger op med fraktionen for at få en ide om hvor
         */




        return currentValue;
    }

    public static int[] getFirstDerivative(int[] input) {

        int[] firstDerivative = new int[input.length - 2];
        //2 pladser mindre end oprindelig
       // System.out.println("Content of first derivative");
        for (int i = 0; i < input.length - 2; i++) {
            //find haeldningen i et punkt for den første afledte:
            firstDerivative[i] = (input[i + 2] - input[i]) / 2;

            //(y1-y0)/(x1-x0) = a
           // System.out.println(firstDerivative[i]);

        }

        return firstDerivative;
    }

    public static int[] getSecondDerivative(int[] input) {


        int[] secondDeriv = new int[input.length - 4];
        int[] first = getFirstDerivative(input);


        //System.out.println("Content of second derivative");
        for (int i = 1; i < first.length - 4; i++) {
            secondDeriv[i] = (input[i + 2] - input[i]) / 2;
            if(secondDeriv[i]>  max){
                max = secondDeriv[i];
            }
          //  System.out.println(secondDeriv[i]);
        }
        System.out.println("Maximum value i 2. afledte:"+max);


        return secondDeriv;


    }


}
