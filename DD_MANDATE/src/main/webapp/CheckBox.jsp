<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DD TXN FILE VERIFICATION QUEUE</title>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<script src="js/jquery-ui.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="css/buttons.dataTables.min.css" />
<script src="js/dataTables.buttons.min.js"></script>
<script src="js/jszip.min.js"></script>
<script src="js/vfs_fonts.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/fsvs/1.2.2/css/style.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="js/jquery-ui.js"></script>

<style>
.center {
	border: 3px solid #5499C7;
	text-align: center;
}

#button {
	display: block;
	margin: 20px auto;
	padding: 10px 30px;
	background-color: #eee;
	border: solid #ccc 1px;
	cursor: pointer;
}

#overlay {
	position: fixed;
	top: 0;
	z-index: 100;
	width: 100%;
	height: 100%;
	display: none;
	background: rgba(0, 0, 0, 0.6);
}

.cv-spinner {
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
}

.spinner {
	width: 40px;
	height: 40px;
	border: 4px #ddd solid;
	border-top: 4px #2e93e6 solid;
	border-radius: 50%;
	animation: sp-anime 0.8s infinite linear;
}

@
keyframes sp-anime { 100% {
	transform: rotate(360deg);
}

}
.is-hide {
	display: none;
}

#spinner-div {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	text-align: center;
	background-color: rgba(255, 255, 255, 0.8);
	z-index: 2;
}
</style>
<style>
/* Style to center grid column */
.col-centered {
	float: none;
	margin: 0 auto;
}

/* Some custom styles to beautify this example */
.row {
	background: #dbdfe5;
}

