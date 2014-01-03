package dalvik.system;
import java.nio.ByteBuffer;
import dalvik.system.Taint;



public class Declassifier{

/**
 * @hide
 */
  public static void RemoveTaint(String src, int source, int sink, int mode)
  {
  
	// write logic to combine sink and source into one argument, and pass it to RemoveTaintString
	//int taint_tag = source | sink ;
	Taint.removeTaintString(src,source,sink);
 	//Taint.log("JAYASHREE"+ taint_tag + "and negation is " + ~taint_tag); 
  
  }
  /**
   * @hide
   */
  public static void RemoveTaint(Double src, int source, int sink, int mode)
  {
  
     //int taint_tag = source | sink ;
	// write logic to combine sink and source into one argument, and pass it to RemoveTaintString
	 Taint.log("IN Declassifiererrrrrrrr !!!!!!! ");
	double latitude;
	 latitude = Taint.RemoveTaintDouble(src,source,sink);
	Taint.log("sink value in declassifier= "+sink);
 	//Taint.log("JAYASHREE"+ taint_tag + "and negation is " + ~taint_tag); 
  }
  
}
		

