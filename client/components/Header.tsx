import Link from "next/link";
import styles from "../styles/components/header.module.scss";
import Image from "next/image";
import gofundIcon from "../images/gofundindie_icon.png";

export default function Header() {
  return (
    <>
      <header>
        <div className={styles.header__div}>
          <Image src={gofundIcon} width="180" height="70" />
          <div className={styles.header__div__link}>
            <Link href="/">
              <a title="홈">홈</a>
            </Link>
          </div>
          <div className={styles.header__div__link}>
            <Link href="/signup">
              <a title="회원 가입">회원가입</a>
            </Link>
          </div>
        </div>
      </header>
    </>
  );
}
