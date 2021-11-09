import Cookies from "js-cookie";
import { useEffect } from "react";
import { useRouter } from "next/router";
import Setaxios from "../fetching/Setaxios";
export default function Approval() {
  const router = useRouter();
  useEffect(() => {
    if (!router.isReady) return;
    console.log(router.query.pg_token);
    console.log(Cookies.get("tid"));
    Setaxios.postAxios("pay/approve", {
      amount: 3000,
      tid: Cookies.get("tid"),
      pg_token: router.query.pg_token,
    })
      .then((res) => {
        alert("정상적으로 결제됐습니다");
        window.close();
      })
      .catch((err) => {
        alert(err);
        window.close();
      });
  });
  return <></>;
}
