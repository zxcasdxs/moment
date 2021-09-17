
async function doA(){//함수 앞에 async 붙여주면 비동기 작동

    console.log("doA...................")

    const response = await axios.get("/replies") // await사용하려면 async가 있어야함.
    const data = response.data
    console.log("doA...data", data )
}

const doB = (fn) => {
    console.log("doB....................")
    try{
        axios.get('/replies').then(response => {
            console.log(response)
            const arr = response.data
            fn(arr)
        })
    }catch(error){
        console.log(err)// 결과는 원래 비동기
}
}

async function doC(obj) {

    const response = await axios.post("/replies", obj)
    return response.data
}

const doD = async (rno) => {
    const response = await axios.delete(`/replies/${rno}`)//백틱사용 el태그삽입 링크화
    return response.data
}

const doE = async (reply) => {

    const response = await axios.put(`/replies/${reply.rno}` , reply)

    return response.data
}

const getReplyList = async (bno) => {

    const response = await axios.get(`/replies/list/${bno}`)

    return response.data

}

async function addReply(obj) {

    const response = await axios.post("/replies", obj)
    return response.data
}

const removeReply = async (rno) => {
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}

const modifyReply = async (reply) => {

    const response = await axios.put(`/replies/${reply.rno}` , reply)

    return response.data
}