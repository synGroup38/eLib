import java.sql.Connection;
import java.sql.DriverManager;
public class main{
   public static void main(String args[]){
      System.out.println("hello world!");
      Loan smallLoan = new Loan("KYS","1MIL","TRP",Loan.DURATION.WEEK);
      System.out.println(smallLoan); 
      System.out.println(smallLoan.getStartDate()); 
   }
}
          

