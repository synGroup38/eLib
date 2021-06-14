import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter; 
import java.util.ArrayList; 
import java.io.IOException; 
public class FileManager{
/* 
   Manages access to an archive file
*/
   private File archive;
   private Scanner reader;
   private ArrayList<String>outStream = new ArrayList<>(); 
   private FileWriter writer; 
   public FileManager(String filePath)throws IOException{
      
      archive = new File(filePath);
      archive.createNewFile();
      reader = new Scanner(archive);
      writer = new FileWriter(archive,false); /*overwrite if file already exists */ 


   }

   public boolean writeLine(String input){
      outStream.add(input); 
      return false; 
   }
   public String readLine(){
      if(reader.hasNextLine()){
         return reader.nextLine(); 
      }
      return null; 
   }
   public boolean flush(){
      try{
         for(String line : outStream){
            writer.write(line + System.lineSeparator());
         }
         writer.flush(); 
         return true; 
      }catch(IOException e){
         return false; 
      }
   }
   public boolean closeFile(){
      try{
         writer.flush(); 
         writer.close(); 
         reader.close(); 
      }catch(IOException e){
         return false; 
      }
      return true; 
   }
}
