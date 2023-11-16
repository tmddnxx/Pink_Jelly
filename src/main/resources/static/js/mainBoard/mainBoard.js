//댓글 등록
async function addComment(commentObj) {
    const request = await axios.post(`/mainComment/register`, commentObj);
    return request.data;
}
//댓글 목록 가져오기
async function getList({mbNo, page, size, goLast}) {
    const result = await axios.get(`/mainComment/list/${mbNo}?page=${page}`, {params: {page, size}});
    if (goLast) {
        const total = result.data.total;
        const lastPage = parseInt(Math.ceil(total/size));

        return getList({mbNo: mbNo, page: page, size: size});
    }
    return result.data;
}
//댓글 삭제
async function removeComment(comNo, mbNo) {
    console.log(mbNo);
    const response = await axios.delete(`/mainComment/${comNo}/${mbNo}`);
    return response.data;
}

//write
//파일 업로드 이벤트
async function uploadToServer(formObj) {
    console.log("upload to server");
    console.log(formObj);

    const response = await axios({
        method: 'post',
        url : '/upload/mainBoardUpload',
        data: formObj,
        headers: {
            'Content-Type' : 'multipart/form-data',
        },
    });
    return response.data;
}
// 파일 삭제
async function removeFileToServer(fileName) {
    const response = await axios.delete(`/upload/mainBoardRemove/${fileName}`);
    return response.data;

}