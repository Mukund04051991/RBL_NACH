<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="DtaeRange.Bean"%>
<%@page import="DtaeRange.taskDao"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>

<script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.17.0/xlsx.full.min.js"></script>

<style>
#myTable {
  border-collapse: collapse; /* Collapse borders */
  width: 100%; /* Full-width */
  border: 1px solid #ddd; /* Add a grey border */
  font-size: 18px; /* Increase font-size */
}

#myTable th, #myTable td {
  text-align: left; /* Left-align text */
  padding: 12px; /* Add padding */
}

#myTable tr {
  /* Add a bottom border to all table rows */
  border-bottom: 1px solid #ddd;
}

#myTable tr.header, #myTable tr:hover {
  /* Add a grey background color to the table header and on hover */
  background-color: #f1f1f1;
}
.header_part {
   /* background:#000000; */
   font-size:150%;
   color:#000000;
   padding:15px 100px;
   top:0;
   width:100%;
   text-align:center;
   z-index:;
  }
  
 .space {
  width: 100px;
  height: auto;
  display: inline-block;
}

.space1 {
  width: 50px;
  height: auto;
  display: inline-block;
}


</style>

<%  
System.out.println("loading....");
String strFromDate = request.getParameter("From_date")==null?"":request.getParameter("From_date");
String strToDate = request.getParameter("To_date")==null?"":request.getParameter("To_date");
String strTypes = request.getParameter("Types")==null?"":request.getParameter("Types");

List<Bean> getUmrnList = new ArrayList<Bean>();
taskDao ud = new taskDao();
getUmrnList = ud.getUmrnDetailss(strFromDate,strToDate,strTypes);
for(Bean b :getUmrnList){
	System.out.println();
}

%>


<body>
<h4 class="header_part" >
	<marquee>Cancellation Report</marquee>
