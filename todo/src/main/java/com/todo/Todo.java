package com.todo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Todo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Boolean isdone;
	private String content;
	private LocalDateTime createdDate;
	 // content를 설정하는 setContent 메소드
	public Todo() {
		  this.isdone = false; 
	}
    public void setContent(String content) {
        this.content = content;
    }

    // content를 반환하는 getContent 메소드
    public String getContent() {
        return content;
    }

    // done을 설정하는 setDone 메소드
    public void setDone(boolean done) {
        this.isdone = done;  // done 필드에 값 설정
    }

    // done을 반환하는 getDone 메소드
    public boolean isDone() {
        return isdone;
    }

    // createdDate를 설정하는 setCreatedDate 메소드
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    // createdDate를 반환하는 getCreatedDate 메소드
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
