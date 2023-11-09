async function getList({crbNo, page, size, goLast}){
    const result = await axios.get(`/crbComment/list/${crbNo}?page=${page}`, {params: {page, size}})
    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({crbNo:crbNo, page:lastPage, size:size, goLast:false})
    }

    return result.data
}