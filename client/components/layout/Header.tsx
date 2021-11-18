import Link from "next/link";
import styles from "../../styles/components/layout/header.module.scss";
import Login from "../Login";
import Signup from "../Signup";
import { useEffect, useState } from "react";
import Setaxios from "../../fetching/Setaxios";
import { useRouter } from "next/router";
import axios from "axios";
import Cookies from "js-cookie";
export default function Header() {
  const [loginModalOpen, setLoginModalOpen] = useState<boolean>(false);
  const [signupModalOpen, setSignupModalOpen] = useState<boolean>(false);
  const [userLoginStatus, setUserLoginStatus] = useState<boolean>(false);
  const [userAdminStatus, setUserAdminStatus] = useState<boolean>(false);
  const [searchedData, setSearchedData] = useState([]);
  const [searchedOnfocus, setSearchedOnfocus] = useState<boolean>(false);
  const router = useRouter();
  //헤더 상단 투명처리
  //TODO::/효율적인 방법 찾기
  useEffect(() => {
    if (!router.isReady) return;
    if (router.pathname !== "/board/view/[boardid]") {
      return;
    }
    const header = document.querySelector("#header__div");
    header?.classList.add(styles.transparent);
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
    if (Cookies.get("refreshToken")) {
      Setaxios.getAxios("reissuance")
        .then((res) => {
          const resData: any = res.data;
          Cookies.set("accesstoken", resData.data.accessToken, {
            sameSite: "lax",
          });
          axios.defaults.headers.common["accesstoken"] =
            resData.data.accessToken;
          setUserLoginStatus(true);
          Setaxios.getAxios("user")
            .then((res) => {
              let userdata: any = res.data;
              if (userdata.data.admin_role) {
                setUserAdminStatus(true);
              }
            })
            .catch((err) => console.log(err.response));
        })
        .catch((err) => console.log(err.response));
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
        Cookies.set("accesstoken", "");
        Cookies.remove("accesstoken");
        router.push("/");
        location.reload();
        delete axios.defaults.headers.common["accesstoken"];
      })
      .catch((err) => {
        alert(err);
      });
  }
  const searchData = (e: any) => {
    const query = `query SearchBoardName ($what: String){
      SearchBoardName(what: $what){
        code
        data{
          id
          posterImg
          title
        }
      }
    }`;
    if (e.target.value === "") {
      setSearchedData([]);
      return;
    }
    Setaxios.postSearchBoardGraphql(query, e.target.value)
      .then((res) => {
        const data: any = res.data;
        console.log(res);
        setSearchedData(data.data.SearchBoardName.data);
      })
      .catch((err) => alert(err));
  };

  const displaySearchedData = () => {
    return searchedData.map((movie: any) => {
      return (
        <div
          onClick={(e) => {
            setSearchedData([]);
            setSearchedOnfocus(false);
            let text: any = document.getElementById("inputText");
            text.value = "";
          }}
          className={styles.link}
          key={movie.title}
        >
          {movie.posterImg ? (
            <img
              width="25px"
              onError={(e) => {
                const img: any = e.target;
                img.src = "/Poster.jpg";
              }}
              src={movie.posterImg}
            />
          ) : null}
          <Link href="/board/view/[boardid]" as={`/board/view/${movie.id}`}>
            {movie.title}
          </Link>
        </div>
      );
    });
  };
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
                <li className={styles.home}>
                  <Link href="/">홈</Link>
                </li>
                <li>
                  <Link href="/board">영화</Link>
                </li>
              </div>
              <li>
                <div className={styles["header-searchbar"]}>
                  <input
                    type="text"
                    id="inputText"
                    placeholder="컨텐츠를 검색해보세요"
                    onBlur={(e) => {
                      if (e.target.value === "") {
                        setSearchedOnfocus(false);
                      }
                    }}
                    onFocus={() => setSearchedOnfocus(true)}
                    onChange={searchData}
                  />
                </div>
                {searchedOnfocus ? (
                  <div className={styles.searchlist}>
                    {displaySearchedData()}
                  </div>
                ) : null}
              </li>
              <div className={styles["flex-end"]}>
                {userAdminStatus ? (
                  <li>
                    <Link href="/management">관리 페이지</Link>
                  </li>
                ) : null}
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
                          setSignupModalOpen(!loginModalOpen);
                        }}
                      >
                        회원가입
                      </button>
                    </li>
                    <li>
                      <button
                        onClick={() => {
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
