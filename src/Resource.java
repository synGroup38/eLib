public class Resource{

   private final String id; 
   private final String name; 
   private boolean isFree = true; 
   
   public Resource(String id, String name){
      this.id = id; 
      this.name = name; 
   }
   public void declare(){
      isFree = false; 
   }
   public boolean isFree(){
      return isFree; 
   }


}
