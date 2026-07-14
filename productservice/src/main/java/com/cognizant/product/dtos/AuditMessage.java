package com.cognizant.product.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditMessage {
	private LocalDateTime timestamp;
	private String userName;
	private String role;
	private String action;

}
