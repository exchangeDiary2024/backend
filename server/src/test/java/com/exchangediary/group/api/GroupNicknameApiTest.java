package com.exchangediary.group.api;

import com.exchangediary.BaseTest;
import com.exchangediary.group.domain.GroupRepository;
import com.exchangediary.group.domain.entity.Group;
import com.exchangediary.group.service.GroupCommandService;
import com.exchangediary.member.domain.MemberRepository;
import com.exchangediary.member.domain.entity.Member;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.core.IsEqual.equalTo;

class GroupNicknameApiTest extends BaseTest {
    private static final String GROUP_NAME = "버니즈";
    private static final String API_PATH = "/api/groups/%d/nickname/verify";
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupCommandService groupCommandService;

    @Test
    void 닉네임_유효성_검사_성공() {
        Group group = createGroup();
        groupRepository.save(group);

        RestAssured
                .given().log().all()
                .queryParam("nickname", "jisunggi")
                .header("Authorization", "Bearer " + token)
                .when().get(String.format(API_PATH, group.getId()))
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("verification", equalTo(true));
    }

    @Test
    void 닉네임_유효성_검사_중복() {
        Group group = createGroup();
        groupRepository.save(group);
        Member member = Member.builder()
                .nickname("jisunggi")
                .kakaoId(12345L)
                .group(group)
                .build();
        memberRepository.save(member);

        RestAssured
                .given().log().all()
                .queryParam("nickname", "jisunggi")
                .header("Authorization", "Bearer " + token)
                .when().get(String.format(API_PATH, group.getId()))
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    private Group createGroup() {
        return Group.builder()
                .name(GROUP_NAME)
                .currentOrder(0)
                .code("code")
                .build();
    }
}