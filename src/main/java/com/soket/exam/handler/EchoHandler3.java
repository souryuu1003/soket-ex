package com.soket.exam.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.soket.exam.dto.MemberDTO;


public class EchoHandler3 extends TextWebSocketHandler {
	// Logback logger (package : org.slf4j.Logger & org.slf4j.LoggerFactory)
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
	private List<MemberDTO> memberList = new ArrayList<MemberDTO>();	
	private static Pattern pattern = Pattern.compile("^\\{\\{.*?\\}\\}");
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionList.add(session);
		Map<String,Object> map = session.getAttributes();
		MemberDTO mDTO = (MemberDTO) map.get("memberInfo");
		
		// 회원 정보에 세션값을 받는다.
		mDTO.setMemberSession(session.getId());
		mDTO.setNowScore("0");
		
		memberList.add(mDTO);
		memberList.get(0).setMemberHost("1");
		
		for(MemberDTO mem : memberList) {
			session.sendMessage(new TextMessage(mem.getItemSet()+"|"+mem.getMemberId()+"|"+mem.getMemberPicture()+"|"+mem.getMemberSession()+"|"+mem.getTotalScore()+"|"+ mem.getNowScore() +"|list|"+mem.getMemberHost()));
		}
		
		// session.setAttribute~ 이걸로 세션에 넣으면되고
		// dto 필요한ㄴ 정보는 저것들임 참고하면됨 ㅇㅋㅇㅋ 는 창을 두개 띄워 그럼 같은값 들어가는거 아ㅣㄴㅁ?
		// 너가 로그인따로 시키면 되자나
		// ㅇㅋ 일단 알겠음
		
		logger.info("Chatting List Response");
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Map<String,Object> map = session.getAttributes();
		MemberDTO mDTO = (MemberDTO) map.get("memberInfo");
		logger.info("from {}, {} received", session.getId(), message.getPayload());
		String opId = "";
		
		// 메시지 내용에 상대 아이디를 {{아이디}}형식으로 받는다.
		Matcher matcher = pattern.matcher(message.getPayload());
		if (matcher.find()) {
			opId = matcher.group();
		}
		
		// 받은 상대 아이디 추출
		final String sendInfo = opId.replaceFirst("^\\{\\{", "").replaceFirst("\\}\\}$", "");
		
		// 추출한 아이디를 메세지 내용에서 삭제하여 내용만 남게 한다.
		final String msg = message.getPayload().replaceAll(pattern.pattern(), "");
		
		// 해당 세션값으로 메시지 전송			
		if (sendInfo.equals("sw")) {
			for(WebSocketSession sess : sessionList) {
				sess.sendMessage(new TextMessage("|"+mDTO.getMemberId()+"|||||sw|"+msg));
			}
		} else if (sendInfo.equals("play")) {
			for(MemberDTO mem : memberList) {
				if(mem.getMemberId().equals(mDTO.getMemberId())) {
					Integer nowS = Integer.parseInt(mem.getNowScore()) + Integer.parseInt(msg);
					
					String scoring = Integer.toString(nowS);
					mem.setNowScore(scoring);
				}
			}
			for(WebSocketSession sess : sessionList) {
				sess.sendMessage(new TextMessage("|"+mDTO.getMemberId()+"||||"+mDTO.getNowScore()+"|play|"));
			}
		} else if (sendInfo.equals("playStart")) {
			for(MemberDTO mem : memberList) {
				if(mem.getMemberId().contentEquals(mDTO.getMemberId())) {
					mem.setNowScore("0");
				}
			}
		}
		
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Map<String,Object> map = session.getAttributes();
		MemberDTO mDTO = (MemberDTO) map.get("memberInfo");		
		memberList.remove(mDTO);
		sessionList.remove(session);
		logger.info("{} disconnected", session.getId());
	}
	
			
}
