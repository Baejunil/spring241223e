package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController는 Restful Web API를 좀 더 쉽게 만들기 위해 
//스프링 프레임워크에 도입된 기능입니다.
//@Controller와 @ResponseBody를 합쳐 놓은 어노테이션입니다.
//@Controller 어노테이션은 자바 객체를  http 응답 본문의 객체로 변환해 
//클라이언트에게 전송합니다.
@RestController
public class FirstController {
	
	@GetMapping(value="/")
	public String HelloWorld() {
		return "Hello World";
	}
	
	@GetMapping(value="test")
	public UserDto test() {
		UserDto userDto=new UserDto();
		userDto.setName("Spring");
		userDto.setAge(20);
		return userDto;
	}
	
	@GetMapping(value="testlombok")
	public UserDtoLombok testlombok() {
		UserDtoLombok userDtoLombok=new UserDtoLombok();
		userDtoLombok.setName("Spring");
		userDtoLombok.setAge(20);
		System.out.println(userDtoLombok);
		return userDtoLombok;
	}

}