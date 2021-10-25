import { useState } from "react";
import styles from "../styles/components/login.module.scss";
export default function Login({
  handleLoginModal,
}: {
  handleLoginModal(): void;
}) {
  const [userData, setUserData] = useState({
    email: "",
    password: "",
  });

  async function loginSubmit() {}
  return (
    <div>
      <div className={styles.loginmodal__wrapper}>
        <div className={styles.loginmodal}>
          <div>이메일</div>
          <input type="text"></input>
          <div>비밀번호</div>
          <input type="password"></input>
          <button onClick={() => loginSubmit}>로그인</button>
          <button onClick={handleLoginModal}>창닫기</button>
        </div>
      </div>
    </div>
  );
}
