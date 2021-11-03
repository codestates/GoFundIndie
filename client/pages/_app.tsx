import "../styles/globals.css";
import Layout from "../components/layout/Layout";
import { AppProps } from "next/dist/shared/lib/router/router";

import "swiper/scss";
import "swiper/scss/pagination";
import "swiper/scss/navigation";

export default function MyApp({ Component, pageProps }: AppProps) {
  return (
    <Layout>
      <Component {...pageProps} />
    </Layout>
  );
}
