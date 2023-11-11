//댓글 등록
async function addComment(commentObj) {
    console.log("왔나?");
    const request = await axios.post(`/crbComment/register`, commentObj);
    return request.data;
}
//댓글 목록 가져오기
async function getList({crbNo, page, size, goLast}) {
    console.log("getList");
    const result = await axios.get(`/crbComment/list/${crbNo}?page=${page}`, {params: {page, size}});
    console.log("result" + result);
    if (goLast) {
        const total = result.data.total;
        const lastPage = parseInt(Math.ceil(total/size));

        return getList({crbNo: crbNo, page: page, size: size});
    }
    return result.data;
}
//댓글 삭제
async function removeComment(comNo, crbNo) {
    console.log(crbNo);
    const response = await axios.delete(`/crbComment/${comNo}/${crbNo}`);
    return response.data;
}