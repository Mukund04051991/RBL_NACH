<%@page import="org.apache.poi.util.SystemOutLogger"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="com.ddmandate.bean.UtilityDetails"%>
<%@page import="com.ddmandate.dao.MandateDashboardDao"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>

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
<script type="text/javascript" src="assets/js/jquery-3.5.1.js"></script>
<script type="text/javascript"
	src="assets/js/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript" src="assets/js/jquery.dataTables.min.js"></script>

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
	font-size: 150%;
	color: #000000;
	padding: 15px 100px;
	top: 0;
	width: 100%;
	text-align: center;
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
	$(document).ready(function() {
		$('#example').DataTable();
	});
</script>



<style type="text/css">
#3 {margin-top = 10px;
	
}

#4 {margin-top = 10px;
	
}
</style>



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
								.click(
										function(e) {
											e.preventDefault();
											var dataTable = $('#example')
													.DataTable();
											var utility_name = $(
													'#utility_name').val();
											console.log('utility_name:'
													+ utility_name);

											var utility_code = $(
													'#utility_code1').val();
											console.log('utility_code1:'
													+ utility_code1);

											var To_date = $('#To_date').val();
											console.log('To_date:' + To_date);

											if (To_date == "") {
												alert("Please Select Date");
												return false;
											} else if (utility_name == "Select Utility Name") {
												alert("Please Select Utility Name");
												return false;
											} else if (utility_code == "Select Utility Code") {
												alert("Please Select Utility Code");
												return false;
											} else {

												$('#example').dataTable()
														.fnDestroy();
												//dataTable.clear().draw();

												alert("utility_name:"
														+ utility_name);
												alert("utility_code:"
														+ utility_code);
												alert("To_date:" + To_date);

												$
														.ajax({
															type : "POST",
															url : "MandateDashboardController",
															data : {
																"utility_name" : utility_name,
																"utility_code" : utility_code,
																"To_date" : To_date
															},

															dataType : "json",

															success : function(
																	resp) {

																//$("#example").dataTable({
																//dataTable.clear().draw();

																//var fragment = "";
																var exceldata = "";
																//var exceld = "";
																
																
															
																

																dataTable = $(
																		'#example')
																		.DataTable(
																				{
																					dom : 'Bfrtip',
																					buttons : [ 'colvis' ],
																					columnDefs : [ {
																						orderable : false,
																						//className : 'select-checkbox',

																						//		"targets" : "_all",
																						"data" : "array",
																						'render' : function(
																								data,
																								type,
																								full,
																								meta)

																						{
																							return;
																						}
																					} ]

																				});

																$
																		.each(
																				resp,
																				function(
																						index,
																						value) {

																					alert('function(index,value)');
																					var obj = jQuery
																							.parseJSON(value);
																					console
																							.log('obj:'
																									+ obj.MANDATE_TYPE);

																					

																					dataTable.row
																							.add([

																									obj.FILE_NAME,
																									obj.UTILITY_NAME,
																									obj.UTILITY_CODE,
																									obj.MMS_TYPE,
																									obj.MANDATE_TYPE,
																									obj.DATA_ENTRY_PENDING_STATUS,
																									obj.PENDING_VERIFICATION_QUEUE,
																									obj.SENDBACK_DATA_ENTRY_PENDING,
																									obj.PENDING_VERIFICATION,
																									obj.SENBACK_BACK_FOR_VERIFICATION,
																									obj.VERIFIED,
																									obj.REJECTED,
																									obj.ACTION_PENDING,
																									obj.ACK_RECEVIED,
																									obj.NACK_RECEIVED

																							]);

																				});
																dataTable
																		.draw();
																exceldata = exceldata
																		.replace(
																				/,\s*$/,
																				"");

																exceld = "["
																		+ exceldata
																		+ "]";

																dataTable
																		.draw();
																$("#overlay")
																		.hide();
																
																
																$('#result').html(
																		result);
																dataTable.clear()
																		.draw();
																loaddata();

															}

															,
															error : function(
																	jqXHR,
																	textStatus,
																	errorThrown) {
																alert("Data Not Found");
															},
															cache : false

														});

											}

										});

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
																						'#utility_code1')
																						.append(
																								'<option value="' + value.CDTR_ACCT_NO + '">'
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

					});
