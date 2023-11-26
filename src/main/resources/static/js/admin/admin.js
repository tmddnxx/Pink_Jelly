//리스트 불러오기 (검색)
async function getList(types, keyword, from, to){
    console.log(types, keyword, from, to);
    const response = await axios.get(`/rest/memberList?types=${types}&keyword=${keyword}&from=${from}&to=${to}`);
    console.log(response);
    return response.data;
}
//삭제
async function removeMember(mno){
    console.log("async removeMember");
    const request = await axios.delete(`/rest/removeMember/${mno}`);
}
