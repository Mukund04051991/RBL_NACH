<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MMS SPONSOR FILE VERIFICATION QUEUE</title>
<script src="js/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.16.2/xlsx.full.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script
	src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/buttons/2.2.3/css/buttons.dataTables.min.css" />
<script
	src="https://cdn.datatables.net/buttons/2.2.3/js/dataTables.buttons.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
<script
	src="https://cdn.datatables.net/buttons/2.2.3/js/buttons.html5.min.js"></script>
<script
	src="https://cdn.datatables.net/buttons/2.2.3/js/buttons.print.min.js"></script>
<style>
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
<script>
	$(function() {
		$("#datepicker").datepicker();
		$("#datepicker1").datepicker();
	});
	$(document)
			.ready(
					function() {

						var dataTable;
						$("#rangeform").on('submit', function(e) {

							e.preventDefault();

						});
						$('#example-select-all').on(
								'click',
								function() {
									alert('click all me');
									$('#example').dataTable().fnDestroy();
									dataTable = $('#example').DataTable({
									//destroy : true
									});
									// Get all rows with search applied
									var rows = dataTable.rows({
										'search' : 'applied'
									}).nodes();
									// Check/uncheck checkboxes for all rows in the table
									$('input[type="checkbox"]', rows).prop(
											'checked', this.checked);
								});
						$("#delete")
								.on(
										"click",
										function(e) {
											alert("hi");
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
											alert('selectvalue2 delete:'
													+ selectvalue2);

											$.ajax({
												type : "POST",
												url : "Test2.jsp",
												data : {
													id : selectvalue2
												},
												success : function(result) {
													/*console.log('result:'
															+ result);*/
													$('#result').html(result);

												},
												error : function(result) {
													alert('error');
												}
											});

										});
						//

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
										alert('el:' + el);
										if (el && el.checked
												&& ('indeterminate' in el)) {
											el.indeterminate = true;
										}
									}
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
											alert('hi');
											//$('#spinner-div').show();
											$("#overlay").show();
											$('#example').dataTable()
													.fnDestroy();

											//$('.loader').show();
											var fromdate = $('#datepicker')
													.val();
											var todate = $('#datepicker1')
													.val();
											alert("fromdate:" + fromdate);
											$
													.ajax({
														type : "POST",
														url : "GetDataController",
														data : {

															"fromdate" : fromdate,
															"todate" : todate,

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
																				buttons : [
																						{
																							extend : 'excelHtml5',
																							title : 'TRANSACTION REPORT'
																						},
																						{
																							extend : 'pdfHtml5',
																							title : 'TRANSACTION REPORT'
																						} ],
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
																				//console.log(value);
																				//console.log('index:' + index);
																				//console.log('value:' + value);
																				var obj = jQuery
																						.parseJSON(value);
																				//console.log('obj:' + obj);
																				//console.log('obj.id:' + obj.TXN_ID);
																				//console.log('obj.TXN_DATE:'
																				//		+ obj.TXN_DATE);
																				//console.log('obj.CUST_NAME:'
																				//		+ obj.CUST_NAME);
																				//console.log('obj.POST_FLAG:'
																				//		+ obj.POST_FLAG);
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
																								obj.TXN_ID,
																								obj.TXN_DATE,
																								obj.CUST_NAME,
																								obj.POST_FLAG,
																								obj.TRAN_REMARKS ]);
																				//dataTable.row.add([ obj.TXN_DATE ]);
																				/*$.each(JSON.parse(value), function(i,
																						item) {

																					console.log(item);
																					fragment = "<tr><td>" + item
																							+ "</td>" + "<td>" + item
																							+ "</td></tr>";
																					dataTable.row.add(fragment);
																					//alert(item.TXN_ID);
																				});*/
																				//console.log('data:' + index.TXN_ID);
																				//dataTable.row.add().draw();
																				//dataTable.row.add(value[index]).draw();
																			});
															exceldata = exceldata
																	.replace(
																			/,\s*$/,
																			"");
															//alert("exceldata>>>" + exceldata);
															exceld = "["
																	+ exceldata
																	+ "]";
															//console.log('exceldata:' + exceldata);
															//console.log('exceld:' + exceld);
															//$('#exceldata').text(exceld);
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
	<div>
		<h3>SPONSOR FILE VERIFICATION QUEUE</h3>
	</div>
	<form action="" id="rangeform">
			<div>
			<span id="result"></span>
		</div>
		<table id="example" class="display">
			<thead>
				<tr>
				<tr>
					<th><input name="select_all" value="" class="checkitem"
						id="example-select-all" type="checkbox" class="call-checkbox" /></th>
					<th>FILE NAME</th>
					<th>COUNT</th>
					<th>CORPORATE NAME</th>
					<th>MANDATE TYPE</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
		<div class="form-outline mb-4">
			<button type="submit" id="delete" value="Submit"
				class="btn btn-primary">DELETE</button>
		</div>
		<div id="overlay">
			<div class="cv-spinner">
				<span class="spinner"></span>
			</div>
		</div>
	</form>
</body>
</html>
