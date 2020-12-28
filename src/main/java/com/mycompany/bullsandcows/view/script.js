$(document).ready(function () {

    $("#new").on("click", function () {
        $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/api/begin',
                success: function () { console.log("done")},
                error: function() {
                    console.log("Error, didn't run api req")
                }
            })
    })

    $("#enter").on("click", function () {

    var guessInput = $("#1").val() +
                      $("#2").val() +
                      $("#3").val() +
                      $("#4").val();

        if(guessInput.length == 4 && guessInput.match(/^[0-9]+$/)) {
            $("#error").text("")
             $.ajax({
                        type: 'POST',
                        url: 'http://localhost:8080/api/guess/',
                        data: JSON.stringify({gameId : $("#gameId").val(),
                                              guess : guessInput}),
                        success: function(response) {
                            $("#exact-result").text("")
                            $("#partial-result").text("")
                            if (e = 4) {
                                $.ajax({
                                    type: 'GET',
                                    url: 'http://localhost:8080/api/guess/',
                                    success: function(response) {
                                        $("#exact-result").text("");
                                        $("#partial-result").text("");
                                        let matches = response.result.split(":");
                                        $("#data").append(`<div class="grid-item">${result.guess}</div>
                                              <div class="grid-item">${matches[1]}</div>
                                              <div class="grid-item">${matches[3]}</div>`)
                                    },
                                    error: function() {
                                        console.log("Error, didnt run api req")
                                    }
                                })
                            }
                            else {
                                        $("#error").text("Invalid entry. All values must be numbers.")
                            }
                        },
                        error: function() {
                            console.log("Error, didnt run api req")
                        }
                    })
        }
    });
});
//Make call for is finished to alert you have guessed correctly