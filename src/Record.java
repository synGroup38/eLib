public abstract class Record {


   public String hash(String candidate){
      int iCandidate = candidate.hashCode(); 
      String result = String.format("%x",iCandidate); 
      return result;
   }
   abstract public String getName(); 
   abstract public String serialise(); 
   abstract public boolean deserialise(String object); 

}