</h4>
		<form action="GetACC" method="post">
            <center>
			<table border="1">
				<tr>
					<label for="From Date">From date:</label>
					<input type="date" id="From_date" width:50px name="From_date">

				</tr>
				<div class="space">
				<tr>
					<label for="To Date">To date:</label>
					<input type="date" id="To_date" width:50px name="To_date">
				</tr>
				<div class="space1">
				<tr>
				  <label for="Types"> Select Type </label>  
                <select name="Types" id="Types">  
                    <option value = "CREATE"> CREATE</option>  
                     <option value = "AMENDED">AMENDED</option>  
                     <option value = "CANCEL"> CANCEL </option>  
                </select>  
                </tr>
                <div class="space1">
				<tr>
					<input type="submit" value="Submit">
				</tr>
				
				<tr>
				<a href ="report.jsp">Generate Report</a>
				</tr>
			</table> </center>
			
			<br>
			<table id="myTable">
			
			                        <tr><th bgcolor="#000000" style="color:#ffffff">CANCELLATION DATE</th>
									<th bgcolor="#000000" style="color:#ffffff">CANCELLATION TYPE</th>
									<th bgcolor="#000000" style="color:#ffffff">UMRN</th>
									<th bgcolor="#000000" style="color:#ffffff">CUST REF NO</th>
									<th bgcolor="#000000" style="color:#ffffff">SCH REF NO</th>
									<th bgcolor="#000000" style="color:#ffffff">CUST NAME</th>
									<th bgcolor="#000000" style="color:#ffffff">BANK</th>
									<th bgcolor="#000000" style="color:#ffffff">MANDATE ID</th>
									<th bgcolor="#000000" style="color:#ffffff">BANK CODE</th>
									<th bgcolor="#000000" style="color:#ffffff">AC TYPE</th>
									<th bgcolor="#000000" style="color:#ffffff">ACCOUNT NO</th>
									<th bgcolor="#000000" style="color:#ffffff">AMOUNT</th>
									<th bgcolor="#000000" style="color:#ffffff">FREQUENCY</th>
									<th bgcolor="#000000" style="color:#ffffff">DEBIT TYPE</th>
									<th bgcolor="#000000" style="color:#ffffff">START DATE</th>
									<th bgcolor="#000000" style="color:#ffffff">END DATE</th>
									<th bgcolor="#000000" style="color:#ffffff">UNTIL CANCEL</th>
									<th bgcolor="#000000" style="color:#ffffff">CATEGORY CODE</th>
									<th bgcolor="#000000" style="color:#ffffff">MOBILE NO</th>
									<th bgcolor="#000000" style="color:#ffffff">MAIL ID</th>
									<th bgcolor="#000000" style="color:#ffffff">UPLOAD DATE</th>
									<th bgcolor="#000000" style="color:#ffffff">ACK DATE</th>
									<th bgcolor="#000000" style="color:#ffffff">RESPONSE DATE</th>
									<th bgcolor="#000000" style="color:#ffffff">UTILITY CODE</th>
									<th bgcolor="#000000" style="color:#ffffff">UTILITY NAME</th>
									<th bgcolor="#000000" style="color:#ffffff">STATUS</th>
									<th bgcolor="#000000" style="color:#ffffff">STATUS CODE</th>
									<th bgcolor="#000000" style="color:#ffffff">REASON</th>
									<th bgcolor="#000000" style="color:#ffffff">ADDITIONAL INFO 1</th>
									<th bgcolor="#000000" style="color:#ffffff">ADDITIONAL INFO 2</th>
									<th bgcolor="#000000" style="color:#ffffff">ADDITIONAL INFO 3</th>
									<th bgcolor="#000000" style="color:#ffffff">ADDITIONAL INFO 4</th>
									<th bgcolor="#000000" style="color:#ffffff">ADDITIONAL INFO 5</th>
									<th bgcolor="#000000" style="color:#ffffff">LOCATION</th>
									<th bgcolor="#000000" style="color:#ffffff">PICKUP POINT</th> 
	 							    </tr>
								     
								     <%
							for (Bean CM : getUmrnList) {
							%>

							<tr>
                                    <th><%=CM.getMANDATE_DATE() == null ? "-" : CM.getMANDATE_DATE()%></th>
									<th><%=CM.getMMS_TYPE() == null ? "-" : CM.getMMS_TYPE()%></th>
									<th><%=CM.getUMRN() == null ? "-" : CM.getUMRN()%></th>
									<th><%=CM.getDBTR_OTHER_DETAILS() == null ? "-" : CM.getDBTR_OTHER_DETAILS()%></th>
									<th><%=CM.getDBTR_PRVTID_ID() == null ? "-" : CM.getDBTR_PRVTID_ID()%></th>
									<th><%=CM.getDBTR_NAME() == null ? "-" : CM.getDBTR_NAME()%></th>
									<th><%=CM.getDBTR_AGNT_NAME() == null ? "-" : CM.getDBTR_AGNT_NAME()%></th>
									<th><%=CM.getREF_NUM() == null ? "-" : CM.getREF_NUM()%></th>
									<th><%=CM.getDBTR_AGNT_IFSC() == null ? "-" : CM.getDBTR_AGNT_IFSC()%></th>
						  		    <th><%=CM.getDBTR_ACCT_PRTRY() == null ? "-" : CM.getDBTR_ACCT_PRTRY()%></th>
									<th><%=CM.getDBTR_ACCT_NO() == null ? "-" : CM.getDBTR_ACCT_NO()%></th>
									<th><%=CM.getAMOUNT() == null ? "-" : CM.getAMOUNT()%></th>
									<th><%=CM.getFREQUENCY() == null ? "-" : CM.getFREQUENCY()%></th>
									<th><%=CM.getAMNT_TYPE() == null ? "-" : CM.getAMNT_TYPE()%></th>
									<th><%=CM.getMMS_FROM_DATE() == null ? "-" : CM.getMMS_FROM_DATE()%></th>
									<th><%=CM.getMMS_TO_DATE() == null ? "-" : CM.getMMS_TO_DATE()%></th>
									<% if(CM.getMMS_TO_DATE().contains("VALID UNTILL CANCELLED")){ %>
										<th>Y</th>
										<% } 
									else {
										%><th><%=CM.getMMS_TO_DATE()%></th><%} %>
									<th><%=CM.getSVCLVL_PRTRY() == null ? "-" : CM.getSVCLVL_PRTRY()%></th>
									<th><%=CM.getDBTR_MOBILE_NO() == null ? "-" : CM.getDBTR_MOBILE_NO()%></th>
									<th><%=CM.getDBTR_EMAIL_ID() == null ? "-" : CM.getDBTR_EMAIL_ID()%></th>
									<th><%=CM.getAUTH_DATE() == null ? "-" : CM.getAUTH_DATE()%></th>
									<th><%=CM.getACK_DATE_NPCI() == null ? "-" : CM.getACK_DATE_NPCI()%></th>
									<th><%=CM.getACK_DATE_RECEIVER() == null ? "-" : CM.getACK_DATE_RECEIVER()%></th>
									<th><%=CM.getCDTR_ACCT_NO() == null ? "-" : CM.getCDTR_ACCT_NO()%></th>
									<th><%=CM.getCDTR_NAME() == null ? "-" : CM.getCDTR_NAME()%></th>
									<th><%=CM.getSTATUS() == null ? "-" : CM.getSTATUS()%></th> 
									<th><%=CM.getREJECT_CODE_DESTINATION() == null ? "-" : CM.getREJECT_CODE_DESTINATION()%></th> 
								    <th><%=CM.getREJECT_REASON() == null ? "-" : CM.getREJECT_REASON()%></th>
								    <th><%=CM.getADDITIONAL_INFO_1() == null ? "-" : CM.getADDITIONAL_INFO_1()%></th>
								    <th><%=CM.getADDITIONAL_INFO_2() == null ? "-" : CM.getADDITIONAL_INFO_2()%></th>
									<th><%=CM.getADDITIONAL_INFO_3() == null ? "-" : CM.getADDITIONAL_INFO_3()%></th>
									<th><%=CM.getADDITIONAL_INFO_4() == null ? "-" : CM.getADDITIONAL_INFO_4()%></th>
									<th><%=CM.getADDITIONAL_INFO_5() == null ? "-" : CM.getADDITIONAL_INFO_5()%></th>
									<th><%=CM.getLOCATION() == null ? "-" : CM.getLOCATION()%></th>
									<th><%=CM.getPICKUP_POINT() == null ? "-" : CM.getPICKUP_POINT()%></th>  									
									</tr>
							<%
							}
								     session.setAttribute("getUmrnList",getUmrnList);
								     session.setAttribute("strFromDate",strFromDate);
								     session.setAttribute("strToDate",strToDate);
							%>
								     
				 </table>
		</form>
		
</body>
</html>