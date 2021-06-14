import java.util.ArrayList; 
public class User{
   private final String id; 
   private final String fName; 
   private final String lName; 
   private int nLoans = 0; 
   public User(String id,String fName , String lName){
      this.id = id; 
      this.fName = fName; 
      this.lName = lName; 
   }
   
   public int getLoanNumber(){
      return nLoans; 
   }

   public void addLoan(){
      nLoans++; 
   }
} 
