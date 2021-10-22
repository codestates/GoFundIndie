import "../styles/globals.css";
import Layout from "../components/Layout";
import { AppProps } from "next/dist/shared/lib/router/router";

export default function MyApp({ Component, pageProps }: AppProps) {
  return (
    <Layout>
      <Component {...pageProps} />
    </Layout>
  );
}
