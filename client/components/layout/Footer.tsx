import styles from "../../styles/components/layout/footer.module.scss";
import Link from "next/link";
export default function Footer() {
  return (
    <footer className={styles.footer}>
      <img className={styles.logo} src="/gofundindie_logo_v2.png" />
      <div className={styles.footer__div}>
        <div>
          <button>개인정보처리방침</button>
        </div>
        <div>
          <span>010-1234-5678</span>
          <span>gofundindie@google.com</span>
        </div>
        <div className={styles.members}>
          <Link href="https://github.com/skalook">
            <div className={styles.member}>
              <img src="/git.png" />
              <span>이준희</span>
            </div>
          </Link>
          <Link href="https://github.com/JogiYo">
            <div className={styles.member}>
              <img src="/git.png" />
              <span>조현기</span>
            </div>
          </Link>
          <Link href="https://github.com/EAJA">
            <div className={styles.member}>
              <img src="/git.png" />
              <span>김찬욱</span>
            </div>
          </Link>
        </div>
      </div>
    </footer>
  );
}
