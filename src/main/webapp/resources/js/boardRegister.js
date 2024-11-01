
console.log("boardRegister.js 연결 확인");

// 파일 업로드 트리거 버튼 처리
document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('file').click();
});

// 실행 파일에 대한 정규표현식 작성 / 파일의 최대 사이즈 20MB 
const regExp = new RegExp("\.(exe|jar|msi|dll|sh|bat)$");
const maxSize = 1024 * 1024 * 20;

function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else{
        return 1;
    }
}


document.addEventListener('change', (e)=>{
    if(e.target.id == 'file'){ // 파일창에 변화가 생겼다면...
        // type="file" element에 저장된 file에 대한 정보를 가져오는 property => files
        const fileObj = document.getElementById('file').files;
        console.log(fileObj);

        // 파일이 새로 들어오면, 아래에서 disabled 된 버튼 원상복구해주고 다시 검증
        document.getElementById('regBtn').disabled = false;

        // 내가 등록한 파일 정보를 파일 목록을 기록
        // 등록한 파일이 validation에 맞지 않으면 register 버튼을 비활성화
        // 파일 목록에 업로드 불가능이라고 표시

        let div = document.getElementById('fileZone');
        div.innerHTML = ""; // 새로 추가되는 목록이 있다면 삭제하고 처리

        // 여러개의 첨부파일이 모두 검증을 통과해야만 register 버튼이 활성화
        // isOk 누적곱으로 여러 검증과정을 지날것
        let isOk = 1;
        let ul = `<ul class="list-group list-group-flush">`;
        // 개별 파일에 대한 검증, 결과 출력
        for(let file of fileObj){
            let validResult = fileValidation(file.name, file.size);
            isOk *= validResult;
            ul += `<li class="list-group-item">`;

            ul += `<div class="ms-2 me-auto">`;
            ul += `<div class="fw-bold">${validResult? '<span class="cmtWriterMod text-success">업로드 가능 </span>' : '<span class="cmtWriterMod text-danger">업로드 불가능 </span>'}`;
            ul += `<span class="badge text-bg-${validResult? 'success' : 'danger'} rounded-pill">${file.size}byte</span>`;
            ul += `</div>${file.name}</div>`;

            ul += `</li>`;
        }
        ul += `</ul>`;
        
        div.innerHTML = ul; // 누적x

        if(isOk == 0){
            //하나라도 검증을 통과하지 못한파일이 있다면 버튼 비활성화
            document.getElementById('regBtn').disabled = true;
        }
    }
});
