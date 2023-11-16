'use strict'

async function uploadToServer(url, formObj) {
    // 서버에 이미지 파일 업로드
    console.log("upload to server...");
    console.log(formObj);

    const response = await axios.post(url, formObj, {
        headers: {
            'Content-Type' : 'multipart/form-data',
        }
    });

    return response.data;
}

async function removeFileToServer(url, fileName) {
    const response = await axios.delete(url + "/" + fileName);

    return response.data;
}
