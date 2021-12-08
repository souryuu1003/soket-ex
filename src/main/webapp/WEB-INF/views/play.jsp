<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="js/play.js"></script>
<script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
<link href="css/play.css" rel="stylesheet">
<!-- <link rel="stylesheet" type="text/css" href="vendor/fontawesome-free-5.8.2-web/css/all.min.css"> -->
<html>
<head>
<title>ITKey 메신저</title>
<script>
//웹소켓 연결 (com.itkey.sam의 EchoHandler3,4 사용)
var sockls = new SockJS("<c:url value="/echo3"/>");
var sockPlay = new SockJS("<c:url value="/echo4"/>");

sockls.onopen = listMessage;

var playTimes = 0;
function listMessage(){
	sockls.onmessage = function (e) {
		var data = e.data;
		var itemset = null;
		var memberid = null;
		var total = null;
		var now = null;
		var statVal = null;
		var strArray = data.split('|');
		
		itemset = strArray[0];
		memberid = strArray[1];
		picture = "images/" + strArray[2];			
		sessionid = strArray[3];
		total = strArray[4];
		now = strArray[5];
		status = strArray[6];
		statVal = strArray[7];
		
		
			picture = "https://ptetutorials.com/images/user-profile.png";
        
        if(status == "list") {
        	// 웹소켓에 연결이 성립되고 회원목록을 출력할 때
			var memboxid = "ready_" + memberid;
			var nowboxid = "nowScore_" + memberid;
			
			var printHTML = "<div class='list_wrap' style='background-image: url("+ picture +");' >";
			printHTML += "<div class='list'>";
			printHTML += "<div class='list_left'>";
			printHTML += "<div class='img_wrap'><img src='"+ picture +"' class='img_file'></div>";
			printHTML += "</div>";
			printHTML += "<div class='list_right'>";
			printHTML += "<div class='list_id'>"+ memberid +"</div>";
			printHTML += "<div class='list_ready "+ memboxid +"'></div>";
			printHTML += "<div class='list_bottom'><div class='list_set'>"+1+"SET / "+total+"</div><div class='clear'></div></div>";
			printHTML += "</div>";
			printHTML += "<div class='clear'></div>";
			printHTML += "</div>";
			printHTML += "<div class='"+ nowboxid +" now_score'>0</div>";
			printHTML += "</div>";
			$(".memberList").append(printHTML);
        } else if (status == "sw") {
        	var memboxid = ".ready_" + memberid;
        	var printHTML = "<img src='images/check.svg'>";
        	$(memboxid).append(printHTML);
        } else if (status == "play") {
        	var nowboxid = "nowScore_" + memberid;
        	var idcheck = document.getElementsByClassName(nowboxid)[0];
        	var startbtn = document.getElementsByClassName("start_btn")[0];
        	var clickedid = document.getElementsByClassName('clicked_player')[0];
        	var nowscores = document.getElementsByClassName("now_score");
        	
        	var chkbox = document.getElementsByClassName("play_btn");
        	for(var i=0; i<chkbox.length; i++) {
        		chkbox[i].parentNode.removeChild(chkbox[i]);
        	}
        	
        	idcheck.innerText = now;
        	
        	playTimes++;
        	var sc = score.toString(10);
        	sc = sc.replace('0','');
        	
        	if(playTimes==1) {
        		clickedid.innerText = "Start!";        		
        		startbtn.style.display = "none";        		
        		
        		sockls.send("{{playStart}}");
        		
        		for(var i=0; i<nowscores.length; i++) {
            		nowscores[i].innerText = "0";
            	}		
        		
        	} else if(playTimes>=6) {
        		clickedid.innerText = memberid + " : " + sc + "pts.";
        		startbtn.style.display = "block";	        		        		
        		        		
        		var cards_score = document.getElementsByClassName("now_score");
        		var cards_id = document.getElementsByClassName("list_id");
        		var card;
        		
        		var elements = new Array(cards_score.length)
        		for(var i=0; i<cards_score.length; i++) {
        			card = {
        				id: cards_id[i].innerText,
        				score: cards_score[i].innerText
        			};
        			elements.push(card);
        		}
        		
        		elements.sort(function (a, b) { 
        			return a.score > b.score ? -1 : a.score < b.score ? 1 : 0;  
        		});
        		
        		setTimeout(function() {
        			clickedid.innerText = "WINNER : " + elements[0].id + " "+ elements[0].score + "pts!";
        	    }, 1000);
        		
        		playTimes = 0;
        	} else {
        		clickedid.innerText = memberid + " : " + sc + "pts.";
        	}
        }
	}
}

//점수
var score = new Number('0');
function btnClick() {
	score = 0;
	var chkbox = document.getElementsByClassName("play_btn");
	for(var i=0; i<chkbox.length; i++) {
		if(chkbox[i].checked == true) {
			score += chkbox[i].value;
		}
		chkbox[i].checked == false;
		chkbox[i].parentNode.removeChild(chkbox[i]);
	}
	sendMessage('play');
}


// 메세지 보낼 때
function sendMessage(e){
	// 메시지 웹소켓에 전송
	if(e=='play') {		
		sockls.send("{{"+e+"}}" + score);
		
		setTimeout(function() {
			playStart();
	    }, 500);
	} else if (e=='sw') {
		var readybtn = document.getElementsByClassName("ready_btn")[0];
		var readybtn2 = document.getElementsByClassName("ready_btn_clicked")[0];
		readybtn.style.display = "none";
		readybtn2.style.display = "block";
		sockls.send("{{"+e+"}}" + "1");
	}
}

var row = 0;
sockPlay.onmessage = function (e) {
	var data = e.data;
	var axisX = null;
	var axisY = null;
	var strArray = data.split('|');
	var addTable = document.getElementById("board_wrap");
	var tableBody = document.getElementsByClassName("board_body")[0];
	
	axisX = strArray[0];
	axisY = strArray[1];
    score = strArray[2];
    row = strArray[3];
	
	if(playTimes==1) {
		addTable.removeChild(tableBody);
		var printHTML = "<tbody class='board_body'><tr></tr></tbody>";
		$('#board_wrap').append(printHTML);
		
		addRow(row);
	}
    
	var axis = "."+axisX+"_"+axisY;
	var printHTML = "<input type='checkbox' class='play_btn' onclick='btnClick();' value='"+score+"'>";
	$(axis).append(printHTML);
}

function playStart() {
	var stat = document.getElementsByClassName("start_status")[0];
	var addTable = document.getElementsByClassName("tablerow")[0];
	row = addTable.value;
	
	stat.value = "1";
	
	if(playTimes<=6 && playTimes>0) {
		sockPlay.send(row);
	} 
}

</script>
</head>
<body>
	<div class="entire_wrap">
		<input type="hidden" class="start_status" value="0">
		<div class="entire_title">DoTheG</div>
		<div class="clicked_player">Waiting...</div>
		<div class="play_wrap">
			<table id="board_wrap">
				<tbody class="board_body">
					<tr></tr>
				</tbody>
			</table>		
		</div>
		
		<div class="ctrl_wrap">
			<select class="tablerow">
				<option value="15">15X15</option>
				<option value="10">10X10</option>
			</select>
			<div class="ready_btn" onclick="sendMessage('sw')">Ready</div>
			<div class="ready_btn_clicked">Ready</div>
			<div class="start_btn" onclick="sendMessage('play')">Start</div>
			<div class="clear"></div>
			<div class="memberList">
			</div>			
		</div>
		<div class="clear"></div>
	</div>
	
	<div>
		<textarea></textarea>
	</div>
</body>
</html>