.demo-content {
	padding: 25px;
	font-size: 18px;
	background: #abb1b8;
}
</style>
<script>
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : 'dd-mm-yy',

		});
		$("#datepicker").datepicker("setDate", "today");
	});
	$(document)
			.ready(
					function() {
						console.log('loaded');
						$.noConflict();
						var fromdate = $('#datepicker').datepicker('setDate',
								'today').val();
						function loaddata(fromdate) {
							alert('loaddata20122022:' + fromdate);
							var exceldata = "";

							$
									.ajax({
										url : "DDTransactionController",
										type : "POST",
										data : {
											"fromdate" : fromdate,
											action : "getFileDetails",
										},
										success : function(resp) {
											//alert('data:' + resp);
											//$("#example").html(resp);

											dataTable = $('#example')
													.DataTable(
															{
																dom : 'Bfrtip',
																buttons : [ 'colvis' ],
																columnDefs : [ {
																	orderable : false,
																	className : 'select-checkbox',
																	targets : 0,
																	'render' : function(
																			data,
																			type,
																			full,
																			meta) {
																		return '<input type="checkbox" name="id[]" value="'
																				+ $(
																						'<div/>')
																						.text(
																								data)
																						.html()
																				+ '">';
																	}
																} ]

															});

											$
													.each(
															resp,
															function(index,
																	value) {

																var obj = jQuery
																		.parseJSON(value);
																//alert("obj.FILE_NAME:"
																//	+ obj.FILE_NAME);

																var results = "\""
																		+ "results"
																		+ "\"";
																exceldata += "{"
																		+ results
																		+ ":"
																		+ "["
																		+ value
																		+ "]}"
																		+ ",";

																dataTable.row
																		.add([
																				,
																				obj.UTILITY_NAME,
																				obj.UTILITY_CODE,
																				obj.UPLOAD_FILE_NAME,
																				obj.UPLOAD_DATE,
																				obj.settlement_date,
																				obj.TOTAL_COUNT,
																				obj.TOTAL_AMOUNT,
																				obj.SUCCESS_COUNT,
																				obj.REJECTED_COUNT,
																				obj.MAKER_ID,
																				'<button type="button" id="view" class=".btn-edit" data-id="7" data-includeTax="N">View Report</button>' ]);

															});
											dataTable.draw();
											exceldata = exceldata.replace(
													/,\s*$/, "");

											exceld = "[" + exceldata + "]";

										}
									});
						}
						//loaddata();
						var dataTable;
						$("#rangeform").on('submit', function(e) {

							e.preventDefault();

						});
						/*$('#example').on('click', 'tr', function() {
							alert($(this).text());
						});*/
						//view  link code
						//view
						$('#example tbody').on(
								'click',
								'view',
								function() {
									var data = $('#example').DataTable().row(
											this).data();
									//alert('You clicked on ' + data[3]
									//	+ "'s row");

								});
						$('#example-select-all').on(
								'click',
								function() {
									alert('click all me');

									/*$('#example').dataTable().fnDestroy();
									dataTable = $('#example').DataTable({
									destroy : true
									});*/
									// Get all rows with search applied
									var rows = dataTable.rows({
										'search' : 'applied'
									}).nodes();
									// Check/uncheck checkboxes for all rows in the table
									$('input[type="checkbox"]', rows).prop(
											'checked', this.checked);
								});
						$("#post")
								.on(
										"click",
										function(e) {
											alert("verify");
											var selectvalue = "";
											var selectvalue2 = "";
											alert('post fromdate:' + fromdate);

											var dataTable = $('#example')
													.DataTable();

											dataTable
													.$(
															'input[type="checkbox"]:checked')
													.each(
															function() {
																selectvalue += dataTable
																		.row(
																				$(
																						this)
																						.closest(
																								'tr'))
																		.data()[3]
																		+ ",";

															});
											selectvalue2 = selectvalue.replace(
													/,\s*$/, "");
											//alert('selectvalue2 delete:'
											//		+ selectvalue2);

											$
													.ajax({
														type : "POST",
														url : "DDTransactionController",
														data : {
															action : "postTransaction",
															filename : selectvalue2,
															"fromdate" : fromdate
														},
														success : function(
																result) {
															console
																	.log('result:'
																			+ result);
															$('#result').html(
																	result);
															//location.reload();
															dataTable.clear()
																	.draw();
															loaddata(fromdate);
															//dataTable.columns().checkboxes
															//	.deselect(true);

														},
														error : function(result) {
															alert('error');
														}
													});

										});
						//
						$("#cancel")
								.on(
										"click",
										function(e) {
											//alert("reject");
											var selectvalue = "";
											var selectvalue2 = "";
											var dataTable = $('#example')
													.DataTable();

											dataTable
													.$(
															'input[type="checkbox"]:checked')
													.each(
															function() {
																selectvalue += dataTable
																		.row(
																				$(
																						this)
																						.closest(
																								'tr'))
																		.data()[1]
																		+ ",";

															});
											selectvalue2 = selectvalue.replace(
													/,\s*$/, "");
											//alert('selectvalue2 delete:'
											//+ selectvalue2);

											$
													.ajax({
														type : "POST",
														url : "DDTransactionController",
														data : {
															filename : selectvalue2,
															action : "reject"
														},
														success : function(
																result) {
															/*console.log('result:'
																	+ result);*/
															$('#result').html(
																	result);
															dataTable.clear()
																	.draw();
															loaddata();

														},
														error : function(result) {
															alert('error');
														}
													});

										});

						$('body').on(
								'change',
								'#example-select-all',
								function() {
									var rows, checked;
									rows = $('#example').find('tbody tr');
									checked = $(this).prop('checked');
									$.each(rows, function() {
										var checkbox = $(
												$(this).find('td').eq(0)).find(
												'input').prop('checked',
												checked);
									});
								});
						$('#example tbody').on(
								'change',
								'input[type="checkbox"]',
								function() {
									if (!this.checked) {
										var el = $('#example-select-all')
												.get(0);
										//alert('el:' + el);
										if (el && el.checked
												&& ('indeterminate' in el)) {
											el.indeterminate = true;
										}
									}
								});

						$('#example tbody').on(
								'click',
								'button[type="button"]',
								function() {
									alert('clicked');
									//add here
									var dataTable = $('#example').DataTable();
									var data = dataTable.row(
											$(this).parents('tr')).data();
									alert('File Name:' + data[3]);

								});
						$('#example tbody')
								.on(
										'click',
										'input[type="checkbox"]',
										function() {
											selectvalue = "";
											var dataTable = $('#example')
													.DataTable();

											if (!this.checked) {
												$('#example-select-all').prop(
														"checked", false);
											} else {
												if ($('input[type="checkbox"]:checked').length == $('#example tbody tr').length) {
													$('#example-select-all')
															.prop("checked",
																	true);
												}
											}
											if ($('#example-select-all').prop(
													"checked")) {
												$(
														'input[type="checkbox"]:checked:not(:first)')
														.each(
																function() {
																	selectvalue += dataTable
																			.row(
																					$(
																							this)
																							.closest(
																									'tr'))
																			.data()[1]
																			+ "       ";
																});
												//alert('selectvalue1:'
												//	+ selectvalue);
											} else {
												$(
														'input[type="checkbox"]:checked')
														.each(
																function() {
																	selectvalue += dataTable
																			.row(
																					$(
																							this)
																							.closest(
																									'tr'))
																			.data()[1]
																			+ "       ";

																});
												//alert('selectvalue2:'
												//	+ selectvalue);

											}

										});

						$('#daterange')
								.click(
										function() {
											//alert('hi');
											//$('#spinner-div').show();
											//	$("#overlay").show();
											$('#example').dataTable()
													.fnDestroy();

											//$('.loader').show();
											var fromdate = $('#datepicker')
													.datepicker({
														dateFormat : 'dd-mm-yy'
													}).val();

											alert("fromdate:" + fromdate);
											$
													.ajax({
														type : "POST",
														url : "DDTransactionController",
														data : {

															action : "getFileDetails",
															"fromdate" : fromdate,

														},
														dataType : "json",
														success : function(resp) {

															//alert(resp);
															//$('#exceldata')
															//	.text(resp);
															//var dataTable = $('#example').DataTable();
															dataTable = $(
																	'#example')
																	.DataTable(
																			{
																				dom : 'Bfrtip',
																				buttons : [ 'colvis' ],
																				columnDefs : [ {
																					orderable : false,
																					className : 'select-checkbox',
																					targets : 0,
																					'render' : function(
																							data,
																							type,
																							full,
																							meta) {
																						return '<input type="checkbox" name="id[]" value="'
																								+ $(
																										'<div/>')
																										.text(
																												data)
																										.html()
																								+ '">';
																					}
																				} ]

																			});
															alert('dataTable:'
																	+ dataTable);

															dataTable.clear()
																	.draw();
															var fragment = "";
															var exceldata = "";
															var exceld = "";
															$
																	.each(
																			resp,
																			function(
																					index,
																					value) {

																				var obj = jQuery
																						.parseJSON(value);

																				var results = "\""
																						+ "results"
																						+ "\"";
																				exceldata += "{"
																						+ results
																						+ ":"
																						+ "["
																						+ value
																						+ "]}"
																						+ ",";

																				dataTable.row
																						.add([

																								,
																								obj.UTILITY_NAME,
																								obj.UTILITY_CODE,
																								obj.UPLOAD_FILE_NAME,
																								obj.UPLOAD_DATE,
																								obj.settlement_date,
																								obj.TOTAL_COUNT,
																								obj.TOTAL_AMOUNT,
																								obj.SUCCESS_COUNT,
																								obj.REJECTED_COUNT,
																								obj.MAKER_ID,
																								'<button type="button" id="view" class="btn green btn-xs select-row" data-id="7" data-includeTax="N">View</button>' ]);

																			});
															exceldata = exceldata
																	.replace(
																			/,\s*$/,
																			"");

															exceld = "["
																	+ exceldata
																	+ "]";

															dataTable.draw();
															$("#overlay")
																	.hide();
														}

													});

										});
					});
