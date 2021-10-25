import Link from "next/link";
import styles from "../styles/components/header.module.scss";
import Image from "next/image";
import gofundIcon from "../images/gofundindie_icon.png";
import Login from "../components/Login";
import { useState } from "react";
import axios from "axios";

export default function Header() {
  const [loginModalOpen, setLoginModalOpen] = useState<boolean>(false);
  const [userLoginStatus, setUserLoginStatus] = useState<boolean>(false);
  const handleLoginModal = (): void => {
    setLoginModalOpen(false);
  };
  const handleLoginStatus = (): void => {
    setUserLoginStatus(true);
  };

  async function Signout() {
    try {
      await axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/signout`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Headers": "Content-Type",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "POST",
          "Access-Control-Allow-Credentials": "true",
        },
        withCredentials: true,
      }).then((res) => {
        alert("로그아웃에 성공하였습니다");
        setUserLoginStatus(false);
      });
    } catch (err) {
      console.log(err);
    }
  }
  return (
    <>
      <header>
        <div className={styles.header__div__wrapper}>
          <div className={styles.header__div}>
            <ul className={styles.header__ul}>
              <li>
                <Link href="/">
                  <Image src={gofundIcon} width="180" height="70" />
                </Link>
              </li>
              <li>
                <li className={styles.header__div__link}>
                  <Link href="/">
                    <a title="홈">홈</a>
                  </Link>
                </li>
              </li>
              <li className={styles.header__div__link}>
                <Link href="/board">
                  <a title="영화">영화</a>
                </Link>
              </li>
              <li className={styles.header__div__link}>
                <input type="text" required />
              </li>
              <li className={styles.header__div__link}>
                <Link href="/signup">
                  <a title="회원 가입">회원가입</a>
                </Link>
              </li>
              <li className={styles.header__div__link}>
                {userLoginStatus ? (
                  <button onClick={Signout}>로그아웃</button>
                ) : (
                  <button onClick={() => setLoginModalOpen(!loginModalOpen)}>
                    로그인
                  </button>
                )}
              </li>
            </ul>
          </div>
        </div>
      </header>
      {loginModalOpen ? (
        <Login
          handleLoginModal={handleLoginModal}
          handleLoginStatus={handleLoginStatus}
        />
      ) : null}
    </>
  );
}
