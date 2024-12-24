package com.shop.entity;

import java.time.LocalDateTime;

import com.shop.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="item")  // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // IDENTITY로 설정하면 MySQL의 AUTO_INCREMENT 사용
	private Long id;
	
	@Column(nullable = false, length = 50)  // item_nm 컬럼, 길이 50
	private String itemNm;
	
	@Column(nullable = false)  // 가격은 필수, int 타입
	private int price;
	
	@Column(nullable = false)  // 재고는 필수, int 타입
	private int stockNumber;
	
	@Lob  // 텍스트 대용량 데이터 저장
	@Column(nullable = false)  // item_detail은 필수, 대용량 텍스트 저장
	private String itemDetail;
	
	@Enumerated(EnumType.STRING)  // enum 타입은 String으로 저장
	@Column(name = "item_sell_status", nullable = false)  // sell_status는 필수, enum 값 저장
	private ItemSellStatus itemSellStatus;
	
	@Column(name = "reg_time", nullable = false)  // 등록 시간
	private LocalDateTime regTime;
	
	@Column(name = "update_time", nullable = false)  // 업데이트 시간
	private LocalDateTime updateTime;
	
	@PrePersist
    public void prePersist() {
        this.regTime = LocalDateTime.now();  // 엔티티 저장 전에 등록 시간 설정
        this.updateTime = LocalDateTime.now();  // 엔티티 저장 전에 수정 시간 설정
    }

    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();  // 엔티티 수정 전에 수정 시간 업데이트
    }	
}
