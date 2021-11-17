import { ChangeEvent, useState } from "react";
import styles from "../styles/components/login.module.scss";
import Setaxios from "../fetching/Setaxios";
import Cookies from "js-cookie";
import axios from "axios";

export default function Login({
  handleLoginModal,
  handleLoginStatus,
  handleSignupModal,
}: {
  handleLoginModal(): void;
  handleLoginStatus(): void;
  handleSignupModal(): void;
}) {
  const [userData, setUserData] = useState({
    email: "",
    password: "",
  });

  const handleInputValue =
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setUserData({ ...userData, [key]: e.target.value });
    };

  async function LoginSubmit() {
    const endpoint = "signin";
    const data = {
      email: userData.email,
      password: userData.password,
    };

    //TODO:: 비밀번호가 틀린건지 아이디가 틀린건지 등등정보 나오게
    Setaxios.postAxios(endpoint, data)
      .then((res) => {
        const resData: any = res.data;
        Cookies.set("refreshToken", resData.data.refreshToken, {
          sameSite: "lax",
        });
        Cookies.set("accesstoken", resData.data.accessToken, {
          sameSite: "lax",
        });
        axios.defaults.headers.common["refreshtoken"] =
          resData.data.refreshToken;
        axios.defaults.headers.common["accesstoken"] = resData.data.accessToken;
        alert("로그인에 성공하였습니다");
        //    location.reload();
        handleLoginModal();
        handleLoginStatus();
      })
      .catch((err) => {
        if (err.response.data.code === 4001) alert("비밀번호가 틀렸습니다");
        if (err.response.data.code === 4003)
          alert("존재하지 않는 이메일입니다");
      });
  }

  return (
    <div>
      <div
        className={styles["screen-wrapper"]}
        onClick={(e) => {
          const screen = document.getElementById("screen");
          e.target === screen ? handleLoginModal() : null;
        }}
      >
        <div id="screen" className={styles["loginmodal-wrapper"]}>
          <div className={styles.loginmodal}>
            <div className={styles.loginmodal__form}>
              <div className={styles["loginmodal__form-upper"]}>
                <div className={styles["form-flexstart"]}>로그인</div>
                <div className={styles["form-flexend"]}>
                  <div>아직 회원이 아니신가요?</div>
                  <div
                    onClick={() => {
                      handleSignupModal();
                      handleLoginModal();
                    }}
                  >
                    회원가입
                  </div>
                </div>
              </div>
              <input
                onChange={handleInputValue("email")}
                placeholder="E-mail"
                className={styles.input__text}
                type="text"
              />
              <input
                type="password"
                placeholder="Password"
                className={styles.input__text}
                onChange={handleInputValue("password")}
              />
              <input
                type="checkbox"
                id="keeplogin"
                className={styles.input__checkbox}
              />
              <label htmlFor="keeplogin">자동 로그인</label>
              {/*TODO : 커스텀 체크박스버튼 만들기*/}
              <button onClick={LoginSubmit}>로그인</button>
              <div>
                <div>비밀번호 찾기</div>
              </div>
              <div>또는 소셜 로그인</div>
              <div className={styles["oauth-icon"]}>
                <img src="/kakao.png" width="180" height="55" />
                <img src="/google.png" width="180" height="55" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
