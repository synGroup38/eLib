public class Resource extends Record{

   private  String id; 
   private  String name;
   private  String author; 
   private boolean isFree = true; 
   public static final char DELIMITER = '@'; 
   public Resource(String id, String name,String author){
      this(name,author);
      this.id = id; 
   }
   public Resource(String name,String author){
      this.name = name; 
      this.author = author; 
      String key  = name + author; 
      id = hash(key); 
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
   public String toString(){
      String msg = "Resource["+id+"]"+System.lineSeparator() +
         "Name:" + name + System.lineSeparator()+
         "Author:" + author + System.lineSeparator();  
         return msg; 
   }
   @Override 
   public String getName(){
      return name+"-"+author; 
   }
   @Override
   public String serialise(){
      String object = id + DELIMITER + 
         name + DELIMITER + author; 
      return object; 
   }

   @Override
   public boolean deserialise(String object){
      final int ID_INDEX = 0; 
      final int NAME_INDEX =1; 
      final int AUTHOR_INDEX=2;  
      String fields[] = object.split(String.valueOf(DELIMITER));
      if(fields.length != 3){
         return false; 
      }
      id = fields[ID_INDEX]; 
      name = fields[NAME_INDEX];
      author = fields[AUTHOR_INDEX];
      return true; 
   }
}
