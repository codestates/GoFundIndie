import styles from "../../styles/Home.module.scss";
import Carousel from "../../components/Carousel";
import "swiper/css/bundle";
import ContentCarousel from "../../components/ContentCarousel";
import { GetServerSideProps } from "next";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

export default function Page({ film }: any) {
  if (film === null)
    return <div className={styles.error}> 서버에 오류가 발생했습니다.</div>;
  const recommend: any = film.FindRandomBoard.data;
  return (
    <div>
      <div className={styles.home__div}>
        <div className={styles.header__blank__div} />
        {recommend.map((theme: any) => {
          console.log(theme);
          return (
            <div key={theme.phrase} className={styles.home__div__wrapper}>
              <ContentCarousel film={theme} />
            </div>
          );
        })}
        <div className={styles.home__div__wrapper}></div>
      </div>
    </div>
  );
}

export const getServerSideProps: GetServerSideProps = async (context) => {
  const query = `{
FindRandomBoard (Limit: 20) {
  code
  data {
      phrase
      data {
          id
          isApprove
          title
          posterImg
          infoCountry
          infoCreatedYear
          infoCreatedDate
          infoTime
          infoLimit
      }
  }
}
}`;

  const res = await fetch(`${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`, {
    method: "POST",
    body: JSON.stringify({ query }),
    headers: {
      "Content-Type": "application/json",
    },
  }).catch((err) => {
    return err;
  });
  if (res === null) return { props: {} };
  if (res.code === "ECONNREFUSED") return { props: { film: null } };
  const film = await (await res).json();
  if (film === null) return { props: {} };
  return {
    props: {
      film: film.data,
    },
  };
};
