//댓글 등록
async function addComment(commentObj) {
    console.log("왔나?");
    const request = await axios.post(`/mainComment/register`, commentObj);
    return request;
}
//댓글 목록 가져오기
async function getList({bno, page, size, goLast}) {
    const result = await axios.get(`/mainComment/list/${mbNo}?page=${page}`, {params: {page, size}});
    if (goLast) {
        const total = result.data.total;
        const lastPage = parseInt(Math.ceil(total/size));

        return getList({mbNo: mbNo, page: lastPage, size: size});
    }
    return result.data;
}