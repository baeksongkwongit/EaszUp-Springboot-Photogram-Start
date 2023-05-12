/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (1) 스토리 로드하기

let page=0;
let principal = $("#principal").val();

function storyLoad() {
	$.ajax({
		url:`/api/image?`+page,
		dataType:"json"
	}).done(res=>{
		//console.log(res);
		res.data.content.forEach((image)=>{
			let storyItem = getStoryItem(image);
			$("#storyList").append(storyItem);
		});
	}).fail(error=>{
		console.log("오류", error);
	});
}
storyLoad();

function getStoryItem(image) {
	//alert("Call!!!");
	//console.log(image);
	let item=``;
		item+=`<div class="story-list__item">`;
	item+=`<div class="sl__item__header">`;
	item+=`<div>`;
	item+=`<img class="profile-image" src="/upload/${image.user.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />`;
	item+=`</div>`;
	item+=`<div>${image.user.username}</div>`;
	item+=`</div>`;
	item+=`<div class="sl__item__img">`;
	item+=`<img src="/upload/${image.postImageUrl}" />`;
	item+=`</div>`;
	item+=`<div class="sl__item__contents">`;
	item+=`<div class="sl__item__contents__icon">`;
	item+=`<button>`;
	if(image.likesStat){
		item+=`<i class="far fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
	}else{
		item+=`<i class="fas fa-heart" id="storyLikeIcon-${image.id}" onClick="toggleLike(${image.id})"></i>`;
	}
 	item+=`</button>`;
	item+=`</div>`;
	item+=`<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount} </b>likes</span>`;
	item+=`<div class="sl__item__contents__content">`;
	item+=`<p>${image.caption}</p>`;
	item+=`</div>`;
	item+=`<div id="storyCommentList-${image.id}">`;
	image.comments.forEach((comment)=>{
		item+=`<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">`;
		item+=`<p>`;
		item+=`<b>${comment.user.username} :</b> ${comment.content}`;
		item+=`</p>`;
		if(principal == comment.user.id){
			item+=`<button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i> </button>`;
		}
		item+=`</div>`;
	});

	item+=`</div>
	<!-- 댓글 들어오는 박스end -->
	<!--댓글입력박스-->
	<div class="sl__item__input">
	<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
	<button type="button" onClick="addComment(${image.id})">게시</button>
	</div>
	<!--댓글달기박스end-->
	</div>
</div>
`;



	return item;
}

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	// console.log("윈도우scrollTop",$(window).scrollTop());
	// console.log("문서의높이", $(document).height());
	// console.log("윈도우 높이",$(window).height());

	let checkNum = $(window).scrollTop() - ($(document).height() -$(window).height());

	//console.log(checkNum);

	if(checkNum <1 && checkNum >-1){
		page++;
		storyLoad();
	}

});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	if (likeIcon.hasClass("far")) { //좋아요 하겠다.

		$.ajax({
			type:"POST",
			url:`/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res=>{
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr)+1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);


			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("오류", error);

		});

	} else {
		$.ajax({
			type:"DELETE",
			url:`/api/image/${imageId}/likes`,
			dataType: "json"
		}).done(res=>{
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr)-1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);


			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("오류", error);

		})

	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId: imageId,
		content: commentInput.val()
	}

	if (data.content === "") {
		alert("댓글을 작성해주세요!");
		return;
	}
	$.ajax({
		type:"post",
		url:"/api/comment",
		data:JSON.stringify(data),
		contentType:"application/json;charset=utf-8",
		dataType:"json"
	}).done(res=>{
		//console.log("성공",res);

		let comment = res.data;

		let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.user.username} :</b>
			      ${comment.content}
			    </p>
			    <button onclick="deleteComment(${comment.id})"><i class="fas fa-times"></i></button>
			 </div>`;
		commentList.prepend(content);

	}).fail(error=>{
		console.log("오류", error.responseJSON.data.content);
		alert(error.responseJSON.data.content);
	})

	commentInput.val(""); //인풋 필드를 깨끗이 비워준다.
}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"json"
	}).done(res=>{
		//console.log("성공", res);
		$(`#storyCommentItem-${commentId}`).remove();
	}).fail(error=>{
		console.log("오류", error);
	})

}