</script>



</head>

<body>



	<form id="sampleUploadFrm" method="POST" action="#">
		<div class="form-group">

			<center>

				<h3>MANDATE DASHBOARD</h3>
				<label for="From Date">Date:</label> <input type="date" id="To_date"
					width:50px name="To_date"> <label for="utility name">Utility
					Name:</label> <select id="utility_name" name="utility_name">
					<option>Select Utility Name</option>
				</select> <label for="utility code">Utility Code:</label> <select
					id="utility_code1" name="utility_code1">
					<option>Select Utility Code</option>
				</select>

				<button type="button" class="btn btn-primary pull-right"
					id="uploadBtn">Submit</button>

				<!-- <input type="submit" value="Submit"> -->

			</center>

		</div>

		<table id="example" class="table table-striped table-bordered"
			style="width: 100%">
			<thead>
				<tr>

					<th bgcolor="#000000" style="color: #ffffff">FILE NAME</th>
					<th bgcolor="#000000" style="color: #ffffff">UTILITY NAME</th>
					<th bgcolor="#000000" style="color: #ffffff">UTILITY CODE</th>
					<th bgcolor="#000000" style="color: #ffffff">MANDATE TYPE</th>
					<th bgcolor="#000000" style="color: #ffffff">MANDATES</th>
					<th bgcolor="#000000" style="color: #ffffff">DATA ENTRY PENDING</th>
					<th bgcolor="#000000" style="color: #ffffff">PENDING FOR VERIFICATION</th>
					<th bgcolor="#000000" style="color: #ffffff">SENDBACK DATA ENTRY PENDING</th>
					<th bgcolor="#000000" style="color: #ffffff">SENDBACK VERIFICATION PENDING</th>
					<th bgcolor="#000000" style="color: #ffffff">VERIFIED</th>
					<th bgcolor="#000000" style="color: #ffffff">REJECTED</th>
					<th bgcolor="#000000" style="color: #ffffff">ACTION PENDING</th>
					<th bgcolor="#000000" style="color: #ffffff">ACK RECEIVED</th>
					<th bgcolor="#000000" style="color: #ffffff">NACK RECEIVED</th>


				</tr>
			</thead>
			<tbody>
				<%-- <%
				for (UtilityDetails CM : getUmrnList) {
				%>

				<tr>
					<th><%=CM.getBATCH_ID()%></th>
					<th><%=CM.getCDTR_AGNT_NAME() == null ? "-" : CM.getCDTR_AGNT_NAME()%></th>
					<th><%=CM.getCDTR_ACCT_NO() == null ? "-" : CM.getCDTR_ACCT_NO()%></th>
					<th><%=CM.getMMS_TYPE() == null ? "-" : CM.getMMS_TYPE()%></th>
					<th><%=CM.getMANDATE_COUNT() == null ? "-" : CM.getMANDATE_COUNT()%></th>
					<th><%=CM.getDATA_ENTRY_PENDING_STATUS() == null ? "-" : CM.getDATA_ENTRY_PENDING_STATUS()%></th>
					<th><%=CM.getPENDING_VERIFICATION_QUEUE() == null ? "-" : CM.getPENDING_VERIFICATION_QUEUE()%></th>
					<th><%=CM.getSENDBACK_DATA_ENTRY_PENDING() == null ? "-" : CM.getSENDBACK_DATA_ENTRY_PENDING()%></th>
					<th><%=CM.getSENBACK_BACK_FOR_VERIFICATION() == null ? "-" : CM.getSENBACK_BACK_FOR_VERIFICATION()%></th>
					<th><%=CM.getVERIFIED() == null ? "-" : CM.getVERIFIED()%></th>
					<th><%=CM.getREJECTED() == null ? "-" : CM.getREJECTED()%></th>
					<th><%=CM.getACTION_PENDING() == null ? "-" : CM.getACTION_PENDING()%></th>
					<th><%=CM.getACK_RECEVIED() == null ? "-" : CM.getACK_RECEVIED()%></th>
					<th><%=CM.getNACK_RECEIVED() == null ? "-" : CM.getNACK_RECEIVED()%></th>
					
				</tr>
				<%
				}
				%>
 --%>
			</tbody>
		</table>





	</form>
</body>
</html>