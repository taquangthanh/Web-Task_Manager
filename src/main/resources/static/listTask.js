$(document).ready(() => {
    const deleteBtn = $(".delete-btn")
    deleteBtn.each((index, item) => {
    item.addEventListener("click", addClickEvent)
})
})

    function addClickEvent(){
    const id = $(this).attr('id').split("-")[3]
    $(".popup").css("display", "block")
    $("#cancel-delete").click(() => {
    $(".popup").css("display", "none")
    $("#blur-all").css('background-color', 'transparent')
})
    $("#confirm-delete").attr('href', `/tasklist/delete/${id}`)
}
