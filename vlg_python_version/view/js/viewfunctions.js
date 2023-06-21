// called on Generate button press
async function generate() {
    let userInput = document.getElementById('my-input').value;
    document.getElementById('message').innerText = "waiting...";
    let response = await eel.process_input(userInput)();
    document.getElementById('message').innerText = response;
}

// called on cancel button press
async function cancel_generation() {

}