import java.util.HashMap;
import java.util.ArrayList; 
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter; 
import java.util.stream.Collectors; 
/*
   Tracks incoming and outgoing resources
*/

public class LibraryManager{
   private HashMap<String,User>userMap;
   private HashMap<String,Resource>resourceMap; 
   private HashMap<String,Loan>loanMap;
   private final int USER_LOAN_LIMIT; 
   public LibraryManager(String fileName,int loanLimit){
      USER_LOAN_LIMIT = loanLimit; 
   }
   private boolean init(){
      updateLoansUponInit(); 
      return true;
   }
   /*deletes records of loans that are expired */
   private boolean updateLoansUponInit(){
      ArrayList<String>markedForDelete = new ArrayList<>(); 
      ArrayList<Loan> loans = new ArrayList<>(loanMap.values()
         .stream().collect(Collectors.toUnmodifiableList())); 
      
      for(Loan currentLoan : loans){
         DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;
         LocalDate tmpEndDate = LocalDate.parse(currentLoan.getEndDate(),format); 
         if(tmpEndDate.isBefore(LocalDate.now())){
            markedForDelete.add(currentLoan.getLoanID());   
         }
      }
      for(String currentID : markedForDelete){
         loanMap.remove(currentID); 
      }
      return true; 
   }


   public boolean permitLoan(String resourceID,String userID){
      User targetUser = null; 
      Resource targetResource = null;

      targetUser = userMap.get(userID);
      targetResource = resourceMap.get(resourceID);

      if(targetUser == null){
         return false; 
      }

      if(targetResource == null){
         return false; 
      }

      if(targetUser.getLoanNumber() == USER_LOAN_LIMIT ){
         return false; 
      }

      if(!targetResource.isFree()){
         return false; 
      }
      
      targetResource.declare(); 
      targetUser.addLoan(); 
      return true; 
   }

   private boolean recordLoan(String resourceID, String userID,Loan.DURATION time){
      Loan nextLoan = new Loan("NEW",resourceID,userID,time);
      String compositeKey = nextLoan.getLoanID() + 
      nextLoan.getUserID() +
      nextLoan.getResourceID() + 
      nextLoan.getEndDate(); 
      System.out.println(nextLoan);
      loanMap.put(compositeKey,nextLoan); 
      return true; 
   }
   /*writes contents of all maps to their matching files*/
   private boolean flushToFiles(){
      return true; 
   }
   public boolean exit(){
      return true; 
   }
}
