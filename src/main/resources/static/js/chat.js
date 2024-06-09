document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('question-form');
    const responseContainer = document.getElementById('response-container');
    const stopButton = document.getElementById('stop-button');

    let eventSource;
    let completeResponse = '';

    form.addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        // Clear any previous responses
        responseContainer.innerHTML = '';
        completeResponse = '';

        // Get the user's question from the input field
        const question = document.getElementById('question-input').value;

        // Close any existing EventSource to avoid multiple open connections
        if (eventSource) {
            eventSource.close();
        }

        // Create a new EventSource to receive streaming responses
        eventSource = new EventSource(`/stream-chat?question=${encodeURIComponent(question)}`);

        eventSource.onmessage = function(event) {
            if (event.data === null) {
                // Close the event source if no more data
                eventSource.close();
                return;
            }

            // Process the received data and replace line breaks with HTML tags
            let newData = event.data.replace(/\\n/g, '<br>');
            completeResponse += " " + newData;
            responseContainer.innerHTML = completeResponse; // Use innerHTML to render HTML tags
            responseContainer.scrollTop = responseContainer.scrollHeight; // Scroll to the latest message
        };

        eventSource.onerror = function(event) {
            console.error("EventSource failed:", event);
            eventSource.close();
        };
    });

    // Handle stopping the stream
    stopButton.addEventListener('click', function() {
        if (eventSource) {
            eventSource.close();
            responseContainer.innerHTML += '<p>Streaming stopped.</p>';
        }
    });
});
