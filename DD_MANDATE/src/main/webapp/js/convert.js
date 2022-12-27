$(document).ready(function() {
	alert('loading');
	$("#exportWorksheet").click(function() {
		var jsonData = $("#exceldata").text();
		alert('jsonData:' + jsonData);
		exportWorksheet(jsonData);
	});
	function exportWorksheet(jsonData) {

		var filename = 'reports.xlsx';

		var ws = XLSX.utils.json_to_sheet(JSON.parse(jsonData));
		var wb = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(wb, ws, "People");
		XLSX.writeFile(wb, filename);
	}

});