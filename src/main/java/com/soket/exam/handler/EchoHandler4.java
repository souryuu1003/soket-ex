package com.soket.exam.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.soket.exam.dto.MemberDTO;

public class EchoHandler4 extends TextWebSocketHandler {
	// Logback logger (package : org.slf4j.Logger & org.slf4j.LoggerFactory)
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<WebSocketSession> visitorList = new ArrayList<WebSocketSession>();
	private List<MemberDTO> memberList = new ArrayList<MemberDTO>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Map<String,Object> map = session.getAttributes();
		MemberDTO mDTO = (MemberDTO) map.get("memberInfo");
		
		if(sessionList.size()<=4) {
			sessionList.add(session);
			mDTO.setMemberPlay("s");
		} else {
			visitorList.add(session);
			mDTO.setMemberPlay("v");
		}
		
		
		
		// 회원 정보에 세션값을 받는다.
		mDTO.setMemberSession(session.getId());		
		memberList.add(mDTO);
		
		logger.debug("Chatting List Response");
	}
	
	// 두더지 위치 랜덤으로 정하여 뿌림
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("from {}, {} received", session.getId(), message.getPayload());
		
		Integer row = Integer.parseInt(message.getPayload());
		Random rdX = new Random();
		Random rdY = new Random();
		Random rdScore = new Random();
		
		String axisX = Integer.toString(rdX.nextInt(row-1));
		String axisY = Integer.toString(rdY.nextInt(row-1));
		String score = Integer.toString(rdScore.nextInt(50));
		
				
		// 해당 세션값으로 메시지 전송
		for(WebSocketSession sess : sessionList) {
			sess.sendMessage(new TextMessage(axisX +"|"+ axisY +"|"+score+"|"+row));
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Map<String,Object> map = session.getAttributes();
		MemberDTO mDTO = (MemberDTO) map.get("memberInfo");		
		
		for(MemberDTO mem : memberList) {
			if(mem.getMemberId().equals(mDTO.getMemberId())) {
				if(mem.getMemberPlay().equals("s")) {
					sessionList.remove(session);
				} else {
					visitorList.remove(session);
				}
			}
		}
		
		memberList.remove(mDTO);
		logger.info("{} disconnected", session.getId());
	}
	
			
}
