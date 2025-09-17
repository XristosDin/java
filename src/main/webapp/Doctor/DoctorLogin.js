// Check if user is already logged in when page loads
document.addEventListener('DOMContentLoaded', function() {
    const loggedInUser = localStorage.getItem('doctorLoggedIn');
    if (loggedInUser) {
        window.location.href = 'DoctorHome.html';
    }
});

function doctorLogin() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (!username || !password) {
        alert('Please enter both username and password.');
        return;
    }

    const data = {
        "username": username,
        "password": password,
    }
    console.log(data);

    fetch('http://localhost:8080/doctors/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.text())
        .then(result => {
            console.log(result);  // Debugging
            if (result.includes("Login successful")) {
                // Store user info in localStorage
                localStorage.setItem('doctorLoggedIn', username);
                localStorage.setItem('userType', 'doctor');
                localStorage.setItem('loginTime', new Date().toString());

                // Redirect to home page
                window.location.href = 'DoctorHome.html';
            } else {
                alert('Invalid username or password.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred. Please try again later.');
        });
}

// Function to log out
function doctorLogout() {
    localStorage.removeItem('doctorLoggedIn');
    localStorage.removeItem('userType');
    localStorage.removeItem('loginTime');
    window.location.href = 'DoctorLogin.html';
}
