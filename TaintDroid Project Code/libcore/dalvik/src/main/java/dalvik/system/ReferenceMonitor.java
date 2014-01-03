package dalvik.system;
import java.nio.ByteBuffer;

public class ReferenceMonitor {

	// Source, sink, type along with the tainted object.
	//creates a object of class Declassifier
    //Calls declassify method with source, sink, type and tainted object. 
    // declassify method is a overidding method in  
	
    /**
     * @hide
     */	
   public static void RemoveTaint(String str, int source, int sink, int type)
   {
	   Taint.log("Declasifier is getting called");
   	   Declassifier.RemoveTaint(str, source, sink, type);
   
   }

   /**
    * @hide
    */
 
      public static void RemoveTaint(Double str, int source, int sink, int type)
   {

	 Taint.log("Declasifier is getting called");
	 Declassifier.RemoveTaint(str, source, sink, type);
   
   }

 }
	
