package com.ssitcloud.common.util;

import com.sun.jna.Library;
import com.sun.jna.Native;

/*Public Declare Function NetSet Lib "lib.dll" (ByVal addr As String, ByVal port As Long) As Boolean

Public Declare Function NetConnectOn Lib "lib.dll" (ByVal qNumber As Long) As Integer       '连接
Public Declare Function NetDconnectOn Lib "lib.dll" (ByVal qNumber As Long) As Boolean            '断开连接
Public Declare Function NetPowerOn Lib "lib.dll" (ByVal qNumber As Long) As Boolean               '打开电源
Public Declare Function NetPowerOff Lib "lib.dll" (ByVal qNumber As Long) As Boolean              '关闭电源
Public Declare Function NetStopAllL Lib "lib.dll" (ByVal qNumber As Long) As Boolean              '停止移动所有的列
Public Declare Function NetFobiddenAllL Lib "lib.dll" (ByVal qNumber As Long) As Boolean          '禁止移动所有的列
Public Declare Function NetClearFobidden Lib "lib.dll" (ByVal qNumber As Long) As Boolean         '解消禁止
Public Declare Function NetVentilation Lib "lib.dll" (ByVal qNumber As Long) As Boolean           '通风
Public Declare Function NetOpenL Lib "lib.dll" (ByVal qNumber As Long, ByVal lNumber As Long, ByVal lName As Long) As Boolean                 '打开指定列
Public Declare Function NetOpenRecord Lib "lib.dll" (ByVal qNumber As Long, ByVal lNumber As Long, ByVal jNumber As Long, ByVal cNumber As Long, ByVal ce As Long, ByVal ben As Long, ByVal docName As String, ByVal lName As Long) As Boolean          '打开记录
Public Declare Function NetStatusChk Lib "lib.dll" (ByVal qNumber As Long, outStr As Byte) As Boolean             '状态查看
Public Declare Function NetCloseL Lib "lib.dll" (ByVal qNumber As Long) As Boolean                '关闭所有列
*/
public class LibDLLUtil {
		
		public interface CLibrary extends Library { 
		 CLibrary INSTANCE = (CLibrary)Native.loadLibrary("lib",CLibrary.class); 
		 
		 Boolean NetSet(String addr,Long port); //设置ip和端口
		 Boolean NetConnectOn(Long qNumber);//连接
		 Boolean NetDconnectOn(Long qNumber);//断开连接
		 Boolean NetPowerOn(Long qNumber);//打开电源
		 Boolean NetPowerOff(Long qNumber);//关闭电源
		 Boolean NetStopAllL(Long qNumber);//停止移动所有的列
		 Boolean NetFobiddenAllL(Long qNumber);//禁止移动所有的列
		 Boolean NetClearFobidden(Long qNumber);//解消禁止
		 Boolean NetVentilation(Long qNumber);//通风
		 Boolean NetOpenL(Long qNumber,Long lNumber,Long lName);//打开指定列
		 Boolean NetOpenRecord(Long qNumber,Long lNumber,Long jNumber,Long cNumber,Long ce,Long ben,String docName,Long lName);//打开记录
		 Boolean NetStatusChk(Long qNumber,Byte outStr);//状态查看
		 Boolean NetCloseL(Long qNumber);//关闭所有列
		 
	    } 
		
		public static Boolean NetSet(String addr,Long port){
			return CLibrary.INSTANCE.NetSet(addr,port);
		}
		
		public static Boolean NetConnectOn(Long qNumber){
			return CLibrary.INSTANCE.NetConnectOn(qNumber);
		}
		
		public static Boolean NetDconnectOn(Long qNumber){
			return CLibrary.INSTANCE.NetDconnectOn(qNumber);
		}
		
		public static Boolean NetPowerOn(Long qNumber){
			return CLibrary.INSTANCE.NetPowerOn(qNumber);
		}
		
		public static Boolean NetPowerOff(Long qNumber){
			return CLibrary.INSTANCE.NetPowerOff(qNumber);
		}
		
		public static Boolean NetStopAllL(Long qNumber){
			return CLibrary.INSTANCE.NetStopAllL(qNumber);
		}
		
		public static Boolean NetFobiddenAllL(Long qNumber){
			return CLibrary.INSTANCE.NetFobiddenAllL(qNumber);
		}
		
		public static Boolean NetClearFobidden(Long qNumber){
			return CLibrary.INSTANCE.NetClearFobidden(qNumber);
		}
		
		public static Boolean NetVentilation(Long qNumber){
			return CLibrary.INSTANCE.NetVentilation(qNumber);
		}
		
		public static Boolean NetOpenL(Long qNumber,Long lNumber,Long lName){
			return CLibrary.INSTANCE.NetOpenL(qNumber,lNumber,lName);
		}
		
		public static Boolean NetOpenRecord(Long qNumber,Long lNumber,Long jNumber,Long cNumber,Long ce,Long ben,String docName,Long lName){
			return CLibrary.INSTANCE.NetOpenRecord(qNumber,lNumber,jNumber,cNumber,ce,ben,docName,lName);
		}
		
		public static Boolean NetStatusChk(Long qNumber,Byte outStr){
			return CLibrary.INSTANCE.NetStatusChk(qNumber,outStr);
		}
		
		public static Boolean NetCloseL(Long qNumber){
			return CLibrary.INSTANCE.NetCloseL(qNumber);
		}
}
