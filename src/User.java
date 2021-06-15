import java.util.ArrayList; 
public class User implements Record{
   private String id; 
   private String fName; 
   private String lName; 
   private int nLoans = 0; 
   public static final char DELIMITER = '%'; 
   public User(String id,String fName , String lName){
      this.id = id; 
      this.fName = fName; 
      this.lName = lName; 
   }
   
   public User(String id,String fName , String lName,int nLoans){
      this.id = id; 
      this.fName = fName; 
      this.lName = lName; 
      this.nLoans = nLoans; 
   }
   public String getUserID(){
      return id; 
   }    
   public int getLoanNumber(){
      return nLoans; 
   }

   public void addLoan(){
      nLoans++; 
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
