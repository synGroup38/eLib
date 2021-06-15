import java.util.ArrayList; 
import java.time.LocalTime; 
public class User extends Record{
   private String id; 
   private String fName; 
   private String lName; 
   private int nLoans = 0; 
   public static final char DELIMITER = '%'; 
   public User(String fName,String lName){
      this(fName,lName,0); 
   }
   public User(String id,String fName , String lName){
      this(fName,lName,0);
      this.id = id;
   }
   
   public User(String id,String fName , String lName,int nLoans){
      this(fName,lName,nLoans);
      this.id = id;
   }
   public User(String fName , String lName , int nLoans){
      this.fName = fName; 
      this.lName = lName; 
      this.nLoans = nLoans; 
      String candidateKey = fName + 
         lName ;  
      id = hash(candidateKey); 
   }
   public String getUserID(){
      return id; 
   }    
   public int getLoanNumber(){
      return nLoans; 
   }
   public void endLoan(){
      if (nLoans > 0){
         nLoans--; 
      }
   }
   public void addLoan(){
      nLoans++; 
   }
   @Override 
   public String toString(){
      String msg = "User["+id+"]" + System.lineSeparator() +
         "First Name:" + fName + System.lineSeparator() +  
         "Second Name:" + lName + System.lineSeparator() + 
         "number of loans:" + nLoans + System.lineSeparator(); 
      return msg; 
   }
   @Override 
   public String getName(){
      return fName+" " + lName;  
   }
   @Override 
   public String serialise(){
      String object = id + DELIMITER + 
         fName + DELIMITER + 
         lName + DELIMITER + 
         nLoans; 
      return object; 
   }
   @Override 
   public boolean deserialise(String object){
      final int ID_INDEX = 0; 
      final int FNAME_INDEX = 1; 
      final int LNAME_INDEX = 2; 
      final int LOANS_INDEX = 3; 
      String fields[] = object.split(String.valueOf(DELIMITER));
      if(fields.length != 4 ){
         return false; 
      }
      id = fields[ID_INDEX]; 
      fName = fields[FNAME_INDEX]; 
      lName = fields[LNAME_INDEX]; 
      nLoans = Integer.parseInt(fields[LOANS_INDEX]); 
      return true; 
   }
} 
