package com.example.springjpa.owner;

import com.example.springjpa.TestExecutionListener;
import com.example.springjpa.domain.Category;
import com.example.springjpa.dto.*;
import com.example.springjpa.entity.RestaurantsMenu;
import com.example.springjpa.entity.vo.Address;
import com.example.springjpa.entity.vo.UserInfo;
import com.example.springjpa.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("식당관리자")
@ExtendWith(TestExecutionListener.class)
public class OwnerTests {

	@Autowired
	private OwnerService ownerService;

	@BeforeEach
	@Tag("테스트 준비")
	void setUp() {
		System.out.println("\nsetUp - start");
		List<RestaurantsMenu> menus = new ArrayList<>();
		menus.add(new RestaurantsMenu("메뉴1", 1));
		menus.add(new RestaurantsMenu("메뉴2", 2));
		menus.add(new RestaurantsMenu("메뉴3", 3));
		menus.add(new RestaurantsMenu("메뉴4", 4));
		menus.add(new RestaurantsMenu("메뉴5", 5));
		OwnerRegisterReqDTO setUp = new OwnerRegisterReqDTO(
				new UserInfo("관리자", "관리자", "12345!"),
				new RestaurantsRegisterReqDTO(
						menus,
						new Address("멋진 도시", "멋진 거리"),
						Category.KR
				)
		);
		try {
			ownerService.register(setUp);
		} catch (IllegalArgumentException i) {
			System.out.println("setUp - already");
		}
		System.out.println("setUp - end");
	}

	@Test
	@Tag("로그인")
	void login() {
		OwnerLoginReqDTO loginReqDTO = new OwnerLoginReqDTO(
				"관리자",
				"12345!"
		);
		assertNotNull(ownerService.login(loginReqDTO));

		OwnerLoginReqDTO loginReqDTO2 = new OwnerLoginReqDTO(
				"관리자1213",
				"12345!DSFSD"
		);
		assertThrows(IllegalArgumentException.class, () -> ownerService.login(loginReqDTO2));
	}

	@Test
	@Tag("가입")
	void register() {
		OwnerRegisterReqDTO reqDTO = new OwnerRegisterReqDTO(
				new UserInfo("관리자1", "나는관리자", "123dskjlfhasd2!"),
				new RestaurantsRegisterReqDTO(
					null,
					new Address("멋진 도시", "멋진 거리"),
					Category.KR
				)
		);
		ownerService.register(reqDTO);

		List<RestaurantsMenu> menus = new ArrayList<>();
		menus.add(new RestaurantsMenu("메뉴1", 123515));
		menus.add(new RestaurantsMenu("메뉴2", 745674));
		menus.add(new RestaurantsMenu("메뉴3", 15156));
		menus.add(new RestaurantsMenu("메뉴4", 4536346));
		menus.add(new RestaurantsMenu("메뉴5", 213));
		OwnerRegisterReqDTO reqDTO2 = new OwnerRegisterReqDTO(
				new UserInfo("관리자2", "나는관리자", "123dskjlfhasd2!"),
				new RestaurantsRegisterReqDTO(
						menus,
						new Address("멋진 도시", "멋진 거리"),
						Category.KR
				)
		);
		ownerService.register(reqDTO2);
	}

	@Test
	@Tag("수정")
	void update() {
		OwnerUpdateReqDTO updateReqDTO1 = new OwnerUpdateReqDTO(
				1,
				"괸리자입니다.",
				"123456"
		);
		ownerService.update(updateReqDTO1);

		OwnerUpdateReqDTO updateReqDTO2 = new OwnerUpdateReqDTO(
				1,
				"괸리자입니다.",
				null
		);
		ownerService.update(updateReqDTO2);

		OwnerUpdateReqDTO updateReqDTO3 = new OwnerUpdateReqDTO(
				99,
				"괸리자입니다.",
				"1111"
		);
		assertThrows(IllegalArgumentException.class, () -> ownerService.update(updateReqDTO3));
	}

	@Test
	@Tag("삭제")
	void delete() {
		int OwnerId = 1;
		assertTrue(ownerService.delete(OwnerId));

		int failOwnerId = 999;
		assertFalse(ownerService.delete(failOwnerId));
	}

}
