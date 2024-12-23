package com.shop.entity;

import jakarta.persistence.GenerationType;

public @interface GenertedValue {

	GenerationType strategy();

}