</script>
</head>
<body>
	<div class="center">
		<h3>DD FILE VERIFICATION QUEUE</h3>
	</div>
	<form action="" id="rangeform">
		<div class="col-lg-6">
			<div class="center">
				<div class="form-outline w-50">
					<label for="datepicker">Upload Date:</label> <input type="text"
						name="fromDate" id="datepicker">
				</div>
				<div>
					<input type="submit" id="daterange" value="Submit"> <br>
					<br>
				</div>
				<div>
					<span id="result"></span>
				</div>

			</div>
			<div>
				<span id="exceldata"></span>
			</div>
			<div class="container my-3 bg-light">
				<div class="col-md-12 text-center">
					<button type="button" id="post" class="btn btn-primary">POST</button>
					<button type="button" id="cancel" class="btn btn-warning">CANCEL</button>
				</div>
			</div>
			<div id="overlay">
				<div class="cv-spinner">
					<span class="spinner"></span>
				</div>
			</div>
			<div>
				<h1>
					<span id="result"></span>
				</h1>
			</div>
		</div>

	</form>
	<div class="table-responsive">
		<table id="example" class="mx-auto w-auto" border="1">
			<thead>
				<tr>
				<tr>
					<th><input name="select_all" value="" class="checkitem"
						id="example-select-all" type="checkbox" class="call-checkbox" /></th>
					<th>Utility Name</th>
					<th>Utility Code</th>
					<th>File Name</th>
					<th>Date of Upload</th>
					<th>Settlement Date</th>
					<th>Total No of Records</th>
					<th>Total Amount</th>
					<th>Success Count</th>
					<th>Rejected Count</th>
					<th>Maker ID</th>
					<th>View Records</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>