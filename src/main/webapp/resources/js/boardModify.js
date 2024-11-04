
console.log("boardModify.js in!!")

document.addEventListener('click', (e)=>{
    if(e.target.classList.contains('file-x')){
        // 화면에서 uuid 가져오기
        let uuid = e.target.dataset.uuid;

        deleteFileFromServer(uuid).then(result =>{
            if(result == '1'){
                console.log("파일 삭제 성공");
                // 삭제 후 화면에서 사라지게하기
                let li = e.target.closest('li');
                li.remove();
            }else{
                console.log("파일 수정 실패!");
            }
        });
    }
});

async function deleteFileFromServer(uuid) {
    try {
        const url = "/board/file/" + uuid;
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