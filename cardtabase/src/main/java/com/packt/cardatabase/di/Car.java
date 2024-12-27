package com.packt.cardatabase.di;

import org.springframework.beans.factory.annotation.Autowired;

//의존성 주입 소개
//의존성 주입은 다른 객체에 의존하는 객체를 만들 수 있는 소프트 ㅜ에어 개발 기업이다
// 의존성 주입은 클래스의 독립적으로 유지하면서 동시에 클래스 간의 상호 작용을
// 도와 준다
public class Car {
// 스프링 부트에서 의존성 주입 이용
//	스프링 부트는 애플리케이션 클래스를 검색하고 특정한 어노테이션 (@Service,
//	@Repositoy,@Controller)이 지정된 클래스를 스프링 빈으로 등록하낟
//	이러한 빈은 @Autowired 어노테이션을 이용해 주입할 수있다
	@Autowired
	private Owner owner;

//	private Owner owner;
	
	//public Car () {
		//owner=new Owner();
	//}
	//private Owner owner=new Owner();
	public void setOwner(Owner owner) {
		this.owner=owner;
	}
}
