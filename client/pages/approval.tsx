import { useEffect, useState } from "react";
import { useRouter } from "next/router";
import Setaxios from "../fetching/Setaxios";
import styles from "../styles/approval.module.scss";
import Cookies from "js-cookie";

export default function Approval() {
  const router = useRouter();
  const [timer, setTimer] = useState(0);
  useEffect(() => {
    const timeout = setTimeout(() => setTimer(timer + 1), 1000);
    if (!router.isReady) return;

    Setaxios.postAxios("pay/approve", {
      pg_token: router.query.pg_token,
      boardId: Cookies.get("boardId"),
    })
      .then((res) => {
        alert("정상적으로 결제됐습니다");
        Cookies.remove("boardId");
        window.close();
      })
      .catch((err) => {
        alert(err.response.data.code);
        window.close();
      });
    return () => clearTimeout(timeout);
  }, [timer]);
  return (
    <div className={styles.payment}>
      <div className={styles.loader}></div>
    </div>
  );
}

// import { GetServerSideProps } from "next";
// process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

// export default function Approval({ message }: { message: string }) {
//   console.log(message);
//   if (typeof window === "undefined") return <div>결제중...</div>;
//   if (message === undefined) return <div>결제중...</div>;
//   alert(message);

//   return <div>결제중...</div>;
// }

// export const getServerSideProps: GetServerSideProps = async (context) => {
//   const cookies: any = context.req.headers.cookie?.split("; ");
//   const accesstoken = cookies[2]?.slice(cookies[2]?.search("accesstoken") + 12);

//   if (cookies.length < 20) {
//     return { props: { userInfo: null } };
//   }
//   const res = await fetch(`${process.env.NEXT_PUBLIC_SERVER_URL}/pay/approve`, {
//     method: "POST",
//     body: JSON.stringify({
//       pg_token: context.query.pg_token,
//     }),
//     headers: {
//       "Content-Type": "application/json",
//       accesstoken: accesstoken,
//     },
//   });
//   console.log(res);
//   const data = await (await res).json();
//   console.log(data);
//   return { props: { message: "결제중..." } };
// };
