<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload Example in JSP and Servlet - Java web
application</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<style type="text/css">
#3 {margin-top = 10px;
	
}

#4 {margin-top = 10px;
	
}
</style>

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


<script>
	$(document)
			.ready(
					function() {
						$('sampleUploadFrm').on('uploadBtn', function(e) {
							// validation code here
							e.preventDefault();

						});
						$
								.ajax({
									url : "GetMandateDashboardController",
									method : "POST",
									data : {
										operation : 'getAll'
									},
									success : function(data, textStatus, jqXHR) {
										console.log(data);
										let obj = $.parseJSON(data);
										$
												.each(
														obj,
														function(key, value) {
															$('#utility_name')
																	.append(
																			'<option value="' + value.CDTR_AGNT_NAME + '">'
																					+ value.CDTR_AGNT_NAME
																					+ '</option>')
														});
										//$('select').formSelect();
									},
									error : function(jqXHR, textStatus,
											errorThrown) {
										$('#utility_name')
												.append(
														'<option>Utility Name Unavailable</option>');
									},
									cache : false
								});
						$("#uploadBtn")
						.on(
								"click",
								function() {
									var xFile = $('#txnfile').val();
									console.log('test:' + xFile);
									var utility_name = $(
											'#utility_name').val();
									console.log('utility_name:'
											+ utility_name);
									var utility_code = $(
											'#utility_code').val();
									console.log('utility_code:'
											+ utility_code);
									var To_date =$('#To_date').val();
									console.log('To_date:'+ To_date);
									
									
									
									if (To_date == "") {
										alert("Please Select Date");
										return false;
									}
									else if (utility_name == "Select Utility Name") {
										alert("Please Select Utility Name");
										return false;
									} else if (utility_code == "Select Utility Code") {
										alert("Please Select Utility Code");
										return false;
									} else if (xFile == "") {
										alert("Please upload file...");
										return false;
									} else {
										var url = "MandateDashboardController";
										var form = $("#sampleUploadFrm")[0];
										var data = new FormData(form);
										console.log('data:' + data);
										/* var data = {};
										data['key1'] = 'value1';
										data['key2'] = 'value2'; */
										$
												.ajax({
													type : "POST",
													
													url : url,
													cache : false,
													data : {
														
														
														To_date : To_date,
														utility_code : utility_code,
														utility_name :utility_name
														
														},
														
														
														
														
														
													success : function(
															msg) {
														var response = JSON
																.parse(msg);
														var status = response.status;
														console
																.log('status:'
																		+ status);
														if (status == 1) {
															alert("File has been uploaded successfully");
														} else if (status == 3) {
															alert("File has been uploaded successfully");
														} else if (status == 4) {
															alert("Utility Code doesn't match with Corporate Mater");
														} else if (status == 5) {
															alert("File Already Uploaded");
														} else if (status == 6) {
															alert("Invalid File Format");
														} else if (status == 7) {
															alert("Please Check Extension of file");
														} else if (status == 8) {
															alert("Settment Date shouldn't be blank/Past Date not allowed");
														} else if (status == 11) {
															alert("Blank Value in Excel");
														} else if (status == 12) {
															alert("Invalid Amount Value in Excel");
														} else {
															alert("No Data Found");
														}
														location
																.reload();
													},
													error : function(
															msg) {
														alert("Couldn't upload file");
													}
												});
									}
								});
				//
				$('#utility_name')
						.change(
								function() {
									$('#utility_code').find('option')
											.remove();
									$('#utility_code')
											.append(
													'<option>Select Utility Code</option>');

									let utility_name = $(
											'#utility_name').val();
									console.log('utility_name:'
											+ utility_name);
									let data = {
										operation : "getCodes",
										utility_name : utility_name
									};

									$
											.ajax({
												url : "GetMandateDashboardController",
												method : "POST",
												data : data,
												success : function(
														data,
														textStatus,
														jqXHR) {
													console.log(data);
													let obj = $
															.parseJSON(data);
													$
															.each(
																	obj,
																	function(
																			key,
																			value) {
																		$(
																				'#utility_code')
																				.append(
																						'<option value="' + value.CDTR_AGNT_NAME + '">'
																								+ value.CDTR_ACCT_NO
																								+ '</option>')
																	});
													//$('select')
													//	.formSelect();
												},
												error : function(jqXHR,
														textStatus,
														errorThrown) {
													$('#utility_code')
															.append(
																	'<option>Utility Code Unavailable</option>');
												},
												cache : false
											});
								});
				//added 23112022
				$('#utility_code')
						.change(
								function() {

									let utility_code = $(
											'#utility_code').val();
									let data = {
										operation : "getEmailId",
										utility_code : utility_code
									};

									$
											.ajax({
												url : "GetMandateDashboardController",
												method : "POST",
												data : data,
												success : function(data) {
													console.log('data:'
															+ data);
													$("#email_id").val(
															data);

												},
												error : function(jqXHR,
														textStatus,
														errorThrown) {

												},
												cache : false
											});
								});

			});
</script>



</head>

<body>

<h3>MANDATE DASHBOARD</h3>
<form id="sampleUploadFrm" method="POST" action="#" >
				<div class="form-group">
				<label for="From Date">Date:</label>
				<input type="date" id="To_date" width:50px name="To_date">
					<label for="utility name">Utility Name:</label> <select
						id="utility_name" name="utility_name">
						<option>Select Utility Name</option>
					</select> <label for="utility code">Utility Code:</label> <select
						id="utility_code" name="utility_code">
						<option>Select Utility Code</option>
					</select>
					
					<button type="button" class="btn btn-primary pull-right"
						id="uploadBtn">Submit</button>
				</div>
<table id="example" class="table table-striped table-bordered" style="width:100%">
        <thead>
            <tr>
                <th>FILE NAME</th>
                <th>Utility Name</th>
                <th>Utility Code</th>
                <th>Mandate Type</th>
                <th>Mandates</th>
                <th>Data Entry Pending</th>
                <th>Pending for Verification</th>
                <th>Sendback Data Entry Pending</th>
                <th>Sendback Verification Pending</th>
                <th>Verified</th>
                <th>Rejected</th>
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
                <td>Tiger Nixon</td>
                <td>System Architect</td>
                <td>Edinburgh</td>
                <td>61</td>
                <td>2011-04-25</td>
                <td>$320,800</td>
                <td>$320,800</td>
                <td>$320,800</td>
            </tr>
            <tr>
                <td>Garrett Winters</td>
                <td>Accountant</td>
                <td>Tokyo</td>
                <td>63</td>
                <td>2011-07-25</td>
                <td>$170,750</td>
                <td>Garrett Winters</td>
                <td>Accountant</td>
                <td>Tokyo</td>
                <td>63</td>
                <td>2011-07-25</td>
                <td>$170,750</td>
                <td>$170,750</td>
                <td>$320,800</td>
            </tr>
            
            </tbody>
       <!--  <tfoot>
            <tr>
                <th>Name</th>
                <th>Position</th>
                <th>Office</th>
                <th>Age</th>
                <th>Start date</th>
                <th>Salary</th>
            </tr>
        </tfoot> -->
    </table>
				
				
				
				
				
	</form>		
</body>
</html>