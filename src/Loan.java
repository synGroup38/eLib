import java.time.LocalDate;
import java.time.format.DateTimeFormatter; 
public class Loan{
   public enum DURATION{ 
      WEEK,
      FORTNIGHT,
      MONTH
   }
   private LocalDate startDate;
   private int days; 
   private String loanID; 
   private String resourceID; 
   private String userID; 


   public Loan(String loanID, String resourceID , String userID,DURATION period){
      this.loanID = loanID; 
      this.resourceID = resourceID; 
      this.userID = userID; 
      startDate = LocalDate.now();
      switch(period){
         case WEEK:
               days = 7;
            break; 
         case FORTNIGHT: 
               days = 14; 
            break;
         case MONTH:  
               days = 30; 
      }
   }
   
   public String getStartDate(){
      return startDate.format(DateTimeFormatter.ISO_LOCAL_DATE); 
   }
   public String getEndDate(){
      return startDate.plusDays(days).format(DateTimeFormatter.ISO_LOCAL_DATE); 
   }
   @Override 
   public String toString(){
      String msg = 
             "Loan["+ loanID +"]" + System.lineSeparator() + 
             "Resource: " + resourceID + " is owned by: " + userID +
             System.lineSeparator()+
             "Start Date: " + getStartDate() + System.lineSeparator() +
             "End Date: " + getEndDate() + System.lineSeparator(); 
      return msg; 
   }
}

