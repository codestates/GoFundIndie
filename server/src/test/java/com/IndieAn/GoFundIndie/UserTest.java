package com.IndieAn.GoFundIndie;

import com.IndieAn.GoFundIndie.Domain.DTO.UserModifyDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.UserSignUpDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.TestDomain.UserTest.OnlyCodeTestDTO;
import com.IndieAn.GoFundIndie.TestDomain.UserTest.SigninTestDTO;
import com.IndieAn.GoFundIndie.TestDomain.UserTest.UserInfoTestDTO;
import com.IndieAn.GoFundIndie.TestDomain.UserTest.WrappingUserInfoTestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import java.util.List;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("#{info['gofundindie.signkey']}")
    private String SIGN_KEY;

    /**
     * test var
     */
    private final String TEST_EMAIL = "test@test.com";
    private final String TEST_PW = "1q2w3e4r";
    private final String TEST_NICK = "HelloTest";
    private final String TEST_PIC = null;

    private String TEST_TOKEN;
    private Cookie TEST_COOKIE;

    @BeforeEach
    void beforeEach() {
        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                    .addFilters(new CharacterEncodingFilter("UTF-8", true))
                    .build();

            objectMapper = Jackson2ObjectMapperBuilder.json()
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .modules(new JavaTimeModule())
                    .build();
        } catch (Exception e) {
            System.out.println("---- Before Each Method Throws ----");
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    @DisplayName("UserTest - Sign Up")
    void Check_Sign_Up() throws Exception {
        UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
        userSignUpDTO.setEmail(TEST_EMAIL);
        userSignUpDTO.setPassword(TEST_PW);
        userSignUpDTO.setNickname(TEST_NICK);
        userSignUpDTO.setProfilePic(TEST_PIC);

        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.post("/signup")
                        .content(objectMapper.writeValueAsString(userSignUpDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Assertions.assertEquals(2000, objectMapper.readValue(response.getContentAsString(), OnlyCodeTestDTO.class).getCode());
    }

    @Test
    @Order(2)
    @DisplayName("UserTest - Duplicate Check")
    void Check_Find_Email() throws Exception {
        // mail check
        MockHttpServletResponse response = mockMvc.perform(
                MockMvcRequestBuilders.get("/check?type=email&query=" + TEST_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        OnlyCodeTestDTO dto = objectMapper.readValue(response.getContentAsString(), OnlyCodeTestDTO.class);
        Assertions.assertEquals(4002, dto.getCode());

        // nickname check
        response = mockMvc.perform(
                        MockMvcRequestBuilders.get("/check?type=nickname&query=" + TEST_NICK)
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        dto = objectMapper.readValue(response.getContentAsString(), OnlyCodeTestDTO.class);
        Assertions.assertEquals(4013, dto.getCode());
    }

    @Test
    @Order(3)
    @DisplayName("UserTest - Log in")
    void Check_Log_In() throws Exception {
        String body = "{\"email\":\"" + TEST_EMAIL + "\",\"password\":\"" + TEST_PW + "\"}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        SigninTestDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SigninTestDTO.class);
        try {
            TEST_TOKEN = result.getData().getAccessToken();
            Assertions.assertNotNull(TEST_TOKEN);

            TEST_COOKIE = mvcResult.getResponse().getCookie("refreshToken");
            String respEmail = String.valueOf(
                    Jwts.parser().setSigningKey(SIGN_KEY)
                            .parseClaimsJws(TEST_COOKIE.getValue())
                            .getBody().get("email"));

            Assertions.assertNotNull(TEST_COOKIE);
            Assertions.assertEquals(respEmail, TEST_EMAIL);
        } catch (NullPointerException e) {
            System.out.println("---- Token Throws NullPointerException ----");
        }
    }

    @Test
    @Order(4)
    @DisplayName("UserTest - Get Info")
    void Check_Get_Info() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                        .header("accesstoken", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        UserInfoTestDTO dto = objectMapper.readValue(result, WrappingUserInfoTestDTO.class).getData();

        Assertions.assertEquals(dto.getEmail(), TEST_EMAIL);
        Assertions.assertEquals(dto.getNickname(), TEST_NICK);
    }

    @Test
    @Order(5)
    @DisplayName("UserTest - Put Info")
    void Check_Put_Info() throws Exception {
        UserModifyDTO input = UserModifyDTO.builder()
                .nickname("IndieAn").adAgree(false).build();

        String result = mockMvc.perform(MockMvcRequestBuilders.put("/user")
                        .header("accesstoken", TEST_TOKEN)
                        .content(objectMapper.writeValueAsString(input))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        int code = objectMapper.readValue(result, WrappingUserInfoTestDTO.class).getCode();

        User user = entityManager
                .createQuery("SELECT u FROM User AS u WHERE u.email='" + TEST_EMAIL + "'", User.class)
                .getSingleResult();

        Assertions.assertEquals(2000, code);
        Assertions.assertNotEquals(user.getNickname(), TEST_NICK);
        Assertions.assertFalse(user.isAdAgree());
    }

    @Test
    @Order(6)
    @DisplayName("UserTest - Access Token Reissuance")
    void Check_Reissuance() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/reissuance")
                        .cookie(TEST_COOKIE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        SigninTestDTO dto = objectMapper.readValue(result, SigninTestDTO.class);

        Assertions.assertEquals(2000, dto.getCode());
        Assertions.assertNotEquals(TEST_TOKEN, dto.getData().getAccessToken());

        TEST_TOKEN = dto.getData().getAccessToken();
    }

    @Test
    @Order(7)
    @DisplayName("UserTest - Sign Out")
    void Check_Sign_Out() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/signout")
                        .cookie(TEST_COOKIE)
                        .header("accesstoken", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        int code = objectMapper.readValue(result, OnlyCodeTestDTO.class).getCode();

        Assertions.assertEquals(2000, code);

        String failure = mockMvc.perform(MockMvcRequestBuilders.get("/reissuance")
                        .cookie(TEST_COOKIE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        int failureCode = objectMapper.readValue(failure, SigninTestDTO.class).getCode();

        Assertions.assertEquals(4407, failureCode);
    }

    @Test
    @Order(8)
    @DisplayName("UserTest - Delete User")
    void Check_Delete_User() throws Exception {
        String body = "{\"email\":\"" + TEST_EMAIL + "\",\"password\":\"" + TEST_PW + "\"}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        SigninTestDTO resp = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SigninTestDTO.class);
        try {
            TEST_TOKEN = resp.getData().getAccessToken();
            TEST_COOKIE = mvcResult.getResponse().getCookie("refreshToken");
        } catch (NullPointerException e) {
            System.out.println("---- Token Throws NullPointerException ----");
        }

        String result = mockMvc.perform(MockMvcRequestBuilders.delete("/user")
                        .cookie(TEST_COOKIE)
                        .header("accesstoken", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        int code = objectMapper.readValue(result, OnlyCodeTestDTO.class).getCode();

        Assertions.assertEquals(2000, code);

        List<User> user = entityManager
                .createQuery("SELECT u FROM User AS u WHERE u.email='" + TEST_EMAIL + "'", User.class)
                .getResultList();

        Assertions.assertEquals(0, user.size());
    }
}
