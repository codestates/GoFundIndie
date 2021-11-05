import Link from "next/link";
import styles from "../../styles/components/layout/header.module.scss";
import Login from "../Login";
import Signup from "../Signup";
import { useEffect, useState } from "react";
import Setaxios from "../../fetching/Setaxios";
import { useRouter } from "next/router";
import axios from "axios";
import cookies from "js-cookie";

export default function Header() {
  const [loginModalOpen, setLoginModalOpen] = useState<boolean>(false);
  const [signupModalOpen, setSignupModalOpen] = useState<boolean>(false);
  const [userLoginStatus, setUserLoginStatus] = useState<boolean>(false);
  const router = useRouter();
  //헤더 상단 투명처리
  //TODO::/효율적인 방법 찾기
  useEffect(() => {
    if (!router.isReady) return;
    if (router.pathname !== "/board/view/[boardid]") {
      return;
    }
    const header = document.querySelector("#header__div");

    function CheckScroll() {
      if (window.scrollY === 0) {
        header?.classList.add(styles.transparent);
      }
      if (window.scrollY > 10) {
        header?.classList.remove(styles.transparent);
      }
    }
    window.addEventListener("scroll", CheckScroll);
  }, [router.pathname]);
  //쿠키 리프레쉬 토큰확인하여 엑세스토큰 받아오기
  useEffect(() => {
    if (cookies.get("refreshToken")) {
      Setaxios.getAxios("reissuance").then((res) => {
        const resData: any = res.data;
        axios.defaults.headers.common["accesstoken"] = resData.data.accessToken;
        setUserLoginStatus(true);
      });
    }
  }, []);
  const handleSignupModal = (): void => {
    setSignupModalOpen(!signupModalOpen);
  };
  const handleLoginModal = (): void => {
    setLoginModalOpen(!loginModalOpen);
  };
  const handleLoginStatus = (): void => {
    setUserLoginStatus(true);
  };

  async function Signout() {
    Setaxios.postAxios("signout")
      .then(() => {
        alert("로그아웃에 성공하였습니다");
        setUserLoginStatus(false);
        delete axios.defaults.headers.common["accesstoken"];
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
              <div className={styles["flex-start"]}>
                <li className={styles.header__ul__logo}>
                  <Link href="/">
                    <img
                      className={styles.logo}
                      src="/gofundindie_icon.png"
                      width="220"
                      height="40"
                    />
                  </Link>
                </li>
                <li>
                  <Link href="/">홈</Link>
                </li>
                <li>
                  <Link href="/board">영화</Link>
                </li>
              </div>
              {/* <li>
                <div className={styles["header-searchbar"]}>
                <input
                    type="text"
                    placeholder="컨텐츠, 인물, 장르를 검색해보세요"
                  />
                </div>
              </li> */}
              <div className={styles["flex-end"]}>
                {userLoginStatus ? (
                  <>
                    <li>
                      <Link href="/mypage">마이페이지</Link>
                    </li>
                    <li>
                      <button onClick={Signout}>로그아웃</button>
                    </li>
                  </>
                ) : (
                  <>
                    <li>
                      <button
                        onClick={() => {
                          window.scrollTo(0, 0);
                          setSignupModalOpen(!loginModalOpen);
                        }}
                      >
                        회원가입
                      </button>
                    </li>
                    <li>
                      <button
                        onClick={() => {
                          window.scrollTo(0, 0);
                          setLoginModalOpen(!loginModalOpen);
                        }}
                      >
                        로그인
                      </button>
                    </li>
                  </>
                )}
              </div>
            </ul>
          </div>
        </div>
      </header>
      {loginModalOpen ? (
        <Login
          handleLoginModal={handleLoginModal}
          handleLoginStatus={handleLoginStatus}
          handleSignupModal={handleSignupModal}
        />
      ) : null}
      {signupModalOpen ? (
        <Signup
          handleSignupModal={handleSignupModal}
          handleLoginModal={handleLoginModal}
        />
      ) : null}
    </>
  );
}
