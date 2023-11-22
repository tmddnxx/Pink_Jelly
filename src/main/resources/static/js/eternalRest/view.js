
//댓글 등록
async function addComment(commentObj) {
    const request = await axios.post(`/erbComment/register`, commentObj);
    return request.data;
}
//댓글 목록 가져오기
async function getList({erbNo, page, size, goLast}) {
    const result = await axios.get(`/erbComment/list/${erbNo}?page=${page}`, {params: {size: size}});
    if (goLast) {
        const total = result.data.total;
        const lastPage = parseInt(Math.ceil(total/size));

        return getList({erbNo: erbNo, page: page, size: size});
    }
    console.log(result.data);
    return result.data;
}
//댓글 삭제
async function removeComment(comNo, erbNo) {
    console.log(erbNo);
    const response = await axios.delete(`/erbComment/${comNo}/${erbNo}`);
    return response.data;
}