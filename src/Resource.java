public class Resource implements Record{

   private  String id; 
   private  String name; 
   private boolean isFree = true; 
   public static final char DELIMITER = '@'; 
   public Resource(String id, String name){
      this.id = id; 
      this.name = name; 
   }
   public String getResourceID(){
      return id; 
   }
   public void declare(){
      isFree = false; 
   }
   public boolean isFree(){
      return isFree; 
   }
   @Override
   public String serialise(){
      String object = id + DELIMITER + 
         name ; 
      return object; 
   }

   @Override
   public boolean deserialise(String object){
      final int ID_INDEX = 0; 
      final int NAME_INDEX =1; 
      
      String fields[] = object.split(String.valueOf(DELIMITER));
      if(fields.length != 2){
         return false; 
      }
      id = fields[ID_INDEX]; 
      name = fields[NAME_INDEX];
      return true; 
   }
}
