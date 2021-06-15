import java.util.HashMap;
import java.util.ArrayList; 
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter; 
import java.util.stream.Collectors; 
import java.io.IOException; 
/*
   Tracks incoming and outgoing resources
*/

public class LibraryManager{
   private HashMap<String,User>userMap;
   private HashMap<String,Resource>resourceMap; 
   private HashMap<String,Loan>loanMap;
   private final int USER_LOAN_LIMIT; 
   public LibraryManager(String loanPath , String userPath , String resourcePath , int loanLimit){
      USER_LOAN_LIMIT = loanLimit;
      init(loanPath , userPath , resourcePath); 
   }
   public boolean init(String loanPath , String usersPath, String resourcePath ){
      parseLoanFile(loanPath);
      parseUserFile(usersPath);
      parseResourceFile(resourcePath); 
      updateLoansUponInit(); 
      return true;
   }
   /*deletes records of loans that are expired */
   private boolean parseLoanFile(String filePath){
      try{
         FileManager fm = new FileManager(filePath);
         String line = fm.readLine(); 
         while(line != null){
            Loan newEntry = new Loan(null,null,null,Loan.DURATION.WEEK);
            if(newEntry.deserialise(line)){
               loanMap.put(newEntry.getLoanID(),newEntry); 
            }
            line = fm.readLine(); 
         }
         return true; 
      }catch(IOException e){
         return false; 
      }
   }
   private boolean parseUserFile(String filePath){
      try{
         FileManager fm = new FileManager(filePath);
         String line = fm.readLine(); 
         while(line != null){
            User newEntry = new User(null,null,null,0);
            if(newEntry.deserialise(line)){
               userMap.put(newEntry.getUserID(),newEntry); 
            }
            line = fm.readLine(); 
         }
         return true; 
      }catch(IOException e){
         return false; 
      }
   }
   private boolean parseResourceFile(String filePath){
      try{
         FileManager fm = new FileManager(filePath);
         String line = fm.readLine(); 
         while(line != null){
            Resource newEntry = new Resource(null,null);
            if(newEntry.deserialise(line)){
               resourceMap.put(newEntry.getResourceID(),newEntry); 
            }
            line = fm.readLine(); 
         }
         return true; 
      }catch(IOException e){
         return false; 
      }
   }
   
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
