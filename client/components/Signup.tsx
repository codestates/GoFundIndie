import styles from "../styles/components/signup.module.scss";
import Image from "next/image";
import google from "../images/google.png";
import kakao from "../images/kakao.png";
import { ChangeEvent, useState } from "react";
import Setaxios from "../fetching/Setaxios";
import Router from "next/router";

export default function Signup({
  handleSignupModal,
}: {
  handleSignupModal(): void;
}) {
  const [userData, setUserData] = useState({
    email: "",
    password: "",
    nickname: "",
    profilepic: "",
  });

  const handleInputValue =
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setUserData({ ...userData, [key]: e.target.value });
    };

  function SignupSubmit() {
    try {
      Setaxios.postAxios("signup", userData).then(() => {
        alert("성공적으로 가입됐습니다");
        Router.push("/");
      });
    } catch (err) {
      console.log(err);
    }
  }
  return (
    <div>
      <div
        className={styles["screen-wrapper"]}
        onClick={(e) => {
          const screen = document.getElementById("screen");
          e.target === screen ? handleSignupModal() : null;
        }}
      >
        <div id="screen" className={styles["signupmodal-wrapper"]}>
          <div className={styles.signupmodal}>
            <div className={styles.signupmodal__form}>
              <div className={styles["signupmodal__form-upper"]}>
                <div className={styles["form-flexstart"]}>
                  소셜 계정으로 간편하게 회원가입
                </div>
                <div className={styles["form-flexend"]}>
                  <div>이미 회원이신가요?</div>
                  <div>로그인</div>
                </div>
              </div>
              <div className={styles["oauth-icon"]}>
                <Image src={kakao} width="180" height="55" />
                <Image src={google} width="180" height="55" />
              </div>
              <div>또는 이메일로 가입하기</div>
              <input
                placeholder="E-mail"
                className={styles.input__text}
                onChange={handleInputValue("email")}
                type="text"
              />
              <input
                placeholder="비밀번호"
                className={styles.input__text}
                onChange={handleInputValue("password")}
                type="password"
              />
              <input
                placeholder="비밀번호 재확인"
                className={styles.input__text}
                type="password"
              />
              <input
                placeholder="닉네임"
                className={styles.input__text}
                onChange={handleInputValue("nickname")}
                type="text"
              />
              <div>
                <input
                  type="checkbox"
                  id="companyrule"
                  className={styles.input__checkbox}
                />
                <label htmlFor="companyrule">개인정보수집 및 이용동의</label>
                <span>(필수)</span>
              </div>
              <div>
                <input
                  type="checkbox"
                  id="subscribing"
                  className={styles.input__checkbox}
                />
                <label htmlFor="subscribing">정기 알림 메일 수신</label>
                <span>(선택)</span>
              </div>
              <button onClick={SignupSubmit}>회원가입</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
