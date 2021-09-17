<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../includes/header.jsp"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
  <!-- Content Header (Page header) -->
  <div class="content-header">
    <div class="container-fluid">
      <div class="row mb-2">
        <div class="col-sm-6">
          <h1 class="m-0">Register Page</h1>
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
              <h3 class="card-title">Board Register</h3>
            </div>
            <!-- /.card-header -->
            <!-- form start -->
            <form id="form1" action="/board/register" method="post">
              <div class="card-body">
                <div class="form-group">
                  <label for="exampleInputEmail1">Title</label>
                  <input type="text" name="title" class="form-control" id="exampleInputEmail1" placeholder="Enter Title">
                </div>
                <div class="form-group">
                  <label for="exampleInputEmail2">Writer</label>
                  <input type="text" name="writer" class="form-control" id="exampleInputEmail2" placeholder="Enter Writer" readonly value="<sec:authentication property="principal.mid"/>">
                </div>
                <div class="row">
                  <div class="col-sm-12">
                    <!-- textarea -->
                    <div class="form-group">
                      <label>Textarea</label>
                      <textarea name="content" class="form-control" rows="3" placeholder="Enter ..."></textarea>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /.card-body -->

              <div class="temp"></div>

              <div class="card-footer">
                <button type="submit" id="submitBtn" class="btn btn-primary">Submit</button>
              </div>
            </form>

            <style>
              .uploadResult {
                display: flex;
                justify-content: center;
                flex-direction: row;
              }
            </style>

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
                <div>
                  ${attach.fileName}
                  <button onclick="javascript:removeFile('${attach.getFileLink()}')">x</button> <%--삭제 하려면 파일링크 필요--%>
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

<%@include file="../includes/footer.jsp"%>

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
                            <button onclick="javascript:removeFile('\${fileLink}',this)" >x</button></div>`
        }else {
          str += `<div data-uuid='\${uuid}'data-filename='\${fileName}' data-uploadpath='\${uploadPath}' data-image='\${image}'><a href='/downFile?file=\${fileLink}'>\${fileName}</a></div>`
        }
      }//end for
      uploadResultDiv.innerHTML += str
    })
    //버튼을 누르면 업로드 post방식 호출

  },false)

  function removeFile(fileLink,ele){
    console.log(fileLink)
    axios.post("/removeFile", {fileName:fileLink}).then(response => {
      const targetDiv = ele.parentElement
      targetDiv.remove()
    })
  }

  document.querySelector("#submitBtn").addEventListener("click", (e) => {
    e.stopPropagation()
    e.preventDefault()

    const form1 = document.querySelector("#form1")
    //현재 화면에 있는 파일정보를 hidden태그로 변화
    const fileDivArr = uploadResultDiv.querySelectorAll("div")
    //첨부파일마다 div생성되므로, div의 갯수가 파일의 갯수

    if(!fileDivArr){
      form1.submit()
      //없으면 종료
      return
    }  //있으면 파일갯수만큼 루프

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
    }



    document.querySelector(".temp").innerHTML = str
    //form1.innerHTML += str
    form1.submit()
    //form을 submit

  },false)

</script>

</body>
</html>