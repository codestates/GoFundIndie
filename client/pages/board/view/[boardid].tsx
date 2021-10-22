import { useRouter } from "next/router";

export default function BoarDetails() {
  const router = useRouter();
  const { boardid } = router.query;
  return (
    <div>
      <h1>영화 상세정보 ID : {boardid}</h1>
    </div>
  );
}
