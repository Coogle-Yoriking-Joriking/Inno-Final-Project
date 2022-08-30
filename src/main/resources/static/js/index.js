var d = [];
var counter = 0;

$(document).ready(function () {
    d = [];
    counter = 0;

    $("#input-tag").on("keyup", function (e) {
        var self = $(this);
        // input 에 focus 되있을 때 엔터 및 스페이스바 입력시 구동
        if (e.key === "Enter" || e.keyCode == 32) {

            var tagValue = self.val().replace(/ /g, ''); // 값 가져오기

            // 값이 없으면 동작 안합니다.
            if (tagValue !== "") {

                // 같은 태그가 있는지 검사한다. 있다면 해당값이 array 로 return 된다.
                var result = Object.values(d).filter(function (word) {
                    return word === tagValue;
                })

                // 태그 중복 검사
                if (result.length == 0) {
                    $(".d-list")
                        .append("<li class='d-item'>" + tagValue + "<span class='delete-d' idx='" + counter + "'>X</span></li>");
                    addTag(tagValue);
                    self.val("");
                } else {
                    alert("태그값이 중복됩니다.");
                }
            }
            e.preventDefault(); // SpaceBar 시 빈공간이 생기지 않도록 방지
        }
    });

    $(".search-button").on("click", function () {
        searchTag();
    })
})
// 태그를 추가한다.
function addTag(value) {
    d.push(value); // 태그를 Object 안에 추가
    counter++; // counter 증가 삭제를 위한 delete-d 의 고유 id 가 된다.
}

// 최종적으로 서버에 넘길때 d 안에 있는 값을 array type 으로 만들어서 넘긴다.
function searchTag() {
    var result = Object.values(d)
    console.log(result)
    return Object.values(d)
        .filter(function (word) {
            return word !== "";
        });
}

$(document).on("click", ".delete-d", function (e) {
    var index = $(this).parent().index();
    $(this)
        .parent()
        .remove();
    d.splice(index, 1);
});