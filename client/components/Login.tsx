import { ChangeEvent, useState } from "react";
import styles from "../styles/components/login.module.scss";
import axios from "axios";

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
        const { accessToken } = res.headers;
        localStorage.setItem("accessToken", accessToken);
        console.log(accessToken);

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
      <div className={styles.loginmodal__wrapper}>
        <div className={styles.loginmodal}>
          <div>이메일</div>
          <input onChange={handleInputValue("email")} type="text"></input>
          <div>비밀번호</div>
          <input
            type="password"
            onChange={handleInputValue("password")}
          ></input>
          <button onClick={LoginSubmit}>로그인</button>
          <button onClick={handleLoginModal}>창닫기</button>
        </div>
      </div>
    </div>
  );
}
