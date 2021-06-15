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


   }

   public boolean writeLine(String input){
      outStream.add(input); 
      return false; 
   }
   public String readLine(){
      if(reader.hasNextLine()){
         String line  = reader.nextLine(); 
         System.out.println("fm:" + line);
         return line; 
      }
      System.out.println("no more lines"); 
      return null; 
   }
   public boolean flush(){
      try{
         writer = new FileWriter(archive,false); /*overwrite file*/ 
         for(String line : outStream){
            writer.write(line + System.lineSeparator());
         }
         writer.flush(); 
         return true; 
      }catch(IOException e){
         System.out.println("fm-file error");
         return false; 
      }
   }
   public boolean close(){
         flush();  
         reader.close(); 
      return true; 
   }
}
