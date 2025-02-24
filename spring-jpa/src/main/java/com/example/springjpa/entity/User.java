package com.example.springjpa.entity;

import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Embedded
	protected UserInfo userInfo;
	
	@Embedded
	protected Address address;

	@CreatedDate
	private LocalDateTime createdAt;

	/**
	 * 사용자의 정보를 업데이트한다,
	 * nick은 아이디로 업데이트하지 않는다
	 * @param info 사용자 정보
	 */
	protected void updateUserInfo(UserInfo info) {
		Objects.requireNonNull(this.userInfo, "address can`t be null");
		if (Objects.isNull(info)) {
			return;
		}
		String changeName = StringUtils.hasText(info.getName()) ? info.getName() : this.userInfo.getName();
		String changePwd = StringUtils.hasText(info.getPwd()) ? info.getPwd() : this.userInfo.getPwd();
		this.userInfo = this.userInfo.toBuilder()
				.name(changeName)
				.pwd(changePwd)
				.build();
	}

	/**
	 * 사용자의 주소를 업데이트한다.
	 * @param address 사용자 주소
	 */
	protected void updateAddress(Address address) {
		Objects.requireNonNull(this.address, "address can`t be null");
		if (Objects.isNull(address)) {
			return;
		}
		String city = StringUtils.hasText(address.getCity()) ? address.getCity() : this.address.getCity();
		String street = StringUtils.hasText(address.getStreet()) ? address.getStreet() : this.address.getStreet();
		this.address = this.address.toBuilder()
				.city(city)
				.street(street)
				.build();
	}

}
