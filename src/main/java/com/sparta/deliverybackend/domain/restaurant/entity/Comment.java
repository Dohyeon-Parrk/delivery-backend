package com.sparta.deliverybackend.domain.restaurant.entity;

import java.time.LocalDateTime;

import com.sparta.deliverybackend.domain.BaseTimeStampEntity;
import com.sparta.deliverybackend.domain.member.entity.Member;
import com.sparta.deliverybackend.domain.order.entity.Order;
import com.sparta.deliverybackend.exception.customException.NotHaveAuthorityException;
import com.sparta.deliverybackend.exception.enums.ExceptionCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comment")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseTimeStampEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String contents;

	@Column
	private String managerReply;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "orders_id")
	private Order order;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@Column
	private LocalDateTime deletedAt;

	@Column
	private LocalDateTime repliedAt;

	public void updateManagerReply(String reply) {
		this.managerReply = reply;
		this.repliedAt = LocalDateTime.now();
	}

	public void validateMember(Member member) {
		if (!this.member.isSameMember(member)) {
			throw new NotHaveAuthorityException(ExceptionCode.NOT_HAVE_AUTHORITY_MEMBER);
		}
	}

	public void delete() {
		this.deletedAt = LocalDateTime.now();
	}
}
