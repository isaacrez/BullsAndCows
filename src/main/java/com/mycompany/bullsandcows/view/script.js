$(document).ready(function () {

    $("#new").on("click", function () {
        $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/api/begin',
                success: function (response) {
                    console.log("game #" + response + " created")
                    $("#gameId").text(`Game #${response}`)},
                error: function() {
                    console.log("Error, didn't run api req")
                }
            })
    })

    $("#enter").on("click", function () {
        event.preventDefault();
        if ($("#gameId").text() == "No game selected") {
            alert("You must start a game before you can guess a number. Select the start game button in the upper right corner to begin.")
        }
        else {
            var guessInput = $("#input").val();
            var gameId = $("#gameId").text().split("#")[1]

            if(guessInput.length == 4 && guessInput.match(/^[0-9]+$/)) {
                 $("#error").text("")
                 $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/api/guess',
                    contentType : "application/json",
                    data: JSON.stringify({"gameId" : gameId,
                                          "guess" : guessInput}),
                    success: function(response) {
                    console.log(response);
                    let exact = response.result.charAt(2)
                    let partial  = response.result.charAt(6)
                    $("#history").prepend(`<div class="grid-item l">${response.guess}</div>
                                            <div class="grid-item l">${exact}</div>
                                            <div class="grid-item l">${partial}</div>`)
                    if (exact == "4") {
                        alert(`Congrats! You have solved this game, the correct answer was ${guessInput}`)
                        $("#error").text("Correct!")
                        $("#error").css("color", "#FF304f")
                    }
                    },
                    error: function() {
                        console.log("Error, didn't run api req")
                    }
                })
            }
            else {
                $("#error").text("Invalid entry. All values must be numbers.")
            }
            $("#input").val("");
        }
    });
});