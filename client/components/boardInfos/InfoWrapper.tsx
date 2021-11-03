import { AppProps } from "next/dist/shared/lib/router/router";
import Cast from "./Cast";
import Comments from "./Comments";
import Stillcuts from "./Stillcuts";

export default function InfoWrapper({ comments }: any) {
  return (
    <div>
      <div>
        <button>기본 정보</button>
        <button>출연/제작</button>
        <button>포토</button>
        <button>평점</button>
      </div>
      <Cast />
      <Stillcuts />
      <Comments comments={comments} />
    </div>
  );
}
