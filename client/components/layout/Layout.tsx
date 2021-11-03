import { ReactNode } from "react";
import Footer from "./Footer";
import Header from "./Header";
import styles from "../../styles/components/layout/layout.module.scss";

export default function Layout({ children }: { children: ReactNode }) {
  return (
    <>
      <div className={styles["body-wrapper"]}>
        <Header />
        <main className={styles.body}>{children}</main>
        <Footer />
      </div>
    </>
  );
}
