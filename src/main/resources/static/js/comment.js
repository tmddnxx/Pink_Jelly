// 댓글 시작 ----------
async function getList({crbNo, page, size, goLast}){
    const result = await axios.get(`/crbComment/list/${crbNo}?page=${page}`, {params: {page, size}})
    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({crbNo:crbNo, page:lastPage, size:size, goLast:false})
    }

    return result.data
}

const crbNo = [[${dto.crbNo}]];

function printReplies(page, size, goLast){
    getList({crbNo, page, size, goLast}).then(
        data => {
            console.log(data);
            printList(data.dtoList); // 목록 처리
            printPages(data); // 페이징 처리
        }
    ).catch(e =>{
        console.error()
    });
}
printReplies(1, 10, false);

const commentList = document.querySelector('.commentList');
const commentPaging = document.querySelector('.commentPaging');

function  printPages(data){ // 페이지 목록 출력
    // pagination
    let pageStr = '';
    if(data.prev){
        pageStr += `<li class="page-item">
              <a class="page-link" data-page="${data.start - 1}">Prev</a></li>`;
    }
    for(let i = data.start; i<= data.end; i++){
        pageStr += `<li class="page-item ${i === data.page ? "active" : ""}">
                    <a class="page-link" data-page="${i}">${i}</a></li>`;
    }
    if(data.next) {
        pageStr += `<li class="page-item"><a class="page-link" data-page="${data.end +1}">Next</a></li>`;
    }
    console.log(pageStr);
    commentPaging.innerHTML = pageStr;
}

function printList(dtoList){ // 댓글 목록 출력
    let str = '';
    if(dtoList && dtoList.length > 0){
        for(const dto of dtoList){

            str += `<li class="list-group-item d-flex replyItem">
                    <span class="col-2">${dto.comNo}</span>
                    <span class="col-6" data-rno="${dto.comNo}"></span>
                    <span class="col-2"></span>
                    <span class="col-2">${dto.addDate}</span>
                </li>`;
        }
    }
    commentList.innerHTML = str;
}