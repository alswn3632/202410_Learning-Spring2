
console.log("boardDetailComment.js. in");
console.log("vscode 연동 성공!");

console.log(authNick);

// 댓글 등록 버튼 이벤트
document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
    const cmtText = document.getElementById('cmtText');
    const cmtWriter = document.getElementById('cmtWriter');

    if(cmtText.value == null || cmtText.value == ''){
        alert('댓글을 입력해주세요!');
        cmtText.focus();
        return false;
    }

    let cmtData = {
        bno : bnoVal,
        writer : cmtWriter.innerText,
        content : cmtText.value
    }

    console.log(cmtData);

    postCommentToServer(cmtData).then(result =>{
        if(result == '1'){
            alert("댓글 등록 성공");
            cmtText.value = "";

            // 댓글 출력 (등록과 동시에 출력)
            spreadCommentList(bnoVal);
        }
    });

});

// 댓글 출력 함수
function spreadCommentList(bno, page=1){
    getCommentListFromServer(bno, page).then(result =>{
        console.log(result);
        // result는 ph값
        // result 안의 cmtList가 있으면된다. 
        const ul = document.getElementById('cmtListArea');
        if(result.cmtList.length > 0){
            if(page == 1){
                ul.innerHTML = ""; // 반복 전 기존 샘플 버리기 (더보기 버튼에 의한 누적 불가능)
            }
            for(let cvo of result.cmtList){
                let li = `<li class="list-group-item" data-cno=${cvo.cno}>`;
                li += `<div class="ms-2 me-auto">`;
                li += `<div class="fw-bold"><span class="cmtWriterMod">${cvo.writer}</span> <span class="badge text-bg-primary rounded-pill">${cvo.regDate}</span>`;
                li += `</div>${cvo.content}</div>`;
                if(cvo.writer == authNick){
                    li += `<button type="button" class="btn btn-secondary btn-sm mod" data-cno=${cvo.cno} data-bs-toggle="modal" data-bs-target="#myModal">수정</button> `;
                    li += `<button type="button" class="btn btn-secondary btn-sm del" data-cno=${cvo.cno}>삭제</button>`;
                }
                li += `</li>`;
                ul.innerHTML += li; 
            }
            // 더보기 버튼의 숨김 여부 체크 코드
            let moreBtn = document.getElementById('moreBtn');
            // 더보기 버튼이 표시되는 조건
            // result = ph > pgvo > pageNo가 1인데 realEndPage 보다 작다면 보여질 것 
            // 현재 페이지가 전체 페이지보다 작으면 표시
            if(result.pgvo.pageNo < result.realEndPage){
                // style.visibility = "hidden" : 숨김 / "visible" : 표시
                moreBtn.style.visibility = 'visible'; // 버튼 표시
                moreBtn.dataset.page = page + 1; // 1 페이지 증가   
            }else{
                // 현재 페이지가 전체보다 작지 않다면
                moreBtn.style.visibility = 'hidden'; // 다시 숨김 처리
            }
        }else{
            ul.innerHTML = `<li class="list-group-item">Comment List Empty</li>`;
        }
    });
}

// 댓글 수정,삭제
document.addEventListener('click', (e)=>{
    // console.log(e.target);
    // 내가 클릭한 버튼의 객체를 모달창으로 전달
    if(e.target.classList.contains('mod')){
        // 클릭한 버튼이 포함된 <li>
        // closest : (나를 포함한) 가장 가까운 (부모)태그
        let li = e.target.closest('li');
        // console.log(li);
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;
        let cmtWriter = li.querySelector('.cmtWriterMod').innerText;
        console.log(cmtWriter);
        document.getElementById('exampleModalLabel').innerText = cmtWriter;
        let cno = li.dataset.cno;

        // cmtModBtn => cno 값을 dataset으로 닫기
        document.getElementById('cmtModBtn').setAttribute("data-cno", cno);
    }

    if(e.target.id == 'cmtModBtn'){
        let cmtData = {
            cno : e.target.dataset.cno,
            content : document.getElementById('cmtTextMod').value
        }
        console.log(cmtData);

        updateCommentToServer(cmtData).then(result =>{
            if(result == '1'){
                alert("댓글 수정 성공");
            }else{
                alert("댓글 수정 실패!");
            }
            // 모달 창 닫기 : btn-close 라는 객체를 클릭
            document.querySelector('.btn-close').click();
            // 수정한 댓글 출력
            spreadCommentList(bnoVal);
        });
    }

    

    if(e.target.classList.contains('del')){
        let li = e.target.closest('li');
        let cno = li.dataset.cno;

        removeCommentToServer(cno).then(result =>{
            if(result == '1'){
                alert("댓글 삭제 성공");
            }else{
                alert("댓글 삭제 실패!");
            }
            // 삭제 후 댓글 출력
            spreadCommentList(bnoVal);
        });
    }
    
    if(e.target.id == 'moreBtn'){
        // 바껴줘~~~
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    }

});

// 비동기 함수 모음
// 댓글 등록
async function postCommentToServer(cmtData) {
    try {
        const url = "/comment/post";
        const config = {
            method : "post",
            headers : {        
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
		const resp = await fetch(url, config);
        console.log(resp);
        const result = await resp.text();

        return result;
    } catch (error) {
        console.log(error);
    }
}

// 댓글 출력
async function getCommentListFromServer(bno, page) {
    try {
        const resp = await fetch("/comment/" + bno + "/" + page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}

// 댓글 수정 : put method
async function updateCommentToServer(cmtData) {
    try {
        const url = "/comment/modify";
        const config = {
            method : 'put',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(cmtData)
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error)
    }
}

// 댓글 삭제 : delete method
async function removeCommentToServer(cno) {
    try {
        const url = "/comment/" + cno;
        const config = {
            method : 'delete'
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error)
    }
}