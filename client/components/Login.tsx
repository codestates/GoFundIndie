import { ChangeEvent, useState } from "react";
import styles from "../styles/components/login.module.scss";
import axios from "axios";
import cookies from "js-cookie";
import Image from "next/image";
import google from "../images/google.png";
import kakao from "../images/kakao.png";

axios.defaults.withCredentials = true;

export default function Login({
  handleLoginModal,
  handleLoginStatus,
}: {
  handleLoginModal(): void;
  handleLoginStatus(): void;
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
    try {
      await axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/signin`, {
        method: "POST",
        data: {
          email: userData.email,
          password: userData.password,
        },
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Headers": "Content-Type",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "POST",
          "Access-Control-Allow-Credentials": "true",
        },
        withCredentials: true,
      }).then((res) => {
        //localStorage.setItem("accessToken", accessToken);
        cookies.get("accessToken");
        cookies.set("test", "test");
        alert("로그인에 성공하였습니다");
        handleLoginModal();
        handleLoginStatus();
      });
    } catch (err) {
      console.log(err);
    }
  }

  return (
    <div>
      <div
        className={styles.screen__wrapper}
        onClick={(e) => {
          const screen = document.getElementById("screen");
          e.target === screen ? handleLoginModal() : null;
        }}
      >
        <div id="screen" className={styles.loginmodal__wrapper}>
          <div className={styles.loginmodal}>
            <div className={styles.loginmodal__form}>
              <div className={styles.loginmodal__form__upper}>
                <div className={styles.form__flexstart}>로그인</div>
                <div className={styles.form__flexend}>
                  <div>아직 회원이 아니신가요?</div>
                  <div>회원가입</div>
                </div>
              </div>
              <input
                onChange={handleInputValue("email")}
                placeholder="E-mail"
                className={styles.input__text}
                type="text"
              ></input>
              <input
                type="password"
                placeholder="Password"
                className={styles.input__text}
                onChange={handleInputValue("password")}
              ></input>
              <input
                type="checkbox"
                id="keeplogin"
                className={styles.input__checkbox}
              />
              <label htmlFor="keeplogin">자동 로그인</label>
              {/*TODO : 커스텀 버튼 만들기*/}
              <button onClick={LoginSubmit}>로그인</button>
              <div>
                <div>비밀번호 찾기</div>
              </div>
              <div>또는 소셜 로그인</div>
              <div className={styles.oauth__icon}>
                <Image src={kakao} width="180" height="55" />
                <Image src={google} width="180" height="55" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
