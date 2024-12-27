package com.packt.order;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Order_item {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;

@ManyToOne(fetch =FetchType.LAZY )
@JoinColumn(name="user_id")
private User user;

public Order_item() {

}

public Order_item(User user) {
this.user = user;
}


}