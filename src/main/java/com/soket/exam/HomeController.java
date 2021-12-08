package com.soket.exam;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soket.exam.dto.MemberDTO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/")
	public String home(Locale locale, Model model) {
		
		return "home";
	}
	
	@RequestMapping(value = "/play.do")
	public String play(@RequestParam Map<String, Object> requestParam, MemberDTO memDTO, HttpSession session) {
		System.out.println(memDTO.getMemberId());
		session.setAttribute("memberInfo", memDTO);
		
		
		return "play";
	}
	
}
