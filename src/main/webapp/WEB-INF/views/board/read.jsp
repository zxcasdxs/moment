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
              <h3 class="card-title">Board Read</h3>
            </div>
            <!-- /.card-header -->
            <!-- form start -->
            <div class="card-body">
              <div class="form-group">
                <label for="exampleInputEmail0">BNO</label>
                <input type="text" name="bno" class="form-control" id="exampleInputEmail0" value="<c:out value="${boardDTO.bno}"></c:out>" readonly>
              </div>
              <div class="form-group">
                <label for="exampleInputEmail1">Title</label>
                <input type="text" name="title" class="form-control" id="exampleInputEmail1" value="<c:out value="${boardDTO.title}"></c:out>" readonly>
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
                    <textarea name="content" class="form-control" rows="3" disabled><c:out value="${boardDTO.content}"></c:out></textarea>
                  </div>
                </div>
              </div>
            </div>
            <!-- /.card-body -->
            <div class="card-footer">
              <button type="button" class="btn btn-default btnList">LIST</button>
              <sec:authentication property="principal" var="memberDTO" />

              <c:if test="${boardDTO.writer  eq  memberDTO.mid }">
              <button type="button" class="btn btn-info btnMod">MODIFY</button>
              </c:if>
            </div>

            <div>
              <c:forEach items="${boardDTO.files}" var="attach">
                <div>
                  <c:if test="${attach.image}">
                    <img onclick="javascript:showOrigin('${attach.getFileLink()}')" src="/viewFile?file=${attach.getThumbnail()}">
                  </c:if>
                    ${attach.fileName}
                </div>
              </c:forEach>
            </div>

          </div>
          <!-- /.card -->
          <!-- DIRECT CHAT -->
          <div class="card direct-chat direct-chat-primary">
            <div class="card-header">
              <h3 class="card-title">Replies</h3>

              <div class="card-tools">
                <span title="3 New Messages" class="badge badge-primary addReplyBtn">Add Reply</span>
                <button type="button" class="btn btn-tool" data-card-widget="collapse">
                  <i class="fas fa-minus"></i>
                </button>
                <button type="button" class="btn btn-tool" title="Contacts" data-widget="chat-pane-toggle">
                  <i class="fas fa-comments"></i>
                </button>
                <button type="button" class="btn btn-tool" data-card-widget="remove">
                  <i class="fas fa-times"></i>
                </button>
              </div>
            </div>
            <!-- /.card-header -->
            <div class="card-body">
              <!-- Conversations are loaded here -->
              <div class="direct-chat-messages">
                <!-- Message. Default to the left -->
                <div class="direct-chat-msg">
                  <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-left">Alexander Pierce</span>
                    <span class="direct-chat-timestamp float-right">23 Jan 2:00 pm</span>
                  </div>
                  <div class="direct-chat-text">
                    Is this template really for free? That's unbelievable!
                  </div>
                </div>
                <!-- /.direct-chat-msg -->
              </div>
              <!--/.direct-chat-messages-->
            </div>
            <!-- /.card-body -->
          </div>
          <!--/.direct-chat -->
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

<div class="modal fade" id="modal-sm">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Reply</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <input type="text" name="replyer" readonly value="<sec:authentication property="principal.mid"/>">
        <input type="text" name="reply">
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary operBtn">Save changes</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="modal-lg">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Modify/Remove</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <input type="hidden" name="rno">
        <input type="text" name="replyerMod">
        <input type="text" name="replyMod">
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-info btnModReply">Modify</button>
        <button type="button" class="btn btn-danger btnRem">Remove</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="modal fade" id="modal-image">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-body">
          <img id="targetImage">
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<%@include file="../includes/footer.jsp"%>

<script>
  const actionForm = document.querySelector("#actionForm")

  document.querySelector(".btnList").addEventListener("click", () => {actionForm.submit()}, false)

  document.querySelector(".btnMod").addEventListener("click", () => {

    const bno = '${boardDTO.bno}'

    actionForm.setAttribute("action", "/board/modify")
    actionForm.innerHTML += `<input type = 'hidden' name = 'bno' value = '\${bno}'>`
    actionForm.submit()}, false)

