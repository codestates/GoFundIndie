import Link from "next/link";
import styles from "../styles/components/header.module.scss";
import Image from "next/image";
import gofundIcon from "../images/gofundindie_icon.png";
import Login from "../components/Login";
import { useState } from "react";

export default function Header() {
  const [loginModalOpen, setLoginModalOpen] = useState<boolean>(false);
  const handleLoginModal = (): void => {
    console.log("불려짐");
    setLoginModalOpen(false);
  };
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
                <button onClick={() => setLoginModalOpen(!loginModalOpen)}>
                  로그인
                </button>
              </li>
            </ul>
          </div>
        </div>
      </header>
      {loginModalOpen ? <Login handleLoginModal={handleLoginModal} /> : null}
    </>
  );
}
