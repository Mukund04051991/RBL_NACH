<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload Example in JSP and Servlet - Java web
application</title>
<!-- bootstrap link is added -->
       
       <!--css  -->
       <link rel="stylesheet" href="assets/css/jquery.dataTables.min.css"> 
      <link rel="stylesheet" href="assets/css/buttons.dataTables.min.css"> 
      
      
      <!--js  -->
   <script type="text/javascript" src ="assets/js/jquery-3.5.1.js"></script> 
      <script type="text/javascript" src ="assets/js/dataTables.bootstrap4.min.js"></script>
       <script type="text/javascript" src ="assets/js/jquery.dataTables.min.js"></script> 
      
<!-- bootstrap link is ended -->
<script type="text/javascript">
$(document).ready(function () {
    $('#example').DataTable();
});
</script>


</head>

<body>

<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>File Name</th>
                <th>Utility Name</th>
                <th>Utility Code</th>
                <th>Mandate Type</th>
                <th>Mandate Type</th>
                <th>Mandates</th>
                <th>Data Entry Pending</th>
                <th>Pending for Verification</th>
                <th>Sendback Data Entry</th>
                <th>Pending Sendback Verification</th>
                <th>PendIng Verified Rejected</th>
                <th>Action Pending</th>
                <th>ACK Received</th>
                <th>Nack Received</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Tiger Nixon</td>
                <td>System Architect</td>
                <td>Edinburgh</td>
                <td>61</td>
                <td>2011-04-25</td>
                <td>$320,800</td>
            </tr>
            <tr>
                <td>Garrett Winters</td>
                <td>Accountant</td>
                <td>Tokyo</td>
                <td>63</td>
                <td>2011-07-25</td>
                <td>$170,750</td>
            </tr>
            
            </tbody>
        <tfoot>
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>Office</th>
                <th>Age</th>
                <th>Start date</th>
                <th>Salary</th>
            </tr>
        </tfoot>
    </table>
				
				
				
				
				
			
</body>
</html>