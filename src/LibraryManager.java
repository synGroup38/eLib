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
   private HashMap<String,User>userMap = new HashMap<>();
   private HashMap<String,Resource>resourceMap = new HashMap<>(); 
   private HashMap<String,Loan>loanMap = new HashMap<>();
   private final int USER_LOAN_LIMIT;
   private String loanPath; 
   private String userPath; 
   private String resourcePath; 
   public LibraryManager(String loanPath , String userPath , String resourcePath , int loanLimit){
      USER_LOAN_LIMIT = loanLimit;
      init(loanPath , userPath , resourcePath); 
   }
   public boolean init(String loanPath , String userPath, String resourcePath ){
      this.loanPath = loanPath; 
      this.resourcePath = resourcePath; 
      this.userPath = userPath; 
      parseLoanFile(loanPath);
      parseUserFile(userPath);
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
               System.out.println("parsed:" + line); 
            }
            line = fm.readLine(); 
         }
         return true; 
      }catch(IOException e){
         System.out.println("file error"); 
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
         System.out.print("Removing loan:"+currentID+" because it is outdated ");
         System.out.println(""); 
         loanMap.remove(currentID); 
      }
      return true; 
   }


   private boolean permitLoan(String resourceID,String userID){
      
      User targetUser = null; 
      Resource targetResource = null;


      targetUser = userMap.get(userID);
      targetResource = resourceMap.get(resourceID);
      if(targetUser == null){
         System.out.println("user does not exist"); 
         return false; 
      }
      if(targetResource == null){
         System.out.println("resource does not exist"); 
         return false; 
      }
      ArrayList<Loan>loanList = new ArrayList<>(loanMap.values()
         .stream().collect(Collectors.toUnmodifiableList())); 
      for(Loan candidateLoan : loanList){
         if(candidateLoan.getResourceID().equals(resourceID)){
            /* out-dated loans are removed during init */ 
            /* thus every loan in loanMap is still pending */
            /*resource is being used my someone else */
            System.out.println("resource: " + resourceID+" already taken");
            return false; 
         }
      }
      return true; 
   }
   private <T extends Record> T findByName(String name,HashMap<String,T>map){
      ArrayList<T> list = new ArrayList<>(map.values()
         .stream().collect(Collectors.toUnmodifiableList())); 
      T candidate = null; 
      for(T entry : list){
         System.out.println("current record:"+ entry.getName()); 
         if(entry.getName().equals(name)){
            candidate = entry;
            return candidate; 
         }
      }
      return null; 
   }
   private boolean addLoan(String resourceID, String userID,Loan.DURATION time){
      if(!permitLoan(resourceID,userID)){
         return false; 
      }
      Loan nextLoan = new Loan(resourceID,userID,time);
      User candidateUser = userMap.get(userID); 
      if(candidateUser != null){
         candidateUser.addLoan(); 
         loanMap.put(nextLoan.getLoanID(),nextLoan); 
      }
      return true; 
   }
   public boolean loanByName(String resourceName, String userName, Loan.DURATION period){
      System.out.println("looking for:" +  resourceName); 
      Resource candidateResource = findByName(resourceName,resourceMap);
      User candidateUser = findByName(userName,userMap); 
      if(candidateUser == null){
         System.out.println("could not find user:" + userName);  
         return false; 
      } 
      if(candidateResource == null){
         System.out.println("could not find resource:"+ resourceName); 
         return false; 
      }
      return addLoan(candidateResource.getResourceID()
            ,candidateUser.getUserID(),period); 

   }
   public boolean addUser(String fName,String lName){
      User candidateUser = new User(fName,lName);   
      if (userMap.get(candidateUser.getUserID()) != null){
         System.out.println("could not add:" + fName); 
         return false; 
      }
      userMap.put(candidateUser.getUserID(),candidateUser); 
      return true; 
   }
   public boolean addResource(String name,String author){
      Resource candidateResource = new Resource(name,author); 
      if(userMap.get(candidateResource.getResourceID()) != null){
         System.out.println("could not add:" + name); 
         return false; 
      }
      resourceMap.put(candidateResource.getResourceID(),candidateResource); 
      return true; 
   }

   /*writes contents of a map to a given file*/
   private <T extends Record> boolean flushToFile(String filePath,HashMap<String,T > dataMap  ){
      ArrayList<T> list = new ArrayList<>(dataMap.values()
         .stream().collect(Collectors.toUnmodifiableList())); 
       
      try{
         FileManager fm = new FileManager(filePath);
         for(T entry : list){
            System.out.println("writing:"+entry); 
            fm.writeLine(entry.serialise()); 
         }
         fm.flush(); 
         fm.close(); 
      }catch(IOException e ){
         return false; 
      }
         
      return true; 
   }
   public void printLogs(){
   System.out.println("Loans"); 
   System.out.println("-------------------------------------"); 
   System.out.println(loanMap);
   System.out.println("-------------------------------------"); 
   

   System.out.println("Users"); 
   System.out.println("-------------------------------------"); 
   System.out.println(userMap);
   System.out.println("-------------------------------------"); 
   

   System.out.println("Resources"); 
   System.out.println("-------------------------------------"); 
   System.out.println(resourceMap);
   System.out.println("-------------------------------------"); 
   }
   public boolean exit(){
      flushToFile(loanPath,loanMap);
      flushToFile(userPath,userMap);
      flushToFile(resourcePath,resourceMap); 
      return true; 
   }
}
