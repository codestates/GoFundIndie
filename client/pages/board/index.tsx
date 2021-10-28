import Link from "next/link";

export default function Board() {
  const boardid = 32;
  return (
    <div>
      <div>영화목록</div>
      <div>
        <Link href="/board/view/[boardid]" as={`/board/view/${boardid}`}>
          id = 32 영화 페이지로 이동
        </Link>
      </div>
    </div>
  );
}
