<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
 <%-- <%@page import="Dao.java.userDao"%> 
 <%@page import="Dao.java.CancelMandate"%> --%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<!-- bootstrap link is added -->
       
       <!--css  -->
       <link rel="stylesheet" href="assets/css/jquery.dataTables.min.css"> 
      <link rel="stylesheet" href="assets/css/buttons.dataTables.min.css"> 
      
      
      <!--js  -->
   <script type="text/javascript" src ="assets/js/jquery-3.5.1.js"></script> 
      <script type="text/javascript" src ="assets/js/dataTables.bootstrap4.min.js"></script>
       <script type="text/javascript" src ="assets/js/jquery.dataTables.min.js"></script> 
      
<!-- bootstrap link is ended -->

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



<script type="text/javascript">

$(document).ready(function () {
    $('#example').DataTable();
});

</script>

<%-- <% 


List<CancelMandate> getUmrnList = new ArrayList<CancelMandate>();
userDao ud = new userDao();
//CancelMandate CM = new CancelMandate();
getUmrnList = ud.getModifyDetails();

System.out.println("Modify Details :" +getUmrnList);
%> --%> 


<body>
<form action="ModifyMandate" method="get">
<h4 class="header_part" >
	<marquee>Mandate Cancellation Queue(Maker)</marquee>
</h4>

<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
            
                        <th><input name="select_all" value="" class="checkitem"
						id="example-select-all" type="checkbox" class="call-checkbox" /></th>
                        
                                    <th bgcolor="#000000" style="color:#ffffff">Ref No</th>
                                    <th bgcolor="#000000" style="color:#ffffff" >UMRN</th>
                                    <th bgcolor="#000000" style="color:#ffffff">Cancel Code</th>
                                    <th bgcolor="#000000" style="color:#ffffff">Utility Code</th>
									<th bgcolor="#000000" style="color:#ffffff">Dest Bank IFSC</th>
									
								
	 							   

                
            </tr>
        </thead>
         <tbody>
            <%-- <%
            for(CancelMandate CM : getUmrnList ){
							%> --%>

							<tr>
                               <th></th>
                         <th><%-- <%=CM.getMms_type() == null ? "-" : CM.getMms_type()%> --%>1</th>
                         <th><%-- <a href="ModifyMandate?action=ThirdServlet&UMRN1=<%=CM.getUMRN1()%>"><%=CM.getUMRN1() == null ? "-" : CM.getUMRN1()%></a> --%>1</th>
                                    
									<th><%-- <%=CM.getCdtr_acct_no() == null ? "-" : CM.getCdtr_acct_no()%> --%>2</th>
									<th><%-- <%=CM.getCdtr_name() == null ? "-" : CM.getCdtr_name()%> --%>3</th>
									<th><%-- <%=CM.getCdtr_agnt_ifsc() == null ? "-" : CM.getCdtr_agnt_ifsc()%> --%>4</th>
								
									
							
									  									
							</tr> 
							<%-- <%
							}
							%> --%>
							
							
							
							
		
        </tbody>
            </table>







</form>
</body>
</html>