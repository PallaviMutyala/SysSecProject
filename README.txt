Note: All the paths start from directory where Android Source code(Base Directory) is downloaded. 
====

List of files modified: 
=======================
./frameworks/base/telephony/java/com/android/internal/telephony/gsm/GsmSMSDispatcher.java
./libcore/luni/src/main/java/org/apache/harmony/xnet/provider/jsse/OpenSSLSocketImpl.java 
./libcore/luni/src/main/java/libcore/io/Posix.java

All the 3 files have exception code added to the functions at respective sink points. 
____________________________________________________________________________________________

./libcore/dalvik/src/main/java/dalvik/system/Taint.java

Below listed 2 functions were added in the file. These functions are called by Declassifier methods to untaint the data. 

native public static void RemoveTaintDouble()
native public static void removeTaintString()

The below constants were newly declared in the above file which are required to identify the sink points. 

public static final int TAINT_NETWORK  	= 0x00010000;
public static final int TAINT_MESSAGE   = 0x00020000;
public static final int TAINT_POSIX_IO  = 0x00040000;
public static final int TAINT_FOREVER   = 0x00000001;
_____________________________________________________________________________________________
./dalvik/vm/native/dalvik_system_Taint.cpp
The corresponding native methods to the methods in Taint.java are added in dalvik_system_Taint.cpp.  

static void Dalvik_dalvik_system_Taint_RemoveTaintDouble()
static void Dalvik_dalvik_system_Taint_removeTaintString()
______________________________________________________________________________________________

List of files newly added:
========================= 
Below are the 2 class files added. This package is imported in the sample Application 

./libcore/dalvik/src/main/java/dalvik/system/Declassifier.java
./libcore/dalvik/src/main/java/dalvik/system/ReferenceMonitor.java
______________________________________________________________________________________________

Sample application:
=================== 
Entire test application is added in  ./packages/apps/SampleApp folder
_______________________________________________________________________________________________

Steps to run the system:
========================
1) Once the Android source code has been built successfully, Launch the TaintdroidNotifier and click the start button. 
2) A new application “SampleApplication Activity” is found in the list of applications. 
3) Launch the application and click on “Normal GPS” or “Normal IMEI”. Application crashes since exception is thrown at  the sink point. 
4) Now click on the “GPS” or “IMEI” buttons. Data is successfully sent over network without any crash. 


