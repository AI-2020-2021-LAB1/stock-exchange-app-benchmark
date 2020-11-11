import java.util.concurrent.ThreadLocalRandom;

public class Algorithm{
    
    static int algPercentages[] = {33, 33, 24, 10, 30, 70, 30, 70, 15, 70, 15, 50, 50};
    static int numOfOperations = 20;
    static int moneyAmount = 1000;
    static boolean register = false;
    
     public static void main(String []args){
         
         {//Coby nie przeliczać tego w "if" - możnaby do funkcji
             algPercentages[1] = algPercentages[0] + algPercentages[1];
             algPercentages[2] = algPercentages[1] + algPercentages[2];
             algPercentages[3] = algPercentages[2] + algPercentages[3];
             algPercentages[5] = algPercentages[4] + algPercentages[5];
             algPercentages[7] = algPercentages[6] + algPercentages[7];
             algPercentages[9] = algPercentages[8] + algPercentages[9];
             algPercentages[10] = algPercentages[9] + algPercentages[10];
             algPercentages[12] = algPercentages[11] + algPercentages[12];
         }
         
        //Wczytywanie danych z komunikatu.
        
        if (register == false) System.out.println("Rejestracja");   //Rejestracja
        else System.out.println("Logowanie");   //Logowanie
        
        while (numOfOperations > 0){
            int randomNum = ThreadLocalRandom.current().nextInt(1, 101);
            if (randomNum <= algPercentages[0]){
            System.out.println("Sprawdzenie listy dostępnych akcji"); 
            //Sprawdzenie listy dostępnych akcji
             numOfOperations--;
             randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[4]){
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                }
            }
            else if (randomNum > algPercentages[0] && randomNum <= algPercentages[1]){
             System.out.println("Sprawdzenie listy posiadanych akcji"); 
             //Sprawdzenie listy posiadanych akcji
             numOfOperations--;
             randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[6]){
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                }
            }
            else if (randomNum > algPercentages[1] && randomNum <= algPercentages[2]){
                System.out.println("Sprawdzenie listy aktualnych zleceń"); 
                //Sprawdzenie listy aktualnych zleceń
                numOfOperations--;
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[8]){
                    if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                    else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                    numOfOperations--;
                }
             else if (randomNum > algPercentages[9]){
                    System.out.println("Usunięcie zlecenia");   //Usunięcie zlecenia
                    numOfOperations--;
                }
            }
            else if (randomNum > algPercentages[2]){
                randomNum = ThreadLocalRandom.current().nextInt(1, 101);
                if (randomNum <= algPercentages[11]) System.out.println("Zlecenie kupna");   //Zlecenie kupna
                else System.out.println("Zlecenie sprzedaży");   //Zlecenie sprzedaży
                numOfOperations--;
            }
        }
     }
}
