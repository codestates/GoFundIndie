import Link from "next/link";
import styles from "../styles/components/header.module.scss";
import Login from "./Login";
import Signup from "./Signup";
import { useEffect, useState } from "react";
import Setaxios from "../fetching/Setaxios";
import Router, { useRouter } from "next/router";

export default function Header() {
  const [loginModalOpen, setLoginModalOpen] = useState<boolean>(false);
  const [signupModalOpen, setSignupModalOpen] = useState<boolean>(false);
  const [userLoginStatus, setUserLoginStatus] = useState<boolean>(false);
  const router = useRouter();
  let pathname = "";
  useEffect(() => {
    if (!router.isReady) return;
    pathname = router.pathname;
    const header = document.querySelector("#header__div");
    if (header !== null) {
      header.classList.add(styles.transparent);
    }
    function CheckScroll() {
      if (window.scrollY > 10) {
        header?.classList.remove(styles.transparent);
      }
    }
    window.addEventListener("scroll", CheckScroll);
  }, [router.pathname]);
  const handleSignupModal = (): void => {
    setSignupModalOpen(false);
  };
  const handleLoginModal = (): void => {
    setLoginModalOpen(false);
  };
  const handleLoginStatus = (): void => {
    setUserLoginStatus(true);
  };

  async function Signout() {
    Setaxios.postAxios("signout")
      .then(() => {
        alert("로그아웃에 성공하였습니다");
        setUserLoginStatus(false);
      })
      .catch((err) => {
        alert(err);
      });
  }
  return (
    <>
      <header>
        <div id="header__div" className={styles.header__div__wrapper}>
          <div className={styles.header__div}>
            <ul className={styles.header__ul}>
              <li className={styles.header__ul__logo}>
                <Link href="/">
                  <img src="/gofundindie_icon.png" width="220" height="40" />
                </Link>
              </li>
              <li>
                <Link href="/">
                  <div title="홈">홈</div>
                </Link>
              </li>
              <li>
                <Link href="/board">
                  <div title="영화">영화</div>
                </Link>
              </li>
              <li>
                <div className={styles["header-searchbar"]}>
                  <input
                    type="text"
                    placeholder="컨텐츠, 인물, 장르를 검색해보세요"
                  />
                </div>
              </li>
              <li>
                {userLoginStatus ? null : (
                  <div onClick={() => setSignupModalOpen(!loginModalOpen)}>
                    회원가입
                  </div>
                )}
              </li>
              <li>
                {userLoginStatus ? (
                  <button onClick={Signout}>로그아웃</button>
                ) : (
                  <div onClick={() => setLoginModalOpen(!loginModalOpen)}>
                    로그인
                  </div>
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
      {signupModalOpen ? (
        <Signup handleSignupModal={handleSignupModal} />
      ) : null}
    </>
  );
}

export async function getStaticProps() {
  return Router.pathname;
}
