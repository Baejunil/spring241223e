package com.packt.cardatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;         // Logger 클래스 임포트
import org.slf4j.LoggerFactory;  // LoggerFactory 클래스 임포트
/*
 * 
 * @SpringBootApplication 어노테이션은 다음과 같은 여러 어노테이션의 조합이다
 * @EnableAutoConfiguration = 스프링 부트 자동 구성을 활성화 한다
 * @ComponentScan - 스프링 부트 컴포넌트 검색으로 애플리 케이션의 모든 컴포넌트를 찾는다
   @Configuration - 빈(Bean) 정의의 원본으로 쓸수 있는 클래스를 정의한다
   
   메인 애플리케이션 클래스는 다른 모든 클래스의 상위인 루트 패키지에 넣는 것이 좋다
   애플리케이션이 정상 작동하지 않는 흔한 이유 중 하나는 스프링 부트가 몇가지 필수 플래스를 찾을수 없기 때문이다
   
   로그와 문제 해결
   Ststem.out.println은 시스템 리소스를 많이 사용하는 문제가 있다
   로그는 애플리케이션의 흐름을 모니터링하고 프로그램 코드의 예기치 못한 오류를 
   포착하는데 도움이 된다 스프링 부트 스타터 패키지에는 별도의
   
   로깅에 쓸 수 있는 로그백이 있다
   로깅 수준에는 trace,debug,info,warn,error,fatal,off의 7가지가 있다
   로깅 수준은 스프링 부트 application.properties파일에 구성할 수 있다
   로깅 수준을 debug로 설정하면 debug 로깅 수준과 그이상에 해당하는 메시지를
   볼 수 있다.별도로 지정하지 않은 상태의 기본 로깅 수준은 info이
 * */
@SpringBootApplication
public class CardtabaseApplication {
    private static final Logger logger = LoggerFactory.getLogger(CardtabaseApplication.class); // LoggerFactory를 사용하여 로거 생성

    public static void main(String[] args) {
        SpringApplication.run(CardtabaseApplication.class, args);
        logger.info("Application started successfully"); // 애플리케이션 시작 로그 출력
    }
}