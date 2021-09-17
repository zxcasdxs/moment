<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2021-09-07
  Time: 오후 2:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <input type="file" name="uploadFiles" multiple><button id="uploadBtn">UPLOAD</button>

    <div class="uploadResult">

    </div>

    <%--axios스크립트--%>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script>

    const uploadResultDiv = document.querySelector(".uploadResult")

    document.querySelector("#uploadBtn").addEventListener("click", (e) => {

        const formData = new FormData()
        const fileInput = document.querySelector("input[name='uploadFiles']")

        for(let i = 0; i <fileInput.files.length; i++) {
            formData.append("uploadFiles", fileInput.files[i])
            //append의 이름이 파라미터의 이름이 됨. 매우중요
        }

        console.dir(formData)

        const headerObj = {headers : {'Content-Type' : 'multipart/form-data'}}
        //헤더객체 생성

        axios.post("/upload", formData, headerObj).then(response => {
            const arr = response.data
            console.log(arr)
            let str = ""
            for(let i = 0; i < arr.length; i++) {
                const {uuid, fileName, uploadPath, image, thumbnail, fileLink} = {...arr[i]}

                if(image) {
                    //버튼을 추가. 버튼을 클릭하면 파일의 링크 전달
                    str += `<div data-uuid='\${uuid}'><img src='/viewFile?file=\${thumbnail}'><span>\${fileName}</span>
                                <button onclick="javascript:removeFile('\${fileLink}', this)">X</button>
                            </div>`
                } else {
                    str += `<div data-uuid='\${uuid}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a></div>`
                }
            }//end for
            uploadResultDiv.innerHTML += str
        })
        //버튼을 누르면 업로드 post방식 호출


    },false)

    function removeFile(fileLink, ele){
        console.log(fileLink)
        axios.post("/removeFile", {fileName:fileLink}).then(response => {
            const targetDiv = ele.parentElement
            targetDiv.remove();
        })
    }

</script>
</body>
</html>
