<%@ page language="java" import="java.util.*,java.lang.management.ThreadInfo,java.lang.management.ThreadMXBean,java.lang.management.ManagementFactory" pageEncoding="UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
class ThreadState{
		private long threadId;
		private String threadName;
		private long cpuTime;
		private String state;
		private StackTraceElement[] stackTraceElements;
		public long getThreadId() {
			return threadId;
		}
		public void setThreadId(long threadId) {
			this.threadId = threadId;
		}
		public String getThreadName() {
			return threadName;
		}
		public void setThreadName(String threadName) {
			this.threadName = threadName;
		}
		public long getCpuTime() {
			return cpuTime;
		}
		public void setCpuTime(long cpuTime) {
			this.cpuTime = cpuTime;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public StackTraceElement[] getStackTraceElements() {
			return stackTraceElements;
		}
		public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
			this.stackTraceElements = stackTraceElements;
		}
				
	}
	
	//ThreadMXBean tm = ManagementFactory.getThreadMXBean();
	//tm.setThreadContentionMonitoringEnabled(true);
	//long [] tid = tm.getAllThreadIds();
	//ThreadInfo [] tia = tm.getThreadInfo(tid, Integer.MAX_VALUE);
	//long [][] threadArray = new long[tia.length][2];
	/* for (int i = 0; i < tia.length; i++) {
		//ThreadState threadState=new ThreadState();
		long threadId = tia[i].getThreadId();//线程ID
		String threadName=tia[i].getThreadName();//线程名称
		String state=tia[i].getThreadState().toString();//线程状态
		StackTraceElement[] stackTraceElements=tia[i].getStackTrace();
		long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId())/(1000*1000*1000);
		//threadState.setCpuTime(cpuTime);
		//threadState.setState(state);
		//threadState.setThreadId(threadId);
		//threadState.setThreadName(threadName);
		//threadState.setStackTraceElements(stackTraceElements);
	} */
	ThreadMXBean tm = ManagementFactory.getThreadMXBean();
		tm.setThreadContentionMonitoringEnabled(true);
		long [] tid = tm.getAllThreadIds();
		ThreadInfo [] tia = tm.getThreadInfo(tid, Integer.MAX_VALUE);
		//long [][] threadArray = new long[tia.length][2];
		StringBuilder table=new StringBuilder();
		table.append("<table class=\"table\">");
		table.append("<tr>")
				.append("<th>threadId </th>")
				.append("<th>threadName </th>")
				.append("<th>state</th>")
				.append("<th>cpuTime</th>")
				.append("<th>stackTraceElements</th>")
				.append("</td>");
		for (int i = 0; i < tia.length; i++) {
			long threadId = tia[i].getThreadId();//线程ID
			String threadName=tia[i].getThreadName();//线程名称
			String state=tia[i].getThreadState().toString();//线程状态
			StackTraceElement[] stackTraceElements=tia[i].getStackTrace();
			long cpuTime = tm.getThreadCpuTime(tia[i].getThreadId())/(1000*1000*1000);
			table.append("<tr>");
			table.append("<td>").append(threadId).append("</td>");
			table.append("<td>").append(threadName).append("</td>");
			table.append("<td>").append(state).append("</td>");
			table.append("<td>").append(cpuTime).append(" sec</td>");
			table.append("<td>");
			for(StackTraceElement stack:stackTraceElements){
				table.append(stack.toString()).append("</br>");
			}
			table.append("</td>");
			table.append("</tr>");
		}
		table.append("</table>");
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Listener.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
			body,table{ 
				font-size:12px; 
			} 
			table{ 
				table-layout:fixed; 
				empty-cells:show; 
				border-collapse: collapse; 
				margin:0 auto; 
			} 
			td{ 
				height:30px; 
			} 
			h1,h2,h3{ 
				font-size:12px; 
				margin:0; 
				padding:0; 
			} 
			.table{ 
				border:1px solid #cad9ea; 
				color:#666; 
			} 
			.table th { 
				background-repeat:repeat-x; 
				height:30px; 
			} 
			.table td,.table th{ 
				border:1px solid #cad9ea; 
				padding:0 1em 0; 
			} 
			.table tr.alter{ 
				background-color:#f5fafe; 
			} 
	</style>
  </head>
  
  <body>
   	 <%=table %>
  </body>
</html>
