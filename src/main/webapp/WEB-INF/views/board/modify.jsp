<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
        <div class="container-fluid">
            <div class="row mb-2">
                <div class="col-sm-6">
                    <h1 class="m-0">Modify Page</h1>
                </div><!-- /.col -->
                <div class="col-sm-6">
                    <ol class="breadcrumb float-sm-right">
                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                        <li class="breadcrumb-item active">Dashboard v1</li>
                    </ol>
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
        <div class="container-fluid">
            <div class="row">
                <!-- left column -->
                <div class="col-md-12">
                    <!-- general form elements -->
                    <div class="card card-primary">
                        <div class="card-header">
                            <h3 class="card-title">Board Read</h3>
                        </div>
                        <!-- /.card-header -->
                        <!-- form start -->
                    <form id="form1">
                        <input type="hidden" name="page" value="${pageRequestDTO.page}">
                        <input type="hidden" name="size" value="${pageRequestDTO.size}">
                        <div class="card-body">
                            <div class="form-group">
                                <label for="exampleInputEmail0">BNO</label>
                                <input type="text" name="bno" class="form-control" id="exampleInputEmail0" value="<c:out value="${boardDTO.bno}"></c:out>" readonly>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Title</label>
                                <input type="text" name="title" class="form-control" id="exampleInputEmail1" value="<c:out value="${boardDTO.title}"></c:out>">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail2">Writer</label>
                                <input type="text" name="writer" class="form-control" id="exampleInputEmail2" value="<c:out value="${boardDTO.writer}"></c:out>" readonly>
                            </div>
                            <div class="row">
                                <div class="col-sm-12">
                                    <!-- textarea -->
                                    <div class="form-group">
                                        <label>Textarea</label>
                                        <textarea name="content" class="form-control" rows="3"><c:out value="${boardDTO.content}"></c:out></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="temp"></div>
                        <!-- /.card-body -->
                        <div class="card-footer">
                            <button type="submit" class="btn btn-warning btnMod">수정</button>
                            <button type="submit" class="btn btn-danger btnDel">삭제</button>
                            <button type="submit" class="btn btn-primary btnList">목록</button>
                        </div>
                    </form>


                        <label for="exampleInputFile">File input</label>
                        <div class="input-group">
                            <div class="custom-file">
                                <input type="file" name="uploadFiles" class="custom-file-input" id="exampleInputFile" multiple>
                                <label class="custom-file-label" for="exampleInputFile">Choose file</label>
                            </div>
                            <div class="input-group-append">
                                <span class="input-group-text" id="uploadBtn">Upload</span>
                            </div>
                        </div>

                        <div class="uploadResult">
                            <c:forEach items="${boardDTO.files}" var="attach">
                                <c:if test="${attach.image}">
                                <div data-uuid="${attach.uuid}" data-filename="${attach.fileName}"
                                     data-uploadpath="${attach.uploadPath}" data-image="${attach.image}">
                                        <img src="/viewFile?file=${attach.getThumbnail()}">
                                        ${attach.fileName}
                                        <button onclick="javascript:removeDiv(this)">x</button>
                                </c:if>
                                </div>
                            </c:forEach>
                        </div>

                    </div>
                    <!-- /.card -->
                </div>
            </div>
        </div>
    </section>
</div>
<!-- /.content-wrapper -->

<form id="actionForm" action="/board/list" method="get">
    <input type="hidden" name="page" value="${pageRequestDTO.page}">
    <input type="hidden" name="size" value="${pageRequestDTO.size}">

    <c:if test="${pageRequestDTO.type != null}">
        <input type="hidden" name="type" value="${pageRequestDTO.type}">
        <input type="hidden" name="keyword" value="${pageRequestDTO.keyword}">
    </c:if>
</form>

<%@include file="../includes/footer.jsp"%>

<script>
    const form = document.querySelector("#form1")
    const actionForm = document.querySelector("#actionForm")

    document.querySelector(".btnList").addEventListener("click", e => {
        e.preventDefault();
        e.stopPropagation();

        actionForm.submit();
    }, false)

    document.querySelector(".btnMod").addEventListener("click", e => {
        e.preventDefault();
        e.stopPropagation();

        const fileDivArr = uploadResultDiv.querySelectorAll("div")
        //첨부파일마다 div생성되므로, div의 갯수가 파일의 갯수

        if(fileDivArr && fileDivArr.length > 0) {
            let str ="";
            for(let i = 0; i < fileDivArr.length; i++){
                const target = fileDivArr[i]
                const uuid = target.getAttribute("data-uuid")
                const fileName = target.getAttribute("data-filename")
                const uploadPath = target.getAttribute("data-uploadpath")
                const image = target.getAttribute("data-image")

                str += `<input type='hidden' name='files[\${i}].uuid' value='\${uuid}' >`
                str += `<input type='hidden' name='files[\${i}].fileName' value='\${fileName}' >`
                str += `<input type='hidden' name='files[\${i}].uploadPath' value='\${uploadPath}' >`
                str += `<input type='hidden' name='files[\${i}].image' value='\${image}' >`

                //4개의 인풋 히든태그 작성. 파일 갯수만큼 반복 생성
                //폼태그에 삽입해야함
            }//end for

            document.querySelector(".temp").innerHTML = str
        }//end if



        form.setAttribute("action", "/board/modify")
        form.setAttribute("method", "post")
        form.submit()
    }, false)

    document.querySelector(".btnDel").addEventListener("click", e => {
        e.preventDefault();
        e.stopPropagation();

        form.setAttribute("action", "/board/remove")
        form.setAttribute("method", "post")
        form.submit()
    }, false)



</script>

<%--axios스크립트--%>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script>

    const uploadResultDiv = document.querySelector(".uploadResult")

    document.querySelector("#uploadBtn").addEventListener("click",(e) => {

        const formData = new FormData()
        const fileInput = document.querySelector("input[name='uploadFiles']")

        for(let i = 0; i < fileInput.files.length; i++){

            //console.log(fileInput.files[i])

            formData.append("uploadFiles", fileInput.files[i])
        }
        //append의 이름이 파라미터의 이름이 됨. 매우중요

        console.log(formData)

        const headerObj = { headers: {'Content-Type': 'multipart/form-data'}}
        //헤더객체 생성

        axios.post("/upload", formData, headerObj).then((response) => {
            const arr = response.data
            console.log(arr)
            let str = ""
            for(let i = 0; i < arr.length; i++){
                const {uuid,fileName,uploadPath,image,thumbnail,fileLink} = {...arr[i]}

                if(image){//버튼을 추가. 버튼을 클릭하면 파일의 링크 전달
                    str += `<div data-uuid='\${uuid}' data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}' ><img src='/viewFile?file=\${thumbnail}'/><span>\${fileName}</span>
                            <button onclick="javascript:removeDiv(this)" >x</button></div>`
                }else {
                    str += `<div data-uuid='\${uuid}'data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a></div>`
                }
            }//end for
            uploadResultDiv.innerHTML += str
        })
        //버튼을 누르면 업로드 post방식 호출

    },false)

    function removeDiv(ele){
        ele.parentElement.remove();
    }
</script>


</body>
</html>