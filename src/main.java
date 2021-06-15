import java.sql.Connection;
import java.sql.DriverManager;
import java.io.FileNotFoundException; 
import java.io.IOException; 
public class main{
   public static void main(String args[]){
      LibraryManager librarian = new LibraryManager("loans.txt",
            "users.txt",
            "resources.txt",5);

      librarian.addUser("Kanye","West");
      librarian.addUser("Thomas","Barra");
      librarian.addResource("The Bible","somebody");
      librarian.addResource("meditations","Marcus Aurelius"); 
      librarian.loanByName("The Bible-somebody","Kanye West",Loan.DURATION.WEEK);
      librarian.loanByName("The Bible-somebody","Marcus Aurelius",
            Loan.DURATION.WEEK);   

      
      librarian.loanByName("The Bible-somebody","Thomas Barra",
            Loan.DURATION.WEEK);   
      
      librarian.printLogs(); 
      librarian.exit(); 
      try{
         FileManager fm = new FileManager("test.txt");
         fm.writeLine("hello from the other side");
         fm.flush(); 
         System.out.println("line written: " + fm.readLine()); 
         fm.close(); 
      }catch(FileNotFoundException e ){
         System.out.println("file does not exist"); 
      }catch(IOException e ){
         System.out.println("file error occured"); 
      }
   
   }
}
          

