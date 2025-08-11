async function getURL(e) {
    const file = e.files[0];
    if (!file) return;

    const name = encodeURIComponent(file.name); // KakaoTalk_Photo_2025-06-15-19-17-09-5.jpeg
    let presigned_url = await fetch("/item/presigned-url?filename=" + name);        // get 요청
    presigned_url = await presigned_url.text();

    // https://haloshoppingmall.s3.ap-northeast-2.amazonaws.com/test/IMG_5517.JPG?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20250616T082327Z&X-Amz-SignedHeaders=host&X-Amz-Credential=AKIATGDLSG3ERDDT4YAL%2F20250616%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Expires=180&X-Amz-Signature=17ca4ce2ba4276e6a4d1eb318f4b719a246b4fdab577d99e1adc40694d09a7bd
    // 여기서 S3에업로드 요청
    let res = await fetch(presigned_url, {
        method: "PUT",
        body: file
    });

    console.log(presigned_url);
    if (res.ok) {   // 전송 성공했으면
        const url = presigned_url.split("?")[0];    // 해당 url에서 쿼리스트링 뺀거
        const img = document.getElementById("preview");
        const text = document.querySelector(".upload-text");

        img.src = url;

        // ✅ 이미지 보일 때 텍스트는 숨기기
        text.style.display = "none";

        //
        document.getElementById("imgUrl").value = url;
    } else {
        alert("이미지 업로드에 실패했습니다.");
    }
}



