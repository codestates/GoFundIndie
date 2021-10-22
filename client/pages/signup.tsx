import styles from "../styles/signup.module.scss";

export default function Signup() {
  const transfortForm = () => {};
  return (
    <div className={styles.signup__wraper}>
      <div>
        <div className={styles.signup__title}>회원가입</div>
        <div className={styles["input-form"]}>
          <h3>
            <label htmlFor="email">이메일</label>
          </h3>
          <span className={styles.input_box}>
            <input type="text" id="email"></input>
          </span>
          <h3>
            <label htmlFor="password">비밀번호</label>
          </h3>
          <span className={styles.input_box}>
            <input type="password" id="password"></input>
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
          <input type="file" />
          <h3>
            <label htmlFor="nickname">닉네임</label>
          </h3>
          <span className={styles.input_box}>
            <input type="text" id="nickname"></input>
          </span>
        </div>
      </div>
      <button className="signup-btn" onClick={transfortForm}>
        회원 가입
      </button>
    </div>
  );
}
