function addRow(e) {
	var table = document.getElementsByClassName("board_body")[0];
	var row = new Array();
	
	for(var i=0; i<e; i++) {
		row[i] = table.insertRow(i+1);
	}
	
	for(var i=0; i<e; i++) {
		for(var j=0; j<e; j++) {
			var cell = row[i].insertCell(j);
			cell.className = i+"_"+j;
		}
	}
}