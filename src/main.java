import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileNotFoundException; 
import java.io.IOException; 
public class main{
   public static void main(String args[]){
      System.out.println("hello world!");
      Loan smallLoan = new Loan("KYS","1MIL","TRP",Loan.DURATION.WEEK);
      System.out.println(smallLoan); 
      System.out.println(smallLoan.getStartDate()); 
      try{
         FileManager fm = new FileManager("test.txt");
         fm.writeLine("hello from the other side");
         fm.flush(); 
         System.out.println("line written: " + fm.readLine()); 
         fm.closeFile(); 
      }catch(FileNotFoundException e ){
         System.out.println("file does not exist"); 
      }catch(IOException e ){
         System.out.println("file error occured"); 
      }
   
   }
}
          

