$(document).ready(function () {
    $("#new").on("click", function () {
        $.ajax({
                type: 'GET',
                url: '/begin',
                success: function(response) {
                    console.log(response)
                    $("#history").text(`<div class="grid-item">Guess:</div>
                                        <div class="grid-item">Partial:</div>
                                        <div class="grid-item">Exact:</div>`)
                },
                error: function() {
                    console.log("Error, didnt run api req")
                }
            })
    })

    $("#enter").on("click", function () {
        var guess = $("#1").val() +
                            $("#2").val() +
                            $("#3").val() +
                            $("#4").val();
        if(guess.length == 4 && guess.match(/^[0-9]+$/)) {
            $("#error").text("")
             $.ajax({
                        type: 'POST',
                        url: '/guess',
                        success: function(response) {
                            $("#exact-result").text("")
                            $("#partial-result").text("")
                            if (e = 4) {
                                $.ajax({
                                    type: 'POST',
                                    url: '/update',
                                    success: function(response) {
                                        $("#exact-result").text("");
                                        $("#partial-result").text("");

                                        let matches = response.result.split(":");

                                        $("#history").append(`<div class="grid-item">${result.guess}</div>
                                                              <div class="grid-item">${matches[1]}</div>
                                                              <div class="grid-item">${matches[3]}</div>`)
                                    },
                                    error: function() {
                                        console.log("Error, didnt run api req")
                                    }
                                })}
                        },
                        error: function() {
                            console.log("Error, didnt run api req")
                        }
                    })
        }
        else {
            $("#error").text("Invalid entry. All values must be numbers.")
        }
    });
});

//Make table of previous guesses
//Make call for is finished to alert you have guessed correctly
//Show history