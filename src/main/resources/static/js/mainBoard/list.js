async function getBoard(mbNo){
    console.log(mbNo);
    const response = await axios.get(`/mainModal/${mbNo}`);
    return response.data;
}

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
    console.log(result.data);
    return result.data;
}
//댓글 삭제
async function removeComment(comNo, mbNo) {
    console.log("mbNo는 ?",mbNo);
    console.log("comNo는 ?",comNo);
    const response = await axios.delete(`/mainComment/${comNo}/${mbNo}`);
    return response.data;
}