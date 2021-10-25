import styles from "../styles/signup.module.scss";
import axios from "axios";
import { useState } from "react";
import Router from "next/router";

axios.defaults.withCredentials = true;

export default function Signup() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [profile_pic, setProfile_pic] = useState("");
  const [nickname, setNickname] = useState("");
  const transfortForm = async () => {
    try {
      await axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/signup`, {
        method: "POST",
        data: {
          email: email,
          password: password,
          nickname: nickname,
        },
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Headers": "Content-Type",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "POST",
          "Access-Control-Allow-Credentials": "true",
        },
        withCredentials: true,
      }).then(() => {
        alert("성공적으로 가입됐습니다");
        Router.push("/");
      });
    } catch (err) {
      console.log(err);
    }
  };
  return (
    <div className={styles.signup__wraper}>
      <div>
        <div className={styles.signup__title}>회원가입</div>
        <div className={styles["input-form"]}>
          <h3>
            <label htmlFor="email">이메일</label>
          </h3>
          <span className={styles.input_box}>
            <input
              type="text"
              id="email"
              onChange={(e) => {
                setEmail(e.target.value);
              }}
            ></input>
          </span>
          <h3>
            <label htmlFor="password">비밀번호</label>
          </h3>
          <span className={styles.input_box}>
            <input
              type="password"
              id="password"
              onChange={(e) => {
                setPassword(e.target.value);
              }}
            ></input>
          </span>
          <h3>
            <label htmlFor="password_check">비밀번호 재확인</label>
          </h3>
          <span className={styles.input_box}>
            <input type="password" id="password_check"></input>
          </span>
        </div>
        <div className={styles["input-form__social"]}>
          <h3>
            <label htmlFor="profile_pictrue">프로필 사진</label>
          </h3>
          <input
            type="file"
            onChange={(e) => {
              setProfile_pic(e.target.value);
            }}
          />
          <h3>
            <label htmlFor="nickname">닉네임</label>
          </h3>
          <span className={styles.input_box}>
            <input
              type="text"
              id="nickname"
              onChange={(e) => {
                setNickname(e.target.value);
              }}
            ></input>
          </span>
        </div>
      </div>
      <button className="signup-btn" onClick={transfortForm}>
        회원 가입
      </button>
    </div>
  );
}