</script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="/resources/js/reply.js"></script>

<script>

  const modalImage = new bootstrap.Modal(document.querySelector('#modal-image'))

  function showOrigin(fileLink){

    document.querySelector("#targetImage").src = `/viewFile?file=\${fileLink}`
    modalImage.show()
  }

  function after(result) { // 순수한 함수 생성
    console.log("after............")
    console.log(result)
  }

  // doA().then(result => console.log(doA()))
  // doB(after)//파라미터로 함수 삽입, 괄호가 없어서 객체 // 자바스크립트의 특징 파라미터의 수가 안맞아도 동작은 한다.
  // const reply = {bno:230, replyer:'user00', reply:'2222222222233333333333000000000000'}
  // doC(reply).then(result => console.log(result))
  // doD(112).then(result => console.log(result))
  // const reply = {rno:112, reply:"Update reply text...."}
  // doE(reply).then(result => console.log(result))

  function getList() {

    const target = document.querySelector(".direct-chat-messages")
    const bno = '${boardDTO.bno}' // 현재 게시글의 번호 el

    function convertTemplate(replyObj) {

      const {rno, bno, reply, replyer, replyDate, modDate} = {...replyObj}

      const template = `
                <div class="direct-chat-msg">
                  <div class="direct-chat-infos clearfix">
                    <span class="direct-chat-name float-left">\${rno} -- \${replyer}</span>
                    <span class="direct-chat-timestamp float-right">\${replyDate}</span>
                  </div>
                  <div class="direct-chat-text" data-rno='\${rno}' data-replyer='\${replyer}'>\${reply}</div>
                </div>`

        return template;
    }
    getReplyList(bno).then(data => {
      console.log(data) // 배열
      let str = "";
      data.forEach(reply => {
        str += convertTemplate(reply)
      })
      target.innerHTML = str
    })
  }

  //최초 실행
  (function () {
    getList()
  })()

  const modalDiv = $("#modal-sm")

  let oper = null

  document.querySelector(".addReplyBtn").addEventListener("click", function (){

    oper = 'add'
    modalDiv.modal('show')

  }, false)

  document.querySelector(".operBtn").addEventListener("click", function (){

    const bno = '${boardDTO.bno}'
    const replyer = document.querySelector("input[name='replyer']").value
    const reply = document.querySelector("input[name='reply']").value

    if(oper === 'add') {
      const replyObj = {bno, replyer, reply} //{bno:bno, replyer:replyer, reply:reply} 와 동일한 문법
      console.log(replyObj)
      addReply(replyObj).then(result => {
        getList()
        modalDiv.modal('hide')
        document.querySelector("input[name='replyer']").value = ""
        document.querySelector("input[name='reply']").value = ""
      })
    }
  }, false)

  //수정/삭제 dom
  const modModal = $("#modal-lg")
  const modReplyer = document.querySelector("input[name='replyerMod']")
  const modReply = document.querySelector("input[name='replyMod']")
  const modRno = document.querySelector("input[name='rno']")

  document.querySelector(".direct-chat-messages").addEventListener("click", (e)=> {

    const target = e.target
    const bno = '${boardDTO.bno}'

    if(target.matches(".direct-chat-text")) {
      const rno = target.getAttribute("data-rno")
      const replyer = target.getAttribute("data-replyer")
      const reply = target.innerHTML
      console.log(rno, replyer, reply, bno)

      modRno.value = rno
      modReply.value = reply
      modReplyer.value = replyer

      document.querySelector(".btnRem").setAttribute("data-rno", rno)

      modModal.modal('show')


    }
  }, false)

  document.querySelector(".btnRem").addEventListener("click", (e) => {
    const rno = e.target.getAttribute("data-rno")
    removeReply(rno).then(result => {
      getList()
      modModal.modal('hide')
    })

  },false)

  document.querySelector(".btnModReply").addEventListener("click", (e) => {

    const replyObj = {rno: modRno.value, reply: modReply.value}
    modifyReply(replyObj).then(result => {
      getList()
      modModal.modal('hide')
    })

  }, false)

</script>


</body>
</html>