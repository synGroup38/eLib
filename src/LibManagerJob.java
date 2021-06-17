import javax.swing.SwingWorker; 
import javax.swing.*; 
import java.util.ArrayList;
/*this class performs database calls asyncronously and updates the corresponding 
 * gui element  */

public class LibManagerJob extends SwingWorker<ArrayList<String>,Integer>{

   public static enum CMD{
      LIST_USER,
      LIST_RESOURCE,
      LIST_LOAN,
      ADD_USER,
      ADD_RESOURCE,
      ADD_LOAN,
      DELETE_USER,
      DELETE_RESOURCE,
      DELETE_LOAN
   }
   private CMD command;
   private JTextArea output = new JTextArea(); 
   private String args []; 
   public LibManagerJob(CMD command,JTextArea output,String args[]){
      this.command = command;
      this.output = output; 
      this.args = args; 
   }
   
   @Override
   protected ArrayList<String> doInBackground(){
      ArrayList<String> result = new ArrayList<>(); 
      String finalResult; 
      String cmdResult; 
      output.setText(null); 
      switch(command){
         case LIST_USER:
            result = main.getLibManager().listUsers(); 
            break; 
         case LIST_RESOURCE: 
            result = main.getLibManager().listResources();
            break; 
         case LIST_LOAN:
            result = main.getLibManager().listLoans(); 
            break; 
         case ADD_USER:
            for (String arg : args){
               if(arg.length() == 0){
                  result.add("ERROR cannot add user, empty input");
                  return result; 
               }
            }

            cmdResult =  main.getLibManager().addUser(args[0],args[1]); 
            finalResult = "Generated userID: " + cmdResult; 
            result.add(finalResult); 
             
            break; 
         case ADD_RESOURCE:
            for (String arg : args){
               if(arg.length() == 0){
                  result.add("ERROR cannot add resource, empty input");
                  return result; 
               }
            }
            cmdResult = (main.getLibManager().addResource(args[0],args[1]));
            finalResult = "Generated resourceID: " + cmdResult;    
            result.add(finalResult); 
            
            break; 
         case ADD_LOAN: 
            for (String arg : args){
               if(arg.length() == 0){
                  result.add("ERROR cannot add resource, empty input");
                  return result; 
               }
            }
            cmdResult = main.getLibManager().addLoanByID(args[0],args[1],
                     main.getLibManager().parseDuration(args[2])); 
            finalResult = "Generated loanID: " + cmdResult; 
            if(cmdResult.contains("Invalid")){
               finalResult = "Error could not add loan: " + cmdResult;  
            }   
            result.add(finalResult);    
            break; 
         case DELETE_USER:
            for (String arg : args){
               if(arg.length() == 0){
                  result.add("ERROR cannot delete user, empty input");
                  return result; 
               }
            }
            if(main.getLibManager().removeUser(args[0])){
               result.add("SUCCESS"); 
            }else{
               result.add("Delete failed");
               result.add("Make sure user:" + args[0] + " exists");
               result.add("Make sure user has no more loans to fufill"); 
            }
            break; 
         case DELETE_LOAN:
            for (String arg : args){
               if(arg.length() == 0){
                  result.add("ERROR cannot delete loan, empty input");
                  return result; 
               }
            }
            if(main.getLibManager().removeLoan(args[0])){
               result.add("SUCCESS"); 
            }else{
               result.add("Delete failed"); 
            }
            break; 
         case DELETE_RESOURCE: 
            for (String arg : args){
               if(arg.length() == 0){
                  result.add("ERROR cannot delete resource, empty input");
                  return result; 
               }
            }
            if(main.getLibManager().removeResource(args[0])){
               result.add("SUCCESS"); 
            }else{
               result.add("Delete failed"); 
               result.add("Make sure resource:" + args[0] + " exists");
               result.add("Make sure resource is not currently on loan"); 
            }
      }
      main.getLibManager().save(); 
      return result; 
   }
   @Override 
   protected void done(){
      ArrayList<String> result = null; 
      try{
         result = get(); 
         for(String r : result){
            output.append(r);
            output.append(System.lineSeparator()); 
         }
      }catch(Exception e){
         e.printStackTrace(); 
      }
   }
}
