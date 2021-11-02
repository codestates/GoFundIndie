import "../styles/globals.css";
import Layout from "../components/Layout";
import { AppProps } from "next/dist/shared/lib/router/router";

import "swiper/scss";
import "swiper/scss/pagination";
import "swiper/scss/navigation";

import "slick-carousel/slick/slick.scss";
import "slick-carousel/slick/slick-theme.scss";
export default function MyApp({ Component, pageProps }: AppProps) {
  return (
    <Layout>
      <Component {...pageProps} />
    </Layout>
  );
}
