import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.awt.EventQueue; 
public class main{
   public static LibraryManager librarian; 
   public static void main(String args[]){
      EventQueue.invokeLater(new Runnable(){
         @Override
         public void run(){
               MainMenu gui = new MainMenu("libero");
               gui.show(); 
            }
         });
      
      librarian = new LibraryManager("loans.txt",
            "users.txt",
            "resources.txt",5);
      
      librarian.addUser("Kanye","West");
      librarian.addUser("Thomas","Barra");
      librarian.addResource("The Bible","somebody");
      librarian.addResource("meditations","Marcus Aurelius"); 

      
      
   
   }
   public static LibraryManager getLibManager(){
      return librarian; 
   }
}
          

