const eventSource = new EventSource("/stream-chat");
let completeResponse = '';

eventSource.onmessage = function(event) {
    if (event.data === null) {
        console.log("event data null");
        // If event data is null, close the event source
        eventSource.close();
        return;
    }

    let newData = event.data.replace(/\\n/g, '<br>'); // Replace \n with line breaks

    completeResponse += " " + newData;

    const responseContainer = document.getElementById("response-container");
    responseContainer.innerHTML = completeResponse; // Use innerHTML to render HTML tags

    // Scroll to the bottom of the container to see the latest messages
    responseContainer.scrollTop = responseContainer.scrollHeight;
};

eventSource.onerror = function(event) {
    console.log(event+"Error here")
    eventSource.close();
};

// Add event listener for the stop button
document.getElementById('stop-button').addEventListener('click', function() {
    eventSource.close();
});
