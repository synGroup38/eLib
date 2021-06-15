import java.time.LocalDate;
import java.time.format.DateTimeFormatter; 
public class Loan extends Record{
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
   public static  final char DELIMITER = '#'; 

   public Loan(String loanID, String resourceID , String userID,DURATION period){
      this(resourceID,userID,period);
      this.loanID = loanID; 
   }

   public Loan(String resourceID,String userID,DURATION period){
      startDate = LocalDate.now(); 

      
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
      String compositeKey = resourceID + userID + getEndDate(); 
      loanID = hash(compositeKey); 
   }   
   public String getLoanID(){
      return loanID; 
   }
   public boolean setLoanPeriod(int nDays){
      if(nDays < 7){
         return false; 
      }
      days = nDays; 
      return true; 
   }
   public String getResourceID(){
      return resourceID; 
   }

   public String getUserID(){
      return userID; 
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
   @Override 
   public String getName(){
      return  loanID; 
   }
   @Override
   public String serialise(){

      String object = loanID + DELIMITER + 
            resourceID + DELIMITER + 
            userID + DELIMITER + 
            getStartDate() + DELIMITER + 
            days ;
      return object; 

   }
   @Override
   public boolean deserialise(String object){
      final int LOAN_INDEX = 0; 
      final int RESOURCE_INDEX = 1;
      final int USER_INDEX = 2; 
      final int DATE_INDEX = 3; 
      final int PERIOD_INDEX = 4; 
      String fields[] = object.split(String.valueOf(DELIMITER));
         if(fields.length < 5){
            return false; 
         }
      loanID = fields[LOAN_INDEX]; 
      resourceID =fields[RESOURCE_INDEX]; 
      userID = fields[USER_INDEX]; 
      startDate = LocalDate.parse(fields[DATE_INDEX],
            DateTimeFormatter.ISO_LOCAL_DATE); 
      days = Integer.parseInt(fields[PERIOD_INDEX]); 
      return true; 
   }
}

