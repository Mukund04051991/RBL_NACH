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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
<script>
	$(document)
			.ready(
					function() {
						$('sampleUploadFrm').on('uploadBtn', function(e) {
							// validation code here
							e.preventDefault();

						});
						/* $
								.ajax({
									url : "GetUtilityController",
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
																			'<option value="' + value.utility_name + '">'
																					+ value.utility_name
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
 */
						function bs_input_file() {
							$(".input-file")
									.before(
											function() {
												if (!$(this).prev().hasClass(
														'input-ghost')) {
													var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
													element
															.attr("name", $(
																	this).attr(
																	"name"));
													element
															.change(function() {
																element
																		.next(
																				element)
																		.find(
																				'input')
																		.val(
																				(element
																						.val())
																						.split(
																								'\\')
																						.pop());
															});
													$(this)
															.find(
																	"button.btn-choose")
															.click(
																	function() {
																		element
																				.click();
																	});
													$(this)
															.find(
																	"button.btn-reset")
															.click(
																	function() {
																		element
																				.val(null);
																		$(this)
																				.parents(
																						".input-file")
																				.find(
																						'input')
																				.val(
																						'');
																	});
													$(this).find('input')
															.css("cursor",
																	"pointer");
													$(this)
															.find('input')
															.mousedown(
																	function() {
																		$(this)
																				.parents(
																						'.input-file')
																				.prev()
																				.click();
																		return false;
																	});
													return element;
												}
											});
						}

						bs_input_file();

						$("#uploadBtn")
								.on(
										"click",
										function() {
											var xFile = $('#txnfile').val();
											console.log('test:' + xFile);
											/* var utility_name = $(
													'#utility_name').val();
											console.log('utility_name:'
													+ utility_name);
											var utility_code = $(
													'#utility_code').val();
											console.log('utility_code:'
													+ utility_code);
											if (utility_name == "Select Utility Name") {
												alert("Please Select Utility Name");
												return false;
											} else if (utility_code == "Select Utility Code") {
												alert("Please Select Utility Code");
												return false; */
											/* } else */ if (xFile == "") {
												alert("Please upload file...");
												return false;
											} else {
												var url = "CancelBulkUploadController";
												var form = $("#sampleUploadFrm")[0];
												var data = new FormData(form);
												console.log('data:' + data);
												/* var data = {};
												data['key1'] = 'value1';
												data['key2'] = 'value2'; */
												$
														.ajax({
															type : "POST",
															encType : "multipart/form-data",
															url : url,
															cache : false,
															processData : false,
															contentType : false,
															data : data,
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
																	alert("Invalid UMRN Number");
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
																	alert("Couldn't upload file");
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
						/* $('#utility_name')
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
														url : "GetUtilityController",
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
																								'<option value="' + value.utility_code + '">'
																										+ value.utility_code
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
														url : "GetUtilityController",
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
 */
					});
</script>
</head>

<body>
	<div class="container">
		<div class="col-md-8 col-md-offset-2">
			<h3>UPLOAD BULK CANCEL MANDATE FILE</h3>
			<form id="sampleUploadFrm" method="POST" action="#"
				enctype="multipart/form-data">
				<!-- <div class="form-group">
					<label for="utility name">Utility Name:</label> <select
						id="utility_name" name="utility_name">
						<option>Select Utility Name</option>
					</select> <label for="utility code">Utility Code:</label> <select
						id="utility_code" name="utility_code">
						<option>Select Utility Code</option>
					</select>
				</div>
				<div class="form-group">
					<label for="email_id">Email ID:</label> <input type="text"
						name="email_id" id="email_id" value="">
				</div> -->
				<div class="input-group input-file" name="file">
					<span class="input-group-btn">
						<button class="btn btn-default btn-choose" type="button">Choose</button>
					</span> <input type="text" id="txnfile" class="form-control"
						placeholder='Choose a file...' /> <span class="input-group-btn">
						<button class="btn btn-warning btn-reset" type="button">Reset</button>
					</span>
				</div>
				<br>
				<div class="form-group">
					<button type="button" class="btn btn-primary pull-right"
						id="uploadBtn">Submit</button>
					<button type="reset" class="btn btn-danger">Reset</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>